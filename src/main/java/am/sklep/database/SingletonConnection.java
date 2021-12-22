package am.sklep.database;

import am.sklep.untils.ApplicationException;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
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
            throw new ApplicationException(FxmlUtils.getResourceBundle().getString("disconnect_date"));
        }
        return sessionFactory;
    }
}