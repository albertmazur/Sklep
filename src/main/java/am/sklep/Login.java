package am.sklep;

import am.sklep.untils.FxmlUtils;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Scene scene = new Scene(FxmlUtils.FxmlLoader("/view/login.fxml"));
        stage.setTitle(FxmlUtils.getResourceBundle().getString("title_application"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}