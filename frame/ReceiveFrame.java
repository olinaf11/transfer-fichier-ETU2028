package frame;

import listener.SendListener;
import panel.ReceivePanel;

import javax.swing.*;
import java.net.Socket;

public class ReceiveFrame extends JFrame {
    String[] listFile;
    SendListener send;
    Socket socket;

    public String[] getListFile() {
        return listFile;
    }

    public void setListFile(String[] listFile) {
        this.listFile = listFile;
    }

    public SendListener getSend() {
        return send;
    }

    public void setSend(SendListener send) {
        this.send = send;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public ReceiveFrame(String[] listFile, SendListener send, Socket socket){
        setListFile(listFile);
        setSend(send);
        setSocket(socket);

        this.setSize(300,300);
        this.setVisible(true);

        ReceivePanel pan = new ReceivePanel(this);
        this.add(pan);
    }
}
