package am.sklep.untils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlUtils {
    public static Pane FxmlLoader(String path){
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(path));

        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("bundles/languages");
    }
}
