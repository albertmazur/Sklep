package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.User;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

public class SettingUserController {
    @FXML
    private Button deleteUserButton;
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
    private Stage stageSettingUser;
    private UserFx userFx;

    @FXML
    private void initialize(){
        stageLogin = Login.getLoginStage();

        userFx = LoginController.getUserFx();

        stageSettingUser = LoginController.getStageSettingUser();
        stageSettingUser.getIcons().add(new Image(SettingUserController.class.getResourceAsStream(MainController.IMG_M)));
        stageSettingUser.setAlwaysOnTop(true);
        stageSettingUser.setResizable(false);
        stageSettingUser.setTitle("Rejestraction");
        stageSettingUser.setOnHiding(e->{
            stageLogin.getScene().getRoot().setDisable(false);
        });

        LocalDate maxDate = LocalDate.now();
        birthDatePicker.setDayCellFactory(d-> new DateCell(){
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate));
            }
        });

        registrationButton.disableProperty().bind(
            passPasswordField.textProperty().isEmpty()
            .or(repeatPassPasswordField.textProperty().isEmpty())
            .or(nameTextField.textProperty().isEmpty())
            .or(surnameTextField.textProperty().isEmpty())
            .or(loginTextField.textProperty().isEmpty())
            .or(birthDatePicker.valueProperty().isNull())
            .or(emailTextField.textProperty().isEmpty())
        );

        stageSettingUser.setOnHidden(event -> {
            stageLogin.getScene().getRoot().setDisable(false);
        });

        if(userFx!=null){
            registrationButton.setText("Zapisz zmiany");

            nameTextField.setText(userFx.getName());
            surnameTextField.setText(userFx.getSurname());
            loginTextField.setText(userFx.getLogin());
            passPasswordField.setText(userFx.getHaslo());
            repeatPassPasswordField.setText(userFx.getHaslo());
            birthDatePicker.setValue(userFx.getDataUrdzenia());
            emailTextField.setText(userFx.getEmail());

            deleteUserButton.setVisible(true);
        }
        stageSettingUser.show();
    }

    @FXML
    private void registrationOnAction(){
        String pass = passPasswordField.getText();
        String repeatPass = repeatPassPasswordField.getText();

        if(pass.equals(repeatPass)){
            if(userFx==null){
                User newUser = new User();
                newUser.setImie(nameTextField.getText());
                newUser.setNazwisko(surnameTextField.getText());
                newUser.setHaslo(pass);
                newUser.setLogin(loginTextField.getText());
                newUser.setEmail(emailTextField.getText());
                newUser.setRokUrodzenia(birthDatePicker.getValue());
                newUser.setStanKonta(1000.00);

                DbManager.save(newUser);
            }
            else{
                userFx.setName(nameTextField.getText());
                userFx.setSurname(surnameTextField.getText());
                userFx.setHaslo(pass);
                userFx.setLogin(loginTextField.getText());
                userFx.setEmail(emailTextField.getText());
                userFx.setDataUrdzenia(birthDatePicker.getValue());

                DbManager.update(Converter.converterToUser(userFx));
            }

            passFailLabel.setVisible(false);

            stageSettingUser.close();
            stageLogin.getScene().getRoot().setDisable(false);
        }
        else passFailLabel.setVisible(true);
        }

    @FXML
    private void deleteUserOnAction(){
        ProductModel productModel = new ProductModel();
        if(!productModel.getProductFxMyObservableList().isEmpty()){

            stageSettingUser.close();

            DbManager.delete(Converter.converterToUser(userFx));
            Login.setLoginStage(null);

            stageLogin.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_LOGIN_FXML)));
            stageLogin.getScene().getRoot().setDisable(false);
        }
        else passFailLabel.setVisible(true);
    }
}
