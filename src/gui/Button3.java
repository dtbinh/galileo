package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class Button3 {
    
   private JFrame mainFrame;
   private JLabel statusLabel;
   private JPanel controlPanel;

   public Button3(){
      prepareGUI();
   }

   public static void main(String[] args){
	   Button3  ButtonCreate = new Button3();      
	   ButtonCreate.showButton();
   }

   private void prepareGUI(){
      mainFrame = new JFrame("Remotecontrol");
      mainFrame.setSize(400,400);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
              
      statusLabel = new JLabel("",JLabel.CENTER);    

      statusLabel.setSize(350,50);

      controlPanel = new JPanel();
      controlPanel.setLayout(null);

      
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   }
    
   private void showButton(){

      JButton Forward = new JButton("Forward");        
      JButton Backward = new JButton("Backward");
      JButton Right = new JButton("Right");
      JButton Left = new JButton("Left");
      
      JButton Restart_map = new JButton("Restart Map");
      
      Left.setHorizontalTextPosition(SwingConstants.LEFT);   

      Forward.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Forward");
         }          
      });

      Backward.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Backward");
         }
      });

      Right.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Right");
          }
       });
      
      Left.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            statusLabel.setText("Left");
         }
      });
      
      Restart_map.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
             statusLabel.setText("Restart_map");
          }
       });
      controlPanel.add(Forward);
      controlPanel.add(Backward);
      controlPanel.add(Right);
      controlPanel.add(Left);
      controlPanel.add(Restart_map); 
      Forward.setBounds(150, 0, 100, 25);
      Backward.setBounds(150, 50, 100, 25);
      Right.setBounds(200, 25, 100, 25);
      Left.setBounds(100, 25, 100, 25);
      Restart_map.setBounds(140, 100, 120, 25);
      mainFrame.setVisible(true);  
   }
}