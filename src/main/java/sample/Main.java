package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("../../resources/sample.fxml"));
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        primaryStage.setTitle("Keimgerät Version 0.1");
        primaryStage.setScene(new Scene(root, 1500, 830));
        primaryStage.setMinWidth(1500);
        primaryStage.setMinHeight(830);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
