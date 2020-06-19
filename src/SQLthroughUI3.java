
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.sql.*;
import java.io.*;

class mainMenu extends JFrame implements ActionListener
{
private JTable table=null;
private JScrollPane jsp,sqlJSP,tableJSP;
private JMenuBar mb;
private JMenu rdbms,radSettingsMenu;
private boolean TB=false; //to find visisblity of table
public static JMenuItem connectMenuItem,disconnectMenuItem,quitMenuItem,radSettingsMenuItem;
JLabel homeLabel1,homeLabel2,homeLabel3,spaceLabel,tableHeadingLabel,sqlLabel;
public static JButton runButton,generateDTOButton;
public static JTextArea outputTA;
JTextArea sqlTF;
JPanel tablePanel;
JPanel sqlPanel,homePanel;
JList tableList;
public static DefaultListModel tableModel;
connect con;
RADSettings radSettings;
Table singleSelectedTable;

Font sqlFont=new Font("arial",Font.PLAIN,18);
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();


mainMenu()
{
setTitle("SQL through UI");
setSize((screenSize.width), 26*(screenSize.height)/27);
connectMenuItem=new JMenuItem("Connect");
disconnectMenuItem=new JMenuItem("Disconnect");
disconnectMenuItem.setEnabled(false);
quitMenuItem=new JMenuItem("QUIT");
connectMenuItem.addActionListener(this);
disconnectMenuItem.addActionListener(this);
quitMenuItem.addActionListener(this);
rdbms=new JMenu("RDBMS");
rdbms.add(connectMenuItem);
rdbms.add(disconnectMenuItem);
rdbms.add(quitMenuItem);
radSettingsMenuItem=new JMenuItem("RAD settings");
radSettingsMenuItem.addActionListener(this);
radSettingsMenu=new JMenu("RAD settings");
radSettingsMenu.add(radSettingsMenuItem);
mb=new JMenuBar();
mb.add(rdbms);
mb.add(radSettingsMenu);
setJMenuBar(mb);
homeLabel1=new JLabel("WELCOME TO");
homeLabel2=new JLabel("'SQL through UI'");
homeLabel3=new JLabel("Create your database here");

Font font1=new Font("Book Antiqua",Font.BOLD,40);
Font font2=new Font("Calligrapher ",Font.PLAIN,60);
Font font3=new Font("Book Antiqua",Font.PLAIN,30);
homeLabel1.setFont(font1);
homeLabel2.setFont(font2);
homeLabel3.setFont(font3);

homeLabel1.setBounds(100,100,600,100);
homeLabel2.setBounds(100,200,600,150);
homeLabel3.setBounds(100,350,600,100);



runButton=new JButton("Run");
runButton.setBounds(3*(screenSize.width)/5+100+20,25,100,35);
runButton.setEnabled(false);
runButton.addActionListener(this);
sqlTF=new JTextArea();
sqlTF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
sqlTF.setLineWrap(true);
sqlJSP = new JScrollPane(sqlTF);
sqlJSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
sqlJSP.setBounds(140,25,3*(screenSize.width)/5-30,40);
sqlTF.setBounds(140,25,3*(screenSize.width)/5-45,40);
sqlTF.setFont(sqlFont);
sqlLabel=new JLabel("SQL statement:");
sqlLabel.setBounds(10,25,130,35);
sqlLabel.setFont(sqlFont);

outputTA=new JTextArea();
outputTA.setFont(sqlFont);
outputTA.setBounds(10,95+4*(screenSize.height)/7-20,3*(screenSize.width)/4-15,2*(screenSize.height)/7-50);
outputTA.setBorder(BorderFactory.createLineBorder(Color.BLACK));
outputTA.setEditable(false);
spaceLabel=new JLabel("Table will be displayed here", SwingConstants.CENTER);
spaceLabel.setFont(font2);
spaceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
spaceLabel.setBounds(10,80,3*(screenSize.width)/4-15, 4*(screenSize.height)/7-15); 
tableHeadingLabel=new JLabel("List of Tables", SwingConstants.CENTER);
tableHeadingLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
tableHeadingLabel.setBounds(3*(screenSize.width)/4+15,80,(screenSize.width)/4-65,35);
Font tableFont=new Font("arial",Font.BOLD,20);
tableHeadingLabel.setFont(tableFont);


tableModel=new DefaultListModel();
tableList=new JList(tableModel);
tableList.setBorder(BorderFactory.createLineBorder(Color.BLACK));
tableList.setBounds(3*(screenSize.width)/4+15,120,(screenSize.width)/4-90,6*(screenSize.height)/7-170);
tableList.setBackground(Color.white);
tableList.setFont(sqlFont);
tableJSP=new JScrollPane(tableList);
tableJSP.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
tableJSP.setBounds(3*(screenSize.width)/4+15,120,(screenSize.width)/4-65,6*(screenSize.height)/7-170);

//tableList.addListSelectionListener(this);
tableList.addMouseListener(new MouseAdapter() 
  {
    public void mouseClicked(MouseEvent evt) 
      {
        //JList list = (JList)evt.getSource();
        if (evt.getClickCount() == 2) 
	{
	 Table selectedTable=(Table)tableList.getSelectedValue(); 
	  String  tableName=selectedTable.getTableName();  
	 Object[][] data={{}};
	 String[] heading={};
	 try
	 {
	 heading=con.createSQL.getTableHeading("select * from "+tableName);
	 data=con.createSQL.getTableData("select * from "+tableName);
	 }
	 catch(SQLException sqle)
	 {
	 outputTA.setText(sqle.getMessage());
	 } catch(ClassNotFoundException cnfe)
	 {
	 outputTA.setText(cnfe.getMessage());
	 }
	 sqlTF.setText("select * from "+tableName);
	 Font tableFont=new Font("arial",Font.PLAIN,16);
	 table=new JTable(data,heading); 
	 table.setFont(tableFont);
	 table.setRowHeight(20);
	 table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
	 jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
	 jsp.setBounds(0,0,3*(screenSize.width)/4, 4*(screenSize.height)/7); 
	
	 remove(tablePanel);
	 tablePanel=new JPanel();
	 tablePanel.setLayout(new GridLayout(1,1));
	 tablePanel.add(jsp);
	 tablePanel.setBounds(10,80,3*(screenSize.width)/4+15, 4*(screenSize.height)/7+15);    //.....
	 add(tablePanel,BorderLayout.CENTER);
	 tablePanel.setVisible(true);
	 TB=true;
	}
 	if(evt.getClickCount()==1)
	{
	 
	 singleSelectedTable=(Table)tableList.getSelectedValue();
	 mainMenu.generateDTOButton.setEnabled(true);
	} 
    }
});

generateDTOButton=new JButton("Generate DL");
generateDTOButton.setBounds(3*(screenSize.width)/4+15,6*(screenSize.height)/7-20,(screenSize.width)/4-65,30);
generateDTOButton.addActionListener(this);
generateDTOButton.setEnabled(false);

sqlPanel=new JPanel();
sqlPanel.setLayout(null);
sqlPanel.add(runButton);
sqlPanel.add(sqlTF);
sqlPanel.add(sqlJSP);
sqlPanel.add(outputTA);
sqlPanel.add(spaceLabel);
sqlPanel.add(tableHeadingLabel);
sqlPanel.add(tableList);
sqlPanel.add(tableJSP);
sqlPanel.add(generateDTOButton);
sqlPanel.add(sqlLabel);


homePanel=new JPanel();
homePanel.setLayout(null);
homePanel.setBackground(Color.white);
homePanel.add(homeLabel1);
homePanel.add(homeLabel2);
homePanel.add(homeLabel3);

tablePanel=new JPanel();
tablePanel.setLayout(new BorderLayout());
add(tablePanel,BorderLayout.CENTER);
tablePanel.setVisible(false);

setLayout(new BorderLayout());

add(homePanel,BorderLayout.CENTER);
setLocation(0,0);

setVisible(true);

setDefaultCloseOperation(EXIT_ON_CLOSE);
}


public void actionPerformed(ActionEvent ev)
{
Color greenColor = new Color(0, 153, 0);
outputTA.setText("");
if(ev.getSource()==runButton)
{
System.out.println("RUN CHALA////");
try
{
String sql=sqlTF.getText();
String[] sqlArray=sql.split(" ",4);
System.out.println("SQL ARRAY CHALA /////");
if(sqlArray[0].equals("select")) 
{
Object[][] data;
String[] heading;


/*
Component[] components=tablePanel.getComponents();
System.out.println("COMPONET KI LENGTH: "+components.length);
if(components.length !=0)
{
tablePanel.remove(components[0]);
tablePanel.revalidate();
tablePanel.repaint();
System.out.println("IF MAI AAYA");
}
*/


heading=con.createSQL.getTableHeading(sql);
data=con.createSQL.getTableData(sql);
Font tableFont=new Font("arial",Font.PLAIN,16);
table=new JTable(data,heading); 
table.setFont(tableFont);
table.setRowHeight(20);
table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
jsp=new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
jsp.setBounds(0,0,3*(screenSize.width)/4, 4*(screenSize.height)/7); 

remove(tablePanel);
tablePanel=new JPanel();
tablePanel.setLayout(new GridLayout(1,1));
tablePanel.add(jsp);
tablePanel.setBounds(10,80,3*(screenSize.width)/4+15, 4*(screenSize.height)/7+15);    //.....
add(tablePanel,BorderLayout.CENTER);
tablePanel.setVisible(true);
System.out.println("YAAH TAK AAYA");
TB=true;
}
else 
{
if(TB) tablePanel.setVisible(false);

if(sqlArray[0].equals("update"))
{
con.createSQL.changeStatement(sql); 
outputTA.setForeground(greenColor);
outputTA.setText("Table updated successfully");
}
else if(sqlArray[0].equals("delete"))
{
con.createSQL.changeStatement(sql); 
outputTA.setForeground(greenColor);
outputTA.setText("Data from table deleted successfully :)");
}
else if(sqlArray[0].equals("insert"))
{
con.createSQL.changeStatement(sql); 
outputTA.setForeground(greenColor);
outputTA.setText("Data inserted successfully :)");
}

else if(sqlArray[0].equals("create"))
{
con.createSQL.changeStatement(sql); 
outputTA.setForeground(greenColor);
outputTA.setText("New table has been created successfully :)");
tableModel.addElement(sqlArray[2]);
}
else if(sqlArray[0].equals("drop"))
{
con.createSQL.changeStatement(sql); 
outputTA.setForeground(greenColor);
int c=tableModel.getSize();
for(int i=0;i<c;i++)
{
Table name=(Table)tableModel.get(i);
if(name.toString().equalsIgnoreCase(sqlArray[2]))
{
tableModel.removeElementAt(i);
break;
}
}
outputTA.setText("table "+sqlArray[2]+" has been deleted successfully :)");
}
else
{
con.createSQL.changeStatement(sql);
outputTA.setText("Job done :)"); 
}
 
}
}catch(SQLException sqlException)
{
if(TB) tablePanel.setVisible(false);
outputTA.setForeground(Color.red);
outputTA.setText(sqlException.getMessage());
System.out.println(sqlException);
}catch(ClassNotFoundException cnfe)
{
if(TB) tablePanel.setVisible(false);
outputTA.setForeground(Color.red);
outputTA.setText(cnfe.getMessage());
}
catch(Exception e)
{
System.out.println("EXCEPTION MAI AAYA");
if(TB) tablePanel.setVisible(false);
System.out.println("YAAHA AAYA0");
e.printStackTrace();

outputTA.setForeground(Color.red);
outputTA.setText("error");
}
}

if(ev.getSource()==connectMenuItem)
{
con=new connect();

if(TB) tablePanel.setVisible(false);
homePanel.setVisible(false);
add(sqlPanel,BorderLayout.CENTER);
outputTA.setForeground(Color.red);
outputTA.setText("PLEASE CONNECT TO SERVER......");
}

if(ev.getSource()==disconnectMenuItem)
{
System.out.println("DISCONNECT CLICK HUA");
try
{
con.createSQL.disconnect();
if(TB) tablePanel.setVisible(false);
System.out.println("DISCONNECT CHALA");
runButton.setEnabled(false);
generateDTOButton.setEnabled(false);
disconnectMenuItem.setEnabled(false);
connectMenuItem.setEnabled(true);                      
outputTA.setForeground(Color.red);
outputTA.setText("PLEASE CONNECT TO SERVER......");

tableModel.removeAllElements();

}catch(SQLException sqlException)
{
if(TB) tablePanel.setVisible(false);
outputTA.setForeground(Color.red);
outputTA.setText(sqlException.getMessage());
}catch(ClassNotFoundException cnfe)
{
if(TB) tablePanel.setVisible(false);
outputTA.setForeground(Color.red);
outputTA.setText(cnfe.getMessage());
}
catch(Exception e)
{
if(TB) tablePanel.setVisible(false);
outputTA.setForeground(Color.red);
outputTA.setText(e.getMessage());
}
}

if(ev.getSource()==quitMenuItem)
{
System.exit(0);
}
if(ev.getSource()==radSettingsMenuItem)
{
System.out.println("GET SOURCE=RAD SETTING MAI AAYA");
radSettings=new RADSettings();
}
if(ev.getSource()==generateDTOButton)
{
JFrame f;
 try
 {
 String  primaryKeyName=con.createSQL.getPrimaryKey(singleSelectedTable.getTableName());
 DTOGenerator dtoGenerator=new DTOGenerator(singleSelectedTable);
 DAOGenerator daoGenerator=new DAOGenerator(singleSelectedTable,primaryKeyName);
 }catch(IOException ioe)
 {
 f=new JFrame();  
 JOptionPane.showMessageDialog(f,ioe.getMessage());  
 }
 catch(SQLException sqle)
 {
 f=new JFrame();  
 JOptionPane.showMessageDialog(f,sqle.getMessage());  
 }
f=new JFrame();  
JOptionPane.showMessageDialog(f,"Data layer for database of "+singleSelectedTable+" has been created successfully ");  
}

}
}







