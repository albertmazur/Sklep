package am.sklep.database;

import am.sklep.database.models.BaseModel;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.DialogUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbManager{
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = SingletonConnection.getSessionFactory();
        } catch (ApplicationException e) {
            DialogUtils.errorDialog(e.getMessage());
        }
    }


    public static<b extends BaseModel> void save(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(baseModel);
        session.getTransaction().commit();
        session.close();
    }

    public static<b extends BaseModel> void update(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.update(baseModel);
        session.getTransaction().commit();
        session.close();
    }

    public static<b extends BaseModel> void delete(b baseModel){
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.delete(baseModel);
        session.getTransaction().commit();
        session.close();
    }

    public static<b extends BaseModel> List download(Class<b> cls){
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<b> query = builder.createQuery(cls);
        Root<b> root = query.from(cls);
        query.select(root);
        Query<b> q =session.createQuery(query);
        List<b> list = q.getResultList();
        return list;
    }
}
