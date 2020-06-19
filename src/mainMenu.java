import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
class mainMenu extends JFrame implements ActionListener
{
private JMenuBar mb;
private JMenu rdbms;
private JMenuItem connectMenuItem,disconnectMenuItem,quitMenuItem;
JLabel homeLabel1,homeLabel2,homeLabel3,l1;
mainMenu()
{
setTitle("SQL through UI");
connectMenuItem=new JMenuItem("Connect");
disconnectMenuItem=new JMenuItem("Disconnect");
quitMenuItem=new JMenuItem("QUIT");
connectMenuItem.addActionListener(this);
disconnectMenuItem.addActionListener(this);
quitMenuItem.addActionListener(this);
rdbms=new JMenu("RDBMS");
rdbms.add(connectMenuItem);
rdbms.add(disconnectMenuItem);
rdbms.add(quitMenuItem);
mb=new JMenuBar();
mb.add(rdbms);
setJMenuBar(mb);
homeLabel1=new JLabel("WELCOME TO");
homeLabel2=new JLabel("SQL through UI");
homeLabel3=new JLabel("Create your database here");
//l1=new JLabel("                  ");
Font font1=new Font("Book Antiqua",Font.BOLD,40);
Font font2=new Font("Calligrapher ",Font.PLAIN,60);
Font font3=new Font("Book Antiqua",Font.PLAIN,30);
homeLabel1.setFont(font1);
homeLabel2.setFont(font2);
homeLabel3.setFont(font3);
homeLabel1.setBounds(100,100,600,100);
homeLabel2.setBounds(100,200,600,150);
homeLabel3.setBounds(100,350,600,100);
JPanel homePanel=new JPanel();
homePanel.setLayout(null);
homePanel.setBackground(Color.white);
homePanel.add(homeLabel1);
homePanel.add(homeLabel2);
homePanel.add(homeLabel3);
//homePanel.add(l1,BorderLayout.WEST);
setLayout(new BorderLayout());
add(homePanel,BorderLayout.CENTER);
setLocation(10,10);
setSize(700,700);
setVisible(true);
setDefaultCloseOperation(EXIT_ON_CLOSE);
}
public void actionPerformed(ActionEvent ev)
{

}
}












class connect extends JFrame implements ActionListener
{
JLabel serverLabel,portLabel,directoryLabel,serverError,portError,directoryError;
JTextField serverTF,portTF,directoryTF;
JButton connectButton;
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
setDefaultCloseOperation(EXIT_ON_CLOSE);
setVisible(true);
}

public void actionPerformed(ActionEvent ev)
{
if(ev.getSource()==connectButton)
{
String port=portTF.getText();
String server=serverTF.getText();
String directory=directoryTF.getText();
try
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
System.out.println("Butten  click hua 1");
Connection c=DriverManager.getConnection("jdbc:derby://"+server+":"+port+"/"+directory);
System.out.println("Butten  click hua 2");
setVisible(false);
}catch(SQLException sqlException)
{
System.out.println("Butten  click hua 3");
serverError.setText("Server name seems wrong");
serverError.setBounds(150,55,280,15);
portError.setText("port number seems wrong");
portError.setBounds(150,105,280,15);

System.out.println(sqlException);
}
catch(ClassNotFoundException cnfe)
{
System.out.println("Butten  click hua 4");
directoryError.setText("wrong path entered");
directoryError.setBounds(150,155,280,15);
System.out.println(cnfe);
}
}
}

}









/*

class test
{
public static void main(String gg[])
{
mainMenu m=new mainMenu();
}
}

*/