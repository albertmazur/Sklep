package am.sklep.untils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle(bundle.getString("close"));
        confirmationDialog.setHeaderText(bundle.getString("close_text"));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("about_title"));
        informationAlert.setHeaderText(bundle.getString("about_header"));
        informationAlert.setContentText(bundle.getString("about_text"));
        informationAlert.showAndWait();
    }

    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString("error_title"));
        errorAlert.setHeaderText(bundle.getString("error_header"));
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}