class sqlClass
{
private Connection c;
private Statement s;
private ResultSet r,r1;
private ResultSetMetaData rsmd;
private Object[][] data;
private Database database;
private Table table;
private Column column;
private String primaryKeyName;
 
sqlClass(String server,int portNo,String directory) throws SQLException,ClassNotFoundException
{
Class.forName("org.apache.derby.jdbc.ClientDriver");
this.c=DriverManager.getConnection("jdbc:derby://"+server+":"+portNo+"/"+directory);

database=new Database();
String dir[]=directory.split("/",0);
int i;
String k="";
for(String d:dir) k=d;
 
database.setDatabaseName(k);
DatabaseMetaData md = c.getMetaData();
String[] types = {"TABLE"};
r = md.getTables(null, null, "%", types);
while (r.next()) 
{
  table=new Table(); 
  table.setTableName(r.getString(3));
System.out.println("TABLE NAME: "+table.getTableName());
this.s=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
   ResultSet.CONCUR_READ_ONLY);


this.r1=s.executeQuery("select * from "+table.getTableName());
this.rsmd = r1.getMetaData();
int columnCount=rsmd.getColumnCount();
for(i=1;i<=columnCount;i++) 
{
column=new Column();
column.setColumnName(rsmd.getColumnLabel(i));
column.setDataType(rsmd.getColumnTypeName(i));
table.addColumn(column);
System.out.println("COLUMN datatype: "+column.getDataType()+" COLUMN Name: "+column.getColumnName());
}
r1.close();
s.close();
database.addTable(table);
}
r.close();
}

