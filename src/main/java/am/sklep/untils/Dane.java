package am.sklep.untils;

import am.sklep.database.DbManager;
import am.sklep.database.models.Client;
import am.sklep.database.models.Product;
import am.sklep.database.models.Shopping;

import java.time.LocalDate;

public class Dane {
    public Dane() {
        Client client1 = new Client();
        client1.setImie("Albert");
        client1.setNazwisko("Mazur");
        client1.setLogin("amazur");
        client1.setHaslo("P@$$w0rd");
        client1.setStanKonta(1000.00);
        client1.setRokUrodzenia(LocalDate.of(2000, 5, 22));
        client1.setEmail("amazur@gmail.com");
        DbManager.save(client1);

        Client client2 = new Client();
        client2.setImie("Monika");
        client2.setNazwisko("Nowak");
        client2.setLogin("mnowak");
        client2.setHaslo("P@$$w0rd");
        client2.setStanKonta(1000.00);
        client2.setRokUrodzenia(LocalDate.of(1989, 3, 15));
        client2.setEmail("mnowak@gmail.com");
        DbManager.save(client2);

        Client client3 = new Client();
        client3.setImie("Arek");
        client3.setNazwisko("Kowal");
        client3.setLogin("akowal");
        client3.setHaslo("P@$$w0rd");
        client3.setStanKonta(1000.00);
        client3.setRokUrodzenia(LocalDate.of(1999, 11, 26));
        client3.setEmail("akowal@gmail.com");
        DbManager.save(client3);

        Product product1 = new Product();
        product1.setCena(500.00);
        product1.setNazwa("Kuchenka");
        product1.setOpis("Super nowoszesna kuchenka");
        product1.setIdSprzedawcy(client1);
        DbManager.save(product1);

        Product product2 = new Product();
        product2.setCena(5.00);
        product2.setNazwa("Zeszyt");
        product2.setOpis("Super nowoszesna kuchenka");
        product2.setIdSprzedawcy(client1);
        DbManager.save(product2);

        Product product3 = new Product();
        product3.setCena(2.50);
        product3.setNazwa("Długopis");
        product3.setOpis("Dużo tuszu");
        product3.setIdSprzedawcy(client2);
        DbManager.save(product3);

        Product product4 = new Product();
        product4.setCena(50.00);
        product4.setNazwa("Książka");
        product4.setOpis("Romeo i Julia");
        product4.setIdSprzedawcy(client2);
        DbManager.save(product4);

        Product product5 = new Product();
        product5.setCena(200.00);
        product5.setNazwa("Zegarek");
        product5.setOpis("Zegarek super marki");
        product5.setIdSprzedawcy(client2);
        DbManager.save(product5);

        Product product6 = new Product();
        product6.setCena(450.00);
        product6.setNazwa("Telefon");
        product6.setOpis("Najnowszy telefon tego roku");
        product6.setIdSprzedawcy(client1);
        DbManager.save(product6);

        Product product7 = new Product();
        product7.setCena(725.00);
        product7.setNazwa("Monotor");
        product7.setOpis("Monitor do gier");
        product7.setIdSprzedawcy(client1);
        DbManager.save(product7);

        Shopping shopping1 = new Shopping();
        shopping1.setDataZakupu(LocalDate.of(2020,4,22));
        shopping1.setIdClient(client1);
        shopping1.setIdProduct(product1);
        DbManager.save(shopping1);

        Shopping shopping2 = new Shopping();
        shopping2.setDataZakupu(LocalDate.of(2021, 4, 2));
        shopping2.setIdClient(client1);
        shopping2.setIdProduct(product2);
        DbManager.save(shopping2);

        Shopping shopping3 = new Shopping();
        shopping3.setDataZakupu(LocalDate.of(2019, 4, 21));
        shopping3.setIdClient(client2);
        shopping3.setIdProduct(product4);
        DbManager.save(shopping3);

        Shopping shopping4 = new Shopping();
        shopping4.setDataZakupu(LocalDate.of(2021, 11, 15));
        shopping4.setIdClient(client3);
        shopping4.setIdProduct(product4);
        DbManager.save(shopping4);
    }
}
