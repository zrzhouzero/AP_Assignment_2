package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.MalformedURLException;

public class AddProperty extends VBox {

    private TextField streetNumField = new TextField();
    private TextField streetNameField = new TextField();
    private TextField suburbField = new TextField();
    private TextField numberOfBedroomField = new TextField();
    private Button addApartment = new Button();
    private Button addPremiumSuite = new Button();
    private Label statusLabel = new Label();
    private Image smallImage;
    private Image largeImage;

    public AddProperty() {

        final FileChooser fileChooser = new FileChooser();

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(50);
        buttonBox.setPrefSize(670, 100);
        buttonBox.setAlignment(Pos.CENTER);

        HBox lowerSection = new HBox();
        lowerSection.setPrefWidth(670);

        VBox lowestButtonBox = new VBox();
        lowestButtonBox.setPrefWidth(670);
        lowestButtonBox.setAlignment(Pos.TOP_CENTER);
        lowestButtonBox.setPadding(new Insets(30, 20, 20, 20));

        VBox imageBox = new VBox();
        imageBox.setPrefWidth(335);
        imageBox.setSpacing(15);
        imageBox.setAlignment(Pos.TOP_CENTER);

        VBox smallImageBox = new VBox();
        smallImageBox.setStyle("-fx-border-width: 1; -fx-border-color: #CCCCCC");
        smallImageBox.setMaxSize(300, 200);
        smallImageBox.setMinSize(300, 200);
        ImageView smallImageView = new ImageView();
        smallImageView.setFitHeight(200);
        smallImageView.setFitWidth(300);
        smallImageView.setPreserveRatio(true);
        smallImageBox.getChildren().add(smallImageView);

        HBox addSmallImageButtonBar = new HBox();
        addSmallImageButtonBar.setSpacing(15);
        addSmallImageButtonBar.setAlignment(Pos.CENTER);

        Button addSmallImage = new Button();
        addSmallImage.setText("Select Display Image");
        addSmallImage.setOnAction(e -> {
            this.smallImage = null;
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                try {
                    this.smallImage = new Image(file.toURI().toURL().toExternalForm());
                    smallImageView.setImage(this.smallImage);
                }
                catch (MalformedURLException urlE) {
                    this.smallImage = null;
                }
            }
        });

        TextArea largeImagePathArea = new TextArea();
        largeImagePathArea.setMaxSize(250, 30);

        Button addLargeImage = new Button();
        addLargeImage.setText("Select Detail Image");
        addLargeImage.setOnAction(e -> {
            this.largeImage = null;
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                try {
                    this.largeImage = new Image(file.toURI().toURL().toExternalForm());
                    largeImagePathArea.setText(file.getAbsolutePath());
                }
                catch (MalformedURLException urlE) {
                    this.largeImage = null;
                }
            }
        });

        addSmallImageButtonBar.getChildren().addAll(addSmallImage);

        VBox contentBox = new VBox();
        contentBox.setPrefWidth(335);
        contentBox.setSpacing(15);
        contentBox.setAlignment(Pos.TOP_CENTER);

        this.statusLabel.setStyle("-fx-font-size: 15; -fx-border-width: 1; -fx-border-color: #000000; -fx-border-radius: 10");
        this.statusLabel.setText("");
        this.statusLabel.setPrefSize(250, 30);

        Label streetNumLabel = new Label();
        Label streetNameLabel = new Label();
        Label suburbLabel = new Label();
        Label numberOfBedroomLabel = new Label();

        streetNumLabel.setStyle("-fx-font-size: 14");
        streetNameLabel.setStyle("-fx-font-size: 14");
        suburbLabel.setStyle("-fx-font-size: 14");
        numberOfBedroomLabel.setStyle("-fx-font-size: 14");

        streetNumLabel.setText("Enter Street Number Below");
        streetNameLabel.setText("Enter Street Name Below");
        suburbLabel.setText("Enter Suburb Below");
        numberOfBedroomLabel.setText("Enter the Number of Bedrooms Below");

        this.streetNumField.setMaxSize(250, 30);
        this.streetNameField.setMaxSize(250, 30);
        this.suburbField.setMaxSize(250, 30);
        this.numberOfBedroomField.setMaxSize(250, 30);

        this.streetNumField.setPromptText("Street Number");
        this.streetNameField.setPromptText("Street Name");
        this.suburbField.setPromptText("Suburb");
        this.numberOfBedroomField.setPromptText("Number of Bedrooms");

        this.streetNumField.setStyle("-fx-font-size: 13");
        this.streetNameField.setStyle("-fx-font-size: 13");
        this.suburbField.setStyle("-fx-font-size: 13");
        this.numberOfBedroomField.setStyle("-fx-font-size: 13");

        this.addApartment.setText("Add Apartment");
        this.addApartment.setPrefSize(250, 30);
        this.addApartment.setStyle("-fx-font-size: 13");

        this.addPremiumSuite.setText("Add Premium Suite");
        this.addPremiumSuite.setPrefSize(250, 30);
        this.addPremiumSuite.setStyle("-fx-font-size: 13");

        Button addA = new Button();
        addA.setMinSize(100, 40);
        addA.setText("Add Apartment");
        addA.setOnAction(e -> {
            contentBox.getChildren().clear();
            contentBox.getChildren().addAll(this.statusLabel, streetNumLabel, this.streetNumField, streetNameLabel, this.streetNameField, suburbLabel, this.suburbField, numberOfBedroomLabel, this.numberOfBedroomField);
            imageBox.getChildren().clear();
            imageBox.getChildren().addAll(smallImageBox, addSmallImageButtonBar, addLargeImage, largeImagePathArea);
            lowestButtonBox.getChildren().clear();
            lowestButtonBox.getChildren().add(this.addApartment);
        });

        Button addS = new Button();
        addS.setMinSize(100, 40);
        addS.setText("Add Premium Suite");
        addS.setOnAction(e -> {
            contentBox.getChildren().clear();
            contentBox.getChildren().addAll(this.statusLabel, streetNumLabel, this.streetNumField, streetNameLabel, this.streetNameField, suburbLabel, this.suburbField);
            imageBox.getChildren().clear();
            imageBox.getChildren().addAll(smallImageBox, addSmallImageButtonBar, addLargeImage, largeImagePathArea);
            lowestButtonBox.getChildren().clear();
            lowestButtonBox.getChildren().add(this.addPremiumSuite);
        });

        lowerSection.getChildren().addAll(contentBox, imageBox);
        buttonBox.getChildren().addAll(addA, addS);

        this.getChildren().addAll(buttonBox, lowerSection, lowestButtonBox);

    }

    public Button getAddApartment() {
        return this.addApartment;
    }

    public Button getAddPremiumSuite() {
        return this.addPremiumSuite;
    }

    public TextField getStreetNumField() {
        return this.streetNumField;
    }

    public TextField getStreetNameField() {
        return this.streetNameField;
    }

    public TextField getSuburbField() {
        return this.suburbField;
    }

    public TextField getNumberOfBedroom() {
        return this.numberOfBedroomField;
    }

    public Label getStatusLabel() {
        return this.statusLabel;
    }

    public Image getLargeImage() {
        return this.largeImage;
    }

    public Image getSmallImage() {
        return this.smallImage;
    }
}
