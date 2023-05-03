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
        MainController mainController = MainController.getMainController();
        productModel = mainController.getProductModel();
        //productModel = new ProductModel();
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
        statusColumn.setCellValueFactory(cellDate -> cellDate.getValue().stringPropertyName());
        sellerColumn.setCellValueFactory(cellDate -> cellDate.getValue().sprzedajacyProperty());
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));

        tableView.setItems(productModel.getProductFxToBuyObservableList());
        searchProductsTextField.textProperty().addListener(new SearchProducts(productModel.getProductFxToBuyObservableList()));
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
                        productModel.getProductFxWithBasketObservableList().add(item);
                        productModel.getProductFxToBuyObservableList().remove(item);
                    });
                }
            }
        });
    }
}
