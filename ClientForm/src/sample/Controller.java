package sample;

import deposit.Deposit;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.fxml.AddDepositController;

import java.util.Collection;

public class Controller {

    @FXML
    TableView tableView;
    @FXML
    TableColumn<Deposit, Integer> idColumn;
    @FXML
    TableColumn<Deposit, String> depositorColumn;
    @FXML
    TableColumn<Deposit, String> bankColumn;
    @FXML
    TableColumn<Deposit, String> countryColumn;
    @FXML
    TableColumn<Deposit, Double> amountColumn;
    @FXML
    TableColumn<Deposit, String> typeColumn;
    @FXML
    TableColumn<Deposit, Double> profitColumn;
    @FXML
    TableColumn<Deposit, Integer> timeColumn;
    @FXML
    Button btnExecute;
    @FXML
    ComboBox listCommands;
    @FXML
    TextField fldParam;

    private Main main;
    private ClientSocket clientSocket;
    private ObservableList<String> commands = FXCollections.observableArrayList();
    private AddDepositController depositController = new AddDepositController();
    public static Deposit createdDeposit;


    public Controller() {

        clientSocket = new ClientSocket(new EventListener());
        clientSocket.connect();

    }

    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    @FXML
    private void onBtnClick() {

        String selCommand = String.valueOf(listCommands.getValue());

        if (selCommand.equals("add") && createdDeposit != null) {
            clientSocket.sendRequest(selCommand, createdDeposit);
            System.out.println(createdDeposit.toString());
        } else if (selCommand.equals("list")
                | selCommand.equals("sum")
                | selCommand.equals("count")) {
            clientSocket.sendRequest(selCommand);
        } else if ((selCommand.equals("info account")
                | selCommand.equals("info depositor")
                | selCommand.equals("show type")
                | selCommand.equals("show bank")
                | selCommand.equals("delete")) & !fldParam.getText().equals("")) {
            clientSocket.sendRequest(new String(selCommand + " " + fldParam.getText()));
        } else {
            showMessageDialog("The field of command parameter is filled incorrect or empty.");
        }

    }

    @FXML
    public void initialize() {
        fillCommands();
        listCommands.setItems(commands);
        listCommands.setOnAction(event -> {
            if (listCommands.getValue().equals("add")) {
                depositController.showAddDeposit();
            }
        });
        idColumn.setCellValueFactory(new PropertyValueFactory<Deposit, Integer>("accountId"));
        depositorColumn.setCellValueFactory(new PropertyValueFactory<Deposit, String>("depositorName"));
        countryColumn.setCellValueFactory(new PropertyValueFactory<Deposit, String>("country"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Deposit, String>("typeOfDeposit"));
        bankColumn.setCellValueFactory(new PropertyValueFactory<Deposit, String>("nameOfBank"));
        profitColumn.setCellValueFactory(new PropertyValueFactory<Deposit, Double>("profitability"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<Deposit, Double>("amountOnDeposit"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<Deposit, Integer>("timeConstraints"));

    }

    private void showResponseMessage(String command, String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Response");
                alert.setHeaderText(command.toUpperCase() + " execution result:");
                alert.setContentText(msg);

                alert.showAndWait();
            }
        });
    }

    private void showMessageDialog(String msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Something went wrong");
                alert.setContentText(msg);

                alert.showAndWait();
            }
        });
    }

    private void fillCommands() {
        commands.add("list");
        commands.add("sum");
        commands.add("count");
        commands.add("info account");
        commands.add("info depositor");
        commands.add("show type");
        commands.add("show bank");
        commands.add("add");
        commands.add("delete");
    }

    public void setMainApp(Main main) {
        this.main = main;
    }


    class EventListener implements CommandListener {

        @Override
        public void onMessageReceived(String command, String msg) {
            showResponseMessage(command, msg);
        }

        @Override
        public void onDepositReceived(Deposit deposit) {
            ObservableList<Deposit> obsDepositList = FXCollections.observableArrayList(deposit);

            tableView.setItems(obsDepositList);
        }

        @Override
        public void onDepositsReceived(Collection<Deposit> deposits) {
            ObservableList<Deposit> obsDepositList = FXCollections.observableArrayList(deposits);
            tableView.setItems(obsDepositList);
        }
    }
}
