package am.sklep.models;

import am.sklep.controller.LoginController;
import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.untils.Converter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ProductModel {

    public final static String DO_KUPIENIA = "Do kupienia";
    public final static String KUPIONE = "Kupione";

    private UserFx userFx = LoginController.getUserFx();
    private ObservableList<ProductFx> productFxToBuyObservableList = FXCollections.observableArrayList();
    private ObservableList<ProductFx> productFxBuyObservableList = FXCollections.observableArrayList();
    private ObservableList<ProductFx> productFxMyObservableList = FXCollections.observableArrayList();

    public void downloadProduct(){
        List<Product> list = DbManager.downloadProduct();
        productFxToBuyObservableList.clear();
        list.forEach(item->{
            if(item.getStatus().equals(DO_KUPIENIA) && item.getIdUser().getId()!=userFx.getId()){
                ProductFx productFx = Converter.converterToProductFX(item);
                productFxToBuyObservableList.add(productFx);
           }
        });
    }

    public void buy(){
            productFxBuyObservableList.forEach( item ->{
                Shopping shopping = new Shopping();
                shopping.setIdProduct(Converter.converterToProduct(item));
                shopping.setIdUser(Converter.converterToUser(userFx));
                shopping.setDataZakupu(LocalDate.now());
                DbManager.save(shopping);

                userFx.setStanKonta(userFx.getStanKonta() - item.getCena());

                item.getSprzedajacy().setStanKonta(item.getSprzedajacy().getStanKonta()+ item.getCena());
                DbManager.update(Converter.converterToUser(item.getSprzedajacy()));

                item.setStatus(ProductModel.KUPIONE);
                item.setSprzedajacy(userFx);
                DbManager.update(Converter.converterToProduct(item));
            });
            DbManager.update(Converter.converterToUser(userFx));
            productFxBuyObservableList.clear();
    }

    public void myProducts(){
        List<Product> myProducts = DbManager.downloadProduct();
        productFxMyObservableList.clear();
        myProducts.forEach(item ->{
            if(item.getIdUser().getId() == userFx.getId()) productFxMyObservableList.add(Converter.converterToProductFX(item));
        });
    }

    public ObservableList<ProductFx> getProductFxToBuyObservableList() {
        return productFxToBuyObservableList;
    }

    public void setProductFxObservableList(ObservableList<ProductFx> productFxObservableList) {
        this.productFxToBuyObservableList = productFxObservableList;
    }

    public ObservableList<ProductFx> getProductFxBuyObservableList() {
        return productFxBuyObservableList;
    }

    public void setProductfxBuyObservableList(ObservableList<ProductFx> productfxBuyObservableList) {
        this.productFxBuyObservableList = productfxBuyObservableList;
    }

    public ObservableList<ProductFx> getProductFxMyObservableList() {
        return productFxMyObservableList;
    }

    public void setProductFxMyObservableList(ObservableList<ProductFx> productFxMyObservableList) {
        this.productFxMyObservableList = productFxMyObservableList;
    }
}
