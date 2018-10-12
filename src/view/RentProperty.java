package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class RentProperty extends SplitPane {

    private TextField propertyIdField = new TextField();
    private TextField customerIdField = new TextField();
    private DatePicker datePicker = new DatePicker();
    private TextField rentalLengthField = new TextField();
    private Button rentPropertyButton = new Button();
    private Button reloadListButton = new Button();
    private VBox availablePropertyIdBox = new VBox();

    public RentProperty() {

        this.setPrefSize(670, 570);
        this.setDividerPositions(0.7);

        VBox infoSection = new VBox();
        ScrollPane idBox = new ScrollPane();

        infoSection.setPrefSize(470, 570);
        infoSection.setPadding(new Insets(30, 20, 20, 20));
        infoSection.setSpacing(20);
        infoSection.setAlignment(Pos.TOP_CENTER);
        infoSection.setMinWidth(300);

        Label propertyIdLabel = new Label();
        Label customerIdLabel = new Label();
        Label checkInDateLabel = new Label();
        Label rentalLengthLabel = new Label();

        this.propertyIdField.setMaxSize(200, 30);
        this.propertyIdField.setPromptText("Property ID");
        this.propertyIdField.setStyle("-fx-font-size: 13");

        this.customerIdField.setMaxSize(200, 30);
        this.customerIdField.setPromptText("Customer ID");
        this.customerIdField.setStyle("-fx-font-size: 13");

        this.datePicker.setMaxSize(200, 30);
        this.datePicker.setPromptText("Check-in Date");
        this.datePicker.setStyle("-fx-font-size: 13");
        this.datePicker.setEditable(false);

        this.rentalLengthField.setMaxSize(200, 30);
        this.rentalLengthField.setPromptText("Rental Length");
        this.rentalLengthField.setStyle("-fx-font-size: 13");

        this.rentPropertyButton.setText("Rent Property");
        this.reloadListButton.setText("Reload ID List");

        propertyIdLabel.setStyle("-fx-font-size: 14");
        propertyIdLabel.setText("Enter Property ID Below");

        customerIdLabel.setStyle("-fx-font-size: 14");
        customerIdLabel.setText("Enter Customer ID Below");

        checkInDateLabel.setStyle("-fx-font-size: 14");
        checkInDateLabel.setText("Enter Check-in Date Below");

        rentalLengthLabel.setStyle("-fx-font-size: 14");
        rentalLengthLabel.setText("Enter Rental Length Below");

        this.availablePropertyIdBox.setAlignment(Pos.TOP_LEFT);
        this.availablePropertyIdBox.setSpacing(10);
        this.availablePropertyIdBox.setPadding(new Insets(20, 10, 10, 10));
        this.availablePropertyIdBox.setPrefWidth(180);

        idBox.setPrefSize(190, 570);
        idBox.setMinWidth(190);
        this.resetAvailablePropertyIdBox();
        idBox.setContent(this.availablePropertyIdBox);

        infoSection.getChildren().addAll(propertyIdLabel, this.propertyIdField, customerIdLabel, this.customerIdField, checkInDateLabel, this.datePicker, rentalLengthLabel, this.rentalLengthField, this.rentPropertyButton, this.reloadListButton);

        this.getItems().addAll(infoSection, idBox);

    }

    public void resetAvailablePropertyIdBox() {
        this.availablePropertyIdBox.getChildren().clear();
        Label label = new Label();
        label.setText("Available Property ID");
        label.setStyle("-fx-font-size: 14");
        this.availablePropertyIdBox.getChildren().add(label);
    }

    public Button getReloadListButton() {
        return reloadListButton;
    }

    public Button getRentPropertyButton() {
        return this.rentPropertyButton;
    }

    public DatePicker getDatePicker() {
        return this.datePicker;
    }

    public TextField getCustomerIdField() {
        return this.customerIdField;
    }

    public TextField getPropertyIdField() {
        return this.propertyIdField;
    }

    public TextField getRentalLengthField() {
        return this.rentalLengthField;
    }

    public VBox getAvailablePropertyIdBox() {
        return this.availablePropertyIdBox;
    }
}
