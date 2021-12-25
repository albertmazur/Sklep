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
    /**
     * Zmienne do ustawienia statusu to buy
     */
    public final static String TO_BUY = FxmlUtils.getResourceBundle().getString("to_buy");
    /**
     * Zmienne do ustawienia statusu bought
     */
    public final static String BOUGHT = FxmlUtils.getResourceBundle().getString("bought");
    /**
     * Zmienne do ustawienia statusu added
     */
    public final static String ADDED = FxmlUtils.getResourceBundle().getString("added");
    /**
     * Zmienne do ustawienia statusu deleted
     */
    public final static String DELETED = FxmlUtils.getResourceBundle().getString("deleted");

    /**
     * Zalogowany użytkownik
     */
    private UserFx userFx = LoginController.getUserFx();
    /**
     * Produkt, który został wybrany do edycji
     */
    private static ProductFx productFxEdit;

    /**
     * Lista produktów, które użytkownik może kupić
     */
    private ObservableList<ProductFx> productFxToBuyObservableList = FXCollections.observableArrayList();

    /**
     * Lista produktów, które użytkownik dodał do koszyka
     */
    private ObservableList<ProductFx> productFxBuyObservableList = FXCollections.observableArrayList();

    /**
     * Lista produktów zalogowanego użytkownika
     */
    private static ObservableList<ProductFx> productFxMyObservableList = FXCollections.observableArrayList();

    /**
     * Pobranie z bazy listy produktów, które zalogowany użytkownik może kupić
     */
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

    /**
     * Dodawanie do bazy kupionych produktów
     */
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

    /**
     * Pobieranie produktów, które zalogowany użytkownik posiada
     */
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
