package com.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class DLException extends Exception {

	public DLException(Exception e) {
		super(e.getMessage());
	}
	
	public DLException(Exception e, String... values) {
		super(values[0]);
		try {
			writeLog(values);
		} catch (IOException e1) {
			System.out.println("There was an error completing an operation.");
		}
	}
	
	public void writeLog(String... values) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
		try {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm:ss a z");
			Date date = new Date(System.currentTimeMillis());
			writer.append(formatter.format(date));
			writer.append("\n");
			writer.append(values[0]);
			writer.append("\n\n");
		} catch (IOException e2) {
			System.out.println("There was an error completing an operation.");
		} finally {
			writer.close();
		}
	}
}
