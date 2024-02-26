package igelWithGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

class getCoor3 implements ActionListener{
    public static String coor;
    public getCoor3(){
        coor=null;
    }

    public void actionPerformed(ActionEvent e) {
            JButton source = (JButton)e.getSource();
            coor=source.getName();
            //String fullName=source.getName();
            //coor=Integer.toString(Integer.parseInt(fullName+1));
        }
}