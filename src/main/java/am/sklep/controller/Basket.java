package am.sklep.controller;

import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class Basket {
    @FXML
    private TableView<ProductFx> tableView;
    @FXML
    private TableColumn<ProductFx, String> nameColumn;
    @FXML
    private TableColumn<ProductFx, String> descColumn;
    @FXML
    private TableColumn<ProductFx, String> priceColumn;
    @FXML
    private TableColumn<ProductFx, UserFx> sellerColumn;
    @FXML
    private TableColumn<ProductFx, ProductFx> buyColumn;

    @FXML
    private Label sumViewLabel;
    @FXML
    private Label balanceFailedLabel;
    @FXML
    private Button buyButton;

    private ProductModel productModel;
    private UserFx userFx;
    private BooleanProperty checkBuy;

    /**
     * Ustawienie stage wartości dla tableView
     */
    @FXML
    private void initialize(){
        MainController.setBasket(this);
        productModel = new ProductModel();
        productModel.downloadProduct();

        userFx = LoginController.getUserFx();

        tableView.setPlaceholder(new Label(FxmlUtils.getResourceBundle().getString("no_products")));

        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nazwaProperty());
        descColumn.setCellValueFactory(cellDate -> cellDate.getValue().opisProperty());
        priceColumn.setCellValueFactory(cellDate ->{
            Double d = cellDate.getValue().cenaProperty().getValue();
            StringProperty s = new SimpleStringProperty();
            s.setValue(Converter.addZero(d));
            return s;
        });
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));


        checkBuy = new SimpleBooleanProperty();
        balanceFailedLabel.visibleProperty().bind(checkBuy);
        buyButton.disableProperty().bind(checkBuy);
        basketOnAction();
    }

    /**
     * Funkcja odpowiedzialna za kliknięcie przycisku Kup
     */
    @FXML
    private void buyOnAction() {
        if(!checkBuy.get()){
            productModel.buy();
            basketOnAction();
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("thank"));
        }
    }

    /**
     * Wyświetlenie produktów, które zostały dodane do koszyka
     */
    private void basketOnAction(){
        tableView.setItems(productModel.getProductFxWithBasketObservableList());

        checkBuy();

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button(FxmlUtils.getResourceBundle().getString("remove_basket"));
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        productModel.getProductFxWithBasketObservableList().remove(item);
                        productModel.getProductFxToBuyObservableList().add(item);
                        basketOnAction();
                    });
                }
            }
        });
    }

    /**
     * Funkcja odpowiedzialna za wyświetlenie użytkownikowi odpowiedniego komunikatu zależności czy jest coś w koszyku lub nie ma wystarczających środków na koncie
     */
    public void checkBuy(){
        double suma = productModel.getProductFxWithBasketObservableList().stream().mapToDouble(ProductFx::getCena).sum();
        if(suma==0.0){
            checkBuy.setValue(true);
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("nothing"));
        }
        else if(suma> userFx.getStanKonta()){
            checkBuy.setValue(true);
            balanceFailedLabel.setText(FxmlUtils.getResourceBundle().getString("not_balance"));
        }
        else{
            checkBuy.setValue(false);
        }
        sumViewLabel.setText(Converter.addZero(suma));
    }
}
