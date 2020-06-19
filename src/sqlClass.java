import java.sql.*;
import java.util.*;
public class sqlClass
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

