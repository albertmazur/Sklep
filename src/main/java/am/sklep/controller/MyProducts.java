package am.sklep.controller;

import am.sklep.database.DbManager;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MyProducts {
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
    private TableColumn<ProductFx, ProductFx> buyColumn;

    @FXML
    private MenuItem editProductMenuItem;

    private ProductModel productModel;

    private static Stage stageSettingProduct;

    /**
     * Ustawienie stage wartości dla tableView
     */
    @FXML
    private void initialize(){
        MainController mainController = MainController.getMainController();
        productModel = mainController.getProductModel();

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
        buyColumn.setCellValueFactory(cellDate -> new SimpleObjectProperty<>(cellDate.getValue()));

        this.tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            productModel.setProductFxEdit(newValue);
        });
        editProductMenuItem.disableProperty().bind(this.tableView.getSelectionModel().selectedItemProperty().isNull());

        boughtOnAction();
    }

    /**
     * Funkcja odpowiedzialna za możliwości edytowania wybranego produktu
     */
    @FXML
    private void editProductOnAction() {
        stageSettingProduct = new Stage();
        stageSettingProduct.setScene(new Scene(FxmlUtils.FxmlLoader("/view/settingProduct.fxml")));
    }

    /**
     * Wyświetlanie produktów, które należą do zalogowanego użytkownika
     */
    private void boughtOnAction(){
        productModel.myProducts();
        tableView.setItems(productModel.getProductFxMyObservableList());

        buyColumn.setCellFactory(param -> new TableCell<ProductFx, ProductFx>(){
            Button buttonSell = new Button(FxmlUtils.getResourceBundle().getString("sell"));
            Button buttonRemove = new Button(FxmlUtils.getResourceBundle().getString("remove_sell"));
            @Override
            protected void updateItem(ProductFx item, boolean empty) {
                super.updateItem(item, empty);

                if(empty){
                    setGraphic(null);
                }
                else if(item.getStatus().equals(ProductModel.TO_BUY)){
                    setGraphic(buttonRemove);
                    buttonRemove.setOnAction(event ->{
                        try {
                            item.setStatus(ProductModel.ADDED);
                            DbManager.update(Converter.converterToProduct(item));
                        }
                        catch (ApplicationException e){
                            DialogUtils.errorDialog(e.getMessage());
                        }
                        boughtOnAction();
                    });
                }
                else{
                    setGraphic(buttonSell);
                    buttonSell.setOnAction(event -> {
                        try {
                            item.setStatus(ProductModel.TO_BUY);
                            DbManager.update(Converter.converterToProduct(item));
                        }
                        catch (ApplicationException e){
                            DialogUtils.errorDialog(e.getMessage());
                        }
                        boughtOnAction();
                    });
                }
            }
        });
    }

    /**
     *  Zwraca stage stageSettingProdukt
     * @return stage dodawania lub edytowania produktu
     */
    public static Stage getStageSettingProduct() {
        return stageSettingProduct;
    }

    /**
     * Ustawia stage dla stageSettingProdukt
     * @param stageSettingProduct stage dla stageSettingProdukt
     */
    public static void setStageSettingProduct(Stage stageSettingProduct) {
        ProductModel.stageSettingProduct = stageSettingProduct;
    }
}
