package am.sklep.models;

import am.sklep.controller.LoginController;
import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ProductModel {
    /**
     * Zmienne do ustawienia statusu to buy
     */
    public final static String TO_BUY = "to_buy";
    /**
     * Zmienne do ustawienia statusu bought
     */
    public final static String BOUGHT = "bought";
    /**
     * Zmienne do ustawienia statusu added
     */
    public final static String ADDED = "added";
    /**
     * Zmienne do ustawienia statusu deleted
     */
    public final static String DELETED = "deleted";
    public static Stage stageSettingProduct;

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
    private static ObservableList<ProductFx> productFxToBuyObservableList = FXCollections.observableArrayList();

    /**
     * Lista produktów, które użytkownik dodał do koszyka
     */
    private static ObservableList<ProductFx> productFxWithBasketObservableList = FXCollections.observableArrayList();

    /**
     * Lista produktów zalogowanego użytkownika
     */
    private static ObservableList<ProductFx> productFxMyObservableList = FXCollections.observableArrayList();

    /**
     * Pobranie z bazy listy produktów, które zalogowany użytkownik może kupić
     */
    public void downloadProduct(){
        try {
            List<Product> list = DbManager.download(Product.class);
            productFxToBuyObservableList.clear();
            list.forEach(item->{
                if(item.getStatus().equals(TO_BUY) && item.getIdUser().getId()!=userFx.getId()){
                    ProductFx productFx = Converter.converterToProductFX(item);
                    AtomicBoolean f= new AtomicBoolean(true);
                    productFxWithBasketObservableList.forEach(i ->{
                        if(i.getId()==item.getId()) f.set(false);
                    });
                    if(f.get()) productFxToBuyObservableList.add(productFx);
                }
            });
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    /**
     * Dodawanie do bazy kupionych produktów
     */
    public void buy(){
        try {
            productFxWithBasketObservableList.forEach(item ->{
                try {
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

                }
                catch (ApplicationException e){
                    DialogUtils.errorDialog(e.getMessage());
                }
                });
            DbManager.update(Converter.converterToUser(userFx));
            productFxWithBasketObservableList.clear();
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    /**
     * Pobieranie produktów, które zalogowany użytkownik posiada
     */
    public void myProducts(){
        try {
            List<Product> myProducts = DbManager.download(Product.class);
            productFxMyObservableList.clear();
            myProducts.forEach(item ->{
                if(item.getIdUser().getId() == userFx.getId() && !item.getStatus().equals(DELETED)) productFxMyObservableList.add(Converter.converterToProductFX(item));
            });
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    public static ObservableList<ProductFx> getProductFxToBuyObservableList() {
        return productFxToBuyObservableList;
    }

    public void setProductFxObservableList(ObservableList<ProductFx> productFxObservableList) {
        this.productFxToBuyObservableList = productFxObservableList;
    }

    public static ObservableList<ProductFx> getProductFxWithBasketObservableList() {
        return productFxWithBasketObservableList;
    }

    public void setProductFxWithBasketObservableList(ObservableList<ProductFx> productFxWithBasketObservableList) {
        this.productFxWithBasketObservableList = productFxWithBasketObservableList;
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