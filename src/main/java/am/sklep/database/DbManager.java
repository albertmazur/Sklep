package am.sklep.database;

import am.sklep.SingletonConnection;
import am.sklep.database.models.Product;
import am.sklep.database.models.User;
import am.sklep.database.models.Shopping;
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
        List<Product> products = session.createSQLQuery("SELECT * FROM product").addEntity(Product.class).list();
        session.close();
        return products;
    }

    public static List downloadProductByUser(User user){
        Session session = sessionFactory.openSession();
        List<Product> products = session.createSQLQuery("SELECT * FROM product").addEntity(Product.class).list();
        session.close();
        return products;
    }

    public static List downloadUsers(){
        Session session = sessionFactory.openSession();
        List<User> users = session.createSQLQuery("SELECT * FROM users").addEntity(User.class).list();
        session.close();
        return users;
    }

    public static void update(Product product){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(product);
        session.getTransaction().commit();
        session.close();
    }

    public static void update(User user){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(user);
        session.getTransaction().commit();
        session.close();
    }

}
