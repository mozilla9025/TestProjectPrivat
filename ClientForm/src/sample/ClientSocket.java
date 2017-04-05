package sample;

import deposit.Deposit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by mozil on 03.04.2017.
 */
public class ClientSocket implements CommandListener {

    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private String command = "";
    private Controller.EventListener listener;


    public ClientSocket(Controller.EventListener listener) {
        this.listener = listener;
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 9001);
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());
            new RequestReceiverThread();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(Object... request) {
        try {
            if (request.length == 1)
                oos.writeUTF(String.valueOf(request[0]));
            else if (request.length == 2) {
                oos.writeUTF(String.valueOf(request[0]));
                oos.writeObject(request[1]);
            }
            oos.flush();
            command = String.valueOf(request[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(String command, String msg) {
        listener.onMessageReceived(command, msg);
    }

    @Override
    public void onDepositReceived(Deposit deposit) {
        listener.onDepositReceived(deposit);
    }

    @Override
    public void onDepositsReceived(Collection<Deposit> deposits) {
        listener.onDepositsReceived(deposits);
    }

    class RequestReceiverThread extends Thread {

        public RequestReceiverThread() {
            start();
        }

        @Override
        public void run() {

            String respond;

            try {

                while (true) {

                    String command = ois.readUTF();
                    System.out.println(command);

                    if (command.equals("list")) {
                        Set<Deposit> depositSet = (Set<Deposit>) ois.readObject();
                        onDepositsReceived(depositSet);
                    } else if (command.equals("sum")) {
                        respond = String.valueOf(ois.readDouble());
                        onMessageReceived(command, respond);
                    } else if (command.equals("count")) {
                        respond = String.valueOf(ois.readInt());
                        onMessageReceived(command, respond);
                    } else if (command.startsWith("info account")) {
                        Object requested = ois.readObject();
                        try {
                            Deposit dep = (Deposit) requested;
                            onDepositReceived(dep);
                        } catch (Exception ex) {
                            onMessageReceived(command, String.valueOf(requested));
                        }
                    } else if (command.startsWith("info depositor") |
                            command.startsWith("show type") |
                            command.startsWith("show bank")) {
                        List<Deposit> deposits = (List<Deposit>) ois.readObject();
                        onDepositsReceived(deposits);
                    } else if (command.startsWith("delete")) {
                        respond = String.valueOf(ois.readUTF());
                        onMessageReceived(command, respond);
                    } else if (command.startsWith("add")) {
                        respond = ois.readUTF();
                        onMessageReceived(command, respond);
                    } else {
                        respond = ois.readUTF();
                        onMessageReceived(command, respond);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
