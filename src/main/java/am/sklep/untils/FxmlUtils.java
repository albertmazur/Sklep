package am.sklep.untils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlUtils {
    public static Pane FxmlLoader(String path){
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(path),getResourceBundle());

        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static FXMLLoader getFxmlLoader(String path){
        FXMLLoader fxmlLoader = new FXMLLoader(FxmlUtils.class.getResource(path), getResourceBundle());
        return fxmlLoader;
    }

    public static ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("bundles/languages");
    }
}
