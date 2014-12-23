package gui;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import movement.Movements;
import network.Net;
import network.NetSettings;

public class Button3 {
	//Die einzelteile der GUI vorbereiten.
   private JFrame mainFrame;
   
   private JPanel controlPanel;
   JTextField eingabe = new JTextField();
   
   public Button3(){
      prepareGUI();
   }
   // GUI starten.
   public static void main(String[] args){
	   Button3  ButtonCreate = new Button3();      
	   ButtonCreate.showButton();
   }
   //Die GUI-Fenster-Eigenschaften angeben.  
   private void prepareGUI(){
      mainFrame = new JFrame("Remotecontrol");
      mainFrame.setSize(450,450);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
              
      
      
      controlPanel = new JPanel();
      
      
      controlPanel.setLayout(null);
      controlPanel.setBounds(0, 0, 450, 450);
      
      mainFrame.add(controlPanel);
      
      mainFrame.setVisible(true);  
   }
   
   
   private void showButton(){
	   //Die Button Vorbereiten.
      JButton Forward = new JButton("Forward");        
      JButton Backward = new JButton("Backward");
      JButton Right = new JButton("Right");
      JButton Left = new JButton("Left");
      JButton RotateRight = new JButton("Rotate Right");
      JButton RotateLeft = new JButton("Rotate Left");
      JButton Restart_map = new JButton("Restart_map");
      
     
      //Die Funktionen der Buttons erstellen.
      Forward.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 100+ Integer.parseInt(eingabe.getText()));
         }          
      });

      Backward.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 200+ Integer.parseInt(eingabe.getText()));
         }
      });

      Right.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
        	  Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 500+ Integer.parseInt(eingabe.getText()));
          }
       });
      
      Left.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	 Net.sendRobotCmd(NetSettings.getEv2Ip(), NetSettings.getRobotPort(), 300+ Integer.parseInt(eingabe.getText()));
         }
      });
      
      RotateLeft.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
         	 
          }
       });
      
      RotateRight.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
         	
          }
       });
      
      Restart_map.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             
          }
       });
      //Die einzelnen Elemente die in der GUI enthalten sein sollen einsetzen.
      controlPanel.add(Forward);
      controlPanel.add(Backward);
      controlPanel.add(Right);
      controlPanel.add(Left);
      controlPanel.add(RotateRight);
      controlPanel.add(RotateLeft);
      controlPanel.add(Restart_map);
      controlPanel.add(eingabe);
     
      
      //Die Positionen der jeweiligen GUI-Elemente festlegen.
      Forward.setBounds(150, 0, 100, 25);
      Backward.setBounds(150, 50, 100, 25);
      Right.setBounds(200, 25, 100, 25);
      Left.setBounds(100, 25, 100, 25);
      RotateRight.setBounds(300, 25, 100, 25);
      RotateLeft.setBounds(0, 25, 100, 25);
      Restart_map.setBounds(140, 100, 100, 25);
      eingabe.setBounds(150, 75, 100, 25);
      
      
      mainFrame.setVisible(true);  
   }
}