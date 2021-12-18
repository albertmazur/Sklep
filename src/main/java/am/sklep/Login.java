package am.sklep;

import am.sklep.untils.Dane;
import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    private static Stage loginStage;

    @Override
    public void start(Stage stage) throws IOException {
        loginStage = stage;
        Scene scene = new Scene(FxmlUtils.FxmlLoader("/view/login.fxml"));
        stage.setTitle(FxmlUtils.getResourceBundle().getString("title_application"));
        stage.setScene(scene);
        stage.show();

        new Dane();
    }

    public static void main(String[] args) {
        launch();
    }

    public static Stage getLoginStage() {
        return loginStage;
    }

    public static void setLoginStage(Stage loginStage) {
        Login.loginStage = loginStage;
    }
}