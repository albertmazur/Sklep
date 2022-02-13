package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.User;
import am.sklep.listener.CheckEmail;
import am.sklep.listener.CheckText;
import am.sklep.listener.CheckName;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
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

    private Stage stageMain;
    private Stage stageSettingUser;
    private UserFx userFx;
    private ProductModel productModel;

    /**
     * Ustawianie sceny, kiedy jest uruchomiana (tytuł, ikony, brak możliwości rozszerzaniu okna i zgaszenie stage main) i przypisanie zalogowanego użytkownika do edycji
     */
    @FXML
    private void initialize() {
        stageMain = Login.getLoginStage();
        stageMain.getScene().getRoot().setDisable(true);

        userFx = LoginController.getUserFx();

        productModel = new ProductModel();

        stageSettingUser = LoginController.getStageSettingUser();
        stageSettingUser.getIcons().add(new Image(SettingUserController.class.getResourceAsStream(MainController.IMG_M)));
        stageSettingUser.setAlwaysOnTop(true);
        stageSettingUser.setResizable(false);
        stageSettingUser.setOnHiding(e -> {
            stageMain.getScene().getRoot().setDisable(false);
        });

        LocalDate maxDate = LocalDate.now();
        birthDatePicker.setDayCellFactory(d -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isAfter(maxDate));
            }
        });

        nameTextField.textProperty().addListener(new CheckName(nameTextField));
        surnameTextField.textProperty().addListener(new CheckName(surnameTextField));
        loginTextField.textProperty().addListener(new CheckText(loginTextField));
        emailTextField.textProperty().addListener(new CheckEmail(emailTextField));

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
            stageMain.getScene().getRoot().setDisable(false);
        });

        if (userFx != null) {
            stageSettingUser.setTitle(FxmlUtils.getResourceBundle().getString("account_setup"));
            registrationButton.setText(FxmlUtils.getResourceBundle().getString("save_changes"));

            nameTextField.setText(userFx.getName());
            surnameTextField.setText(userFx.getSurname());
            loginTextField.setText(userFx.getLogin());
            passPasswordField.setText(userFx.getHaslo());
            repeatPassPasswordField.setText(userFx.getHaslo());
            birthDatePicker.setValue(userFx.getDataUrodzenia());
            emailTextField.setText(userFx.getEmail());

            deleteUserButton.setVisible(true);
        } else {
            stageSettingUser.setTitle(FxmlUtils.getResourceBundle().getString("registration"));
            registrationButton.setText(FxmlUtils.getResourceBundle().getString("create_account"));
        }
        stageSettingUser.show();
    }

    /**
     * Edycja lub dodanie użytkownika do bazy
     */
    @FXML
    private void registrationOnAction() {
        try {
            String pass = passPasswordField.getText();
            String repeatPass = repeatPassPasswordField.getText();
            String email = emailTextField.getText();

            if(email.indexOf('@')==email.lastIndexOf('@') && email.lastIndexOf('@')!=-1 && email.indexOf('.')==email.lastIndexOf('.') && email.lastIndexOf('.')!=-1 && (email.lastIndexOf('@')+1)<email.lastIndexOf('.') && email.lastIndexOf('.')<email.length()) {
                if (pass.length() > 8) {
                    if (pass.equals(repeatPass)) {
                        if (userFx == null) {
                            User newUser = new User();
                            newUser.setImie(nameTextField.getText());
                            newUser.setNazwisko(surnameTextField.getText());
                            newUser.setHaslo(pass);
                            newUser.setLogin(loginTextField.getText());
                            newUser.setEmail(emailTextField.getText());
                            newUser.setRokUrodzenia(birthDatePicker.getValue());
                            newUser.setStanKonta(1000.00);
                            newUser.setCzyAktywne(1);

                            DbManager.save(newUser);
                        } else {
                            userFx.setName(nameTextField.getText());
                            userFx.setSurname(surnameTextField.getText());
                            userFx.setHaslo(pass);
                            userFx.setLogin(loginTextField.getText());
                            userFx.setEmail(emailTextField.getText());
                            userFx.setDataUrodzenia(birthDatePicker.getValue());
                            System.out.println(userFx.getCzyAktywne());
                            DbManager.update(Converter.converterToUser(userFx));
                        }
                        passFailLabel.setVisible(false);

                        stageSettingUser.close();
                        stageMain.getScene().getRoot().setDisable(false);
                    } else {
                        passFailLabel.setText(FxmlUtils.getResourceBundle().getString("not_similar_passwords"));
                        passFailLabel.setVisible(true);
                    }
                } else {
                    passFailLabel.setText(FxmlUtils.getResourceBundle().getString("too_short_password"));
                    passFailLabel.setVisible(true);
                }
            } else {
                passFailLabel.setText(FxmlUtils.getResourceBundle().getString("incorrect_email"));
                passFailLabel.setVisible(true);
            }
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    /**
     * Usuwanie użytkownika z bazy
     */
    @FXML
    private void deleteUserOnAction(){
        try {
            productModel.myProducts();
            if(productModel.getProductFxMyObservableList().isEmpty()){
                stageSettingUser.close();

                User user = Converter.converterToUser(userFx);

                try{
                    DbManager.delete(user);
                }
                catch (Exception e){
                    user.setCzyAktywne(0);
                    DbManager.update(user);
                }
                LoginController.setUserFx(null);

                stageMain.getScene().getRoot().setDisable(false);
                stageMain.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_LOGIN_FXML)));
                stageMain.show();
            }
            else{
                passFailLabel.setText(FxmlUtils.getResourceBundle().getString("remove_products"));
                passFailLabel.setVisible(true);
            }
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }
}
