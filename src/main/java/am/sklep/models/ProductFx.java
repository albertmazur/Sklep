package am.sklep.models;

import javafx.beans.property.*;

public class ProductFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty nazwa = new SimpleStringProperty();
    private StringProperty opis = new SimpleStringProperty();
    private DoublePropertyBase cena = new SimpleDoubleProperty();
    private StringProperty status = new SimpleStringProperty();
    private ObjectProperty<UserFx> sprzedajacy = new SimpleObjectProperty<>();

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
