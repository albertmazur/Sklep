package am.sklep.controller;

import am.sklep.database.DbManager;
import am.sklep.models.ClientFx;
import am.sklep.models.ProductFx;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
    @FXML
    private TableColumn<ProductFx, String> nameColumn;
    @FXML
    private TableColumn<ProductFx, String> descColumn;
    @FXML
    private TableColumn<ProductFx, Double> priceColumn;
    @FXML
    private TableColumn<ProductFx, ClientFx> sellerColumn;
    @FXML
    private TableView tableView;

    @FXML
    private void initialize(){
        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nazwaProperty());
        descColumn.setCellValueFactory(cellDate -> cellDate.getValue().opisProperty());
        priceColumn.setCellValueFactory(cellDate -> cellDate.getValue().cenaProperty().asObject());
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        productsOnAction();
    }

    @FXML
    private void productsOnAction() {
        tableView.setItems(DbManager.downloadProduct());
    }

    @FXML
    private void basketOnAction() {
    }

    @FXML
    private void boughtOnAction() {
    }

    @FXML
    private void addProductsOnAction() {
    }

    @FXML
    private void settingOnAction() {
    }
}
