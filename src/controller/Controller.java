package controller;

import exceptions.*;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utilities.*;
import model.*;
import view.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {

    private FlexiSystem flexiSystem = new FlexiSystem();
    private HomePage mainView = new HomePage();
    private DatabaseProcessor databaseProcessor = new DatabaseProcessor();

    private AddProperty addPropertyPage = new AddProperty();
    private RentProperty rentPropertyPage = new RentProperty();
    private ReturnProperty returnPropertyPage = new ReturnProperty();
    private PerformMaintenance performMaintenancePage = new PerformMaintenance();
    private CompleteMaintenance completeMaintenancePage = new CompleteMaintenance();
    private DisplayAllProperties displayAllPropertiesPage;
    private LongDescriptionEditor longDescriptionEditor = new LongDescriptionEditor();

    private File file;

    public Controller() {

        this.flexiSystem.setRentalProperties(this.databaseProcessor.retrieveData());
        displayAllPropertiesPage = new DisplayAllProperties(this.flexiSystem.getRentalProperties());

        this.file = null;

        this.displayAllPropertiesPage.setUp();
        this.mainView.getDisplayPane().getChildren().add(this.displayAllPropertiesPage);

        this.mainView.getAddProperty().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.addPropertyPage);
        });

        this.mainView.getRentProperty().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.rentPropertyPage);
        });

        this.mainView.getReturnProperty().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.returnPropertyPage);
        });

        this.mainView.getPerformMaintenance().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.performMaintenancePage);
        });

        this.mainView.getCompleteMaintenance().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.completeMaintenancePage);
        });

        this.mainView.getDisplayAllProperties().setOnAction(e -> {
            this.mainView.getDisplayPane().getChildren().clear();
            this.mainView.getDisplayPane().getChildren().add(this.displayAllPropertiesPage);
        });

        this.mainView.getChangeDescription().setOnAction(e -> this.longDescriptionEditor.show());

        this.mainView.getMenuItemImport().setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            this.file = fileChooser.showOpenDialog(new Stage());
            readImportFile(file);
        });

        this.mainView.getMenuItemExport().setOnAction(e -> exportFile());

        this.addPropertyPage.getAddApartment().setOnAction(e -> this.addApartment());
        this.addPropertyPage.getAddPremiumSuite().setOnAction(e -> this.addPremiumSuite());
        this.rentPropertyPage.getRentPropertyButton().setOnAction(e -> this.rentProperty());
        this.rentPropertyPage.getReloadListButton().setOnAction(e -> this.reloadAvailablePropertyIdForRentBox());
        this.returnPropertyPage.getSearchForPropertyButton().setOnAction(e -> this.searchForPropertyIdInReturnPropertyPage());
        this.returnPropertyPage.getReturnPropertyButton().setOnAction(e -> this.returnProperty());
        this.performMaintenancePage.getReloadListButton().setOnAction(e -> this.reloadAvailablePropertyIdForMaintenanceBox());
        this.performMaintenancePage.getPerformMaintenanceButton().setOnAction(e -> this.performMaintenance());
        this.completeMaintenancePage.getReloadIdListButton().setOnAction(e -> this.reloadPropertyIdUnderMaintenance());
        this.completeMaintenancePage.getCompleteMaintenanceButton().setOnAction(e -> this.completeMaintenance());

        this.longDescriptionEditor.getSaveButton().setOnAction(e -> {
            String propertyId = this.longDescriptionEditor.getPropertyId();
            String description = this.longDescriptionEditor.getLongDescription();
            int index = this.flexiSystem.findPropertyByPropertyId(propertyId);
            if (index < 0) {
                new TipWindow("System Error", "Property ID not Found");
                return;
            }
            this.flexiSystem.getRentalProperties().get(index).setLongDescription(description);
            this.databaseProcessor.updatePropertyLongDescription(propertyId, description);
            new TipWindow("Congratulations", "The description of " + this.flexiSystem.getRentalProperties().get(index).getPropertyId() + " is changed!");
        });

    }

    private void addApartment() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String propertyId;
        String streetNumber;
        String streetName;
        String suburb;
        int numberOfBedrooms;

        streetNumber = this.addPropertyPage.getStreetNumField().getText();
        streetName = this.addPropertyPage.getStreetNameField().getText();
        suburb = this.addPropertyPage.getSuburbField().getText();

        if (this.validateAddingPropertyAddressInfo(streetNumber, streetName, suburb)) {
            return;
        }

        try {
            numberOfBedrooms = Integer.parseInt(this.addPropertyPage.getNumberOfBedroom().getText());
            if (isEmptyItem(this.addPropertyPage.getNumberOfBedroom().getText())) {
                throw new EmptyItemException();
            }
            if (numberOfBedrooms < 1 || numberOfBedrooms > 3) {
                throw new BedroomsNumberException();
            }
        }
        catch (NumberFormatException nfe) {
            tipBoxContent = "Please enter a number for bedroom number!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        catch (BedroomsNumberException bne) {
            tipBoxContent = "The number of bedrooms should be between 1 and 3!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        catch (EmptyItemException eie) {
            tipBoxContent = "Please enter number of bedrooms";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        try {
            propertyId = "A_" + streetNumber + streetName.toUpperCase().substring(0,2) + suburb.toUpperCase().substring(0,2);
        }
        catch (StringIndexOutOfBoundsException se) {
            tipBoxContent = "Address is not long enough!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        if (this.flexiSystem.findPropertyByPropertyId(propertyId) >= 0) {
            tipBoxContent = "This Apartment is already in the system!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        if (this.addPropertyPage.getSmallImage() == null || this.addPropertyPage.getLargeImage() == null) {
            this.flexiSystem.addProperty(new Apartment(propertyId, streetNumber, streetName, suburb, numberOfBedrooms));
            this.databaseProcessor.insertPropertyRow(new Apartment(propertyId, streetNumber, streetName, suburb, numberOfBedrooms));
            tipBoxContent = "Apartment " + propertyId + " is added to the system!";
            tipBoxTitle = "Congratulations";
            new TipWindow(tipBoxTitle, tipBoxContent);
        }
        else {
            this.flexiSystem.addProperty(new Apartment(propertyId, streetNumber, streetName, suburb, numberOfBedrooms, this.addPropertyPage.getSmallImage(), this.addPropertyPage.getLargeImage()));
            this.databaseProcessor.insertPropertyRow(new Apartment(propertyId, streetNumber, streetName, suburb, numberOfBedrooms, this.addPropertyPage.getSmallImage(), this.addPropertyPage.getLargeImage()));
            tipBoxContent = "Apartment " + propertyId + " is added to the system!";
            tipBoxTitle = "Congratulations";
            new TipWindow(tipBoxTitle, tipBoxContent);
        }
    }

    private void addPremiumSuite() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String propertyId;
        String streetNumber;
        String streetName;
        String suburb;

        streetNumber = this.addPropertyPage.getStreetNumField().getText();
        streetName = this.addPropertyPage.getStreetNameField().getText();
        suburb = this.addPropertyPage.getSuburbField().getText();

        if (this.validateAddingPropertyAddressInfo(streetNumber, streetName, suburb)) {
            return;
        }

        try {
            propertyId = "S_" + streetNumber + streetName.toUpperCase().substring(0,2) + suburb.toUpperCase().substring(0,2);
        }
        catch (StringIndexOutOfBoundsException se) {
            tipBoxContent = "Address is not long enough!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        if (this.flexiSystem.findPropertyByPropertyId(propertyId) >= 0) {
            tipBoxContent = "This Premium Suite is already in the system!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        if (this.addPropertyPage.getSmallImage() == null || this.addPropertyPage.getLargeImage() == null) {
            this.flexiSystem.addProperty(new PremiumSuite(propertyId, streetNumber, streetName, suburb));
            this.databaseProcessor.insertPropertyRow(new PremiumSuite(propertyId, streetNumber, streetName, suburb));
            tipBoxContent = "Premium Suite " + propertyId + " is added to system!";
            tipBoxTitle = "Congratulations";
            new TipWindow(tipBoxTitle, tipBoxContent);
        }
        else {
            this.flexiSystem.addProperty(new PremiumSuite(propertyId, streetNumber, streetName, suburb, this.addPropertyPage.getSmallImage(), this.addPropertyPage.getLargeImage()));
            this.databaseProcessor.insertPropertyRow(new PremiumSuite(propertyId, streetNumber, streetName, suburb, this.addPropertyPage.getSmallImage(), this.addPropertyPage.getLargeImage()));
            tipBoxContent = "Premium Suite " + propertyId + " is added to system!";
            tipBoxTitle = "Congratulations";
            new TipWindow(tipBoxTitle, tipBoxContent);
        }
    }

    private boolean validateAddingPropertyAddressInfo(String streetNumber, String streetName, String suburb) {
        String tipBoxContent;
        String tipBoxTitle = "System Error";

        try {
            if (isEmptyItem(streetNumber)) {
                throw new EmptyItemException();
            }
            Integer.valueOf(streetNumber);
        }
        catch (NumberFormatException nfe) {
            tipBoxContent = "Please enter a correct street number!";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return true;
        }
        catch (EmptyItemException eie) {
            tipBoxContent = "Please enter street number";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return true;
        }

        try {
            if (isEmptyItem(streetName)) {
                throw new EmptyItemException();
            }
        }
        catch (EmptyItemException eie) {
            tipBoxContent = "Please enter street name";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return true;
        }

        try {
            if (isEmptyItem(suburb)) {
                throw new EmptyItemException();
            }
        }
        catch (EmptyItemException eie) {
            tipBoxContent = "Please enter suburb";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return true;
        }
        return false;
    }

    private void rentProperty() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String propertyId = this.rentPropertyPage.getPropertyIdField().getText();
        tipBoxContent = "Please enter property ID";
        if (this.isEmptyStringBox(propertyId, tipBoxTitle, tipBoxContent)) {
            return;
        }

        String customerId = this.rentPropertyPage.getCustomerIdField().getText();
        tipBoxContent = "Please enter customer ID";
        if (this.isEmptyStringBox(customerId, tipBoxTitle, tipBoxContent)) {
            return;
        }

        String parseCheckInDate;
        try {
            parseCheckInDate = this.rentPropertyPage.getDatePicker().getValue().toString();
        }
        catch (NullPointerException npe) {
            tipBoxContent = "Please select check-in date";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        String parseRentalLength = this.rentPropertyPage.getRentalLengthField().getText();

        DateTime checkInDate;
        int year = Integer.parseInt(parseCheckInDate.substring(0, 4));
        int month = Integer.parseInt(parseCheckInDate.substring(5, 7));
        int day = Integer.parseInt(parseCheckInDate.substring(8, 10));
        checkInDate = new DateTime(day, month, year);

        int rentalLength;
        try {
            if (isEmptyItem(parseRentalLength)) {
                throw new EmptyItemException();
            }
            rentalLength = Integer.parseInt(parseRentalLength);
        }
        catch (EmptyItemException eie) {
            tipBoxContent = "Please enter rental length";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        catch (NumberFormatException nfe) {
            tipBoxContent = "Please enter a number for rental length";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }
        this.flexiSystem.rentProperty(propertyId, customerId, checkInDate, rentalLength);

    }

    private void reloadAvailablePropertyIdForRentBox() {
        this.rentPropertyPage.resetAvailablePropertyIdBox();
        for (RentalProperty rentalProperty : this.flexiSystem.getRentalProperties()) {
            if (rentalProperty.getPropertyStatus() == RentalProperty.PropertyStatus.AVAILABLE) {
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefHeight(20);
                textArea.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
                textArea.setText(rentalProperty.getPropertyId());
                this.rentPropertyPage.getAvailablePropertyIdBox().getChildren().add(textArea);
            }
        }
    }

    private void reloadAvailablePropertyIdForMaintenanceBox() {
        this.performMaintenancePage.resetAvailablePropertyIdBox();
        for (RentalProperty rentalProperty : this.flexiSystem.getRentalProperties()) {
            if (rentalProperty.getPropertyStatus() == RentalProperty.PropertyStatus.AVAILABLE) {
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefHeight(20);
                textArea.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
                textArea.setText(rentalProperty.getPropertyId());
                this.performMaintenancePage.getAvailablePropertyIdBox().getChildren().add(textArea);
            }
        }
    }

    private void reloadPropertyIdUnderMaintenance() {
        this.completeMaintenancePage.resetUnderMaintenancePropertyIdBox();
        for (RentalProperty rentalProperty : this.flexiSystem.getRentalProperties()) {
            if (rentalProperty.getPropertyStatus() == RentalProperty.PropertyStatus.UNDERMAINTENANCE) {
                TextArea textArea = new TextArea();
                textArea.setEditable(false);
                textArea.setWrapText(true);
                textArea.setPrefHeight(20);
                textArea.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
                textArea.setText(rentalProperty.getPropertyId());
                this.completeMaintenancePage.getUnderMaintenanceIdBox().getChildren().add(textArea);
            }
        }
    }

    private void returnProperty() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String customerId = this.returnPropertyPage.getCustomerIdField().getText();
        tipBoxContent = "Please enter customer ID";
        if (this.isEmptyStringBox(customerId, tipBoxTitle, tipBoxContent)) {
            return;
        }

        String propertyId;
        try {
            propertyId = this.returnPropertyPage.getPropertyIdBox().getValue();
            if (propertyId.equals("")) {
                throw new EmptyItemException();
            }
        }
        catch (Exception npe) {
            tipBoxContent = "Please select a property ID or type in a valid customer ID";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        String parseCheckOutDate;
        try {
            parseCheckOutDate = this.returnPropertyPage.getDatePicker().getValue().toString();
        }
        catch (NullPointerException npe) {
            tipBoxContent = "Please select check-out date";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        DateTime checkOutDate;
        int year = Integer.parseInt(parseCheckOutDate.substring(0, 4));
        int month = Integer.parseInt(parseCheckOutDate.substring(5, 7));
        int day = Integer.parseInt(parseCheckOutDate.substring(8, 10));
        checkOutDate = new DateTime(day, month, year);

        if (!this.flexiSystem.returnProperty(customerId, propertyId, checkOutDate)) {
            return;
        }

        this.returnPropertyPage.getReturnDetailBox().getChildren().clear();
        Label label = new Label();
        label.setWrapText(true);
        label.setText(this.flexiSystem.getRentalProperties().get(this.flexiSystem.findPropertyByPropertyId(propertyId)).getLatestRecord().recordDetails());
        this.returnPropertyPage.getReturnDetailBox().getChildren().add(label);

    }

    private void searchForPropertyIdInReturnPropertyPage() {
        this.returnPropertyPage.getPropertyIdBox().getItems().clear();
        String tipBoxContent = "Please enter customer ID";
        String tipBoxTitle = "System Error";
        String customerId = this.returnPropertyPage.getCustomerIdField().getText();
        if (this.isEmptyStringBox(customerId, tipBoxTitle, tipBoxContent)) {
            return;
        }
        if (this.flexiSystem.findPropertyByCustomerIdForReturn(customerId).size() == 0) {
            new TipWindow("System Error", "This customer has no rent records currently");
            return;
        }
        for (int i: this.flexiSystem.findPropertyByCustomerIdForReturn(customerId)) {
            this.returnPropertyPage.getPropertyIdBox().getItems().add(this.flexiSystem.getRentalProperties().get(i).getPropertyId());
        }
    }

    private void performMaintenance() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String propertyId = performMaintenancePage.getPropertyIdField().getText();
        tipBoxContent = "Please enter property ID";
        if (this.isEmptyStringBox(propertyId, tipBoxTitle, tipBoxContent)) {
            return;
        }

        String parseCheckOutDate;
        try {
            parseCheckOutDate = this.performMaintenancePage.getDatePicker().getValue().toString();
        }
        catch (NullPointerException npe) {
            tipBoxContent = "Please select check-out date";
            new TipWindow(tipBoxTitle, tipBoxContent);
            return;
        }

        DateTime maintainDate;
        int year = Integer.parseInt(parseCheckOutDate.substring(0, 4));
        int month = Integer.parseInt(parseCheckOutDate.substring(5, 7));
        int day = Integer.parseInt(parseCheckOutDate.substring(8, 10));
        maintainDate = new DateTime(day, month, year);

        this.flexiSystem.performMaintenance(propertyId, maintainDate);

    }

    private void completeMaintenance() {
        String tipBoxTitle = "System Error";
        String tipBoxContent;

        String propertyId = completeMaintenancePage.getPropertyIdField().getText();
        tipBoxContent = "Please enter property ID";
        if (this.isEmptyStringBox(propertyId, tipBoxTitle, tipBoxContent)) {
            return;
        }

        this.flexiSystem.completeMaintenance(propertyId);

    }

    public VBox getMainView() {
        return this.mainView;
    }

    private boolean isEmptyItem(String s) {
        return s.equals("");
    }

    private void readImportFile(File file) {
        ArrayList<String> convertList = new ArrayList<>();
        ArrayList<RentalProperty> rentalProperties = new ArrayList<>();
        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String str = input.nextLine();
                if (!str.equals("")) {
                    convertList.add(str);
                }
            }
        }
        catch (FileNotFoundException e) {
            new TipWindow("System Error", "The file is not found!");
            return;
        }
        catch (NullPointerException e) {
            return;
        }
        for (String s: convertList) {
            String convertLine[] = s.split(":");
            try {
                if (convertLine[4].equals("Premium Suite")) {
                    File file1 = new File(convertLine[8]);
                    File file2 = new File(convertLine[9]);
                    Image image1 = new Image(file1.toURI().toURL().toExternalForm());
                    Image image2 = new Image(file2.toURI().toURL().toExternalForm());
                    PremiumSuite ps = new PremiumSuite(convertLine[0], convertLine[1], convertLine[2], convertLine[3], image1, image2);
                    ps.setLongDescription(convertLine[10]);
                    rentalProperties.add(ps);
                }
                else {
                    File file1 = new File(convertLine[8]);
                    File file2 = new File(convertLine[9]);
                    Image image1 = new Image(file1.toURI().toURL().toExternalForm());
                    Image image2 = new Image(file2.toURI().toURL().toExternalForm());
                    Apartment a = new Apartment(convertLine[0], convertLine[1], convertLine[2], convertLine[3], Integer.parseInt(convertLine[7]), image1, image2);
                    a.setLongDescription(convertLine[10]);
                    rentalProperties.add(a);
                }
            }
            catch (Exception e) {
                new TipWindow("Error", "Incompatible File");
                return;
            }
        }
        int times = 0;
        for (RentalProperty r: rentalProperties) {
            if (this.flexiSystem.findPropertyByPropertyId(r.getPropertyId()) < 0) {
                this.flexiSystem.getRentalProperties().add(r);
                times++;
            }
        }
        new TipWindow("Congratulation", "Successfully imported " + times + " item(s).");

    }

    private void exportFile() {
        FileChooser fileChooser = new FileChooser();
        File exportFile = fileChooser.showSaveDialog(new Stage());
        try {
            PrintWriter output = new PrintWriter(exportFile);
            int end = this.flexiSystem.getRentalProperties().size();
            for (int i = 0; i < end; i++) {
                output.write(this.flexiSystem.getRentalProperties().get(i).toString() + "\n");
                for (RentalRecord record: this.flexiSystem.getRentalProperties().get(i).getRentalRecord()) {
                    output.write(record.toString() + "\n");
                }
            }
            output.close();
            new TipWindow("Congratulation", "Export succeeded.");
        } catch (FileNotFoundException e) {
            new TipWindow("Error", "Cannot find the File");
        } catch (NullPointerException ignored) {
        }
    }

    private boolean isEmptyStringBox(String s, String tipBoxTitle, String tipBoxContent) {
        try {
            if (s.equals("")) {
                throw new EmptyItemException();
            }
        }
        catch (EmptyItemException eie) {
            new TipWindow(tipBoxTitle, tipBoxContent);
            return true;
        }
        return false;
    }

}
