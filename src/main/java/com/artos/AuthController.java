/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ludovick XIV
 */
public class AuthController implements Initializable {

    private DataLoader loader;

    @FXML
    private Button btn;

    @FXML
    private TextField loginTf;

    @FXML
    private PasswordField pswFld;

    @FXML
    private Text errorText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loader = new DataLoader();

        loader.setListener(new OnDataPass() {  // слушатель для запроса к серверу
            @Override
            public void onSuccses(User user) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        showMainWindow(user);
                    }
                });
            }

            @Override
            public void onSuccses(List<Repo> repos) {
            }
            
            @Override
            public void onFailure(int code) {
                pswFld.clear();
                errorText.setVisible(true);
                loginTf.setEditable(true);
                pswFld.setEditable(true);
            }
        });
        
        loginTf.focusedProperty().addListener((observable, oldValue, newValue) -> {  // При нажатии на поле логин убирает текст ошибки, если таков был виден
            errorText.setVisible(false);
        });

        pswFld.focusedProperty().addListener((observable, oldValue, newValue) -> { // При нажатии на поле пароль убирает текст ошибки, если таков был виден
            errorText.setVisible(false);
        });
        btn.disableProperty().bind(Bindings.isEmpty(loginTf.textProperty())
                .or(Bindings.isEmpty(pswFld.textProperty())));

        btn.setOnAction((ActionEvent event) -> {
            loader.loadData(loginTf.getText(), pswFld.getText());
            loginTf.setEditable(false);
            pswFld.setEditable(false);

        });
    }

    /**
     * Метод для вызова окна с репозиториями
     * вызывается только при успешной загрузки данных с сервера
     * @param user принимает экземпляр объекта пользователь 
     * полученный ранее
     */
    private void showMainWindow(User user) {
        btn.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/fxml/main_layout.fxml"));

        MainController controller = new MainController();
        controller.setUser(user);

        loader.setController(controller);

        try {
            loader.load();
        } catch (IOException ioe) {
        }
        Parent root = loader.getRoot();

        Stage stage = new Stage();
        stage.setScene(new Scene(root, 620, 420));
        stage.setMinHeight(435);
        stage.setMinWidth(635);
        stage.showAndWait();
    }
}

