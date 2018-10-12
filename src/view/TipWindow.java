package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TipWindow extends Stage {

    public TipWindow(String title, String tip) {

        VBox vb = new VBox();

        vb.setMaxSize(400, 150);
        vb.setMinSize(400, 150);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10, 10, 10, 10));
        vb.setSpacing(20);

        Label label = new Label();
        Button button = new Button();

        label.setWrapText(true);
        label.setText(tip);

        button.setText("OK");
        button.setMinSize(100, 30);
        button.setMaxSize(100, 30);
        button.setOnAction(e -> this.close());

        vb.getChildren().addAll(label, button);

        this.setTitle(title);
        this.setResizable(false);
        this.setScene(new Scene(vb, 400, 150));
        this.show();

    }

}
