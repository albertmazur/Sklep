package am.sklep.controller;

import am.sklep.database.DbManager;
import am.sklep.database.models.Shopping;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;

public class MainController {
    @FXML
    private Label balanceLabel;
    @FXML
    private Button buyButton;
    @FXML
    private TableColumn<ProductFx, ProductFx> buyColumn;
    @FXML
    private TableColumn<ProductFx, String> nameColumn;
    @FXML
    private TableColumn<ProductFx, String> descColumn;
    @FXML
    private TableColumn<ProductFx, String> priceColumn;
    @FXML
    private TableColumn<ProductFx, UserFx> sellerColumn;
    @FXML
    private TableView<ProductFx> tableView;

    private ProductModel productModel;

    private UserFx userFx = LoginController.getUserFx();

    @FXML
    private void initialize(){
        productModel = new ProductModel();
        productModel.downloadProduct();
        
        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nazwaProperty());
        descColumn.setCellValueFactory(cellDate -> cellDate.getValue().opisProperty());
        //priceColumn.setCellValueFactory(cellDate -> cellDate.getValue().cenaProperty().asObject());
        priceColumn.setCellValueFactory(cellDate ->{
            Double d = cellDate.getValue().cenaProperty().getValue();
            StringProperty s = new SimpleStringProperty();
            s.setValue(Converter.addZero(d));
            return s;
        });
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));
        productsOnAction();

        balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));
        buyButton.setVisible(false);
    }

    @FXML
    private void productsOnAction() {
        tableView.setItems(productModel.getProductFxToBuyObservableList());
        buyButton.setVisible(false);

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button("Dodaj do kosztyka");
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        productModel.getProductFxBuyObservableList().add(item);
                        productModel.getProductFxToBuyObservableList().remove(item);
                        productsOnAction();
                    });
                }
            }
        });
    }

    @FXML
    private void basketOnAction() {
        productModel.downloadProduct();
        buyButton.setVisible(true);
        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button("Usuń z koszyka");
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        productModel.getProductFxBuyObservableList().remove(item);
                        productModel.getProductFxToBuyObservableList().add(item);
                        basketOnAction();
                    });
                }
            }
        });
        tableView.setItems(productModel.getProductFxBuyObservableList());
    }

    @FXML
    private void boughtOnAction() {
        buyButton.setVisible(false);
        tableView.setItems(productModel.getProductFxBuyObservableList());
    }

    @FXML
    private void addProductsOnAction() {
        buyButton.setVisible(false);
    }

    @FXML
    private void settingOnAction() {
        buyButton.setVisible(false);
    }

    @FXML
    private void buyOnAction() {
        productModel.buy();
        basketOnAction();
        balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));
    }
}
