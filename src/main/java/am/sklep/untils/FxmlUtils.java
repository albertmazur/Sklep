package am.sklep.untils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ResourceBundle;

public class FxmlUtils {
    /**
     * Ustawia scene dal stage
     * @param path Ścieżka do pliku fxml
     * @return Pane z ustawioną sceną
     */
    public static Pane FxmlLoader(String path){
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(path), getResourceBundle());

        try {
            return loader.load();
        }
        catch (IOException e) {
            DialogUtils.errorDialog(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    /**
     * ResourceBundle do ustawienia napisów dla danego języka
     * @return ResourceBundle
     */
    public static ResourceBundle getResourceBundle(){
        return ResourceBundle.getBundle("bundles/languages");
    }
}
