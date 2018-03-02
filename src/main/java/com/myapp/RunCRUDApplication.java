package com.myapp;

import java.sql.SQLException;

import org.apache.log4j.BasicConfigurator;

public class RunCRUDApplication {
	
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
		MyJDBC runApp = new MyJDBC();
		BasicConfigurator.configure();
	
		MyJDBC.getConnection();
	
		MyJDBC.performSQLOperations();
	
		System.out.println("Successfully connected to the Database!");
	
		Boolean isValid = false;
	
		while(!isValid) {
			switch(runApp.mainMenu()) {
		
				case 1: 
					runApp.createTable();
					break;
				
				case 2: 
					runApp.removeTable();
					break;
					
				case 3: 
					runApp.insertRecord();
					break;
					
				case 4: 
					runApp.updateRecord();
					break;
					
				case 5: 
					runApp.deleteRecord();
					break;
					
				case 6: 
					runApp.searchRecords();
					break;
					
				case 7:
					runApp.viewAllRecords();
					break;
					
				default: 
					isValid = true;
					System.out.println("Exiting the application... ");
					break;
			
				}
			}	
	
			runApp.closeConnections();
		
			System.out.println("Exited successfully!");
		}
	}
