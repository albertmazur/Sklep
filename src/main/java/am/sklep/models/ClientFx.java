package am.sklep.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class ClientFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty surname = new SimpleStringProperty();
    private StringProperty login = new SimpleStringProperty();
    private StringProperty haslo = new SimpleStringProperty();
    private ObjectProperty<LocalDate> dataUrdzenia = new SimpleObjectProperty<>();
    private StringProperty email = new SimpleStringProperty();
    private DoubleProperty stanKonta = new SimpleDoubleProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSurname() {
        return surname.get();
    }

    public StringProperty surnameProperty() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public String getLogin() {
        return login.get();
    }

    public StringProperty loginProperty() {
        return login;
    }

    public void setLogin(String login) {
        this.login.set(login);
    }

    public String getHaslo() {
        return haslo.get();
    }

    public StringProperty hasloProperty() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo.set(haslo);
    }

    public LocalDate getDataUrdzenia() {
        return dataUrdzenia.get();
    }

    public ObjectProperty<LocalDate> dataUrdzeniaProperty() {
        return dataUrdzenia;
    }

    public void setDataUrdzenia(LocalDate dataUrdzenia) {
        this.dataUrdzenia.set(dataUrdzenia);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public double getStanKonta() {
        return stanKonta.get();
    }

    public DoubleProperty stanKontaProperty() {
        return stanKonta;
    }

    public void setStanKonta(double stanKonta) {
        this.stanKonta.set(stanKonta);
    }

    @Override
    public String toString() {
        return name.getValue() + " " + surname.getValue();
    }
}
