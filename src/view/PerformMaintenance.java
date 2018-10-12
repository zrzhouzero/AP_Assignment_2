package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class PerformMaintenance extends SplitPane {

    private TextField propertyIdField = new TextField();
    private DatePicker datePicker = new DatePicker();
    private Button performMaintenanceButton = new Button();
    private VBox availablePropertyIdBox = new VBox();
    private Button reloadListButton = new Button();

    public PerformMaintenance() {

        this.setPrefSize(670, 570);
        this.setDividerPositions(0.7);

        VBox infoSection = new VBox();
        ScrollPane idBox = new ScrollPane();

        infoSection.setMinWidth(300);
        idBox.setMinWidth(190);
        infoSection.setPrefSize(470, 570);
        idBox.setPrefSize(190, 568);
        infoSection.setAlignment(Pos.TOP_CENTER);
        infoSection.setPadding(new Insets(30, 20, 20, 20));
        infoSection.setSpacing(20);
        this.availablePropertyIdBox.setSpacing(10);
        this.availablePropertyIdBox.setPadding(new Insets(20, 10, 10, 10));
        this.availablePropertyIdBox.setAlignment(Pos.TOP_LEFT);
        this.availablePropertyIdBox.setPrefWidth(180);

        Label propertyIdLabel = new Label();
        Label maintenanceStartDateLabel = new Label();

        propertyIdLabel.setStyle("-fx-font-size: 14");
        propertyIdLabel.setText("Enter Property ID");

        maintenanceStartDateLabel.setStyle("-fx-font-size: 14");
        maintenanceStartDateLabel.setText("Select Maintenance Start Date");

        this.propertyIdField.setMaxSize(200, 30);
        this.propertyIdField.setPromptText("Property ID");
        this.propertyIdField.setStyle("-fx-font-size: 13");

        this.datePicker.setMaxSize(200, 30);
        this.datePicker.setPromptText("Maintenance Date");
        this.datePicker.setStyle("-fx-font-size: 13");

        this.performMaintenanceButton.setText("Perform Maintenance");
        this.performMaintenanceButton.setPrefHeight(30);

        this.reloadListButton.setText("Reload ID List");

        this.resetAvailablePropertyIdBox();

        idBox.setContent(this.availablePropertyIdBox);
        infoSection.getChildren().addAll(propertyIdLabel, this.propertyIdField, maintenanceStartDateLabel, this.datePicker, this.performMaintenanceButton, this.reloadListButton);

        this.getItems().addAll(infoSection, idBox);

    }

    public DatePicker getDatePicker() {
        return this.datePicker;
    }

    public VBox getAvailablePropertyIdBox() {
        return this.availablePropertyIdBox;
    }

    public TextField getPropertyIdField() {
        return this.propertyIdField;
    }

    public Button getPerformMaintenanceButton() {
        return this.performMaintenanceButton;
    }

    public Button getReloadListButton() {
        return this.reloadListButton;
    }

    public void resetAvailablePropertyIdBox() {
        this.availablePropertyIdBox.getChildren().clear();
        Label label = new Label();
        label.setText("Available Property ID");
        label.setStyle("-fx-font-size: 14");
        this.availablePropertyIdBox.getChildren().add(label);
    }

}
