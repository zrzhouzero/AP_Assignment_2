package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class CompleteMaintenance extends SplitPane {

    private TextField propertyIdField = new TextField();
    private Button completeMaintenanceButton = new Button();
    private VBox underMaintenanceIdBox = new VBox();
    private Button reloadIdListButton = new Button();

    public CompleteMaintenance() {

        VBox infoSection = new VBox();
        ScrollPane idBox = new ScrollPane();

        Label propertyIdLabel = new Label();

        this.setPrefSize(670, 570);
        this.setDividerPositions(0.7);

        infoSection.setPrefSize(470, 570);
        infoSection.setAlignment(Pos.TOP_CENTER);
        infoSection.setMinWidth(200);
        infoSection.setPadding(new Insets(30, 20, 20, 20));
        infoSection.setSpacing(20);

        idBox.setPrefSize(190, 570);
        idBox.setMinWidth(190);

        propertyIdLabel.setText("Enter Property ID");
        propertyIdLabel.setStyle("-fx-font-size: 14");

        this.propertyIdField.setPromptText("Property ID");
        this.propertyIdField.setMaxSize(200, 30);
        this.propertyIdField.setStyle("-fx-font-size: 13");

        this.completeMaintenanceButton.setText("Complete Maintenance");
        this.completeMaintenanceButton.setPrefHeight(30);

        this.underMaintenanceIdBox.setAlignment(Pos.TOP_LEFT);
        this.underMaintenanceIdBox.setSpacing(10);
        this.underMaintenanceIdBox.setPadding(new Insets(20, 10, 10, 10));
        this.underMaintenanceIdBox.setMinWidth(180);

        this.reloadIdListButton.setText("Reload ID List");

        this.resetUnderMaintenancePropertyIdBox();

        infoSection.getChildren().addAll(propertyIdLabel, this.propertyIdField, this.completeMaintenanceButton, this.reloadIdListButton);
        idBox.setContent(this.underMaintenanceIdBox);

        this.getItems().addAll(infoSection, idBox);

    }

    public TextField getPropertyIdField() {
        return this.propertyIdField;
    }

    public Button getCompleteMaintenanceButton() {
        return this.completeMaintenanceButton;
    }

    public VBox getUnderMaintenanceIdBox() {
        return this.underMaintenanceIdBox;
    }

    public Button getReloadIdListButton() {
        return this.reloadIdListButton;
    }

    public void resetUnderMaintenancePropertyIdBox() {
        this.underMaintenanceIdBox.getChildren().clear();
        Label label = new Label();
        label.setText("Under Maintenance Property ID");
        label.setMaxWidth(180);
        label.setStyle("-fx-font-size: 14; -fx-wrap-text: true");
        this.underMaintenanceIdBox.getChildren().add(label);
    }

}
