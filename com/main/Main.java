package com.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) throws DLException {		
		// instantiate equipment object
		Equipment equipment = new Equipment();
		
		// fetch formatted
		// equipment collection
		equipment.fetch(true);
	}
}