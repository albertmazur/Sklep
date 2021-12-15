package am.sklep.database;

import am.sklep.SingletonConnection;
import am.sklep.database.models.Client;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.models.ClientFx;
import am.sklep.models.ProductFx;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class DbManager{
    private static SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
    public static void save(Client client){
        SessionFactory sessionFactory = SingletonConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(client);
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
        List<Client> list = session.createSQLQuery("SELECT * FROM client").addEntity(Client.class).list();
        ObservableList<Client> observableList = FXCollections.observableArrayList();
        list.forEach(item->{
            observableList.add(item);
        });

        return observableList;
    }

    public static ObservableList downloadClient(Integer id){
        Session session = sessionFactory.openSession();
        List<Client> list = session.createSQLQuery("SELECT * FROM client WHERE id_klienta="+id).addEntity(Client.class).list();
        ObservableList<Client> observableList = FXCollections.observableArrayList();
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
            ClientFx clientFx = new ClientFx();
            clientFx.setId(item.getIdSprzedawcy().getId());
            clientFx.setName(item.getIdSprzedawcy().getImie());
            clientFx.setSurname(item.getIdSprzedawcy().getNazwisko());
            clientFx.setLogin(item.getIdSprzedawcy().getLogin());
            clientFx.setHaslo(item.getIdSprzedawcy().getHaslo());
            clientFx.setDataUrdzenia(item.getIdSprzedawcy().getRokUrodzenia());
            clientFx.setEmail(item.getIdSprzedawcy().getEmail());
            clientFx.setStanKonta(item.getIdSprzedawcy().getStanKonta());
            productFx.setSprzedajacy(clientFx);
            observableList.add(productFx);
        });

        return observableList;
    }
}
