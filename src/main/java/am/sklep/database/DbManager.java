package am.sklep.database;

import am.sklep.database.models.BaseModel;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.FxmlUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DbManager{
    private static SessionFactory sessionFactory;

    /**
     * Tworzenie SessionFactory, który umożliwia połączenie z bazą danych
     */
    private static void createSessionFactory() {
        try {
            sessionFactory = SingletonConnection.getSessionFactory();
        } catch (ApplicationException e) {

        }
    }

    /**
     * Zapisywanie obiektu do bazy danych
     * @param baseModel Obiekt, który ma zostać zapisany do bazy
     * @param <b> Klasa obiektu, która ma zostać zapisana i implementuje BaseModel
     */
    public static<b extends BaseModel> void save(b baseModel) throws ApplicationException {
        createSessionFactory();
        try{
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(baseModel);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("disconnect_date"));
        }

    }

    /**
     * Zaktualizowany obiektu do bazy danych
     * @param baseModel Obiekt, który ma zostać zaktualizowany do bazy
     * @param <b> Klasa obiektu, która ma zostać zaktualizowana i implementuje BaseModel
     */
    public static<b extends BaseModel> void update(b baseModel) throws ApplicationException {
        createSessionFactory();
        try {
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.update(baseModel);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("disconnect_date"));
        }

    }

    /**
     * Usunięcie obiektu do bazy danych
     * @param baseModel Obiekt, który ma zostać usunięty do bazy
     * @param <b> Klasa obiektu, która ma zostać usunięta i implementuje BaseModel
     */
    public static<b extends BaseModel> void delete(b baseModel) throws ApplicationException {
        createSessionFactory();
        try{
            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(baseModel);
            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("disconnect_date"));
        }

    }

    /**
     * Pobranie listy z bazy danych
     * @param cls Klasa obiektów, jaka ma zostać pobrana
     * @param <b> Klasa obiektu, która ma zostać pobrana i implementuje BaseModel
     * @return Lista obiektów klasy b
     */
    public static<b extends BaseModel> List download(Class<b> cls) throws ApplicationException {
        createSessionFactory();
        try{
            Session session = sessionFactory.openSession();
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<b> query = builder.createQuery(cls);
            Root<b> root = query.from(cls);
            query.select(root);
            Query<b> q =session.createQuery(query);
            List<b> list = q.getResultList();
            return list;
        }
        catch (Exception e){
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("disconnect_date"));
        }
    }
}
