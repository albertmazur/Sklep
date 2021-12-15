package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.Client;
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

    private Stage stageLogin = Login.getLoginStage();
    private Stage stageRegistration =LoginController.getStageRegistration();


    @FXML
    private void initialize(){
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
    }

    @FXML
    private void registrationOnAction(){
        String pass = passPasswordField.getText();
        String repeatPass = repeatPassPasswordField.getText();

        if(pass.equals(repeatPass)){
            Client newClient = new Client();
            newClient.setImie(nameTextField.getText());
            newClient.setNazwisko(surnameTextField.getText());
            newClient.setHaslo(pass);
            newClient.setLogin(loginTextField.getText());
            newClient.setEmail(emailTextField.getText());
            newClient.setRokUrodzenia(birthDatePicker.getValue());
            newClient.setStanKonta(1000.00);
            passFailLabel.setVisible(false);
            DbManager.save(newClient);

            LoginController.getStageRegistration().close();
            stageLogin.getScene().getRoot().setDisable(false);
        }
        else passFailLabel.setVisible(true);
        }
}