public void createTable() throws SQLException,ClassNotFoundException
{
//to be done
}
public Object[][] getTableData(String sqlStatement) throws SQLException,ClassNotFoundException
{
this.s=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
   ResultSet.CONCUR_READ_ONLY);
int i,j;
this.r=s.executeQuery(sqlStatement);
this.rsmd = r.getMetaData();

String[] tableHeading;
int columnCount=rsmd.getColumnCount();
tableHeading=new String[columnCount];
for(i=1;i<=columnCount;i++) tableHeading[i-1]=rsmd.getColumnLabel(i);
r.last();
int rowsCount=r.getRow();
r.beforeFirst();
this.data=new Object[rowsCount][columnCount];
i=0;
while(r.next())
{
for(j=0;j<columnCount;j++) data[i][j]=r.getString(tableHeading[j]);
i++;
}
r.close();
s.close();
return data;
}
public String[] getTableHeading(String sqlStatement) throws SQLException,ClassNotFoundException
{
this.s=c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, 
   ResultSet.CONCUR_READ_ONLY);
System.out.println(sqlStatement);
int i;
this.r=s.executeQuery(sqlStatement);
this.rsmd = r.getMetaData();
String[] tableHeading;
int columnCount=rsmd.getColumnCount();
tableHeading=new String[columnCount];
for(i=1;i<=columnCount;i++) tableHeading[i-1]=rsmd.getColumnLabel(i);
r.close();
s.close();
return tableHeading;
}
public void changeStatement(String sqlStatement) throws SQLException,ClassNotFoundException
{
this.s=c.createStatement();
this.s.executeUpdate(sqlStatement);
s.close();
}

