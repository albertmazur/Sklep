package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.User;
import am.sklep.models.UserFx;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegistrationController {
    @FXML
    private Label passFailLabel;
    @FXML
    private PasswordField passPasswordField;
    @FXML
    private PasswordField repeatPassPasswordField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField loginTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private DatePicker birthDatePicker;
    @FXML
    private Button registrationButton;

    private Stage stageLogin;
    private Stage stageRegistration;
    private UserFx userFx;

    @FXML
    private void initialize(){
        stageLogin = Login.getLoginStage();
        stageRegistration = LoginController.getStageRegistration();
        userFx = LoginController.getUserFx();

        registrationButton.disableProperty().bind(
                    passPasswordField.textProperty().isEmpty()
                .or(repeatPassPasswordField.textProperty().isEmpty())
                .or(nameTextField.textProperty().isEmpty())
                .or(surnameTextField.textProperty().isEmpty())
                .or(loginTextField.textProperty().isEmpty())
                .or(birthDatePicker.valueProperty().isNull())
                .or(emailTextField.textProperty().isEmpty())
        );

        stageRegistration.setOnHidden(event -> {
            stageLogin.getScene().getRoot().setDisable(false);
        });
        System.out.println(userFx);
        if(userFx!=null){
            emailTextField.textProperty().bindBidirectional(userFx.emailProperty());
        }
    }

    @FXML
    private void registrationOnAction(){
        String pass = passPasswordField.getText();
        String repeatPass = repeatPassPasswordField.getText();

        if(pass.equals(repeatPass)){
            User newUser = new User();
            newUser.setImie(nameTextField.getText());
            newUser.setNazwisko(surnameTextField.getText());
            newUser.setHaslo(pass);
            newUser.setLogin(loginTextField.getText());
            newUser.setEmail(emailTextField.getText());
            newUser.setRokUrodzenia(birthDatePicker.getValue());
            newUser.setStanKonta(1000.00);
            passFailLabel.setVisible(false);
            DbManager.save(newUser);

            stageRegistration.close();
            stageLogin.getScene().getRoot().setDisable(false);
        }
        else passFailLabel.setVisible(true);
        }
}
