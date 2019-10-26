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
	 * Instantiates a new my SQL database and sets database connection attributes.
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
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			try {
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
			conn.close();
		} catch (SQLException e) {
			try {
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
	 *            the sql string
	 * @param numFields
	 *            the num fields
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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		return objectList;
	}

	/**
	 * Fetch data from mysql database called from model class.
	 *
	 * @param sqlString
	 *            the sql string
	 * @param columns
	 *            the columns
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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		if (columns) {
			try {
				ResultSetMetaData rsmd = rs.getMetaData();
				System.out.printf("%n%-10s%-17s%-24s%-10s", rsmd.getColumnName(1), rsmd.getColumnName(2),
						rsmd.getColumnName(3), rsmd.getColumnName(4));

				System.out.printf("%n%-10s%-17s%-24s%-10s", rsmd.getColumnDisplaySize(1), rsmd.getColumnDisplaySize(2),
						rsmd.getColumnDisplaySize(3), rsmd.getColumnDisplaySize(4));

			} catch (SQLException e) {
				try {
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
		PreparedStatement preparedStmt = prepare(sqlString, stringList);

		// create equipment to list,
		// default to null value
		ArrayList<Object> tempList = null;

		// store and return values
		// in 2d arraylist
		ArrayList<ArrayList<Object>> objectList = new ArrayList<ArrayList<Object>>();

		try {

			// execute prepared statement
			ResultSet rs = preparedStmt.executeQuery();

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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
			status = -1;
		}

		return status;
	}

	/**
	 * Prepare.
	 *
	 * @param sql
	 * @return preparedstatement
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
				String[] errorInfo = { String.valueOf(e.getStackTrace()) };
				throw new DLException(e, errorInfo);
			} catch (DLException e1) {
				System.out.println("There was an error completing an operation.");
			}
		}

		return preparedStmt;
	}

	public int setData(String sqlString, List<String> stringList) {
		return executeStmt(sqlString, stringList);
	}

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
}