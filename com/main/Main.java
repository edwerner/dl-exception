package com.main;

import java.util.ArrayList;

public class Main {
	
	public static void main(String[] args) {
		
		// create sql database instance
		// and connect to database
		MySQLDatabase db = new MySQLDatabase();
		db.connect();
		
		// instantiate equipment object
		Equipment equipment = new Equipment();
		
		// create and fetch formatted
		// equipment collection
		ArrayList<Equipment> equipmentListFormatted = equipment.fetch(true);
		
		// format data table
		formatTable(equipmentListFormatted);
		
		// close connection
		db.close();
		
		// open connection
		db.connect();
		
		// create and fetch unformatted
		// equipment collection
		ArrayList<Equipment> equipmentListUnformatted = equipment.fetch(false);
		formatTable(equipmentListUnformatted);

		// close database connection
		db.close();
	}
	
	// format data table from equipment list
	public static void formatTable(ArrayList<Equipment> equipmentList) {
		
		for (int i = 0; i < equipmentList.size(); i++) {
		    System.out.format("%n%-10s%-17s%-24s%-10s", 
		    		String.valueOf(equipmentList.get(i).getId()), 
		    		equipmentList.get(i).getName(),
		    		equipmentList.get(i).getDescription(), 
		    		String.valueOf(equipmentList.get(i).getCapacity()));
		}
	}
}