package com.artos;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Stage window = primaryStage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/auth_layout.fxml"));
        window.setResizable(false);
        window.setScene(new Scene(root));
        window.show();
    }
}
