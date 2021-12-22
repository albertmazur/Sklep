package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MainController {
    public static final String VIEW_SETTING_USER_FXML = "/view/settingUser.fxml";
    public static final String VIEW_LOGIN_FXML = "/view/login.fxml";
    public static final String VIEW_SETTING_PRODUCT_FXML = "/view/settingProduct.fxml";
    public static final String VIEW_MAIN_FXML = "/view/main.fxml";
    public static final String IMG_M = "/img/iconM.png";

    @FXML
    private MenuItem editProductMenuItem;
    @FXML
    private TableView<ProductFx> tableView;
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
    private Label sumLabel;
    @FXML
    private Label sumViewLabel;
    @FXML
    private Label currencyLabel;
    @FXML
    private Label balanceFailedLabel;
    @FXML
    private Label balanceLabel;
    @FXML
    private Button buyButton;

    private ProductModel productModel;
    private UserFx userFx;
    private Stage stageMain;
    private static Stage stageSettingProduct;
    private BooleanProperty checkBuy;

    @FXML
    private void initialize(){
        productModel = new ProductModel();
        productModel.downloadProduct();

        stageMain = Login.getLoginStage();
        stageMain.setResizable(true);

        userFx = LoginController.getUserFx();

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

        this.tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            this.productModel.setProductFxEdit(newValue);
        });
        editProductMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());

        balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));

        checkBuy = new SimpleBooleanProperty();
        balanceFailedLabel.visibleProperty().bind(checkBuy);
        buyButton.disableProperty().bind(checkBuy);

        productsOnAction();
    }

    @FXML
    private void productsOnAction() {
        tableView.setItems(productModel.getProductFxToBuyObservableList());

        viewMyBasket(false);
        editProductMenuItem.setVisible(false);

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
        tableView.setItems(productModel.getProductFxBuyObservableList());

        viewMyBasket(true);
        editProductMenuItem.setVisible(false);
        checkBuy();

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
    }

    @FXML
    private void buyOnAction() {
        if(!checkBuy.get()){
            productModel.buy();
            basketOnAction();
            balanceFailedLabel.setText("Dziękujemy za zakup");
            balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));
        }
    }

    private void checkBuy(){
        double suma = productModel.getProductFxBuyObservableList().stream().mapToDouble(ProductFx::getCena).sum();
        if(suma==0.0){
            checkBuy.setValue(true);
            balanceFailedLabel.setText("Nie wybrałeś żadengo produktu");
        }
        else if(suma> userFx.getStanKonta()){
            checkBuy.setValue(true);
            balanceFailedLabel.setText("Nie masz wystarczających środków na koncie");
        }
        else{
            checkBuy.setValue(false);
        }
        sumViewLabel.setText(Converter.addZero(suma));
    }

    @FXML
    private void boughtOnAction() {
        productModel.myProducts();
        tableView.setItems(productModel.getProductFxMyObservableList());

        checkBuy();

        viewMyBasket(false);
        editProductMenuItem.setVisible(true);

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button buttonSell = new Button("Sprzedaj");
            Button buttonRemove = new Button("Usuń ze sprzedarzy");
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setGraphic(null);
                }
                else if(item.getStatus().equals(ProductModel.DO_KUPIENIA)){
                    setGraphic(buttonRemove);
                    buttonRemove.setOnAction(event ->{
                        item.setStatus(ProductModel.DODANY);
                        DbManager.update(Converter.converterToProduct(item));
                        boughtOnAction();
                    });
                }
                else{
                    setGraphic(buttonSell);
                    buttonSell.setOnAction(event -> {
                        item.setStatus(ProductModel.DO_KUPIENIA);
                        DbManager.update(Converter.converterToProduct(item));
                        boughtOnAction();
                    });
                }
            }
        });
    }

    private void viewMyBasket(boolean b){
        buyButton.setVisible(b);
        statusColumn.setVisible(!b);
        sumLabel.setVisible(b);
        sumViewLabel.setVisible(b);
        currencyLabel.setVisible(b);
        checkBuy.setValue(b);
    }

    @FXML
    private void addProductsOnAction() {
        ProductModel.setProductFxEdit(null);

        stageMain.getScene().getRoot().setDisable(true);

        setStageSettingProduct(new Stage());
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_PRODUCT_FXML)));
    }

    @FXML
    private void settingOnAction() {
        stageMain.getScene().getRoot().setDisable(true);

        Stage stage = LoginController.getStageSettingUser();
        stage.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_USER_FXML)));
    }

    @FXML
    private void logoutOnAction(){
        LoginController.setUserFx(null);
        stageMain.close();
        stageMain.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_LOGIN_FXML)));
    }

    @FXML
    private void editProductOnAction() {
        stageSettingProduct = new Stage();
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_PRODUCT_FXML)));
    }

    public static Stage getStageSettingProduct() {
        return stageSettingProduct;
    }

    public static void setStageSettingProduct(Stage stageSettingProduct) {
        MainController.stageSettingProduct = stageSettingProduct;
    }
}
