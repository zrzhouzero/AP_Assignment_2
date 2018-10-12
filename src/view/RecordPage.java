package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.RentalProperty;
import model.RentalRecord;

public class RecordPage extends Stage {

    public RecordPage(RentalProperty rentalProperty) {

        this.setTitle("All Records for " + rentalProperty.getPropertyId());
        this.setMinWidth(420);
        this.setMinHeight(400);

        ScrollPane scrollPane = new ScrollPane();
        VBox largeBox = new VBox();

        scrollPane.setPrefSize(420, 400);

        largeBox.setSpacing(10);
        largeBox.setPrefSize(400, 400);
        largeBox.setAlignment(Pos.TOP_CENTER);
        largeBox.setPadding(new Insets(30, 10, 10, 10));

        Label title = new Label();
        title.setWrapText(true);
        title.setText("All Records for " + rentalProperty.getPropertyId());
        title.setStyle("-fx-font-size: 15;");

        largeBox.getChildren().add(title);


        for (RentalRecord r: rentalProperty.getRentalRecord()) {

            VBox vb = new VBox();
            Label label = new Label();

            vb.setPrefWidth(400);
            vb.setPadding(new Insets(20, 10, 10, 10));

            label.setWrapText(true);
            label.setMaxWidth(380);
            label.setText(r.recordDetails());
            label.setMinHeight(120);

            vb.getChildren().add(label);

            largeBox.getChildren().add(vb);

        }

        scrollPane.setContent(largeBox);

        this.setScene(new Scene(scrollPane, 420, 400));

        this.show();

    }

}
