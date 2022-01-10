package am.sklep.listener;

import am.sklep.controller.LoginController;
import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.models.ProductFx;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;

import java.util.List;

import static am.sklep.models.ProductModel.TO_BUY;

public class SearchProducts implements ChangeListener<String> {

    private ObservableList<ProductFx> productFxToBuyObservableList;
    private UserFx userFx;

    public SearchProducts(ObservableList<ProductFx>  listProducts) {
        this.productFxToBuyObservableList = listProducts;
        userFx = LoginController.getUserFx();
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
        List<Product> productAllList = DbManager.download(Product.class);

        productFxToBuyObservableList.clear();

        if(t1.length()!=0){
            productAllList.forEach(item ->{
                String nameProduct = item.getNazwa().toLowerCase();
                if (item.getStatus().equals(TO_BUY) && item.getIdUser().getId()!=userFx.getId() && (item.getNazwa().equals(t1) || nameProduct.equals(t1))){
                    productFxToBuyObservableList.add(Converter.converterToProductFX(item));
                }
            });
        }
        else {
            productAllList.forEach(item ->{
                if (item.getStatus().equals(TO_BUY) && item.getIdUser().getId()!=userFx.getId()){
                    productFxToBuyObservableList.add(Converter.converterToProductFX(item));
                }
            });
        }
    }
}
