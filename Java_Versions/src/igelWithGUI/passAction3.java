package igelWithGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class passAction3 implements ActionListener{
    public static String pass;
    public passAction3(){
        pass=null;
    }

    public void actionPerformed(ActionEvent e) {
            pass="pass";
        }
}