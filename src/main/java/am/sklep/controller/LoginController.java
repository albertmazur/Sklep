package am.sklep.controller;

import am.sklep.Login;
import am.sklep.SingletonConnection;
import am.sklep.database.models.Client;
import am.sklep.untils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    private Label loginFailLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordPasswordField;

    private SessionFactory sessionFactory = SingletonConnection.getSessionFactory();

    private Stage stageLogin = Login.getLoginStage();

    private static Stage stageRegistration = new Stage();

    public static Stage getStageRegistration() {
        return stageRegistration;
    }

    @FXML
    private void logInOnAction(){
        String login = loginTextField.getText();
        String pass = passwordPasswordField.getText();
        boolean log = true;
        Session session = sessionFactory.openSession();
        List<Client> clients = session.createSQLQuery("SELECT * from client").addEntity(Client.class).list();
        for(Client client: clients){
            if(login.equals(client.getLogin()) && pass.equals(client.getHaslo())){
                stageLogin.close();
                stageLogin = new Stage();
                Scene scene = new Scene(FxmlUtils.FxmlLoader("/view/main.fxml"));
                stageLogin.setScene(scene);
                stageLogin.show();

                log=false;
                loginFailLabel.setVisible(log);
            }
            else{
                loginFailLabel.setVisible(log);
                loginTextField.clear();
                passwordPasswordField.clear();
                log=true;
            }
        }
        session.close();
    }

    @FXML
    private void registrationOnAction(){
        FXMLLoader loader = FxmlUtils.getFxmlLoader("/view/registration.fxml");
        Scene sceneLogin = stageLogin.getScene();
        sceneLogin.getRoot().setDisable(true);
        try {
            Scene scene = new Scene(loader.load());
            stageRegistration.setScene(scene);
            stageRegistration.setAlwaysOnTop(true);
            stageRegistration.setTitle("Rejestraction");
            stageRegistration.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
}