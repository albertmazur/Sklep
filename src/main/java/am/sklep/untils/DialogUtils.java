package am.sklep.untils;

import am.sklep.controller.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Optional;
import java.util.ResourceBundle;

public class DialogUtils {
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();

    private static Window setIcon(Window window){
        Stage stage = (Stage) window;
        stage.getIcons().add(new Image(DialogUtils.class.getResourceAsStream(MainController.IMG_M)));
        return stage.getOwner();
    }

    public static Optional<ButtonType> confirmationDialog(){
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.initOwner(setIcon(confirmationDialog.getDialogPane().getScene().getWindow()));
        confirmationDialog.setTitle(bundle.getString("close"));
        confirmationDialog.setHeaderText(bundle.getString("close_text"));
        Optional<ButtonType> result = confirmationDialog.showAndWait();
        return result;
    }

    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.initOwner(setIcon(informationAlert.getDialogPane().getScene().getWindow()));
        informationAlert.setTitle(bundle.getString("about_title"));
        informationAlert.setHeaderText(bundle.getString("about_header"));
        informationAlert.setContentText(bundle.getString("about_text"));
        informationAlert.showAndWait();
    }

    public static void errorDialog(String error){
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.initOwner(setIcon(errorAlert.getDialogPane().getScene().getWindow()));
        errorAlert.setTitle(bundle.getString("error_title"));
        errorAlert.setHeaderText(bundle.getString("error_header"));
        errorAlert.setContentText(error);
        errorAlert.showAndWait();
    }
}
