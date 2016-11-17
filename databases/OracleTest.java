package databases;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//
// oracle thin driver ojdbc6.jar must be added as external jar
// to Java Build path of this project
//
public class OracleTest extends JFrame {
	//
	private JPanel p;
	private JLabel lblUser,lblPassword;
	private JTextField txtUser;
	JPasswordField txtPassword;
	JButton reset, enter;
	JTextArea display;
	//
	public OracleTest()
	{
		p=new JPanel();
		lblUser=new JLabel("User:");
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword=new JLabel ("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		txtUser=new JTextField(20);
		txtUser.setText("comp228");
		txtPassword=new JPasswordField(20);
		//
		Font f = new Font("Arial", Font.BOLD, 16);
		txtUser.setFont(f);
		txtPassword.setFont(f);
		//
		p.setLayout(new GridLayout(3,2,5,5));
		p.add(lblUser); p.add(txtUser);
		p.add(lblPassword); p.add(txtPassword);
		reset=new JButton("Reset");
		enter = new JButton("Enter");
		p.add(reset);
		p.add(enter);
		//
		reset.setFont(f);
		enter.setFont(f);
		//
		add(p,BorderLayout.CENTER);
		ButtonHandler bHandler= new ButtonHandler();
		reset.addActionListener(bHandler);
		enter.addActionListener(bHandler);
		//create the text area and add scrolling
		display=new JTextArea(10,5);
		JScrollPane scrollPane = new JScrollPane(display);
		add(scrollPane,BorderLayout.SOUTH);
	}
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			String user = txtUser.getText();
			String password = txtPassword.getText();
			connect(user,password);
		}
		
	}
	
	public void connect(String user, String password)
	{
		try
		{
			Class.forName("oracle.jdbc.OracleDriver"); 

			//Class.forName("oracle.jdbc.driver.OracleDriver"); 
			Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@oracle1.centennialcollege.ca:1521:SQLD", user,password);
			//get metadata information
			DatabaseMetaData metadata = connection.getMetaData();
			//create a statement
			Statement st = connection.createStatement();
			//get a resultset from EMP table
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "EMP", null);
			String drop = "Drop TABLE EMP";
			int n;
			//check if EMP table exists
			if(resultSet.next()) //there is a record
			{
			    // Table exists, drop it
				n = st.executeUpdate(drop);
			}
			
			String create ="CREATE TABLE emp (empno NUMBER(4),name VARCHAR2(20))";
			//create the table		
			n = st.executeUpdate(create);
			//populate the table with three rows
			n=st.executeUpdate("insert into emp values(1239,'John Red')");
			n=st.executeUpdate("insert into emp values(2231,'Michael Brown')");
			n=st.executeUpdate("insert into emp values(2236,'Sara Green')");
			//retrieve all rows
			ResultSet rs = st.executeQuery("SELECT * FROM emp");
			//get metadata info for the result set
			ResultSetMetaData md = rs.getMetaData();
			int row=0;
			String info="";
			while(rs.next())
			{
				//System.out.println("Row " +row+"\n");
				for( int i=1;i <= md.getColumnCount();i++)
				{
					info+=md.getColumnName(i)+": "+rs.getString(i)+"\n"; 
				}
				row+=1;
			}
			display.setText(info);
			rs.close();
		}
		catch(ClassNotFoundException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}
		catch(SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			e.printStackTrace();
		}

	}
	public static void main(String[] args) {
		JFrame frame = new OracleTest();
		frame.setSize(600,400);
		frame.setVisible(true);
		
	}
	
}
