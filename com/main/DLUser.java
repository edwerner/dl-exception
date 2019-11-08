package com.main;

import java.util.ArrayList;

public abstract class DLUser {
	
	protected int userId;
	protected String userName;
	protected String password;
	protected String name;
	protected String access;
	protected MySQLDatabase db;

	public DLUser(int userId) {
		
		this.userId = userId;
		
		// create sql database instance
		db = new MySQLDatabase();
	}
	
	public DLUser(int userId,
			String userName, 
			String password, 
			String name,
			String access) {
		
		// set user attributes
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.access = access;
		
		// create sql database instance
		// and connect to database
		db = new MySQLDatabase();
	}

	/**
	 * Log in user with username and password
	 *
	 * @param userName
	 * @param password
	 * @return boolean
	 */
	public boolean login(String userName, String password) {
		
		// default login status
		boolean loginStatus = false;
		
		// fetch current bluser and validate
		// username and password
		BLUser user = this.fetch().get(0);
		if (user.getUsername().equals(userName) && user.getPassword().equals(password)) {
			loginStatus = true;
		}
		
		// return login status
		return loginStatus;
	}

	/**
	 * Method contract for new equipment save
	 *
	 * @param user
	 * @param  equipment
	 * @return boolean
	 */
	public abstract boolean save(BLUser user, BLEquipment equipment);

	/**
	 * Method contract for bluser fetch
	 *
	 * @param user
	 * @param  equipment
	 * @return bluser arraylist
	 */
	public abstract ArrayList<BLUser> fetch();

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return userName;
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
}