public String getPrimaryKey(String sqlClassName) throws SQLException
{
ResultSet r = null;
DatabaseMetaData meta = c.getMetaData();
r = meta.getPrimaryKeys(null, null, sqlClassName);
while (r.next()) 
{
primaryKeyName = r.getString("COLUMN_NAME");      
}
r.close();
return primaryKeyName;
}


public void disconnect() throws SQLException,ClassNotFoundException
{
c.close();
}
public LinkedList<Table> getTableList()
{
return database.getTableList();
}
}










class connect extends JFrame implements ActionListener,WindowListener
{
JLabel serverLabel,portLabel,directoryLabel,serverError,portError,directoryError;
JTextField serverTF,portTF,directoryTF;
JButton connectButton;
public sqlClass createSQL;
public static LinkedList<Table> linkedListTable;
connect()
{
Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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

directoryLabel=new JLabel("path to DB");
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
setLocation((screenSize.width/2)-250,(screenSize.height/2)-135);
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

linkedListTable=createSQL.getTableList();          
int i=0;
for(Table t:linkedListTable)
{
mainMenu.tableModel.add(i,t);
i++;
}

mainMenu.disconnectMenuItem.setEnabled(true);
mainMenu.connectMenuItem.setEnabled(false);
mainMenu.runButton.setEnabled(true);
mainMenu.generateDTOButton.setEnabled(false);
mainMenu.outputTA.setForeground(greenColor);
mainMenu.outputTA.setText("Start your job");
setVisible(false);                                  /////////          
}catch(SQLException sqlException)
{
/*
serverError.setText("Server name may be wrong");
serverError.setForeground(Color.red);
serverError.setBounds(150,55,280,15);
portError.setText("port number may be wrong");
portError.setForeground(Color.red);
portError.setBounds(150,105,280,15);
directoryError.setText("wrong path may be entered");
directoryError.setBounds(150,155,280,15);
directoryError.setForeground(Color.red);
System.out.println(sqlException);
*/
JFrame f;
f=new JFrame();  
JOptionPane.showMessageDialog(f,sqlException.getMessage());  
}
catch(ClassNotFoundException cnfe)
{
JFrame f;
f=new JFrame();  
JOptionPane.showMessageDialog(f,cnfe.getMessage());  
directoryError.setBounds(150,155,280,15);
directoryError.setForeground(Color.red);
System.out.println(cnfe);
}
}
catch(Exception e)
{
JFrame f;
f=new JFrame();  
JOptionPane.showMessageDialog(f,e.getMessage());  
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











class myUIofSQL
{
public static void main(String gg[])
{
mainMenu m=new mainMenu();
}
} 