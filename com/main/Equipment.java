package com.main;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Equipment
 */
public class Equipment {

	private int id;
	private String name;
	private String description;
	private int capacity;
	private MySQLDatabase db;

	/**
	 * Empty constructor
	 */
	public Equipment() {
		// create sql database instance
		// and connect to database
		db = new MySQLDatabase();
	}

	/**
	 * Instantiates a new equipment instance with id parameter
	 *
	 * @param id
	 */
	public Equipment(int id) {
		this.id = id;
		
		// create sql database instance
		// and connect to database
		db = new MySQLDatabase();
	}

	/**
	 * Instantiates a new equipment instance with parameters
	 *
	 * @param id
	 * @param name
	 * @param description
	 * @param capacity
	 */
	public Equipment(int id, String name, String description, int capacity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.capacity = capacity;

		// create sql database instance
		// and connect to database
		db = new MySQLDatabase();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * Fetch equipment arraylist
	 *
	 * @param columns
	 * @return equipment
	 */
	public ArrayList<Equipment> fetch() {

		// query database for equipment by id
		ArrayList<ArrayList<Object>> tempList = new ArrayList<ArrayList<Object>>();
		ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();

		// create and execute query passing in
		// table format boolean and return
		// equipment collection
		String query = "SELECT * FROM equipment where EquipID = ?";

		// connect to database
		db.connect();

		// create string list and set id as string
		List<String> stringList = new ArrayList<String>();
		stringList.add(0, String.valueOf(this.getId()));

		// query database with get method
		tempList = db.getData(query, stringList);

		// close database connection
		db.close();

		// iterate through collection and
		// set equipment entity attributes
		for (int i = 0; i < tempList.size(); i++) {

			// retrieve and formate objects
			// returned from database query
			int id = (int) tempList.get(i).get(0);
			String name = (String) tempList.get(i).get(1);
			String description = (String) tempList.get(i).get(2);
			int capacity = (int) tempList.get(i).get(3);

			// instantiate new equipment pojo
			// and add to equipment arraylist
			Equipment equipment = new Equipment(id, name, description, capacity);
			equipmentList.add(equipment);
		}

		// return equipment arraylist
		return equipmentList;
	}

	/**
	 * Update existing database record
	 *
	 * @param equipId
	 * @param column
	 * @param value
	 * @return int
	 */
	public int put(String column, String value) {

		String putQuery = "UPDATE equipment SET " + column + " = ? WHERE EquipID = ?";

		// create string list and set id as string
		List<String> stringList = new ArrayList<String>();
		stringList.add(0, value);
		stringList.add(1, String.valueOf(this.id));

		// connect to database
		db.connect();

		// query database with get
		// method and save to database
		int putDataResult = db.setData(putQuery, stringList);

		// close database connection
		db.close();

		// return records changed count
		return putDataResult;
	}

	/**
	 * Post new record to database
	 *
	 * @return int
	 */
	public int post() {

		// create post query
		String postQuery = "INSERT into `equipment` (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity)"
				+ "VALUES (?, ?, ?, ?)";

		// create string list and set id as string
		List<String> stringList = new ArrayList<String>();
		stringList.add(0, String.valueOf(this.id));
		stringList.add(1, this.name);
		stringList.add(2, this.description);
		stringList.add(3, String.valueOf(this.capacity));

		// connect to database
		db.connect();

		// post data
		int postDataResult = db.setData(postQuery, stringList);

		// close database connection
		db.close();
		
		// return records changed count
		return postDataResult;
	}

	/**
	 * Delete database record
	 *
	 * @param id
	 * @return int
	 */
	public int delete() {
		
		String disableFKQuery = "SET FOREIGN_KEY_CHECKS=0;"; 
		String enableFKQuery = "SET FOREIGN_KEY_CHECKS=1;";
		String deleteEquipQuery = "DELETE FROM equipment WHERE EquipID = ?;";
		String deleteTripQuery = "DELETE FROM trip WHERE EquipId = ?;";
		
		// store prepared statement attributes
		List<String> stringList = new ArrayList<String>();
		stringList.add(0, String.valueOf(this.id));
		
		// connect to database
		db.connect();

		// delete record by id and
		// return record result count
		db.setData(disableFKQuery, 1);
		int deleteEquipResult = db.setData(deleteEquipQuery, stringList);
		db.setData(deleteTripQuery, stringList);
		db.setData(enableFKQuery, 1);

		// close database connection
		db.close();
		
		// return records changed count
		return deleteEquipResult;
	}
}
