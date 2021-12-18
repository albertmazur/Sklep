package am.sklep.untils;

import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;
import am.sklep.database.models.User;
import am.sklep.models.ProductModel;

import java.time.LocalDate;

public class Dane {
    public Dane() {
        User user1 = new User();
        user1.setImie("Albert");
        user1.setNazwisko("Mazur");
        user1.setLogin("amazur");
        user1.setHaslo("P@$$w0rd");
        user1.setStanKonta(1000.00);
        user1.setRokUrodzenia(LocalDate.of(2000, 5, 22));
        user1.setEmail("amazur@gmail.com");
        DbManager.save(user1);

        User user2 = new User();
        user2.setImie("Monika");
        user2.setNazwisko("Nowak");
        user2.setLogin("mnowak");
        user2.setHaslo("P@$$w0rd");
        user2.setStanKonta(1000.00);
        user2.setRokUrodzenia(LocalDate.of(1989, 3, 15));
        user2.setEmail("mnowak@gmail.com");
        DbManager.save(user2);

        User user3 = new User();
        user3.setImie("Arek");
        user3.setNazwisko("Kowal");
        user3.setLogin("akowal");
        user3.setHaslo("P@$$w0rd");
        user3.setStanKonta(1000.00);
        user3.setRokUrodzenia(LocalDate.of(1999, 11, 26));
        user3.setEmail("akowal@gmail.com");
        DbManager.save(user3);

        Product product1 = new Product();
        //product1.setCena(new BigDecimal(500));
        product1.setCena(500.00);
        product1.setNazwa("Kuchenka");
        product1.setOpis("Super nowoszesna kuchenka");
        product1.setStatus(ProductModel.KUPIONE);
        product1.setIdUser(user1);
        DbManager.save(product1);

        Product product2 = new Product();
        //product2.setCena(new BigDecimal(5.99").movePointRight(2));
        product2.setCena(5.99);
        product2.setNazwa("Zeszyt");
        product2.setOpis("Ma 100 kartek");
        product2.setStatus(ProductModel.KUPIONE);
        product2.setIdUser(user1);
        DbManager.save(product2);

        Product product3 = new Product();
        //product3.setCena(new BigDecimal("2.5"));
        product3.setCena(2.50);
        product3.setNazwa("Długopis");
        product3.setOpis("Dużo tuszu");
        product3.setStatus(ProductModel.DO_KUPIENIA);
        product3.setIdUser(user2);
        DbManager.save(product3);

        Product product4 = new Product();
        //product4.setCena(new BigDecimal(50));
        product4.setCena(50.00);
        product4.setNazwa("Książka");
        product4.setStatus(ProductModel.KUPIONE);
        product4.setOpis("Romeo i Julia");
        product4.setIdUser(user2);
        DbManager.save(product4);

        Product product5 = new Product();
        //product5.setCena(new BigDecimal(200));
        product5.setCena(200.00);
        product5.setNazwa("Zegarek");
        product5.setOpis("Zegarek super marki");
        product5.setStatus(ProductModel.DO_KUPIENIA);
        product5.setIdUser(user2);
        DbManager.save(product5);

        Product product6 = new Product();
        //product6.setCena(new BigDecimal(450.0));
        product6.setCena(450.00);
        product6.setNazwa("Telefon");
        product6.setOpis("Najnowszy telefon tego roku");
        product6.setStatus(ProductModel.KUPIONE);
        product6.setIdUser(user1);
        DbManager.save(product6);

        Product product7 = new Product();
        //product7.setCena(new BigDecimal(725.00));
        product7.setCena(725.00);
        product7.setNazwa("Monotor");
        product7.setOpis("Monitor do gier");
        product7.setStatus(ProductModel.DO_KUPIENIA);
        product7.setIdUser(user1);
        DbManager.save(product7);

        Product product8 = new Product();
        //product8.setCena(new BigDecimal(725.00));
        product8.setCena(3000.00);
        product8.setNazwa("Samochód");
        product8.setOpis("Samochód dla całej rodziny");
        product8.setStatus(ProductModel.DO_KUPIENIA);
        product8.setIdUser(user3);
        DbManager.save(product8);

        Shopping shopping1 = new Shopping();
        shopping1.setDataZakupu(LocalDate.of(2020,4,22));
        shopping1.setIdUser(user1);
        shopping1.setIdProduct(product1);
        DbManager.save(shopping1);

        Shopping shopping2 = new Shopping();
        shopping2.setDataZakupu(LocalDate.of(2021, 4, 2));
        shopping2.setIdUser(user1);
        shopping2.setIdProduct(product2);
        DbManager.save(shopping2);

        Shopping shopping3 = new Shopping();
        shopping3.setDataZakupu(LocalDate.of(2019, 4, 21));
        shopping3.setIdUser(user2);
        shopping3.setIdProduct(product4);
        DbManager.save(shopping3);

        Shopping shopping4 = new Shopping();
        shopping4.setDataZakupu(LocalDate.of(2021, 11, 15));
        shopping4.setIdUser(user3);
        shopping4.setIdProduct(product6);
        DbManager.save(shopping4);
    }
}
