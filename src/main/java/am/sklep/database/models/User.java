package am.sklep.database.models;

import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User implements BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Lob
    @Column(name = "haslo", nullable = false)
    private String haslo;

    @Lob
    @Column(name = "imie", nullable = false)
    private String imie;

    @Lob
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Lob
    @Column(name = "nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "rok_urodzenia", nullable = false)
    private LocalDate rokUrodzenia;

    @Column(name = "stan_konta", nullable = false)
    private Double stanKonta;

    @Column(name = "czy_aktywne", nullable = false)
    private Integer czyAktywne;

    public Integer getCzyAktywne() {
        return czyAktywne;
    }

    public void setCzyAktywne(Integer czyAktywne) {
        this.czyAktywne = czyAktywne;
    }

    public Double getStanKonta() {
        return stanKonta;
    }

    public void setStanKonta(Double stanKonta) {
        this.stanKonta = stanKonta;
    }

    public LocalDate getRokUrodzenia() {
        return rokUrodzenia;
    }

    public void setRokUrodzenia(LocalDate rokUrodzenia) {
        this.rokUrodzenia = rokUrodzenia;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }
    @Type(type="encryptedString")
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}