package am.sklep.database;

import am.sklep.SingletonConnection;
import am.sklep.database.models.BaseModel;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbManager{
    private static SessionFactory sessionFactory = SingletonConnection.getSessionFactory();


    public static<b extends BaseModel> void save(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(baseModel);
        session.getTransaction().commit();
        session.close();
    }

    /*
    public static void save(User user){
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
*/
    public static<b extends BaseModel> List download(Class<b> cls){
        Session session =sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<b> query = builder.createQuery(cls);
        Root<b> root = query.from(cls);
        query.select(root);
        Query<b> q =session.createQuery(query);
        List<b> list = q.getResultList();
        return list;
    }
/*
    public static List downloadProduct(){
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
*/
    public static<b extends BaseModel> void update(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(baseModel);
        session.getTransaction().commit();
        session.close();
    }
    /*
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
    */
    public static<b extends BaseModel> void delete(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(baseModel);
        session.getTransaction().commit();
        session.close();
    }
}
