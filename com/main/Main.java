package com.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {
		
		// create and fetch bluser by userid
		BLUser user = new BLUser(3).fetch().get(0);
		
		// login user with username
		// and password credentials
		boolean loggedIn = user.login("general", "general");
		
		if (loggedIn) {
			// instantiate equipment
			// object and set id
			BLEquipment equipment = new BLEquipment(568);
			
			// fetch and print out values
			formatTable(equipment.fetch());
			
			// swap equipment names
			equipment.swap(894);

			// fetch swapped equipment list
			formatTable(equipment.fetch());	
			
//			// test login and save logic
//			// for admin and editor access
//			equipment = new BLEquipment();
//			equipment.setCapacity(189);
//			equipment.setDescription("Passenger and Cargo");
//			equipment.setId(350);
//			equipment.setName("CL-44D4");
//			
//			boolean saved = user.save(user, equipment);
//			System.out.println("");
//			System.out.println("Saved: " + saved);
		}
	}

	// format data table from equipment list
	public static void formatTable(ArrayList<BLEquipment> equipmentList) {

		for (int i = 0; i < equipmentList.size(); i++) {
			System.out.format("%n%-10s%-17s%-24s%-10s", String.valueOf(equipmentList.get(i).getId()),
					equipmentList.get(i).getName(), equipmentList.get(i).getDescription(),
					String.valueOf(equipmentList.get(i).getCapacity()));
		}
	}
}