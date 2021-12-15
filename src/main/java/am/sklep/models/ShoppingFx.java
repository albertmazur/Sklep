package am.sklep.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;

public class ShoppingFx {
    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<UserFx> clientFx = new SimpleObjectProperty<>();
    private ObjectProperty<ProductFx> productFx = new SimpleObjectProperty<>();
    private ObjectProperty<LocalDate> dataDodanie = new SimpleObjectProperty<>();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public UserFx getClientFx() {
        return clientFx.get();
    }

    public ObjectProperty<UserFx> clientFxProperty() {
        return clientFx;
    }

    public void setClientFx(UserFx userFx) {
        this.clientFx.set(userFx);
    }

    public ProductFx getProductFx() {
        return productFx.get();
    }

    public ObjectProperty<ProductFx> productFxProperty() {
        return productFx;
    }

    public void setProductFx(ProductFx productFx) {
        this.productFx.set(productFx);
    }

    public LocalDate getDataDodanie() {
        return dataDodanie.get();
    }

    public ObjectProperty<LocalDate> dataDodanieProperty() {
        return dataDodanie;
    }

    public void setDataDodanie(LocalDate dataDodanie) {
        this.dataDodanie.set(dataDodanie);
    }
}
