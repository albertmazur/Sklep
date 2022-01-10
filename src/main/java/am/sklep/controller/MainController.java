package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.listener.SearchProducts;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Optional;

public class MainController {
    /**
     * Odnośniki do pliku settingUser.fxml
     */
    public static final String VIEW_SETTING_USER_FXML = "/view/settingUser.fxml";
    /**
     * Odnośniki do pliku login.fxml
     */
    public static final String VIEW_LOGIN_FXML = "/view/login.fxml";
    /**
     * Odnośniki do pliku settingProduct.fxml
     */
    public static final String VIEW_SETTING_PRODUCT_FXML = "/view/settingProduct.fxml";
    /**
     * Odnośniki do pliku main.fxml
     */
    public static final String VIEW_MAIN_FXML = "/view/main.fxml";
    /**
     * Odnośniki do pliku iconM.png
     */
    public static final String IMG_M = "/img/iconM.png";

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
    private VBox VBoxAddBalance;
    @FXML
    private MenuItem editProductMenuItem;
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
    @FXML
    private Label addBalanceLabel;
    @FXML
    private TextField searchProductsTextField;

    private ProductModel productModel;
    private UserFx userFx;
    private Stage stageMain;
    private static Stage stageSettingProduct;
    private BooleanProperty checkBuy;

    /**
     * Ustawienie stage wartości dla tabelView
     */
    @FXML
    private void initialize(){
        productModel = new ProductModel();
        productModel.downloadProduct();

        stageMain = Login.getLoginStage();
        stageMain.setResizable(true);
        stageMain.setMaximized(true);

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

    /**
     * Wyświetlenie produktów do kupienia
     */
    @FXML
    private void productsOnAction() {
        tableView.setItems(productModel.getProductFxToBuyObservableList());
        searchProductsTextField.textProperty().addListener(new SearchProducts(productModel.getProductFxToBuyObservableList()));
        searchProductsTextField.setVisible(true);

        viewMyBasket(false);
        editProductMenuItem.setVisible(false);

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button(FxmlUtils.getResourceBundle().getString("add_basket"));
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

    /**
     * Wyświetlenie produktów, które zostały dodane do koszyka
     */
    @FXML
    private void basketOnAction() {
        tableView.setItems(productModel.getProductFxBuyObservableList());
        searchProductsTextField.setVisible(false);

        viewMyBasket(true);
        editProductMenuItem.setVisible(false);
        checkBuy();

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button(FxmlUtils.getResourceBundle().getString("remove_basket"));
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

    /**
     * Funkcja odpowiedzialna za kliknięcie przycisku Kup
     */
    @FXML
    private void buyOnAction() {
        if(!checkBuy.get()){
            productModel.buy();
            basketOnAction();
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("thank"));
            balanceLabel.setText(Converter.addZero(userFx.stanKontaProperty().getValue()));
        }
    }

    /**
     * Funkcja odpowiedzialna za wyświetlenie użytkownikowi odpowiedniego komunikatu zależności czy jest coś w koszyku lub nie ma wystarczających środków na koncie
     */
    private void checkBuy(){
        double suma = productModel.getProductFxBuyObservableList().stream().mapToDouble(ProductFx::getCena).sum();
        if(suma==0.0){
            checkBuy.setValue(true);
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("nothing"));
        }
        else if(suma> userFx.getStanKonta()){
            checkBuy.setValue(true);
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("not_balance"));
        }
        else{
            checkBuy.setValue(false);
        }
        sumViewLabel.setText(Converter.addZero(suma));
    }

