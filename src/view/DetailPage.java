package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import model.PremiumSuite;
import model.RentalProperty;

public class DetailPage extends HBox {

    public DetailPage(RentalProperty rentalProperty) {

        this.setPrefSize(800, 600);

        VBox buttonBar = new VBox();
        buttonBar.setPrefSize(120, 600);
        buttonBar.setPadding(new Insets(30, 10, 10, 10));
        buttonBar.setSpacing(20);
        buttonBar.setAlignment(Pos.TOP_CENTER);

        ScrollPane contentSection = new ScrollPane();
        contentSection.setPrefSize(680, 600);

        VBox contentBox = new VBox();
        contentBox.setPrefWidth(660);

        HBox pictureSection = new HBox();
        pictureSection.setPrefWidth(660);

        ImageView imageView = new ImageView();
        if (rentalProperty.getLargeImage() == null) {
            imageView.setImage(new Image("images/a0.png"));
        }
        else {
            imageView.setImage(rentalProperty.getLargeImage());
        }
        imageView.setFitWidth(660);
        imageView.setPreserveRatio(true);

        pictureSection.getChildren().add(imageView);

        HBox infoSection = new HBox();
        infoSection.setPrefWidth(660);

        VBox basicInfoSection = new VBox();
        basicInfoSection.setPrefWidth(330);
        basicInfoSection.setAlignment(Pos.TOP_LEFT);
        basicInfoSection.setSpacing(10);
        basicInfoSection.setPadding(new Insets(10, 10, 10, 10));

        Label propertyIdLabel = new Label();
        Label addressLabel = new Label();
        Label propertyTypeLabel = new Label();
        Label dailyRentalFeeLabel = new Label();
        Label lateFeeRateLabel = new Label();
        Label statusLabel = new Label();
        Label lastMaintenanceDateLabel = new Label();
        Label nextMaintenanceDateLabel = new Label();

        propertyIdLabel.setWrapText(true);
        propertyIdLabel.setText("Property ID: " + rentalProperty.getPropertyId());
        addressLabel.setWrapText(true);
        addressLabel.setText("Address: " + rentalProperty.getAddress());
        propertyTypeLabel.setWrapText(true);
        propertyTypeLabel.setText("Property Type: " + rentalProperty.getPropertyType());
        dailyRentalFeeLabel.setWrapText(true);
        dailyRentalFeeLabel.setText("Daily Rental Fee: " + String.format("%.2f", rentalProperty.getDailyRental()) + "$/DAY");
        lateFeeRateLabel.setWrapText(true);
        lateFeeRateLabel.setText("Late Fee Rate: " + String.format("%.2f", rentalProperty.getLateFeeRate()) + "$/DAY");
        statusLabel.setWrapText(true);
        if (rentalProperty.getPropertyStatus() == RentalProperty.PropertyStatus.AVAILABLE) {
            statusLabel.setText("Property Status: Available");
        }
        else if (rentalProperty.getPropertyStatus() == RentalProperty.PropertyStatus.RENTED) {
            statusLabel.setText("Property Status: Being Rented");
        }
        else {
            statusLabel.setText("Property Status: Under Maintenance");
        }

        basicInfoSection.getChildren().addAll(propertyIdLabel, addressLabel, propertyTypeLabel, dailyRentalFeeLabel, lateFeeRateLabel, statusLabel);

        if (rentalProperty instanceof PremiumSuite) {
            lastMaintenanceDateLabel.setWrapText(true);
            lastMaintenanceDateLabel.setText("Last Maintenance Date: " + ((PremiumSuite) rentalProperty).getLastMaintenanceDate());
            nextMaintenanceDateLabel.setWrapText(true);
            nextMaintenanceDateLabel.setText("Next Maintenance Date: " + ((PremiumSuite) rentalProperty).getNextMaintenanceDate());
            basicInfoSection.getChildren().addAll(lastMaintenanceDateLabel, nextMaintenanceDateLabel);
        }

        VBox detailInfoSection = new VBox();
        detailInfoSection.setPrefWidth(330);
        detailInfoSection.setAlignment(Pos.TOP_LEFT);
        detailInfoSection.setSpacing(10);
        detailInfoSection.setPadding(new Insets(10, 10, 10, 10));

        Label detailedInfo = new Label();
        detailedInfo.setWrapText(true);
        detailedInfo.setText(rentalProperty.getLongDescription());

        detailInfoSection.getChildren().add(detailedInfo);

        infoSection.getChildren().addAll(basicInfoSection, detailInfoSection);

        Button showRecordsButton = new Button();

        showRecordsButton.setTextAlignment(TextAlignment.CENTER);
        showRecordsButton.setStyle("-fx-font-size: 12");
        showRecordsButton.setWrapText(true);
        showRecordsButton.setPrefSize(100, 50);
        showRecordsButton.setText("Show Rent Records");
        showRecordsButton.setOnAction(e -> new RecordPage(rentalProperty));

        buttonBar.getChildren().addAll(showRecordsButton);
        contentBox.getChildren().addAll(pictureSection, infoSection);
        contentSection.setContent(contentBox);

        this.getChildren().addAll(buttonBar, contentSection);

    }

}
