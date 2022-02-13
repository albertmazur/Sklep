package am.sklep.controller;

import am.sklep.listener.SearchProducts;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.Converter;
import am.sklep.untils.FxmlUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductsToBuy {
    @FXML
    private TableView<ProductFx> tableView;
    @FXML
    private TableColumn<ProductFx, String> nameColumn;
    @FXML
    private TableColumn<ProductFx, String> descColumn;
    @FXML
    private TableColumn<ProductFx, String> priceColumn;
    @FXML
    private TableColumn<ProductFx, String> statusColumn;
    @FXML
    private TableColumn<ProductFx, UserFx> sellerColumn;
    @FXML
    private TableColumn<ProductFx, ProductFx> buyColumn;
    @FXML
    private TextField searchProductsTextField;

    private ProductModel productModel;

    /**
     * Ustawienie stage wartości dla tableView
     */
    @FXML
    private void initialize(){
        productModel = new ProductModel();
        productModel.downloadProduct();

        tableView.setPlaceholder(new Label(FxmlUtils.getResourceBundle().getString("no_products")));

        nameColumn.setCellValueFactory(cellDate -> cellDate.getValue().nazwaProperty());
        descColumn.setCellValueFactory(cellDate -> cellDate.getValue().opisProperty());
        priceColumn.setCellValueFactory(cellDate ->{
            Double d = cellDate.getValue().cenaProperty().getValue();
            StringProperty s = new SimpleStringProperty();
            s.setValue(Converter.addZero(d));
            return s;
        });
        statusColumn.setCellValueFactory(cellDate -> cellDate.getValue().statusProperty());
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));

        setItems();

        tableView.setItems(ProductModel.getProductFxToBuyObservableList());
        searchProductsTextField.textProperty().addListener(new SearchProducts(ProductModel.getProductFxToBuyObservableList()));
        searchProductsTextField.setVisible(true);

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button button = new Button(FxmlUtils.getResourceBundle().getString("add_basket"));
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);
                if(empty) setGraphic(null);
                else{
                    setGraphic(button);
                    button.setOnAction(event -> {
                        ProductModel.getProductFxWithBasketObservableList().add(item);
                        ProductModel.getProductFxToBuyObservableList().remove(item);
                        setItems();
                    });
                }
            }
        });
    }

    private void setItems() {
        statusColumn.setCellFactory(para -> new TableCell<ProductFx, String>(){
            @Override
            protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);
                if(!b){
                    if (s.equals(ProductModel.ADDED)) setText(FxmlUtils.getResourceBundle().getString("added"));
                    if (s.equals(ProductModel.BOUGHT)) setText(FxmlUtils.getResourceBundle().getString("bought"));
                    if (s.equals(ProductModel.TO_BUY)) setText(FxmlUtils.getResourceBundle().getString("to_buy"));
                }
            }
        });
    }
}
