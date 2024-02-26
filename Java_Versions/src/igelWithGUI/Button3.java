package igelWithGUI;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

import javax.swing.event.EventListenerList;

//(java.awt.event.ActionEvent evt)

public class Button3 extends DefaultButtonModel {

    public java.awt.event.ActionListener thingy;
    //public java.awt.event.ActionListener getCoorThing;
    private ArrayList<Integer> coor;

    public Button3(ArrayList<Integer> coor){
        this.coor=coor;
        
        //this.listenerList=new EventListenerList();
        
    }



    //public void setCoor(ArrayList<Integer> coor){
    //    this.coor=coor;
    //}

    //public void getCoorAction(){
    //    getCoorThing=new java.awt.event.ActionListener() {
    //        public void actionPerformed(java.awt.event.ActionEvent evt) {
    //            getCoor(evt);
    //
    //        }
    //    };
    //}

    public void getCoor(java.awt.event.ActionEvent evt){
        System.out.println(this.coor.get(0));
    }

    public java.awt.event.ActionListener placeAction(int col, char color){
        thingy=new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                placeToken(evt, col, color);
            }
        };
        return thingy;
    }
        
    private void placeToken(java.awt.event.ActionEvent evt, int col, char color){
        Board3.placeToken(this.coor.get(0), col, color);
    }
}