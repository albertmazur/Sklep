package am.sklep.controller;

import am.sklep.models.UserFx;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class Balance {
    @FXML
    private Label balanceLabel;

    private UserFx userFx;

    /**
     * Wyświetlenie stanu konta zalogowanego użytkownika
     */
    @FXML
    private void initialize(){
        userFx = LoginController.getUserFx();
        StringConverter<Number> stringConverter = new NumberStringConverter();
        balanceLabel.textProperty().bindBidirectional(userFx.stanKontaProperty(), stringConverter);
    }
}