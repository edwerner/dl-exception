package com.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {		
		// instantiate equipment object
		Equipment equipment = new Equipment();
		
		ArrayList<Equipment> equipmentList = equipment.fetch();
		formatTable(equipmentList);
		
//		int postCount = equipment.post(5000, "Lego Airplane", "Airplane made of legos", 1);
//		System.out.println("POST COUNT: " + postCount);
//		
//		int deleteCount = equipment.delete(5000);
//		System.out.println("DELETE COUNT: " + deleteCount);
		
//		int putCount = equipment.put(5000, "EquipmentName", "Fake Plane");
//		System.out.println("PUT COUNT:" + putCount);
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