import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
class connect extends JFrame implements ActionListener,WindowListener
{
JLabel serverLabel,portLabel,directoryLabel,serverError,portError,directoryError;
JTextField serverTF,portTF,directoryTF;
JButton connectButton;
public sqlClass createSQL;
connect()
{
directoryError=new JLabel("   ");
portError=new JLabel("     ");
serverError=new JLabel("     ");

setTitle("Connect");
Font font=new Font("arial",Font.PLAIN,18);
Font font2=new Font("arial",Font.PLAIN,18);
serverLabel=new JLabel("Server name");
serverLabel.setFont(font);
serverLabel.setBounds(10,20,120,50);
serverTF=new JTextField(30);
serverTF.setFont(font2);
serverTF.setBounds(150,25,280,30);

portLabel=new JLabel("Port number");
portLabel.setFont(font);
portLabel.setBounds(10,70,120,50);
portTF=new JTextField(30);
portTF.setFont(font2);
portTF.setBounds(150,75,280,30);

directoryLabel=new JLabel("Directory");
directoryLabel.setFont(font);
directoryLabel.setBounds(10,120,120,50);
directoryTF=new JTextField(30);
directoryTF.setFont(font2);
directoryTF.setBounds(150,125,280,30);

connectButton=new JButton("connect");
connectButton.addActionListener(this);
connectButton.setBounds(185,180,120,30);
add(serverLabel);
add(serverTF);
add(portLabel);
add(portTF);
add(directoryLabel);
add(directoryTF);
add(connectButton);
add(portError);
add(serverError);
add(directoryError);
setLayout(null);
setLocation(30,30);
setSize(500,270);
//setDefaultCloseOperation(EXIT_ON_CLOSE);
addWindowListener(this);
setVisible(true);
}


public void actionPerformed(ActionEvent ev)
{
Color greenColor = new Color(0, 153, 0);
if(ev.getSource()==connectButton)
{
try
{
int port=Integer.parseInt(portTF.getText());
String server=serverTF.getText();
String directory=directoryTF.getText();
try
{
createSQL=new sqlClass(server,port,directory);
mainMenu.disconnectMenuItem.setEnabled(true);
mainMenu.connectMenuItem.setEnabled(false);
mainMenu.runButton.setEnabled(true);
mainMenu.outputTA.setForeground(greenColor);
mainMenu.outputTA.setText("Start your job");
mainMenu.errorTA.setText("");
setVisible(false);                                  /////////          
}catch(SQLException sqlException)
{
serverError.setText("Server name may be wrong");
serverError.setForeground(Color.red);
serverError.setBounds(150,55,280,15);
portError.setText("port number may be wrong");
portError.setForeground(Color.red);
portError.setBounds(150,105,280,15);
directoryError.setText("wrong path may entered");
directoryError.setBounds(150,155,280,15);
directoryError.setForeground(Color.red);
System.out.println(sqlException);
}
catch(ClassNotFoundException cnfe)
{
directoryError.setText("wrong path entered");
directoryError.setBounds(150,155,280,15);
directoryError.setForeground(Color.red);
System.out.println(cnfe);
}
}
catch(Exception e)
{
portError.setText("Enter valid port number");
}
}
}

public void windowClosing(WindowEvent ev)
{
setVisible(false);
}
public void windowClosed(WindowEvent e){}
public void windowOpened(WindowEvent e){}
public void windowActivated(WindowEvent e){}
public void windowDeactivated(WindowEvent e){}
public void windowIconified(WindowEvent e){}
public void windowDeiconified(WindowEvent e){}
}







class test
{
public static void main(String ss[])
{
connect c=new connect();
}
}