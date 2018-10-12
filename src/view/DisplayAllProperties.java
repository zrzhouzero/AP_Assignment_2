package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.DatabaseProcessor;
import model.RentalProperty;

import java.util.ArrayList;

public class DisplayAllProperties extends VBox {

    private ArrayList<RentalProperty> rentalProperties = new ArrayList<>();
    private ComboBox<String> propertyTypeComboBox = new ComboBox<>();
    private ComboBox<String> numOfBedroomsComboBox = new ComboBox<>();
    private ComboBox<String> statusComboBox = new ComboBox<>();
    private ComboBox<String> suburbComboBox = new ComboBox<>();
    private Button searchButton = new Button();
    private Button resetButton = new Button();
    private VBox propertyDisplaySection = new VBox();

    public DisplayAllProperties(ArrayList<RentalProperty> rentalProperties) {

        this.rentalProperties = rentalProperties;
        this.setPrefSize(670, 570);

        HBox buttonLine1 = new HBox();
        HBox buttonLine2 = new HBox();
        ScrollPane propertyBox = new ScrollPane();

        buttonLine1.setMaxHeight(50);
        buttonLine1.setPrefWidth(670);
        buttonLine1.setPadding(new Insets(10, 10, 10, 10));
        buttonLine1.setAlignment(Pos.CENTER);
        buttonLine1.setSpacing(10);

        buttonLine2.setMaxHeight(40);
        buttonLine2.setPrefWidth(670);
        buttonLine2.setPadding(new Insets(0, 10, 10, 10));
        buttonLine2.setAlignment(Pos.TOP_CENTER);
        buttonLine2.setSpacing(10);

        propertyBox.setPrefSize(670, 480);

        this.propertyTypeComboBox.setPromptText("Property Type");
        this.propertyTypeComboBox.getItems().addAll("Apartment", "Premium Suite", "All Property Types");
        this.propertyTypeComboBox.setPrefHeight(30);
        this.propertyTypeComboBox.setStyle("-fx-font-size: 10");

        this.numOfBedroomsComboBox.setPromptText("Number of Bedrooms");
        this.numOfBedroomsComboBox.getItems().addAll("1", "2", "3", "All Numbers of Bedrooms");
        this.numOfBedroomsComboBox.setPrefHeight(30);
        this.numOfBedroomsComboBox.setStyle("-fx-font-size: 10");

        this.statusComboBox.setPromptText("Status");
        this.statusComboBox.getItems().addAll("Available", "Under Maintenance", "Rented", "All Status");
        this.statusComboBox.setPrefHeight(30);
        this.statusComboBox.setStyle("-fx-font-size: 10");

        this.suburbComboBox.setPromptText("Suburb");
        this.suburbComboBox.getItems().addAll("Suburb 1", "Suburb 2", "All Suburbs");
        this.suburbComboBox.setEditable(true);
        this.suburbComboBox.setPrefHeight(30);
        this.suburbComboBox.setStyle("-fx-font-size: 10");

        this.searchButton.setPrefSize(80, 30);
        this.searchButton.setText("Search");
        this.searchButton.setStyle("-fx-font-size: 12");

        this.resetButton.setPrefSize(80, 30);
        this.resetButton.setText("Reset");
        this.resetButton.setStyle("-fx-font-size: 12");

        this.propertyDisplaySection.setPrefWidth(650);
        this.propertyDisplaySection.setPadding(new Insets(10, 10, 10, 10));
        this.propertyDisplaySection.setSpacing(10);
        this.propertyDisplaySection.setAlignment(Pos.TOP_LEFT);

        for (RentalProperty rentalProperty: this.rentalProperties) {
            PropertyBar propertyBar = new PropertyBar(rentalProperty);
            this.propertyDisplaySection.getChildren().add(propertyBar);
        }

        this.searchButton.setOnAction(e -> {

            @SuppressWarnings("unchecked")
            ArrayList<RentalProperty> searchList = (ArrayList<RentalProperty>) this.rentalProperties.clone();

            int i = 0;
            int end = searchList.size();
            String propertyType = this.propertyTypeComboBox.getValue();
            if (propertyType == null) {
                propertyType = "All Property Types";
            }
            String nob = this.numOfBedroomsComboBox.getValue();
            if (nob == null) {
                nob = "All Numbers of Bedrooms";
            }
            String status = this.statusComboBox.getValue();
            if (status == null) {
                status = "All Status";
            }
            String suburb = this.suburbComboBox.getValue();
            if (suburb == null) {
                suburb = "All Suburbs";
            }
            if (suburb.equals("")) {
                suburb = "All Suburbs";
            }

            while (i < end) {

                boolean ifRemove = false;

                if (!propertyType.equals("All Property Types")) {
                    if (!propertyType.equals(searchList.get(i).getPropertyType())) {
                        ifRemove = true;
                    }
                }

                if (!ifRemove) {
                    if (!nob.equals("All Numbers of Bedrooms")) {
                        if (Integer.valueOf(nob) != searchList.get(i).getNumberOfBedrooms()) {
                            ifRemove = true;
                        }
                    }
                }

                if (!ifRemove) {
                    if (!status.equals("All Status")) {
                        String statusString;
                        if (searchList.get(i).getPropertyStatus() == RentalProperty.PropertyStatus.AVAILABLE) {
                            statusString = "Available";
                        }
                        else if (searchList.get(i).getPropertyStatus() == RentalProperty.PropertyStatus.UNDERMAINTENANCE) {
                            statusString = "Under Maintenance";
                        }
                        else {
                            statusString = "Rented";
                        }
                        if (!status.equals(statusString)) {
                            ifRemove = true;
                        }
                    }
                }

                if (!ifRemove) {
                    if (!suburb.equals("All Suburbs")) {
                        if (!suburb.toUpperCase().equals(searchList.get(i).getSuburb().toUpperCase())) {
                            ifRemove = true;
                        }
                    }
                }

                if (ifRemove) {
                    searchList.remove(i);
                    end--;
                }
                else {
                    i++;
                }
            }
            this.propertyDisplaySection.getChildren().clear();
            for (RentalProperty rentalProperty: searchList) {
                PropertyBar propertyBar = new PropertyBar(rentalProperty);
                this.propertyDisplaySection.getChildren().add(propertyBar);
            }
        });

        this.resetButton.setOnAction(e -> {
            this.propertyDisplaySection.getChildren().clear();
            DatabaseProcessor databaseProcessor = new DatabaseProcessor();
            this.rentalProperties = databaseProcessor.retrieveData();
            for (RentalProperty rentalProperty: this.rentalProperties) {
                PropertyBar propertyBar = new PropertyBar(rentalProperty);
                this.propertyDisplaySection.getChildren().add(propertyBar);
            }
        });

        buttonLine1.getChildren().addAll(this.propertyTypeComboBox, this.numOfBedroomsComboBox, this.statusComboBox, this.suburbComboBox);
        buttonLine2.getChildren().addAll(this.searchButton, this.resetButton);
        propertyBox.setContent(this.propertyDisplaySection);

        this.getChildren().addAll(buttonLine1, buttonLine2, propertyBox);

    }

    public void setUp() {
        this.propertyDisplaySection.getChildren().clear();
        for (RentalProperty rentalProperty: this.rentalProperties) {
            PropertyBar propertyBar = new PropertyBar(rentalProperty);
            this.propertyDisplaySection.getChildren().add(propertyBar);
        }
    }
}
