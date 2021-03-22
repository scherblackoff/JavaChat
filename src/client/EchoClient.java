package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class EchoClient {

    private final String SERVER_ADDR = "localhost";
    private final int SERVER_PORT = 8189;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Scanner scanner;

    public static void main(String[] args) {
        try {
            new EchoClient().openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openConnection() throws IOException {
        socket = new Socket(SERVER_ADDR, SERVER_PORT);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());
        new Thread(() ->{
            try {
                while (true){
                    scanner = new Scanner(System.in);
                    String messageForServer = scanner.nextLine();
                    out.writeUTF(messageForServer);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                while (true){
                    String strFromServer = in.readUTF();
                    if (strFromServer.equalsIgnoreCase("/end")){
                        break;
                    }
                    System.out.println(strFromServer);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
