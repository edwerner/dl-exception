package com.main;

import java.util.ArrayList;
import java.util.List;

public class BLUser extends DLUser {
	
	public BLUser(int userId) {
		super(userId);
		this.userId = userId;
	}
	
	public BLUser(int userId,
			String userName, 
			String password, 
			String name,
			String access) {
		
		super(userId, userName, password, name, access);
		
		// set user attributes
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.access = access;
	}
	
	public boolean save(BLUser user, BLEquipment equipment) {
//		boolean isLoggedIn = this.login(user.getUsername(), user.getPassword());
//		System.out.println("IS LOGGED IN: " + isLoggedIn);
		
		boolean authenticated = false;
		if (user.getAccess().equals("Admin") || user.getAccess().equals("Editor")) {
			
			// user is authenticated
			authenticated = true;
			System.out.println("User has save rights");
			
			// save new equipment object
			equipment.post();
		} else {
			System.out.println("User has no save rights");
		}
		return authenticated;
	}
	
	public ArrayList<BLUser> fetch() {

		// query database for equipment by id
		ArrayList<ArrayList<Object>> tempList = new ArrayList<ArrayList<Object>>();
		ArrayList<BLUser> userList = new ArrayList<BLUser>();

		// create and execute query passing in
		// table format boolean and return
		// equipment collection
		String query = "SELECT * FROM users WHERE UserID = ?";

		// connect to database
		db.connect();

		// create string list and set id as string
		List<String> stringList = new ArrayList<String>();
		stringList.add(0, String.valueOf(this.getUserId()));
		System.out.println("USER ID: " + this.getUserId());

		// query database with get method
		tempList = db.getData(query, stringList);

		System.out.println("TEMP LIST: " + tempList);


		// iterate through collection and
		// set equipment entity attributes
		for (int i = 0; i < tempList.size(); i++) {
			System.out.println("Entered for loop");
			// retrieve and format objects
			// returned from database query
			int userId = (int) tempList.get(i).get(0);
			String username = (String) tempList.get(i).get(1);
			String password = (String) tempList.get(i).get(2);
			String name = (String) tempList.get(i).get(3);
			String access = (String) tempList.get(i).get(4);

			// instantiate new bluser pojo
			// and add to user arraylist
			BLUser user = new BLUser(userId, username, password, name, access);
			userList.add(user);
		}
		
		// close database connection
		db.close();

		// return equipment arraylist
		return userList;
	}
}
