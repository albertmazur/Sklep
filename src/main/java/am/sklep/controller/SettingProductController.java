package am.sklep.controller;

import am.sklep.Login;
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

    @FXML
    private void initialize(){
        stageMain = Login.getLoginStage();

        //deleteButton.setVisible(true);

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
    }

    @FXML
    public void addOnAction() {
        stageMain.getScene().getRoot().setDisable(false);
        stageSettingProduct.close();
    }

    @FXML
    public void deleteOnAction() {
        stageMain.getScene().getRoot().setDisable(false);
        stageSettingProduct.close();
    }
}
