package am.sklep.models;

import am.sklep.untils.FxmlUtils;
import javafx.beans.property.*;

/**
 * Klasa umożliwiająca operować na obiektach Produkt
 */
public class ProductFx {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nazwa = new SimpleStringProperty();
    private final StringProperty opis = new SimpleStringProperty();
    private final DoublePropertyBase cena = new SimpleDoubleProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final ObjectProperty<UserFx> sprzedajacy = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNazwa() {
        return nazwa.get();
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa.set(nazwa);
    }

    public String getOpis() {
        return opis.get();
    }

    public StringProperty opisProperty() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis.set(opis);
    }

    public double getCena() {
        return cena.get();
    }

    public DoubleProperty cenaProperty() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena.set(cena);
    }

    public String getStatus() {
        return status.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty stringPropertyName(){
        String name = status.get();
        if (name.equals(ProductModel.ADDED)) name = FxmlUtils.getResourceBundle().getString("added");
        if (name.equals(ProductModel.BOUGHT)) name = FxmlUtils.getResourceBundle().getString("bought");
        if (name.equals(ProductModel.TO_BUY)) name = FxmlUtils.getResourceBundle().getString("to_buy");
        return new SimpleStringProperty(name);
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public UserFx getSprzedajacy() {
        return sprzedajacy.get();
    }

    public ObjectProperty<UserFx> sprzedajacyProperty() {
        return sprzedajacy;
    }

    public void setSprzedajacy(UserFx sprzedajacy) {
        this.sprzedajacy.set(sprzedajacy);
    }
}
