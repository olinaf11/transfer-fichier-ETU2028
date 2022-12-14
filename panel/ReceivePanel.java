package panel;

import frame.ReceiveFrame;
import listener.receiveListener;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;

public class ReceivePanel extends JPanel {
    ReceiveFrame frame;

    public ReceiveFrame getFrame() {
        return frame;
    }

    public void setFrame(ReceiveFrame frame) {
        this.frame = frame;
    }

    public  ReceivePanel(ReceiveFrame frame){
        setFrame(frame);
        drawPan();
    }
    public void drawPan(){
        Box vertical=Box.createVerticalBox();
        vertical.add(new JLabel("liste des fichiers pouvant etre telecharge"));
        //mandefa ny anaran ilay fichier ilaina
        receiveListener listener = new receiveListener(getFrame().getSend(),getFrame().getSocket());
        for (int i = 0; i < getFrame().getListFile().length; i++) {
            JLabel jlFileName = new JLabel(getFrame().getListFile()[i]);
            jlFileName.setForeground(new ColorUIResource(0, 200, 153));
            jlFileName.addMouseListener(listener);
            vertical.add(jlFileName);
        }
        this.add(vertical);
    }
}
