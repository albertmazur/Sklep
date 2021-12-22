package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.User;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.util.List;

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

    @FXML
    private void initialize(){
        stageSettingUser = new Stage();

        if(userFx==null) stageMain = Login.getLoginStage();
        stageMain.setTitle(FxmlUtils.getResourceBundle().getString("title_application"));
        stageMain.getIcons().add(new Image(LoginController.class.getResourceAsStream(MainController.IMG_M)));
        stageMain.setResizable(false);
        stageMain.setOnCloseRequest(c ->{
            System.exit(0);
        });
        stageMain.show();
    }

    @FXML
    private void logInOnAction(){

        String login = loginTextField.getText();
        String pass = passwordPasswordField.getText();
        boolean log = true;
        List<User> users = DbManager.download(User.class);
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
                loginFailLabel.setVisible(log);
                loginTextField.clear();
                passwordPasswordField.clear();
                loginTextField.requestFocus();
                log=true;
            }
        }
    }

    @FXML
    private void registrationOnAction(){
        stageMain.getScene().getRoot().setDisable(true);

        stageSettingUser.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_SETTING_USER_FXML)));
    }

    @FXML
    private void loginPassOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            logInOnAction();
        }
    }

    @FXML
    private void loginOnKeyPressed(KeyEvent keyEvent) {
        if(keyEvent.getCode() == KeyCode.ENTER){
            passwordPasswordField.requestFocus();
        }
    }

    public static UserFx getUserFx() {
        return userFx;
    }

    public static void setUserFx(UserFx userFx) {
        LoginController.userFx = userFx;
    }

    public static Stage getStageSettingUser() {
        return stageSettingUser;
    }
}