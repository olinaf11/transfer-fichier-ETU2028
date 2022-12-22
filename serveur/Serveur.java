package serveur;

import client.Client;
import handler.ServerHandler;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Vector;

public class Serveur {
    ServerSocket serverSocket;
    Vector<Client> socketSec = new Vector<>();
    Client client;
    String option;

    public Vector<Client> getSocketSec() {
        return socketSec;
    }

    public void setSocketSec(Vector<Client> socketSec) {
        this.socketSec = socketSec;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Serveur() throws IOException {
        System.out.println("Wait for the client");
        setClient(new Client());
        for (int i = 0; i < 3; i++) {
            getSocketSec().add(new Client(new Socket("localhost", 1011+(1+i))));
        }
    }
    public Vector<Client> getClientDisp(int length) throws IOException {
        Vector<Client> clients = new Vector<>();
        for (int i = 0; i < length; i++) {
            if (isServerOpen("localhost", 1011+(i+1)))
                clients.add(new Client("localhost", 1011+(i+1)));
        }
        return clients;
    }
    public boolean isServerOpen(String hostname, int port) {
        boolean isOpen = false;
        SocketAddress socketAddress = new InetSocketAddress(hostname,port);
        Socket socket = new Socket();
        int timeout = 2000;//en ms
        try {
            socket.connect(socketAddress, timeout);
            socket.close();
            isOpen = true;
        } catch (IOException exception){
            System.out.println("SocketException "+ hostname + "," + port + "."+ exception.getMessage());
        }
        return isOpen;
    }
    public void openServer(int port) throws IOException {
        System.out.println("Wait for the client");
        setServerSocket(new ServerSocket(port));
        System.out.println("Server open");
        boolean first = true;
        while (true){
            Socket client = getServerSocket().accept();
            setClient(new Client(client));
            ServerHandler handler = new ServerHandler(this, first);
            Thread t = new Thread(handler);
            t.start();
        }
    }
    public String getFileName() throws IOException {
        int length = getClient().getInput().readInt();
        byte[] b = new byte[length];
        getClient().getInput().readFully(b, 0, length);
        return new String(b);
    }
    public void getfirstoption() {
        try {
            ObjectInputStream obj1=new ObjectInputStream(getClient().getSocket().getInputStream());
            setOption((String)obj1.readObject());
            System.out.println("Option:"+getOption());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    public void sendFile() throws Exception{
        String name = getFileName();

        int count = getClient().getInput().readInt();

        System.out.println("taille = "+count);

        byte[] fileContentBytes = new byte[count];
        getClient().getInput().read(fileContentBytes);
        int divisor = getSocketSec().size();
        int divide = count / divisor;
        int off = 0;

        for(int increment = 1; increment <= divisor ; increment++, off += divide){
            //manoratra anle contenue
            getSocketSec().get(increment-1).getOutput().writeInt(name.getBytes().length);
            getSocketSec().get(increment-1).getOutput().write(name.getBytes());
            if (increment == 3 ) divide=count-off;
            System.out.println("de "+off+" a "+divide);
            getSocketSec().get(increment-1).getOutput().write(fileContentBytes, off, divide);
            getSocketSec().get(increment-1).getOutput().flush();
//            getSocketSec().get(increment-1).getOutput().close();
        }
        JOptionPane.showMessageDialog(new JFrame(), "Send");
    }
    public static void setFile(DataInputStream input, String folder) throws IOException {
        String fileName = getName(input);
        FileOutputStream out = new FileOutputStream(folder + fileName);
        byte[] b = new byte[4096];
        int count;
        while ((count = input.read(b)) > -1){
            out.write(b, 0, count);
        }
    }
    public static void sendFile(Socket client, String folder) throws Exception{
        DataInputStream input = new DataInputStream(client.getInputStream());
        setFile(input, folder);
    }
    public static String getName(DataInputStream din) throws IOException {
        byte[] b = haveByte(din);
        return new String(b);
    }
    public static byte[] haveByte(DataInputStream dataInputStream) throws IOException {
        int dataSend = dataInputStream.readInt();
        byte[] bytes = new byte[dataSend];
        dataInputStream.readFully(bytes, 0, dataSend);
        return bytes;
    }
    public static void main(String[] args) throws IOException {
        Serveur serveur = new Serveur();
        serveur.openServer(1011);
    }

}