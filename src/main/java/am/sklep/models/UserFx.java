package am.sklep.models;

import javafx.beans.property.*;

import java.time.LocalDate;

/**
 * Klasa umożliwiająca operować na obiektach User
 */
public class UserFx {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty surname = new SimpleStringProperty();
    private final StringProperty login = new SimpleStringProperty();
    private final StringProperty haslo = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dataUrodzenia = new SimpleObjectProperty<>();
    private final StringProperty email = new SimpleStringProperty();
    private final DoubleProperty stanKonta = new SimpleDoubleProperty();
    private final IntegerProperty czyAktywne = new SimpleIntegerProperty();

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

    public LocalDate getDataUrodzenia() {
        return dataUrodzenia.get();
    }

    public ObjectProperty<LocalDate> dataUrodzeniaProperty() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(LocalDate dataUrodzenia) {
        this.dataUrodzenia.set(dataUrodzenia);
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

    public int getCzyAktywne() {
        return czyAktywne.get();
    }

    public IntegerProperty czyAktywneProperty() {
        return czyAktywne;
    }

    public void setCzyAktywne(int czyAktywne) {
        this.czyAktywne.set(czyAktywne);
    }

    @Override
    public String toString() {
        return name.getValue() + " " + surname.getValue();
    }
}
