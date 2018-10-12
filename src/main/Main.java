package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.QuitConfirmBox;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        Controller controller = new Controller();

        primaryStage.setTitle("FlexiRent");
        primaryStage.setScene(new Scene(controller.getMainView(), 800, 600));
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            new QuitConfirmBox();
        });
        primaryStage.show();

    }

    public static void main(String args[]) {
        Application.launch(Main.class, args);
    }

}
