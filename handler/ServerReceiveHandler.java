package handler;

import serveur.Serveur;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ServerReceiveHandler implements Runnable{
    Serveur serveur;
    StringBuilder path;

    public Serveur getServeur() {
        return serveur;
    }

    public void setServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    public StringBuilder getPath() {
        return path;
    }

    public void setPath(StringBuilder path) {
        this.path = path;
    }

    public ServerReceiveHandler(Serveur serveur, StringBuilder path){
        setPath(path);
        setServeur(serveur);
    }

    @Override
    public void run() {
        try {
            while (true){
                //maka ny ANARAN ilay fichier omena azy
                System.out.println("okkkkkkkkkk");
                ObjectInputStream obj=new ObjectInputStream(getServeur().getClient().getSocket().getInputStream());
                String filetodownload=(String)obj.readObject();
                System.out.println("okkkkkkkkkkbeeeeeeeeee");
                System.out.println(filetodownload);

                 System.out.println(getServeur().getSocketSec().get(0).getSocket().isClosed()+"1");
                //mandefa fichier nilainy
                String[] nomfichierserversecondaire={"save1","save2","save3"};

                //mandefa any @ client ireo fichier anaty nomfichierserversecondaire

                for (int i = 0; i < getServeur().getSocketSec().size(); i++) {
                    String stringBuilder=this.getPath().toString()+"//"+nomfichierserversecondaire[i]+"//"+filetodownload;
                    System.out.println(stringBuilder);
                    File file=new File(stringBuilder);
                    FileInputStream fileInputStream = new FileInputStream(file);
                    DataOutputStream dataOutputStream = new DataOutputStream(getServeur().getClient().getSocket().getOutputStream());
                    System.out.println(getServeur().getSocketSec().get(i).getSocket().isClosed()+" "+i);
                    String filename = file.getName();
                    byte[] fileNameBytes = filename.getBytes();
                    byte[] fileContentBytes = new byte[(int)file.length()];
                    fileInputStream.read(fileContentBytes);

                    dataOutputStream.writeInt(fileNameBytes.length);
                    System.out.println(fileNameBytes.length);
                    dataOutputStream.write(fileNameBytes);

                    dataOutputStream.writeInt(fileContentBytes.length);
                    System.out.println(fileContentBytes.length);
                    System.out.println(new String(fileContentBytes));
                    dataOutputStream.write(fileContentBytes, 0, fileContentBytes.length);
                    System.out.println("lasa ny "+(i+1) );
                    dataOutputStream.flush();
                }
                JOptionPane.showMessageDialog(new JFrame(), "Arrived!!");
            }
        } catch (Exception l) {
            // TODO: handle exception
            l.printStackTrace();
        }
    }
}
