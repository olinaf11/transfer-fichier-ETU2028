package handler;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.Socket;

public class ClientReceiveHandler implements Runnable{
    Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ClientReceiveHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
            //MAKA ILAY FICHIER NILAINA
        try{
            DataInputStream dataInputStream = new DataInputStream(this.getSocket().getInputStream());
            System.out.println(" hahahaha "+this.getSocket().isClosed());

            int fileNameLength = dataInputStream.readInt();
            byte[] fileNameBytes = new byte[fileNameLength];
            dataInputStream.readFully(fileNameBytes,0,fileNameBytes.length);
            String filename = new String(fileNameBytes);

            int fileContentLength = dataInputStream.readInt();
            System.out.println("nandray: "+fileContentLength);
            byte[] fileContentBytes = new byte[fileContentLength];
            dataInputStream.readFully(fileContentBytes,0,fileContentLength);
            //MAMETRAKA ILAY FICHIER AZO ANATY REPERTOIRE
            System.out.println(new String(fileContentBytes));
            File fileToDownload = new File("./FileReceive/"+filename);
            FileOutputStream fileOutputStream = new FileOutputStream(fileToDownload,true);
            fileOutputStream.write(fileContentBytes);
            JOptionPane.showMessageDialog(new JFrame(), "Received!!");
            System.out.println("tongaaaaa");
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
}
