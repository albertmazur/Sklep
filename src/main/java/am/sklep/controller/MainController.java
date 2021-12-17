package am.sklep.controller;

import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MainController {
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
            s.setValue(String.valueOf(d));
            if((d*100)%10==0) s.setValue(String.valueOf(d)+"0");;
            return s;
        });
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));
        productsOnAction();

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button("Kup");
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        //Dodawanie do koszyka
                    });
                }
            }
        });
    }

    @FXML
    private void productsOnAction() {
        tableView.setItems(productModel.getProductFxObservableList());
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
