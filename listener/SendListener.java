package listener;

import client.Client;
import frame.ReceiveFrame;
import panel.Sendpanel;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.net.Socket;

public class SendListener implements MouseListener {
    Sendpanel sendpanel;
    File filetoSend;
    Socket socket;
    Client client;
    String option;

    public Sendpanel getSendpanel() {
        return sendpanel;
    }

    public File getFiletoSend() {
        return filetoSend;
    }

    public Socket getSocket() {
        return socket;
    }

    public Client getClient() {
        return client;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setSendpanel(Sendpanel sendpanel) {
        this.sendpanel = sendpanel;
    }

    public void setFiletoSend(File filetoSend) {
        this.filetoSend = filetoSend;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public SendListener(Sendpanel sendpanel, File filetoSend, Socket socket) throws IOException {
        setSendpanel(sendpanel);
        setFiletoSend(filetoSend);
        setSocket(socket);
        setClient(new Client(socket));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ObjectOutputStream objOutput = null;
        ObjectInputStream objInput = null;
        try {
            if (e.getSource() instanceof JButton jb){
                if (jb.getName().compareTo("choose") == 0){
                    JFileChooser jfc = new JFileChooser();
                    jfc.showOpenDialog(null);
                    if (jfc.getSelectedFile() != null){
                        setFiletoSend(jfc.getSelectedFile());
                        getSendpanel().getJTextField().setText(jfc.getSelectedFile().getName());
                    }
                }
                else if (jb.getName().compareTo("send") == 0){
                    objOutput = new ObjectOutputStream(getSocket().getOutputStream());
                    setOption("send");
                    objOutput.writeObject(getOption());
                    objOutput.flush();
                    if (getSendpanel().getJTextField().getText().compareTo("")!=0){
                        try {
                            getClient().sendFile(getFiletoSend(), getSocket().getOutputStream());
                            System.out.println("lasa");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(new JFrame(), "Tsy nisafidy fichier");
                    }
                } else if (jb.getName().compareTo("receive") == 0) {
                    objOutput = new ObjectOutputStream(getSocket().getOutputStream());
                    setOption("receive");
                    objOutput.writeObject(getOption());
                    objOutput.flush();

                    ///maka liste nom fichiers azo alaina
                    StringBuilder stringBuilder = new StringBuilder("C:/ITU/prog-sys/transfert/send/FileSave");
                    String path = stringBuilder.toString()+"/save1";
                    File file = new File(path);
                    String[] filesinside = file.list();
                    assert filesinside != null;
                    System.out.println(filesinside[0]);

                    ReceiveFrame frame = new ReceiveFrame(filesinside, this, this.getSocket());

                    JOptionPane.setRootFrame(frame);
                    System.out.println("yyyy");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
