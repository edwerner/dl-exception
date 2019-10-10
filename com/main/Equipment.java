package com.main;

import java.util.ArrayList;

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
	 * Instantiates a new equipment
	 * instance with id parameter
	 *
	 * @param id the id
	 */
	public Equipment(int id) {
		this.id = id;
	}
	
	/**
	 * Instantiates a new equipment
	 * instance with parameters
	 *
	 * @param id the id
	 * @param name the name
	 * @param description the description
	 * @param capacity the capacity
	 */
	public Equipment(int id, String name, String description, int capacity) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.capacity = capacity;
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
	public ArrayList<Equipment> fetch(boolean columns) {
		
		// query database for equipment by id
		ArrayList<ArrayList<Object>> tempList = new ArrayList<ArrayList<Object>>();
		ArrayList<Equipment> equipmentList = new ArrayList<Equipment>();
		
		// create and execute query passing in
		// table format boolean and return
		// equipment collection
		String query = "SELECT * FROM equipment";
		
		// connect to database
		db.connect();
		
		// query database passing in
		// columns boolean
		tempList = db.getData(query, columns);
		
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
		
		// format data table
		formatTable(equipmentList);

		// close database connection
		db.close();
		
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
	public int put(int equipId, String column, String value) {
		
		// create put query
		String putQuery = "Update `equipment` SET `" + column + "` = '" + value + "'  where `EquipID` = '" + equipId +"'";
		
		// connect to database
		db.connect();
		
		// update data record by id
		// and save to database
		int putDataResult = db.setData(putQuery, 4);
		
		// close database connection
		db.close();
		
		// set data
		return putDataResult;
	}
	
	/**
	 * Post new record in database
	 *
	 * @param id
	 * @param name
	 * @param description
	 * @param capacity
	 * @return int
	 */
	public int post(int id, String name, String description, int capacity) {
		
		// create post query
		String postQuery = "INSERT into `equipment` (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity)" + 
		"VALUES ('" + id + "','" + name + "','" + description + "','" + capacity + "')";

		// connect to database
		db.connect();
		
		// post data and return result count
		int postDataResult = db.setData(postQuery, 4);
		
		// close database connection
		db.close();
		
		return postDataResult;
	}
	
	/**
	 * Delete database record
	 *
	 * @param id
	 * @return int
	 */
	public int delete(int id) {
		
		// create delete query
		String deleteQuery = "DELETE from equipment WHERE EquipID = " + id;
		
		// connect to database
		db.connect();
				
		// delete record by id and
		// return record result count
		int deleteDataResult = db.setData(deleteQuery, 4);

		// close database connection
		db.close();
		
		return deleteDataResult;
	}


	// format data table from equipment list
	public void formatTable(ArrayList<Equipment> equipmentList) {

		for (int i = 0; i < equipmentList.size(); i++) {
			System.out.format("%n%-10s%-17s%-24s%-10s", String.valueOf(equipmentList.get(i).getId()),
					equipmentList.get(i).getName(), equipmentList.get(i).getDescription(),
					String.valueOf(equipmentList.get(i).getCapacity()));
		}
	}
}
