package am.sklep.listener;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

public class CheckEmail implements ChangeListener<String> {
    private TextField textField;

    public CheckEmail(TextField textField) {
        this.textField = textField;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        boolean f=true;
        for(int i=0; i<t1.length(); i++){
            char c = t1.charAt(i);
            if((c<'a' || c>'z') && (c<'A' || c>'Z') && (c<'0' || c>'9') && c!='@' && c!='.'){
                f=false;
                break;
            }
        }

        if(f) textField.setText(t1);
        else textField.setText(s);
    }
}
