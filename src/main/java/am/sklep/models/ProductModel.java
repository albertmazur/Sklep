package am.sklep.models;

import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class ProductModel {
    ObservableList<ProductFx> productFxObservableList = FXCollections.observableArrayList();

    public void downloadProduct(){
        List<Product> list = DbManager.downloadProduct();
        list.forEach(item->{
            ProductFx productFx = new ProductFx();
            productFx.setId(item.getId());
            productFx.setNazwa(item.getNazwa());
            productFx.setOpis(item.getOpis());
            productFx.setCena(item.getCena());
            productFx.setStatus(item.getStatus());
            UserFx userFx = new UserFx();
            userFx.setId(item.getIdUser().getId());
            userFx.setName(item.getIdUser().getImie());
            userFx.setSurname(item.getIdUser().getNazwisko());
            userFx.setLogin(item.getIdUser().getLogin());
            userFx.setHaslo(item.getIdUser().getHaslo());
            userFx.setDataUrdzenia(item.getIdUser().getRokUrodzenia());
            userFx.setEmail(item.getIdUser().getEmail());
            userFx.setStanKonta(item.getIdUser().getStanKonta());
            productFx.setSprzedajacy(userFx);
            productFxObservableList.add(productFx);
        });
    }

    public ObservableList<ProductFx> getProductFxObservableList() {
        return productFxObservableList;
    }

    public void setProductFxObservableList(ObservableList<ProductFx> productFxObservableList) {
        this.productFxObservableList = productFxObservableList;
    }
}
