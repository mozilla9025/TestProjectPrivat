package server;

import deposit.Deposit;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by mozil on 01.04.2017.
 */
public class Server {

    private static final int PORT = 9001;
    public static final File FILE = new File("depositSet.txt");

    public static void main(String[] args) {
        Set<Deposit> depositSet = new HashSet<Deposit>();
        depositSet = deserialize(FILE);
        depositSet.forEach(temp -> System.out.println(temp.toString()));
        try {
            ServerSocket server = new ServerSocket(PORT);

            while (true) {
                Socket client = server.accept();
                new ClientHandler(client, depositSet);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Set<Deposit> deserialize(File file) {
        FileInputStream fis = null;
        ObjectInputStream in = null;
        Set<Deposit> depositSet = null;
        try {
            fis = new FileInputStream(file);
            in = new ObjectInputStream(fis);
            depositSet = (Set<Deposit>) in.readObject();
            in.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return depositSet;
    }

    public static void serialize(File file, Object object) {
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(file);
            out = new ObjectOutputStream(fos);
            out.writeObject(object);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}