/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.artos;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ludovick XIV
 */
public class MainController {

    private DataLoader loader;
    private User user;
    private ObservableList<Repo> repoList;
    private StringSelection stringSelection;
    private Clipboard clpbrd;
    private FilteredList<Repo> filteredData;
    private ContextMenu contextMenu;
    private MenuItem menuItem;

    @FXML
    private ImageView profileImage;
    @FXML
    private Text profileName;
    @FXML
    private Text profileLogin;
    @FXML
    private TextField searchField;
    @FXML
    private Button btnExit;
    @FXML
    private TableView<Repo> tableView;
    @FXML
    private TableColumn<Repo, String> columnName;
    @FXML
    private TableColumn<Repo, String> columnDescription;
    @FXML
    private TableColumn<Repo, Integer> columnForks;
    @FXML
    private TableColumn<Repo, Integer> columnStars;
    @FXML
    private TableColumn<Repo, Integer> columnWatchers;

    @FXML
    public void initialize() {
        Image image = new Image(user.getPhoto());
        profileImage.setImage(image);
        Circle clip = new Circle(45f, 45f, 45f); // Создание круга для аватара пользователя
        profileImage.setClip(clip);
        profileName.setText(user.getName());
        profileLogin.setText(user.getLogin());

        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        columnForks.setCellValueFactory(new PropertyValueFactory<>("forksCount"));
        columnStars.setCellValueFactory(new PropertyValueFactory<>("stargazersCount"));
        columnWatchers.setCellValueFactory(new PropertyValueFactory<>("watchersCount"));

        contextMenu = new ContextMenu();
        menuItem = new MenuItem("Clone URL");
        contextMenu.getItems().add(menuItem);
        
        tableView.setColumnResizePolicy((TableView.ResizeFeatures p) -> true);
        loader = new DataLoader();
        loader.getRepo(user);
        
        addListeners();
    }
    
    /**
     * Метод для вызова списка пользователей
     * вызывается только при успешном подключении к серверу
     * @param repo принимает параметр List<Repo>
     */
    private void setData(List<Repo> repo) {
        repoList = FXCollections.observableArrayList(repo);
        tableView.setItems(repoList);

        filteredData = new FilteredList<>(repoList, r -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate((Repo mRepo) -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (mRepo.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else {
                    return false;
                }
            });
        });
        SortedList<Repo> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }

    private void addListeners() {
        loader.setListener(new OnDataPass() {
            
            @Override
            public void onSuccses(User repo) {
            }
            
            @Override
            public void onSuccses(List<Repo> repo) {
                setData(repo);
            }

            @Override
            public void onFailure(int code) {

            }
        });

        btnExit.setOnAction((ActionEvent event) -> {  // кнопка выхода, закрывает текущее окно и открывает окно логина
            btnExit.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/auth_layout.fxml"));

            try {
                loader.load();
            } catch (IOException ioe) {
            }
            Parent root = loader.getRoot();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        });

        menuItem.setOnAction(event -> {        
            clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
            clpbrd.setContents(stringSelection, null);
            contextMenu.hide();
        });

        tableView.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            contextMenu.hide();
            if (event.getButton() == MouseButton.SECONDARY) {
                if (tableView.getSelectionModel().getSelectedIndices().get(0) <= -1) {
                    tableView.getSelectionModel().clearSelection();
                } else {
                    System.out.println(filteredData.get(tableView.getSelectionModel().getFocusedIndex()).getName());
                    contextMenu.show(tableView, event.getScreenX(), event.getScreenY());
                    stringSelection = new StringSelection(filteredData.get(tableView.getSelectionModel().getFocusedIndex()).getCloneUrl());
                }
            }
        });
    }
    
    /**
     * Обновления списка по нажатию F5
     * @param event 
     */
    @FXML
    void uploadData(KeyEvent event) { 
        if (event.getCode() == KeyCode.F5) {
            repoList.clear();
            filteredData.clear();
            loader.getRepo(user);
            tableView.refresh();
        }
    }

    /**
     * Setter для поля пользователь
     * @param user принимает экземпляр класса User
     */
    public void setUser(User user) {
        this.user = user;
    }
}

