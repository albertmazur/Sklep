package am.sklep.database;

import am.sklep.SingletonConnection;
import am.sklep.database.models.User;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.models.UserFx;
import am.sklep.models.ProductFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DbManager{
    private static SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
    public static void save(User user){
        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    public static void save(Product product){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();
        session.close();
    }

    public static void save(Shopping shopping){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(shopping);
        session.getTransaction().commit();
        session.close();
    }

    public static ObservableList downloadClient(){
        Session session = sessionFactory.openSession();
        List<User> list = session.createSQLQuery("SELECT * FROM client").addEntity(User.class).list();
        ObservableList<User> observableList = FXCollections.observableArrayList();
        list.forEach(item->{
            observableList.add(item);
        });

        return observableList;
    }

    public static ObservableList downloadClient(Integer id){
        Session session = sessionFactory.openSession();
        List<User> list = session.createSQLQuery("SELECT * FROM client WHERE id_klienta="+id).addEntity(User.class).list();
        ObservableList<User> observableList = FXCollections.observableArrayList();
        list.forEach(item->{
            observableList.add(item);
        });

        return observableList;
    }

    public static ObservableList downloadProduct(){
        Session session = sessionFactory.openSession();
        List<Product> list = session.createSQLQuery("SELECT * FROM product").addEntity(Product.class).list();
        ObservableList<ProductFx> observableList = FXCollections.observableArrayList();
        list.forEach(item->{
            ProductFx productFx = new ProductFx();
            productFx.setId(item.getId());
            productFx.setNazwa(item.getNazwa());
            productFx.setOpis(item.getOpis());
            productFx.setCena(item.getCena());
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
            observableList.add(productFx);
        });

        return observableList;
    }
}
