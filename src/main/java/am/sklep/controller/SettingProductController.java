package am.sklep.controller;

import am.sklep.Login;
import am.sklep.database.DbManager;
import am.sklep.models.ProductFx;
import am.sklep.models.ProductModel;
import am.sklep.untils.Converter;
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
    private ProductModel productModel;

    @FXML
    private void initialize(){
        stageMain = Login.getLoginStage();

        productModel = new ProductModel();

        deleteButton.setVisible(false);

        stageSettingProduct = MainController.getStageSettingProduct();
        stageSettingProduct.getIcons().add(new Image(SettingProductController.class.getResourceAsStream(MainController.IMG_M)));
        stageSettingProduct.setTitle("Dodaj produkt");
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

        productFxEdit = ProductModel.getProductFxEdit();
        if(productFxEdit!=null){

            deleteButton.setVisible(true);

            nameTextField.setText(productFxEdit.getNazwa());
            descTextArea.setText(productFxEdit.getOpis());
            priceTextField.setText(String.valueOf(productFxEdit.getCena()));
        }
    }

    @FXML
    public void addOnAction() {
        stageMain.getScene().getRoot().setDisable(false);
        stageSettingProduct.close();

        productModel.getProductFxMyObservableList().remove(productFxEdit);

        productFxEdit.setNazwa(nameTextField.getText());
        productFxEdit.setOpis(descTextArea.getText());
        productFxEdit.setCena(Double.valueOf(priceTextField.getText()));

        DbManager.update(Converter.converterToProduct(productFxEdit));
        productModel.getProductFxMyObservableList().add(productFxEdit);
    }

    @FXML
    public void deleteOnAction() {
        stageMain.getScene().getRoot().setDisable(false);
        stageSettingProduct.close();

        DbManager.delete(Converter.converterToProduct(productFxEdit));
        productModel.getProductFxMyObservableList().remove(productFxEdit);
    }
}
