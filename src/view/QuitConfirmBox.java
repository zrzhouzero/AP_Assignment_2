package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class QuitConfirmBox extends Stage {

    public QuitConfirmBox() {

        VBox vb = new VBox();

        vb.setMaxSize(400, 150);
        vb.setMinSize(400, 150);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(10, 10, 10, 10));
        vb.setSpacing(20);

        Label label = new Label();
        Button button1 = new Button();
        Button button2 = new Button();

        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER);
        hb.setSpacing(20);

        label.setWrapText(true);
        label.setText("Are you sure you want to quit?");

        button1.setText("No");
        button1.setMinSize(100, 30);
        button1.setMaxSize(100, 30);
        button1.setOnAction(e -> this.close());

        button2.setText("Yes");
        button2.setMinSize(100, 30);
        button2.setMaxSize(100, 30);
        button2.setOnAction(e -> System.exit(0));

        hb.getChildren().addAll(button1, button2);
        vb.getChildren().addAll(label, hb);

        this.setTitle("Confirm Exit");
        this.setResizable(false);
        this.setScene(new Scene(vb, 400, 150));
        this.show();

    }

}
