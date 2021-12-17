package am.sklep.database;

import am.sklep.SingletonConnection;
import am.sklep.database.models.Product;
import am.sklep.database.models.User;
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

    public static List downloadProduct(){
        Session session = sessionFactory.openSession();
        return session.createSQLQuery("SELECT * FROM product").addEntity(Product.class).list();
    }
}
