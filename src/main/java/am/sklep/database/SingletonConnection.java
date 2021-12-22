package am.sklep.database;

import am.sklep.untils.ApplicationException;
import am.sklep.untils.DialogUtils;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SingletonConnection {
    private static SessionFactory sessionFactory;

    private SingletonConnection() {
    }

    public static synchronized SessionFactory getSessionFactory() throws ApplicationException {
        try {
            if (sessionFactory == null) sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        catch (Exception e){
            DialogUtils.errorDialog(e.getMessage());
            throw new ApplicationException("Nie można połączuć się z bazą danych");
        }
        return sessionFactory;
    }
}