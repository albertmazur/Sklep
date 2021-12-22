package am.sklep.models;

import am.sklep.controller.LoginController;
import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ProductModel {

    public final static String TO_BUY = FxmlUtils.getResourceBundle().getString("to_buy");
    public final static String BOUGHT = FxmlUtils.getResourceBundle().getString("bought");
    public final static String ADDED = FxmlUtils.getResourceBundle().getString("added");
    public final static String DELETED = FxmlUtils.getResourceBundle().getString("deleted");

    private UserFx userFx = LoginController.getUserFx();
    private static ProductFx productFxEdit;

    private ObservableList<ProductFx> productFxToBuyObservableList = FXCollections.observableArrayList();
    private ObservableList<ProductFx> productFxBuyObservableList = FXCollections.observableArrayList();
    private static ObservableList<ProductFx> productFxMyObservableList = FXCollections.observableArrayList();

    public void downloadProduct(){
        List<Product> list = DbManager.download(Product.class);
        productFxToBuyObservableList.clear();
        list.forEach(item->{
            if(item.getStatus().equals(TO_BUY) && item.getIdUser().getId()!=userFx.getId()){
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

                item.setStatus(ProductModel.BOUGHT);
                item.setSprzedajacy(userFx);
                DbManager.update(Converter.converterToProduct(item));
            });
            DbManager.update(Converter.converterToUser(userFx));
            productFxBuyObservableList.clear();
    }

    public void myProducts(){
        List<Product> myProducts = DbManager.download(Product.class);
        productFxMyObservableList.clear();
        myProducts.forEach(item ->{
            if(item.getIdUser().getId() == userFx.getId() && !item.getStatus().equals(DELETED)) productFxMyObservableList.add(Converter.converterToProductFX(item));
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

    public void setProductFxBuyObservableList(ObservableList<ProductFx> productFxBuyObservableList) {
        this.productFxBuyObservableList = productFxBuyObservableList;
    }

    public static ObservableList<ProductFx> getProductFxMyObservableList() {
        return productFxMyObservableList;
    }

    public void setProductFxMyObservableList(ObservableList<ProductFx> productFxMyObservableList) {
        this.productFxMyObservableList = productFxMyObservableList;
    }

    public static ProductFx getProductFxEdit() {
        return productFxEdit;
    }

    public static void setProductFxEdit(ProductFx productFxEdit) {
        ProductModel.productFxEdit = productFxEdit;
    }
}
