package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LongDescriptionEditor extends Stage {

    private TextField textField = new TextField();
    private Button saveButton = new Button();
    private TextArea textArea = new TextArea();

    public LongDescriptionEditor() {

        this.setTitle("Long Detail Description Editor");

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(20, 10, 10, 10));
        vBox.setPrefSize(400, 300);

        Label label1 = new Label();
        label1.setText("Enter Property ID Below");

        Label label2 = new Label();
        label2.setText("Enter Long Description Below");

        this.textField.setMaxSize(200, 30);
        this.saveButton.setText("Save Description");
        this.textArea.setWrapText(true);
        this.textArea.setMaxWidth(380);

        vBox.getChildren().addAll(label1, this.textField, label2, this.textArea, this.saveButton);

        this.setScene(new Scene(vBox, 400, 300));

    }

    public String getLongDescription() {
        return this.textArea.getText();
    }

    public Button getSaveButton() {
        return this.saveButton;
    }

    public String getPropertyId() {
        return this.textField.getText();
    }
}
