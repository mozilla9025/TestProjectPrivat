package sample;

import deposit.Deposit;

import java.util.Collection;

/**
 * Created by mozil on 03.04.2017.
 */
public interface CommandListener {

    void onMessageReceived(String command, String msg);
    void onDepositReceived(Deposit deposit);
    void onDepositsReceived(Collection<Deposit> deposits);


}
