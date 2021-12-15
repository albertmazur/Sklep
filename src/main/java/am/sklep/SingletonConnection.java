package am.sklep;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SingletonConnection {

    private static SessionFactory sessionFactory;

    private SingletonConnection() {
    }

    public static synchronized SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            sessionFactory = new Configuration()
                    .configure().buildSessionFactory();
        }
        return sessionFactory;
    }

}
