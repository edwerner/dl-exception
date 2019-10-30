package com.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {
		
		// instantiate equipment
		// object and set id
		Equipment equipment = new Equipment();
		equipment.setId(894);
		
		ArrayList<Equipment> equipList = equipment.fetch();
		formatTable(equipList);
		
		equipment.swap(568);
//		
//		ArrayList<Equipment> swapEquipList = equipment.fetch();
//		formatTable(swapEquipList);
	}

	// format data table from equipment list
	public static void formatTable(ArrayList<Equipment> equipmentList) {

		for (int i = 0; i < equipmentList.size(); i++) {
			System.out.format("%n%-10s%-17s%-24s%-10s", String.valueOf(equipmentList.get(i).getId()),
					equipmentList.get(i).getName(), equipmentList.get(i).getDescription(),
					String.valueOf(equipmentList.get(i).getCapacity()));
		}
	}
}