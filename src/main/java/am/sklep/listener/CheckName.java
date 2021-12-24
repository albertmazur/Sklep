package am.sklep.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CheckName implements ChangeListener<String> {
    private TextField textField;

    public CheckName(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        boolean f=true;
        if (t1.length()>0) {
            if (t1.charAt(0) >= 'A' && t1.charAt(0) <= 'Z') {
                for (int i = 1; i < t1.length(); i++) {
                    char c = t1.charAt(i);
                    if (c < 'a' || c > 'z') {
                        f = false;
                        break;
                    }
                }
            } else f = false;
        }
        if(f) textField.setText(t1);
        else textField.setText(s);
    }
}
