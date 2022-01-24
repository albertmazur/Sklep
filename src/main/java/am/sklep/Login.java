package am.sklep;

import am.sklep.controller.LoginController;
import am.sklep.controller.MainController;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Optional;

public class Login extends Application {
    private static Stage loginStage;

    /**
     * Uruchomienie aplikacji, wyświetlenie sceny logowanie i wgranie początkowych danych
     * @param stage Główny stage
     */
    @Override
    public void start(Stage stage){
        setLoginStage(stage);
        stage.setScene(new Scene(FxmlUtils.FxmlLoader(MainController.VIEW_LOGIN_FXML)));
        stage.setTitle(FxmlUtils.getResourceBundle().getString("title_application"));
        stage.getIcons().add(new Image(LoginController.class.getResourceAsStream(MainController.IMG_M)));
        stage.setOnCloseRequest(c ->{
            c.consume();
            Optional<ButtonType> result = DialogUtils.confirmationDialog();
            if(result.get()==ButtonType.OK){
                Platform.exit();
                System.exit(0);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Zwraca głównego stage, dzięki czemu można zmieniać sceny
     * @return Zwraca w głównego stage
     */
    public static Stage getLoginStage() {
        return loginStage;
    }

    /**
     * Ustawania główny stage
     * @param loginStage Główny stage
     */
    public static void setLoginStage(Stage loginStage) {
        Login.loginStage = loginStage;
    }
}