package com.main;

import java.sql.*;
import java.util.ArrayList;

/**
 * The Class MySQLDatabase
 */
public class MySQLDatabase {

	private static Connection conn;
	private String url;
	private String username;
	private String password;

	/**
	 * Instantiates a new my SQL database
	 * and sets database connection attributes
	 */
	public MySQLDatabase() {
		url = "jdbc:mysql://localhost:3306/travel?useSSL=false";
		username = "root";
		password = "Gv3rn1ca";
	}
	
	/**
	 * Connect to mysql driver
	 */
	public void connect() {
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected to database");
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}
	}

	/**
	 * Close mysql database connection
	 */
	public void close() {
		try {
			conn.close();
			System.out.println("\n");
			System.out.println("Closed database connection");
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}
	}

	/**
	 * Fetch data from mysql database
	 * called from model class
	 *
	 * @param sqlString
	 * @param numFields
	 * @return objectlist
	 */
	public static ArrayList<ArrayList<Object>> getData(String sqlString, int numFields) {

		Statement stmnt = null;
		ResultSet rs = null;
		ArrayList<ArrayList<Object>> objectList = new ArrayList<ArrayList<Object>>();
		ArrayList<Object> tempList = new ArrayList<Object>();

		try {
			stmnt = conn.createStatement();
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}

		try {
			rs = stmnt.executeQuery(sqlString);
			while (rs.next()) {
				for (int i = 1; i <= numFields; i++) {
					tempList.add(rs.getString(i));
				}
			}
			objectList.add(tempList);
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}
		
		return objectList;
	}
	
	/**
	 * Fetch data from mysql database
	 * called from model class
	 *
	 * @param sqlString
	 * @param length
	 * @return objectlist
	 */
	public ArrayList<ArrayList<Object>> getData(String sqlString, boolean columns) {

		Statement stmnt = null;
		ResultSet rs = null;
		ArrayList<Object> tempList;
		ArrayList<ArrayList<Object>> objectList = new ArrayList<ArrayList<Object>>();
		
		try {
			stmnt = conn.createStatement();
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}

		try {
			rs = stmnt.executeQuery(sqlString);
			
			while (rs.next()) {
				tempList = new ArrayList<Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnTypeName(i) == "INT") {
						tempList.add(rs.getInt(i));
					} else {
						tempList.add(rs.getString(i));
					}
				}
				objectList.add(tempList);
			}
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
		}

		if (columns) {
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
			    System.out.printf("%n%-10s%-17s%-24s%-10s", 
			    		rsmd.getColumnName(1),
			    		rsmd.getColumnName(2),
			    		rsmd.getColumnName(3),
			    		rsmd.getColumnName(4));
			    
			    System.out.printf("%n%-10s%-17s%-24s%-10s", 
			    		rsmd.getColumnDisplaySize(1),
			    		rsmd.getColumnDisplaySize(2),
			    		rsmd.getColumnDisplaySize(3),
			    		rsmd.getColumnDisplaySize(4));
				
			} catch (SQLException e) {
				try {
					throw new DLException(e, e.getMessage());
				} catch (DLException e1) {
					System.out.println("There was an error completing operation.");
				}
			}
		}
		return objectList;
	}

	/**
	 * Sets the data for put, post
	 * and delete model methods
	 *
	 * @param sqlString
	 * @param numFields
	 * @return int
	 */
	public int setData(String sqlString, int numFields) {

		int status = -1;

		try {
			PreparedStatement preparedStmt = conn.prepareStatement(sqlString);
			preparedStmt.executeUpdate();
			status = preparedStmt.getUpdateCount();
		} catch (SQLException e) {
			try {
				throw new DLException(e, e.getMessage());
			} catch (DLException e1) {
				System.out.println("There was an error completing operation.");
			}
			status = -1;
		}
		
		return status;
	}
}