package am.sklep.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CheckPrice implements ChangeListener<String> {
    private TextField textField;

    /**
     * Konstruktor, który przyjmuje TextFiled
     * @param textField TextFiled, na jakim ma działać filtrowanie
     */
    public CheckPrice(TextField textField) {
        this.textField = textField;
    }

    /**
     * Umożliwiania wpisanie tylko znaków do poprawnego wpisania ceny
     * @param observableValue Obiekt
     * @param s Stara wartość nasłuchiwanego textFiledu
     * @param t1 Nowa wartość nasłuchiwanego textFiledu
     */
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        if(t1.length()>0) {
            boolean f = true;
            for (int i = 0; i < t1.length(); i++) {
                char c = t1.charAt(i);
                if ((c < '0' || c > '9') && !(c == '.' && t1.indexOf('.') == t1.lastIndexOf('.') && (t1.indexOf('.') == (t1.length() - 1) || t1.indexOf('.') == (t1.length() - 2) || t1.indexOf('.') == (t1.length() - 3)))) {
                    f = false;
                    break;
                }
            }
            if (f) textField.setText(t1);
            else textField.setText(s);
        }
    }
}
