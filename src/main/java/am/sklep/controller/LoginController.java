package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.User;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

/**
 * Obsługuje scene login.fxml (logowanie do sklepu)
 */
public class LoginController {

    @FXML
    private Label loginFailLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private static Stage stageMain;
    private static Stage stageSettingUser;
    private static UserFx userFx;

    /**
     * Ustawianie sceny, kiedy jest uruchomiana (tytuł, ikony, brak możliwości rozszerzaniu okna i wyłączenie aplikacji przy naciśnięci X) i przypisanie Stage do stageSettingUser
     */
    @FXML
    private void initialize(){
        stageMain = Login.getLoginStage();
        stageMain.show();
        stageMain.setTitle(FxmlUtils.getResourceBundle().getString("title_application"));
        stageMain.getIcons().add(new Image(LoginController.class.getResourceAsStream(MainController.IMG_M)));
        stageMain.setResizable(false);
        stageMain.setMaximized(false);
        stageMain.setOnCloseRequest(c ->{
            Optional<ButtonType> result = DialogUtils.confirmationDialog();
            if(result.get()==ButtonType.OK){
                Platform.exit();
                System.exit(0);
            }
        });
        setStageSettingUser(new Stage());
    }

    /**
     * Logowanie do aplikacji
     */
    @FXML
    private void logInOnAction(){
        String login = loginTextField.getText();
        String pass = passwordPasswordField.getText();
        boolean log = true;
        List<User> users = DbManager.download(User.class);
        if(users.size()==0){
            failLogin(log);
            log=true;
        }
        for(User user : users){
            if(login.equals(user.getLogin()) && pass.equals(user.getHaslo()) && user.getCzyAktywne()==1){
                setUserFx(Converter.converterToUserFX(user));

                log=false;
                loginFailLabel.setVisible(log);

                stageMain.close();
                stageMain.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_MAIN_FXML)));
                stageMain.show();
            }
            else{
                failLogin(log);
                log=true;
            }
        }
    }

    /**
     * Funkcja wywoływana, kiedy jest nie poprawny login lub hasło
     * @param log warunek wyświetlenia komunikatu
     */
    private void failLogin(boolean log){
        loginFailLabel.setVisible(log);
        loginTextField.clear();
        passwordPasswordField.clear();
        loginTextField.requestFocus();
    }

    /**
     * Przypisanie stage do stageSettingUser i tym samym jego pokazanie
     */
    @FXML
    private void registrationOnAction(){
        stageMain.getScene().getRoot().setDisable(true);
        stageSettingUser.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_SETTING_USER_FXML)));
    }

    /**
     * Wywołanie funkcji logInOnAction (logowanie) po naciśnięciu Enter w passwordPasswordFiled (miejsce na wspinanie hasła)
     * @param keyEvent Jaki klawisz został wciśnięty
     */
    @FXML
    private void loginPassOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            logInOnAction();
        }
    }

    /**
     * Ustawia focus na passwordPasswordField po naciśnięciu Enter w loginTextFiled
     * @param keyEvent Jaki klawisz został wciśnięty
     */
    @FXML
    private void loginOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            passwordPasswordField.requestFocus();
        }
    }

    /**
     * Zwraca zalogowanego użytkownika
     * @return Zwraca obiekt UserFx
     */
    public static UserFx getUserFx() {
        return userFx;
    }

    /**
     * Ustawia zalogowanego użytkownika
     * @param userFx Przyjmuje obiekty UserFx
     */
    public static void setUserFx(UserFx userFx) {
        LoginController.userFx = userFx;
    }

    /**
     * Zwraca stage stageSettingUser (rejestracja użytkownika)
     * @return Zwraca stage rejestracji
     */
    public static Stage getStageSettingUser() {
        return stageSettingUser;
    }

    /**
     * Ustawienie stage dla stageSettingUser (rejestracja użytkownika)
     * @param stageSettingUser Stage jaki ma buć dla stageSettingUser
     */
    public static void setStageSettingUser(Stage stageSettingUser) {
        LoginController.stageSettingUser = stageSettingUser;
    }
}