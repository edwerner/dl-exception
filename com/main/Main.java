package com.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {
		
		DLUser user = new BLUser(1);
		
		// fetch and print out values
		ArrayList<BLUser> userList = user.fetch();
		System.out.println("UserList size: " + userList.size());
//		formatTable(userList);
		System.out.println("USER LIST: " + userList);
				
//		user.login("admin", "admin");
		
//		// instantiate equipment object
//		// object and set id
//		BLEquipment equipment = new BLEquipment(894);
//		
//		// fetch and print out values
//		ArrayList<BLEquipment> equipList = equipment.fetch();
//		formatTable(equipList);
//		
//		// swap equipment names
//		equipment.swap(568);
//
//		// fetch new equipment list
//		ArrayList<BLEquipment> swapEquipList = equipment.fetch();
//		formatTable(swapEquipList);
//		
//		// instantiate equipment
//		// object and set id
//		BLEquipment equipmentReset = new BLEquipment(568);
//
//		// fetch updated equipment instance
//		ArrayList<BLEquipment> resetEquipList = equipmentReset.fetch();
//		formatTable(resetEquipList);
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