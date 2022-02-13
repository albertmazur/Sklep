package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.database.models.Product;
import am.sklep.listener.CheckPrice;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.models.UserFx;
import am.sklep.untils.ApplicationException;
import am.sklep.untils.Converter;
import am.sklep.untils.DialogUtils;
import am.sklep.untils.FxmlUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SettingProductController {
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextArea descTextArea;
    @FXML
    private TextField priceTextField;

    private Stage stageMain;
    private Stage stageSettingProduct;
    private ProductFx productFxEdit;
    private UserFx userFx;

    /**
     * Ustawianie sceny, kiedy jest uruchomiana (tytuł, ikony, brak możliwości rozszerzaniu okna i zgaszenie stage main) i przypisanie produktu do edycji
     */
    @FXML
    private void initialize(){
        stageMain = Login.getLoginStage();
        stageMain.getScene().getRoot().setDisable(true);

        userFx = LoginController.getUserFx();
        productFxEdit = ProductModel.getProductFxEdit();

        deleteButton.setVisible(false);
        if(productFxEdit==null) stageSettingProduct = MainController.getStageSettingProduct();
        else stageSettingProduct = MyProducts.getStageSettingProduct();
        stageSettingProduct.getIcons().add(new Image(SettingProductController.class.getResourceAsStream(MainController.IMG_M)));
        stageSettingProduct.setAlwaysOnTop(true);
        stageSettingProduct.setResizable(false);
        stageSettingProduct.setOnHiding(e->{
            stageMain.getScene().getRoot().setDisable(false);
        });
        stageSettingProduct.show();

        addButton.disableProperty().bind(
            nameTextField.textProperty().isEmpty()
            .or(descTextArea.textProperty().isEmpty())
            .or(priceTextField.textProperty().isEmpty())
        );

        if(productFxEdit!=null){
            deleteButton.setVisible(true);

            nameTextField.setText(productFxEdit.getNazwa());
            descTextArea.setText(productFxEdit.getOpis());
            priceTextField.setText(String.valueOf(productFxEdit.getCena()));

            stageSettingProduct.setTitle(FxmlUtils.getResourceBundle().getString("edit_product"));
            addButton.setText(FxmlUtils.getResourceBundle().getString("save_changes"));
        }
        else {
            stageSettingProduct.setTitle(FxmlUtils.getResourceBundle().getString("add_product"));
            addButton.setText(FxmlUtils.getResourceBundle().getString("add"));
        }

        priceTextField.textProperty().addListener(new CheckPrice(priceTextField));
    }

    /**
     * Dodawanie lub edytowanie produktu do bazy
     */
    @FXML
    public void addOnAction() {
        try {
            if(productFxEdit!=null){
                ProductModel.getProductFxMyObservableList().remove(productFxEdit);

                productFxEdit.setNazwa(nameTextField.getText());
                productFxEdit.setOpis(descTextArea.getText());
                productFxEdit.setCena(Double.valueOf(priceTextField.getText()));

                DbManager.update(Converter.converterToProduct(productFxEdit));
                ProductModel.getProductFxMyObservableList().add(productFxEdit);
            }
            else{
                Product product = new Product();
                product.setNazwa(nameTextField.getText());
                product.setOpis(descTextArea.getText());
                product.setCena(Double.valueOf(priceTextField.getText()));
                product.setStatus(ProductModel.ADDED);
                product.setIdUser(Converter.converterToUser(userFx));
                DbManager.save(product);
                ProductModel.getProductFxMyObservableList().add(Converter.converterToProductFX(product));
            }
            stageMain.getScene().getRoot().setDisable(false);
            stageSettingProduct.close();
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }

    /**
     * Usuwanie produktu z bazy
     */
    @FXML
    public void deleteOnAction() {
        try {
            Product product = Converter.converterToProduct(productFxEdit);

            try{
                DbManager.delete(product);
            }
            catch (Exception e){
                product.setStatus(ProductModel.DELETED);
                DbManager.update(product);
            }
            stageMain.getScene().getRoot().setDisable(false);
            stageSettingProduct.close();

            ProductModel.getProductFxMyObservableList().remove(productFxEdit);
        }
        catch (ApplicationException e){
            DialogUtils.errorDialog(e.getMessage());
        }
    }
}
