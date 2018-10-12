package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.time.LocalDateTime;

public class HomePage extends VBox {

    private Button addProperty = new Button();
    private Button rentProperty = new Button();
    private Button returnProperty = new Button();
    private Button performMaintenance = new Button();
    private Button completeMaintenance = new Button();
    private Button displayAllProperties = new Button();
    private Button changeDescription = new Button();

    private MenuItem menuItemImport;
    private MenuItem menuItemExport;

    private Pane displayPane;

    public HomePage() {

        this.setPrefSize(800, 600);

        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        this.menuItemImport = new MenuItem("Import");
        this.menuItemExport = new MenuItem("Export");
        MenuItem menuItemQuit = new MenuItem("Quit");
        menuFile.getItems().addAll(menuItemImport, menuItemExport, menuItemQuit);
        menuBar.getMenus().addAll(menuFile);

        SplitPane splitPane = new SplitPane();
        splitPane.setDividerPositions(0.15);

        VBox buttonBox = new VBox();
        this.displayPane = new Pane();

        splitPane.setPrefSize(800, 570);
        splitPane.getItems().addAll(buttonBox, displayPane);

        buttonBox.setAlignment(Pos.TOP_CENTER);
        buttonBox.setSpacing(20);
        buttonBox.setPadding(new Insets(20, 10, 10, 10));
        buttonBox.setPrefSize(120, 570);

        this.addProperty.setTextAlignment(TextAlignment.CENTER);
        this.addProperty.setContentDisplay(ContentDisplay.CENTER);
        this.addProperty.setAlignment(Pos.CENTER);
        this.addProperty.setPrefSize(100, 40);
        this.addProperty.setText("Add Property");
        this.addProperty.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.rentProperty.setTextAlignment(TextAlignment.CENTER);
        this.rentProperty.setContentDisplay(ContentDisplay.CENTER);
        this.rentProperty.setAlignment(Pos.CENTER);
        this.rentProperty.setPrefSize(100, 40);
        this.rentProperty.setText("Rent Property");
        this.rentProperty.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.returnProperty.setTextAlignment(TextAlignment.CENTER);
        this.returnProperty.setContentDisplay(ContentDisplay.CENTER);
        this.returnProperty.setAlignment(Pos.CENTER);
        this.returnProperty.setPrefSize(100, 40);
        this.returnProperty.setText("Return Property");
        this.returnProperty.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.performMaintenance.setTextAlignment(TextAlignment.CENTER);
        this.performMaintenance.setContentDisplay(ContentDisplay.CENTER);
        this.performMaintenance.setAlignment(Pos.CENTER);
        this.performMaintenance.setPrefSize(100, 40);
        this.performMaintenance.setText("Perform Maintenance");
        this.performMaintenance.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.completeMaintenance.setTextAlignment(TextAlignment.CENTER);
        this.completeMaintenance.setContentDisplay(ContentDisplay.CENTER);
        this.completeMaintenance.setAlignment(Pos.CENTER);
        this.completeMaintenance.setPrefSize(100, 40);
        this.completeMaintenance.setText("Complete Maintenance");
        this.completeMaintenance.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.displayAllProperties.setTextAlignment(TextAlignment.CENTER);
        this.displayAllProperties.setContentDisplay(ContentDisplay.CENTER);
        this.displayAllProperties.setAlignment(Pos.CENTER);
        this.displayAllProperties.setPrefSize(100, 40);
        this.displayAllProperties.setText("Display All Properties");
        this.displayAllProperties.setStyle("-fx-font-size: 11; -fx-wrap-text: true");

        this.changeDescription.setTextAlignment(TextAlignment.CENTER);
        this.changeDescription.setContentDisplay(ContentDisplay.CENTER);
        this.changeDescription.setAlignment(Pos.CENTER);
        this.changeDescription.setPrefSize(100, 40);
        this.changeDescription.setText("Change Property Description");
        this.changeDescription.setStyle("-fx-font-size: 10; -fx-wrap-text: true");

        Label currentTimeLabel = new Label();

        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> currentTimeLabel.setText(LocalDateTime.now().toLocalDate().toString() + "\n" + LocalDateTime.now().toLocalTime().toString().substring(0, 8))), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();

        currentTimeLabel.setTextAlignment(TextAlignment.CENTER);
        currentTimeLabel.setContentDisplay(ContentDisplay.CENTER);
        currentTimeLabel.setAlignment(Pos.CENTER);
        currentTimeLabel.setPrefSize(100, 40);
        currentTimeLabel.setStyle("-fx-font-size: 10; -fx-wrap-text: true");

        menuItemQuit.setOnAction(e -> new QuitConfirmBox());

        buttonBox.getChildren().addAll(this.addProperty, this.rentProperty, this.returnProperty, this.performMaintenance, this.completeMaintenance, this.displayAllProperties, this.changeDescription, currentTimeLabel);

        this.displayPane.setPrefSize(670, 570);

        this.getChildren().addAll(menuBar, splitPane);

    }

    public Button getAddProperty() {
        return this.addProperty;
    }

    public Button getCompleteMaintenance() {
        return this.completeMaintenance;
    }

    public Button getDisplayAllProperties() {
        return this.displayAllProperties;
    }

    public Button getRentProperty() {
        return this.rentProperty;
    }

    public Button getPerformMaintenance() {
        return this.performMaintenance;
    }

    public Button getReturnProperty() {
        return this.returnProperty;
    }

    public Pane getDisplayPane() {
        return this.displayPane;
    }

    public Button getChangeDescription() {
        return this.changeDescription;
    }

    public MenuItem getMenuItemExport() {
        return this.menuItemExport;
    }

    public MenuItem getMenuItemImport() {
        return this.menuItemImport;
    }

}
