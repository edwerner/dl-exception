package com.main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLDatabase {

	private static Connection conn;
	private String url;
	private String username;
	private String password;

	/**
	 * Instantiates a new MySQL database and sets database connection attributes.
	 */
	public MySQLDatabase() {
		url = "jdbc:mysql://localhost:3306/travel?useSSL=false";
		username = "root";
		password = "Gv3rn1ca";
	}

	/**
	 * Connect to mysql driver.
	 */
	public void connect() {
		try {
			// create connection with attributes
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}
	}

	/**
	 * Close mysql database connection.
	 */
	public void close() {
		try {
			// close connection
			conn.close();
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}
	}

	/**
	 * Fetch data from mysql database called from model class.
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
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		try {
			// execute string query
			rs = stmnt.executeQuery(sqlString);
			while (rs.next()) {
				for (int i = 1; i <= numFields; i++) {
					tempList.add(rs.getString(i));
				}
			}
			// add temp list to objectlist
			objectList.add(tempList);
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		// return objectlist
		return objectList;
	}

	/**
	 * Fetch data from mysql database called from model class.
	 *
	 * @param sqlString
	 * @param columns
	 * @return objectlist
	 */
	public ArrayList<ArrayList<Object>> getData(String sqlString, boolean columns) {

		Statement stmnt = null;
		ResultSet rs = null;
		ArrayList<Object> tempList;
		ArrayList<ArrayList<Object>> objectList = new ArrayList<ArrayList<Object>>();

		try {
			// create statement
			stmnt = conn.createStatement();
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		try {
			// execute query
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
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		if (columns) {
			try {
				// get result set metadata and
				// print out table data
				ResultSetMetaData rsmd = rs.getMetaData();
				System.out.printf("%n%-10s%-17s%-24s%-10s", rsmd.getColumnName(1), rsmd.getColumnName(2),
						rsmd.getColumnName(3), rsmd.getColumnName(4));

				System.out.printf("%n%-10s%-17s%-24s%-10s", rsmd.getColumnDisplaySize(1), rsmd.getColumnDisplaySize(2),
						rsmd.getColumnDisplaySize(3), rsmd.getColumnDisplaySize(4));

			} catch (SQLException e) {
				try {
					// throw dlexception and pass error info
					String[] errorInfo = { String.valueOf(e.getStackTrace()) };
					throw new DLException(e, errorInfo);
				} catch (DLException e1) {
					System.out.println("There was an error completing an operation.");
				}
			}
		}
		return objectList;
	}

	public ArrayList<ArrayList<Object>> getData(String sqlString, List<String> stringList) {

		// create prepared statement
		// with sql string
		PreparedStatement preparedStmt = prepare(sqlString, stringList);;

		// create equipment to list,
		// default to null value
		ArrayList<Object> tempList = null;

		// store and return values
		// in 2d arraylist
		ArrayList<ArrayList<Object>> objectList = null;
		try {

			// execute prepared statement
			ResultSet rs = preparedStmt.executeQuery();
			
			//instantiate object list
			objectList = new ArrayList<ArrayList<Object>>();

			// iterate and store values
			// in temp arraylist based
			// on data type
			while (rs.next()) {
				tempList = new ArrayList<Object>();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					if (rs.getMetaData().getColumnTypeName(i) == "INT") {
						tempList.add(rs.getInt(i));
					} else {
						tempList.add(rs.getString(i));
					}
				}
				// add temp list to 2d arraylist
				objectList.add(tempList);
			}
		} catch (SQLException e) {
			try {
				// throw dlexception and pass param
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		// return 2d array list
		return objectList;
	}

	/**
	 * Sets the data for put, post and delete model methods.
	 *
	 * @param sqlString
	 * @param numFields
	 * @return dataCount
	 */
	public int setData(String sqlString, int numFields) {

		int dataCount = -1;

		try {
			// create prepared statement
			// and execute and get update
			// count
			PreparedStatement preparedStmt = conn.prepareStatement(sqlString);
			preparedStmt.executeUpdate();
			dataCount = preparedStmt.getUpdateCount();
		} catch (SQLException e) {
			try {
				// throw dlexception and pass param
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
			dataCount = -1;
		}

		return dataCount;
	}
	


	/**
	 * prepare statement
	 *
	 * @param sqlString
	 * @param stringList
	 * @return preparedStmt
	 */
	private PreparedStatement prepare(String sqlString, List<String> stringList) {

		PreparedStatement preparedStmt = null;
		try {
			// create prepared statement
			preparedStmt = conn.prepareStatement(sqlString);

			// iterate and bind data to
			// prepared statement
			for (int i = 0; i < stringList.size(); i++) {
				preparedStmt.setString(i + 1, stringList.get(i));
			}
		} catch (SQLException e) {
			try {
				// throw dlexception and pass param
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		// return prepared statement
		return preparedStmt;
	}

	/**
	 * setData
	 *
	 * @param sqlString
	 * @param stringList
	 * @return rowCount
	 */
	public int setData(String sqlString, List<String> stringList) {
		return executeStmt(sqlString, stringList);
	}

	/**executeStmt
	 *
	 * @param sqlString
	 * @param stringList
	 * @return rowCount
	 */
	private int executeStmt(String sqlString, List<String> stringList) {

		int rowCount = 0;

		try {
			// create prepared statement
			// with sql string
			PreparedStatement preparedStmt = prepare(sqlString, stringList);

			// execute prepared update
			// and assign to rowcount
			rowCount = preparedStmt.executeUpdate();
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		// return row count or
		// default to zero
		return rowCount;
	}

	public void startTrans() {
		try {
			conn.setAutoCommit(false);
		} catch (SQLException e) {
			try {
				// rollback transaction
				rollbackTrans();
				
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}
	}
	
	public void endTrans() {
		try {
			conn.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				// rollback transaction
				rollbackTrans();
				
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}
	}
	
	public void rollbackTrans() {
		try {
			conn.rollback();
		} catch (SQLException e) {
			try {
				// throw dlexception and pass error info
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}
	}
}