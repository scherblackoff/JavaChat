package server;

import server.handlers.ClientHandler;
import server.services.AuthService;
import server.services.SimpleAuthService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Vector;

public class Server {

    private List<ClientHandler> clients;

    private AuthService authService;

    public Server() {
        clients = new Vector<>();
        authService = new SimpleAuthService();
        ServerSocket server = null;
        Socket socket;

        final int PORT = 8080;

        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен!");

            while (true) {
                socket = server.accept();
                new ClientHandler(this, socket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert server != null;
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public AuthService getAuthService() {
        return authService;
    }

    public void broadcastMsg(String msg){
        for (ClientHandler c:clients) {
            c.sendMsg(msg);
        }
    }

    public void personalMessage(ClientHandler clientHandler, String msg, String nick){
        for (ClientHandler c:clients) {
            if (c.getNick().equals(nick)) {
                System.out.println("вошли в цикл");
                if (c.getNick().equals(clientHandler.getNick())) {
                    return;
                }
                c.sendMsg(msg);
                return;
            }
        }
    }

    public void subscribe(ClientHandler clientHandler){
        clients.add(clientHandler);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler clientHandler){
        clients.remove(clientHandler);
        broadcastClientList();
    }

    public boolean isLoginAuthorized(String login){
        for (ClientHandler c:clients) {
            if (c.getLogin().equals(login)){
                return true;
            }
        }
        return false;
    }

    private void broadcastClientList(){
        StringBuilder stringBuilder = new StringBuilder("/clientList ");
        for (ClientHandler c:clients) {
            stringBuilder.append(c.getNick()).append(" ");
        }
        String msg = stringBuilder.toString();
        for (ClientHandler c: clients){
            c.sendMsg(msg);
        }
    }

}
