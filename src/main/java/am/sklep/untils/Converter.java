package am.sklep.untils;

import am.sklep.database.models.Product;
import am.sklep.database.models.User;
import am.sklep.models.ProductFx;
import am.sklep.models.UserFx;

public class Converter {

    /**
     * Zmiana obiektu z produkt na produktFx
     * @param product Obiekty klasy Produkt
     * @return Obiekt klasy ProduktFx
     */
    public static ProductFx converterToProductFX(Product product){
        ProductFx productFx = new ProductFx();
        productFx.setId(product.getId());
        productFx.setNazwa(product.getNazwa());
        productFx.setCena(product.getCena());
        productFx.setOpis(product.getOpis());
        productFx.setStatus(product.getStatus());
        productFx.setSprzedajacy(converterToUserFX(product.getIdUser()));
        return productFx;
    }

    /**
     * Zmiana obiektu z user na userFx
     * @param user Obiekty klasy user
     * @return Obiekt klasy userFx
     */
    public static UserFx converterToUserFX(User user){
        UserFx userFx = new UserFx();
        userFx.setId(user.getId());
        userFx.setEmail(user.getEmail());
        userFx.setHaslo(user.getHaslo());
        userFx.setName(user.getImie());
        userFx.setLogin(user.getLogin());
        userFx.setSurname(user.getNazwisko());
        userFx.setDataUrodzenia(user.getRokUrodzenia());
        userFx.setStanKonta(user.getStanKonta());
        userFx.setCzyAktywne(user.getCzyAktywne());
        return userFx;
    }

    /**
     * Zmiana obiektu z produkt na produktFx
     * @param productFx Obiekty klasy produktFx
     * @return Obiekty klasy Produkt
     */
    public static Product converterToProduct(ProductFx productFx){
        Product product = new Product();
        product.setId(productFx.getId());
        product.setNazwa(productFx.getNazwa());
        product.setCena(productFx.getCena());
        product.setOpis(productFx.getOpis());
        product.setStatus(productFx.getStatus());
        product.setIdUser(converterToUser(productFx.getSprzedajacy()));
        return product;
    }

    /**
     * Zmiana obiektu z userFx na user
     * @param userFx Obiekt klasy userFx
     * @return Obiekty klasy user
     */
    public static User converterToUser(UserFx userFx){
        User user = new User();
        user.setId(userFx.getId());
        user.setEmail(userFx.getEmail());
        user.setHaslo(userFx.getHaslo());
        user.setImie(userFx.getName());
        user.setLogin(userFx.getLogin());
        user.setNazwisko(userFx.getSurname());
        user.setRokUrodzenia(userFx.getDataUrodzenia());
        user.setStanKonta(userFx.getStanKonta());
        user.setCzyAktywne(userFx.getCzyAktywne());
        return user;
    }

    /**
     * Wyświetlanie ceny z dwoma liczbami po kropce
     * @param d Cena (double), która ma zostać wyświetlona
     * @return String, który ma zostać wyświetlony
     */
    public static String addZero(Double d){
        if((d*100)%10==0) return d+"0";
        else return String.valueOf(d);
    }
}
