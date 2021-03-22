package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class EchoServer {


    private DataOutputStream out;
    private DataInputStream in;
    private Scanner scanner;

    public static void main(String[] args) {
        new EchoServer().getConnect();
    }

    private void getConnect() {
        Socket socket = null;
        try(ServerSocket serverSocket = new ServerSocket(8189)){
            System.out.println("Сервер запущен, ожидаем подключения...");
            socket = serverSocket.accept();
            System.out.println("Клиент подключился");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(() -> {
                try {
                    while (true){
                        String str = in.readUTF();
                        if (str.equals("/end")){
                            break;
                        }
                        System.out.println(str);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }).start();
            new Thread(() -> {
                try {
                    while (true){
                        scanner = new Scanner(System.in);
                        String messageForClient = scanner.nextLine();
                        out.writeUTF(messageForClient);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }).start();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
