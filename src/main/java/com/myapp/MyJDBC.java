package com.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import org.apache.log4j.Logger;

public class MyJDBC {
	final static Logger log = Logger.getLogger(MyJDBC.class);
	
	private static final String DRIVER_NAME = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_NAME = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String USERNAME = "c##vishal";
	private static final String PASSWORD = "vishal";
	
	private static Connection connect;
	private static Statement statement;
	private static ResultSet results;
	
	private static final Scanner scan = new Scanner(System.in);

	public static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER_NAME);
		
		connect = DriverManager.getConnection(DB_NAME, USERNAME, PASSWORD);
		
		log.info("Database credentials are validated successfully!");
		
	}
	
	public static void performSQLOperations() throws SQLException {
		statement = connect.createStatement();
	}
	
	public int mainMenu() {
		System.out.println("Main Menu: ");
		
		System.out.println("1.	Create a new Table");
		System.out.println("2.	Delete a Table");
		System.out.println("3. 	Add a new record");
		System.out.println("4. 	Update a record");
		System.out.println("5. 	Delete a record");
		System.out.println("6. 	Search records");
		System.out.println("7. 	View records");		
		System.out.println("8. 	Exit");
		
		System.out.println("Please, enter your selection: ");
		int selection = scan.nextInt();
		
		return selection;
	}
	
	void createTable() throws SQLException {
		scan.nextLine();
		String tableName = scan.nextLine();
		
		try {
			
			statement
			.execute("Create table "
					+ tableName+"(E_ID NUMBER(10) Primary key, "
					+ "E_NAME VARCHAR(40), "
					+ "E_SALARY NUMBER(10),"
					+ "E_SSN NUMBER(9))");
			
			System.out.println("A Table has been successfully created!");
		} catch (SQLException e) {
			
			log.error("Table name already exists!");
		}
		
	}
	
	void removeTable() {
		System.out.println("Enter the table name: ");
		String tableName = scan.next();
		
		System.out.println("Removing the Table. Are you sure?");
		scan.nextLine();
		String confirm = scan.nextLine();
		
		if(confirm.toLowerCase().equals("yes")) {
			try {
				
				statement.execute("Drop table " +tableName);
				System.out.println("Table deleted!");
			} catch (SQLException e) {
				
				System.out.println("Table does not exist / has been deleted!");
			}
		} 

	}
	
	

	void deleteRecord() {
		System.out.println("DELETE Record");
		System.out.println("Enter a Table Name: ");
		String tableName = scan.next();
		
		System.out.println("Enter the Employee to delete: ");
		String empName = scan.next();
		
		try {
			
			statement.execute("Delete from " +tableName
					+ " where e_name='" +empName +"'");
			
			System.out.println("Record deleted!");
		} catch (SQLException e) {
			
			log.warn("Cannot delete the record!");
		}
	}

	void insertRecord() {
		System.out.println("Inserting new records...");
		System.out.println("New ID for the record?");
		int emp_id = scan.nextInt();
		
		System.out.println("New Name for the record?");
		scan.nextLine();
		String emp_name = scan.nextLine();
		
		System.out.println("New Salary for the record?");
		int emp_sal = scan.nextInt();
		
		System.out.println("SSN?");
		int emp_ssn = scan.nextInt();
		
		try {
			
			statement
//			.execute("insert into newTable values(" +23 +, 'Jon', 3400, 569012)");
				.execute("insert into newTable values("
						+ emp_id +", '" 
						+ emp_name +"', " 
						+emp_sal +", " 
						+emp_ssn +")");
			
			log.info(emp_name +" inserted successfully!");
		} catch (SQLException e) {
			
			System.out.println(e.getMessage());
			log.error("Please, insert a unique ID");
		}
	}

	void searchRecords() {
		System.out.println("SEARCH Records");
		System.out.println("Enter a Table Name: ");
		String tableName = scan.next();
		
		System.out.println("Enter a name to begin search: ");
		String empName = scan.next();
		
		try {
			results = statement.executeQuery("Select * from " +tableName +" where E_NAME = '" +empName +"'");
			 
			while(results.next()) {
				System.out.println("Emp ID: " +results.getInt(1)
						+ "\nEmp Name: " +results.getString(2)
						+ "\nEmp Salary: " +results.getInt(3)
						+ "\nEmp SSN: " +results.getInt(4));
			}
			
		} catch (SQLException e) {
			
			System.out.println("No records found!");
		}
		
	}

	void updateRecord() {
		log.info("Update Record");
		System.out.println("Enter the Table Name: ");
		String tableName = scan.next();
		
		System.out.println("Enter the unique ID for the record");
		int empId = scan.nextInt();
		
		System.out.println("Enter the Name you want to update: ");
		String empName = scan.next();
		
		try {
			statement.execute("Update " +tableName
					+" SET e_name = '" +empName +"'"
					+ " where e_id = " +empId);
			
			System.out.println("Updated the record!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	void viewAllRecords() {
		System.out.println("Enter a Table Name: ");
		String tableName = scan.next();
		int count = 0;
		
		try {
			
			results = statement.executeQuery("Select * from " +tableName);
			System.out.println("****************All Records***********************");
//			System.out.println("S.No \t ID \t Name \t Salary \t SSN");
			while(results.next()) {
				count++;
				System.out.println("Record " +count +": \t" 
						+results.getInt(1) +"\t "
						+results.getString(2) +"\t "
						+results.getInt(3) +"\t "
						+results.getInt(4));
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		System.out.println("**************************************************");
	}

	public void closeConnections() throws SQLException {
		statement.close();
		connect.close();
	}

}
