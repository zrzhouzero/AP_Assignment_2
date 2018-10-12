package view;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class ReturnProperty extends SplitPane {

    private TextField customerIdField = new TextField();
    private Button searchForPropertyButton = new Button();
    private ComboBox<String> propertyIdBox = new ComboBox<>();
    private DatePicker datePicker = new DatePicker();
    private Button returnPropertyButton = new Button();
    private VBox returnDetailBox = new VBox();

    public ReturnProperty() {

        this.setPrefSize(670, 570);
        this.setOrientation(Orientation.HORIZONTAL);
        this.setDividerPositions(0.7);

        this.returnDetailBox.setMinWidth(190);
        this.returnDetailBox.setAlignment(Pos.TOP_LEFT);
        this.returnDetailBox.setPadding(new Insets(30, 10, 10, 10));
        this.returnDetailBox.setMaxWidth(330);

        VBox infoSection = new VBox();
        infoSection.setSpacing(20);
        infoSection.setPadding(new Insets(30, 20, 20, 20));
        infoSection.setPrefSize(320, 570);
        infoSection.setMinWidth(200);
        infoSection.setAlignment(Pos.TOP_CENTER);

        Label customerIdLabel = new Label();
        Label propertyIdLabel = new Label();
        Label returnDateLabel = new Label();

        customerIdLabel.setStyle("-fx-font-size: 14");
        propertyIdLabel.setStyle("-fx-font-size: 14");
        returnDateLabel.setStyle("-fx-font-size: 14");

        customerIdLabel.setText("Enter Customer ID");
        propertyIdLabel.setText("Select or Enter Property ID");
        returnDateLabel.setText("Select Return Date");

        customerIdField.setMaxSize(200, 30);
        searchForPropertyButton.setMaxSize(200, 30);
        propertyIdBox.setMaxSize(200, 30);
        datePicker.setMaxSize(200, 30);
        returnPropertyButton.setMaxSize(200, 30);

        customerIdField.setPromptText("Customer ID");
        searchForPropertyButton.setText("Search for Property ID");
        propertyIdBox.setPromptText("Property ID");
        datePicker.setPromptText("Select Return Date");
        returnPropertyButton.setText("Return Property");

        customerIdField.setStyle("-fx-font-size: 13");
        searchForPropertyButton.setStyle("-fx-font-size: 13");
        propertyIdBox.setStyle("-fx-font-size: 13");
        datePicker.setStyle("-fx-font-size: 13");
        returnPropertyButton.setStyle("-fx-font-size: 13");

        propertyIdBox.setEditable(false);

        propertyIdBox.setPrefSize(200, 30);
        datePicker.setPrefSize(200, 30);

        infoSection.getChildren().addAll(customerIdLabel, this.customerIdField, this.searchForPropertyButton, propertyIdLabel, this.propertyIdBox, returnDateLabel, this.datePicker, this.returnPropertyButton);

        this.getItems().addAll(infoSection, this.returnDetailBox);

    }

    public TextField getCustomerIdField() {
        return customerIdField;
    }

    public Button getSearchForPropertyButton() {
        return searchForPropertyButton;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Button getReturnPropertyButton() {
        return returnPropertyButton;
    }

    public VBox getReturnDetailBox() {
        return returnDetailBox;
    }

    public ComboBox<String> getPropertyIdBox() {
        return propertyIdBox;
    }

}