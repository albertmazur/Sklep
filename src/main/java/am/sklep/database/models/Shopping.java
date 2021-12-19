package am.sklep.database.models;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "shopping")
public class Shopping implements BaseModel{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "data_zakupu", nullable = false)
    private LocalDate dataZakupu;

    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product idProduct;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Product getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Product idProduct) {
        this.idProduct = idProduct;
    }

    public LocalDate getDataZakupu() {
        return dataZakupu;
    }

    public void setDataZakupu(LocalDate dataZakupu) {
        this.dataZakupu = dataZakupu;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}