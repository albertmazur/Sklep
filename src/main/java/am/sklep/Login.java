package am.sklep;

import am.sklep.controller.MainController;
import am.sklep.untils.Dane;
import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        stage.show();

        new Dane();
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