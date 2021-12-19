package am.sklep.controller;

import am.sklep.database.DbManager;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Label balanceFailedLabel;
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
    private TableColumn<ProductFx, String> statusColumn;
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
        priceColumn.setCellValueFactory(cellDate ->{
            Double d = cellDate.getValue().cenaProperty().getValue();
            StringProperty s = new SimpleStringProperty();
            s.setValue(Converter.addZero(d));
            return s;
        });
        statusColumn.setCellValueFactory(cellDate -> cellDate.getValue().statusProperty());
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));

        balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));

        productsOnAction();
    }

    @FXML
    private void productsOnAction() {
        tableView.setItems(productModel.getProductFxToBuyObservableList());
        buyButton.setVisible(false);
        statusColumn.setVisible(true);
        balanceFailedLabel.setVisible(false);

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
                    });
                }
            }
        });
    }

    @FXML
    private void basketOnAction() {
        buyButton.setVisible(true);
        statusColumn.setVisible(true);
        balanceFailedLabel.setVisible(false);

        tableView.setItems(productModel.getProductFxBuyObservableList());

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
                    });
                }
            }
        });

    }

    @FXML
    private void boughtOnAction() {
        productModel.myProducts();
        buyButton.setVisible(false);
        statusColumn.setVisible(true);
        balanceFailedLabel.setVisible(false);
        tableView.setItems(productModel.getProductFxMyObservableList());

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button("Sprzedaj");
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item.getStatus().equals(ProductModel.DO_KUPIENIA)) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        item.setStatus(ProductModel.DO_KUPIENIA);
                        DbManager.update(Converter.converterToProduct(item));
                        boughtOnAction();
                    });
                }
            }
        });
        tableView.setItems(productModel.getProductFxMyObservableList());
    }

    @FXML
    private void addProductsOnAction() {
        buyButton.setVisible(false);
        statusColumn.setVisible(true);
        balanceFailedLabel.setVisible(false);
    }

    @FXML
    private void settingOnAction() {
        buyButton.setVisible(false);
        statusColumn.setVisible(true);
        balanceFailedLabel.setVisible(false);

        Stage stageMain = LoginController.getStageLogin();
        stageMain.getScene().getRoot().setDisable(true);

        Stage stage = LoginController.getStageRegistration();
        stage.setScene(new Scene(FxmlUtils.FxmlLoader("/view/registration.fxml")));
        stage.show();
    }

    @FXML
    private void buyOnAction() {
        double suma = productModel.getProductFxBuyObservableList().stream().mapToDouble(ProductFx::getCena).sum();
        if(suma< userFx.getStanKonta()){
            productModel.buy();
            basketOnAction();
            balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));
            balanceFailedLabel.setVisible(false);
        }
        else{
            balanceFailedLabel.setVisible(true);
        }
    }

    @FXML
    private void logoutOnAction(){
        LoginController.setUserFx(null);
        Stage stage = LoginController.getStageLogin();
        stage.close();
        stage.setScene(new Scene(FxmlUtils.FxmlLoader("/view/login.fxml")));
        stage.show();
    }
}
