package serveur;

import handler.ServSecHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur1 {
    ServerSocket serverSocket;
    Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void openServer(int port) throws IOException {
        System.out.println("Wait for the main server");
        setServerSocket(new ServerSocket(port));
        System.out.println("Server open");
        while (true){
            Socket client = getServerSocket().accept();
            ServSecHandler handler = new ServSecHandler(client, "./FileSave/save1/");
            Thread t = new Thread(handler);
            t.start();
        }
    }

    public static void main(String[] args) {
        try {
            Serveur1 serveur1 = new Serveur1();
            serveur1.openServer(1012);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
