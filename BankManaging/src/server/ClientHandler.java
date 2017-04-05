package server;

import deposit.Deposit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;

/**
 * Created by mozil on 01.04.2017.
 */
public class ClientHandler extends Thread {

    private Socket client;
    private Set<Deposit> depositSet;

    public ClientHandler(Socket client, Set<Deposit> depositSet) {
        System.out.println("Connected!");
        this.client = client;
        this.depositSet = depositSet;
        start();
    }

    private Set<Deposit> getDepositSet() {
        return depositSet;
    }

    private double getSumOfDeposits() {
        double sum = 0;
        for (Deposit temp : depositSet)
            sum += temp.getAmountOnDeposit();
        return sum;
    }

    private int getCountOfDeposits() {
        return depositSet.size();
    }

    private Deposit getAccountInfo(int accountId) {
        for (Deposit temp : depositSet) {
            if (temp.getAccountId() == accountId) {
                return temp;
            }
        }
        throw new NoSuchElementException("No account with this ID: " + accountId);
    }

    private List<Deposit> getDepositorInfo(String name) {
        List<Deposit> depByName = new ArrayList<>();
        for (Deposit temp : depositSet) {
            if (temp.getDepositorName().equalsIgnoreCase(name))
                depByName.add(temp);

        }
        if (depByName.isEmpty())
            throw new NoSuchElementException("Depositor with this name not found.");
        else
            return depByName;
    }

    private List<Deposit> getDepositsByType(String type) {
        List<Deposit> depByType = new ArrayList<>();
        for (Deposit temp : depositSet) {
            if (temp.getTypeOfDeposit().equalsIgnoreCase(type))
                depByType.add(temp);
        }
        return depByType;
    }

    private List<Deposit> getDepositsByBank(String bank) {
        List<Deposit> depByBank = new ArrayList<>();
        for (Deposit temp : depositSet) {
            if (temp.getNameOfBank().equalsIgnoreCase(bank))
                depByBank.add(temp);
        }
        return depByBank;
    }

    private String addDeposit(Deposit newDeposit) {
        String result = null;
        boolean isCorrect = true;

        for (Deposit temp : depositSet) {
            if (temp.getAccountId() == newDeposit.getAccountId()) {
                result = "Deposit with ID " + newDeposit.getAccountId() + " exists.";
                isCorrect = false;
            }
        }

        if (newDeposit.getAmountOnDeposit() <= 0) {
            result = "Invalid deposit amount!\n";
            isCorrect = false;
        } else if (newDeposit.getProfitability() <= 0) {
            result += "Invalid deposit profitability!\n";
            isCorrect = false;
        } else if (newDeposit.getTimeConstraints() <= 0) {
            result += "Invalid deposit time constraints!\n";
            isCorrect = false;
        }
        if (isCorrect) {
            result = "OK";
            depositSet.add(newDeposit);
            Server.serialize(Server.FILE, depositSet);
            depositSet = Server.deserialize(Server.FILE);
        }
        return result;
    }

    private String deleteDeposit(int accountID) {
        String result = "No such ID";
        for (Deposit temp : depositSet) {
            if (temp.getAccountId() == accountID) {
                depositSet.remove(temp);
                result = "Deleted successfully";
                Server.serialize(Server.FILE, depositSet);
                depositSet = Server.deserialize(Server.FILE);
                break;
            }
        }
        return result;
    }


    @Override
    public void run() {
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        try {
            ois = new ObjectInputStream(client.getInputStream());
            oos = new ObjectOutputStream(client.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {

                if (ois.available() > 0) {
                    String command = ois.readUTF();
                    System.out.println(command);
                    oos.writeUTF(command);
                    if (command.equals("list")) {
                        oos.writeObject(getDepositSet());
                        oos.flush();
                    } else if (command.equals("sum")) {
                        oos.writeDouble(getSumOfDeposits());
                        oos.flush();
                    } else if (command.equals("count")) {
                        oos.writeInt(getCountOfDeposits());
                        oos.flush();
                    } else if (command.startsWith("info account")) {
                        int accountId = Integer.valueOf(command.substring("info account ".length(), command.length()));
                        try {
                            oos.writeObject(getAccountInfo(accountId));
                        } catch (NoSuchElementException ex) {
                            oos.writeObject(ex.getMessage());
                        } finally {
                            oos.flush();
                        }
                    } else if (command.startsWith("info depositor")) {
                        String name = String.valueOf(command.substring("info depositor ".length(), command.length()));
                        oos.writeObject(getDepositorInfo(name));
                        oos.flush();
                    } else if (command.startsWith("show type")) {
                        String type = String.valueOf(command.substring("show type ".length(), command.length()));
                        oos.writeObject(getDepositsByType(type));
                        oos.flush();
                    } else if (command.startsWith("show bank")) {
                        String bank = String.valueOf(command.substring("show bank ".length(), command.length()));
                        oos.writeObject(getDepositsByBank(bank));
                        oos.flush();
                    } else if (command.startsWith("delete")) {
                        int id = Integer.valueOf(command.substring("delete ".length(), command.length()));
                        oos.writeUTF(deleteDeposit(id));
                        oos.flush();
                    } else if (command.startsWith("add")) {
                        oos.writeUTF(addDeposit((Deposit) ois.readObject()));
                        oos.flush();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
