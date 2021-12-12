module am.sklep {
    requires javafx.controls;
    requires javafx.fxml;


    opens am.sklep to javafx.fxml;
    exports am.sklep;
}