    /**
     * Wyświetlanie produktów, które należą do zalogowanego użytkownika
     */
    @FXML
    private void boughtOnAction() {
        productModel.myProducts();
        tableView.setItems(productModel.getProductFxMyObservableList());
        searchProductsTextField.setVisible(false);

        checkBuy();

        viewMyBasket(false);
        editProductMenuItem.setVisible(true);

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button buttonSell = new Button(FxmlUtils.getResourceBundle().getString("sell"));
            Button buttonRemove = new Button(FxmlUtils.getResourceBundle().getString("remove_sell"));
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setGraphic(null);
                }
                else if(item.getStatus().equals(ProductModel.TO_BUY)){
                    setGraphic(buttonRemove);
                    buttonRemove.setOnAction(event ->{
                        item.setStatus(ProductModel.ADDED);
                        DbManager.update(Converter.converterToProduct(item));
                        boughtOnAction();
                    });
                }
                else{
                    setGraphic(buttonSell);
                    buttonSell.setOnAction(event -> {
                        item.setStatus(ProductModel.TO_BUY);
                        DbManager.update(Converter.converterToProduct(item));
                        boughtOnAction();
                    });
                }
            }
        });
    }

    /**
     * @param b Jeśli jest <strong>true</strong> to wyświetla przysiek kup, kwotę do zapłaty produktów w koszyku, jeśli jest <strong>false</strong> to wyświetla kolumnę status
     */
    private void viewMyBasket(boolean b){
        buyButton.setVisible(b);
        statusColumn.setVisible(!b);
        sumLabel.setVisible(b);
        sumViewLabel.setVisible(b);
        currencyLabel.setVisible(b);
        checkBuy.setValue(b);
    }

    /**
     * Funkcja odpowiedzialna za wyświetlanie stage do dodawania produktów
     */
    @FXML
    private void addProductsOnAction() {
        ProductModel.setProductFxEdit(null);

        stageMain.getScene().getRoot().setDisable(true);

        setStageSettingProduct(new Stage());
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_PRODUCT_FXML)));
    }

    /**
     * Funkcja odpowiedzialna za wyświetlanie stage do zmiany ustawień użytkownika, który jest zalogowany
     */
    @FXML
    private void settingOnAction() {
        stageMain.getScene().getRoot().setDisable(true);

        Stage stage = LoginController.getStageSettingUser();
        stage.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_USER_FXML)));
    }

    /**
     * Wylogowanie się z aplikacji, ustawionei zalogowanie użytkownika na null i przełączenie sceny main.fxml na login.fxml
     */
    @FXML
    private void logoutOnAction(){
        LoginController.setUserFx(null);
        stageMain.close();
        stageMain.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_LOGIN_FXML)));
        stageMain.show();
    }

    /**
     * Funkcja odpowiedzialna za możliwości edytowania wybranego produktu
     */
    @FXML
    private void editProductOnAction() {
        setStageSettingProduct(new Stage());
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_PRODUCT_FXML)));
    }

    //-----------------Getter and Setter---------------------------------

    /**
     *  Zwraca stage stageSettingProdukt
     * @return stage dodawania lub edytowania produktu
     */
    public static Stage getStageSettingProduct() {
        return stageSettingProduct;
    }

    /**
     * Ustawia stage dla stageSettingProdukt
     * @param stageSettingProduct stage dla stageSettingProdukt
     */
    public static void setStageSettingProduct(Stage stageSettingProduct) {
        MainController.stageSettingProduct = stageSettingProduct;
    }

    //----------------------TopMenuBar------------------------------

    /**
     * Zamykanie aplikacji
     */
    @FXML
    private void closeApplication() {
        Optional<ButtonType> result = DialogUtils.confirmationDialog();
        if(result.get()==ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Ustawienie stylu Caspian dla aplikacji
     */
    @FXML
    private void setCaspian() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_CASPIAN);
    }

    /**
     * Ustawienie stylu Modena dla aplikacji
     */
    @FXML
    private void setModena() {
        Application.setUserAgentStylesheet(Application.STYLESHEET_MODENA);
    }

    /**
     * Wyświetlenie alertu z informacjami o aplikacji
     */
    @FXML
    private void about() {
        DialogUtils.dialogAboutApplication();
    }

    //-----------------------------Add balance--------------------------------------------

    /**
     * Wyświetlanie lub chowanie pane z możliwością dodawania środków na konto
     */
    @FXML
    private void showAddBalanceOnAction() {
        if (VBoxAddBalance.isVisible()) VBoxAddBalance.setVisible(false);
        else VBoxAddBalance.setVisible(true);
        addBalanceLabel.setText("0.00");
    }

    /**
     * Wyświetlanie kwoty na labelu addBalanceLabel
     * @param actionEvent Przycisk + lub -
     */
    @FXML
    private void addBalanceOnAction(ActionEvent actionEvent) {
        double value = Double.parseDouble(addBalanceLabel.getText());

        if (((Button) actionEvent.getSource()).getText().equals("+")){
            if(value<10000) addBalanceLabel.setText(Converter.addZero(value+100.0));
        }
        if (((Button) actionEvent.getSource()).getText().equals("-")){
            if(value>0) addBalanceLabel.setText(Converter.addZero(value-100.0));
        }
    }

    /**
     * Dodawanie kwoty na konto z aktualizacją w bazie danych
     */
    @FXML
    private void updateBalanceOnAction() {
        VBoxAddBalance.setVisible(false);

        userFx.setStanKonta(userFx.getStanKonta()+Double.parseDouble(addBalanceLabel.getText()));
        balanceLabel.setText(Converter.addZero(userFx.getStanKonta()));
        DbManager.update(Converter.converterToUser(userFx));

        addBalanceLabel.setText("0.00");

        checkBuy();
    }
}
