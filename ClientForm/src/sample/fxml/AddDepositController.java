package sample.fxml;

import deposit.Deposit;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller;
import sample.Main;

import java.io.IOException;

/**
 * Created by mozil on 04.04.2017.
 */
public class AddDepositController {

    @FXML
    TextField fldID;
    @FXML
    TextField fldDepositorName;
    @FXML
    TextField fldCountry;
    @FXML
    TextField fldType;
    @FXML
    TextField fldAmount;
    @FXML
    TextField fldProfit;
    @FXML
    TextField fldBankName;
    @FXML
    TextField fldTimeConstraints;
    @FXML
    Button btnAddDeposit;
    @FXML
    Button btnCancel;
    @FXML
    Label lblID;
    @FXML
    Label lblDepositorName;
    @FXML
    Label lblCountry;
    @FXML
    Label lblType;
    @FXML
    Label lblAmount;
    @FXML
    Label lblProfit;
    @FXML
    Label lblBankName;
    @FXML
    Label lblTimeConstraints;


    private Main main;
    private Stage dialogStage;

    public AddDepositController() {
    }

    public void setDialogStage(Stage stage) {
        this.dialogStage = stage;
    }

    public void showAddDeposit() {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("fxml/addDeposit.fxml"));
        AnchorPane pane = null;
        try {
            pane = (AnchorPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage dialogStage = new Stage();
        dialogStage.setTitle("Add deposit");
        Scene scene = new Scene(pane);
        dialogStage.setScene(scene);

        AddDepositController controller = loader.getController();
        controller.setMainApp(main);
        controller.setDialogStage(dialogStage);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dialogStage.showAndWait();
            }
        });

    }

    public void setMainApp(Main main) {
        this.main = main;

    }

    @FXML
    private void onBtnAddDepositClick() {
        Controller.createdDeposit = createNewDeposit();

        dialogStage.close();
    }

    @FXML
    private void onBtnCancelClick() {
        dialogStage.close();
    }


    private Deposit createNewDeposit() {

        String nameOfBank = fldBankName.getText();
        String country = fldCountry.getText();
        String typeOfDeposit = fldType.getText();
        String depositorName = fldDepositorName.getText();
        int accountID = Integer.valueOf(fldID.getText());
        double amountOnDeposit = Double.valueOf(fldAmount.getText());
        double profitability = Double.valueOf(fldProfit.getText());
        int timeConstraints = Integer.valueOf(fldTimeConstraints.getText());

        return new Deposit(nameOfBank, country,
                typeOfDeposit, depositorName,
                accountID, amountOnDeposit,
                profitability, timeConstraints);
    }
}
