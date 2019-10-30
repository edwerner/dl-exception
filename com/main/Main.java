package com.main;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {
		
		// instantiate equipment
		// object and set id
		Equipment equipment = new Equipment();
		equipment.setId(894);
		
		// fetch and log updated record
		ArrayList<Equipment> equipmentList = equipment.fetch();
		formatTable(equipmentList);
		
		// update and log existing data record
		equipment.put("EquipmentName", "Airbus A320");
		ArrayList<Equipment> alteredEquipmentList = equipment.fetch();
		formatTable(alteredEquipmentList);
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