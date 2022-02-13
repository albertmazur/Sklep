package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
     * Odnośniki do pliku products_to_buy.fxml
     */
    public static final String VIEW_PRODUCTS_TO_BUY_FXML = "/view/products_to_buy.fxml";
    /**
     * Odnośniki do pliku basket.fxml
     */
    public static final String VIEW_BASKET_FXML = "/view/basket.fxml";
    /**
     * Odnośniki do pliku my_products.fxml
     */
    public static final String VIEW_MY_PRODUCTS_FXML = "/view/my_products.fxml";
    /**
     * Odnośniki do pliku iconM.png
     */
    public static final String IMG_M = "/img/iconM.png";

    @FXML
    private VBox VBoxAddBalance;
    @FXML
    private Label addBalanceLabel;
    @FXML
    private BorderPane borderPane;

    private UserFx userFx;
    private Stage stageMain;
    private static Stage stageSettingProduct;
    private static Basket basket;

    public static void setBasket(Basket basket) {
        MainController.basket = basket;
    }

    /**
     * Ustawienie stage wartości dla Maina
     */
    @FXML
    private void initialize(){
        stageMain = Login.getLoginStage();
        stageMain.setResizable(true);
        stageMain.setMaximized(true);
        stageMain.setMinWidth(850);
        stageMain.setMinHeight(500);

        userFx = LoginController.getUserFx();

        productsOnAction();
    }

    /**
     * Wyświetlenie produktów do kupienia
     */
    @FXML
    private void productsOnAction() {
        borderPane.setCenter(FxmlUtils.FxmlLoader(VIEW_PRODUCTS_TO_BUY_FXML));
    }

    /**
     * Wyświetlenie produktów, które zostały dodane do koszyka
     */
    @FXML
    private void basketOnAction() {
        borderPane.setCenter(FxmlUtils.FxmlLoader(VIEW_BASKET_FXML));
    }

    /**
     * Wyświetlanie produktów, które należą do zalogowanego użytkownika
     */
    @FXML
    private void boughtOnAction() {
        borderPane.setCenter(FxmlUtils.FxmlLoader(VIEW_MY_PRODUCTS_FXML));
    }

    /**
     * Funkcja odpowiedzialna za wyświetlanie stage do dodawania produktów
     */
    @FXML
    private void addProductsOnAction() {
        ProductModel.setProductFxEdit(null);

        setStageSettingProduct(new Stage());
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader(VIEW_SETTING_PRODUCT_FXML)));
    }

    /**
     * Funkcja odpowiedzialna za wyświetlanie stage do zmiany ustawień użytkownika, który jest zalogowany
     */
    @FXML
    private void settingOnAction() {
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
        VBoxAddBalance.setVisible(!VBoxAddBalance.isVisible());
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
        try {
            VBoxAddBalance.setVisible(false);

            userFx.setStanKonta(userFx.getStanKonta()+Double.parseDouble(addBalanceLabel.getText()));
            DbManager.update(Converter.converterToUser(userFx));

            basket.checkBuy();
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
            userFx.setStanKonta(userFx.getStanKonta()-Double.parseDouble(addBalanceLabel.getText()));
        }
        finally {
            addBalanceLabel.setText("0.00");
        }
    }
}
