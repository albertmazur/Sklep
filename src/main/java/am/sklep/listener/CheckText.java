package am.sklep.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CheckText implements ChangeListener<String> {
    private TextField textField;

    /**
     * Konstruktor, który przyjmuje TextFiled
     * @param textField TextFiled, na jakim ma działać filtrowanie
     */
    public CheckText(TextField textField) {
        this.textField = textField;
    }

    /**
     * Umożliwiania wpisanie tylko znaków do poprawnego wpisania loginu
     * @param observableValue Obiekt
     * @param s Stara wartość nasłuchiwanego textFiledu
     * @param t1 Nowa wartość nasłuchiwanego textFiledu
     */
    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        boolean f=true;
        for(int i=0; i<t1.length(); i++){
            char c = t1.charAt(i);
            if((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9')){
                f=false;
                break;
            }
        }

        if(f) textField.setText(t1);
        else textField.setText(s);
    }
}
