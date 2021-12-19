package am.sklep.database.models;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "cena", nullable = false)
    private Double cena;

    @Lob
    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    @Lob
    @Column(name = "opis", nullable = false)
    private String opis;

    @Lob
    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false)
    private User idUser;

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}