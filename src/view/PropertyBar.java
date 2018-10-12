package view;

import exceptions.EmptyImagePathException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.RentalProperty;

class PropertyBar extends HBox {

    PropertyBar(RentalProperty rentalProperty) {

        this.setPrefWidth(630);
        this.setMaxHeight(200);

        VBox imagePane = new VBox();
        VBox infoBox = new VBox();
        VBox buttonBox = new VBox();

        imagePane.setMinWidth(300);
        imagePane.setMaxWidth(300);
        imagePane.setAlignment(Pos.CENTER);
        infoBox.setMinWidth(250);
        infoBox.setMaxWidth(250);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.setPadding(new Insets(10, 10, 10, 10));
        infoBox.setSpacing(10);
        buttonBox.setMinWidth(80);
        buttonBox.setMaxWidth(80);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        buttonBox.setPadding(new Insets(10, 10, 10, 10));

        // Build Image Section
        Image image;
        try {
            if (rentalProperty.getSmallImage() == null) {
                throw new EmptyImagePathException();
            }
            image = rentalProperty.getSmallImage();
        }
        catch (Exception e) {
            image = new Image("images/a0.png");
        }
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(300);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
        imagePane.getChildren().add(imageView);

        // Build Information Section
        TextArea idLabel = new TextArea();
        TextArea addressLabel = new TextArea();
        TextArea priceLabel = new TextArea();
        idLabel.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
        addressLabel.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
        priceLabel.setStyle("-fx-control-inner-background: #F4F4F4; -fx-background-color: #F4F4F4");
        idLabel.setWrapText(true);
        addressLabel.setWrapText(true);
        priceLabel.setWrapText(true);
        idLabel.setMaxWidth(240);
        addressLabel.setMaxWidth(240);
        priceLabel.setMaxWidth(240);
        idLabel.setEditable(false);
        addressLabel.setEditable(false);
        priceLabel.setEditable(false);

        idLabel.setText("Property ID: " + rentalProperty.getPropertyId());
        addressLabel.setText("Address: " + rentalProperty.getAddress());
        priceLabel.setText("Daily Price: " + String.format("%.2f", rentalProperty.getDailyRental()) + "$/day");
        infoBox.getChildren().addAll(idLabel, addressLabel, priceLabel);

        // Build Button Section
        Button button = new Button();
        button.setText("More Details");
        button.setStyle("-fx-font-size: 11; -fx-wrap-text: true");
        button.setTextAlignment(TextAlignment.CENTER);
        button.setOnAction(e -> {
            HBox detailPage = new DetailPage(rentalProperty);
            Stage stage = new Stage();
            stage.setScene(new Scene(detailPage, 400, 400));
            stage.setTitle(rentalProperty.getPropertyId());
            stage.show();
        });

        button.setOnAction(e -> {
            Stage stage = new Stage();
            stage.setTitle(rentalProperty.getPropertyId() + " Details");
            stage.setScene(new Scene(new DetailPage(rentalProperty)));
            stage.show();
        });

        buttonBox.getChildren().add(button);

        this.setStyle("-fx-border-color: #CCCCCC; -fx-border-width: 1");
        this.getChildren().addAll(imagePane, infoBox, buttonBox);

    }

}
