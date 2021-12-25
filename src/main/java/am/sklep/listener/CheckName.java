package am.sklep.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CheckName implements ChangeListener<String> {
    private TextField textField;

    /**
     * Konstruktor, który przyjmuje TextFiled
     * @param textField TextFiled, na jakim ma działać filtrowanie
     */
    public CheckName(TextField textField) {
        this.textField = textField;
    }

    /**
     * Umożliwiania wpisanie tylko znaków do poprawnego wpisania imienia i nazwiska
     * @param observableValue Obiekt
     * @param s Stara wartość nasłuchiwanego textFiledu
     * @param t1 Nowa wartość nasłuchiwanego textFiledu
     */
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        boolean f=true;
        if(t1.length()>0) {
            char t = t1.charAt(0);
            if (t >= 'A' && t <= 'Z' || (t=='Ą' || t=='Ć' || t=='Ę' || t=='Ł' || t=='Ń' || t=='Ó' || t=='Ś' || t=='Ź' || t=='Ż')) {
                for (int i = 1; i < t1.length(); i++) {
                    char c = t1.charAt(i);
                    if (c < 'a' || c > 'z' && !(c=='ą' || c=='ć' || c=='ę' || c=='ł' || c=='ń' || c=='ó' ||  c=='ś' || c=='ź' || c=='ż')) {
                        f = false;
                        break;
                    }
                }
            }
            else f = false;
            if (f) textField.setText(t1);
            else textField.setText(s);
        }
    }
}
