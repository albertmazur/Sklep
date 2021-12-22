package am.sklep.untils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Zamknij");
        confirmationDialog.setHeaderText("Czy chcesz zamjnąć apkikację");
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("O Aplikacji");
        informationAlert.setHeaderText("Aplikacje Sklep");
        informationAlert.setContentText("Aplikacje wykonał Albert Mazur");
        informationAlert.showAndWait();
    }

    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle("Błąd");
        errorAlert.setHeaderText("Błąd z bazą danych");
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}
