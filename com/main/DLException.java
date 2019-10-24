package com.main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("serial")
public class DLException extends Exception {

	public DLException(Exception e) {
		super(e);
	}
	
	public DLException(Exception e, String... values) {
		super(e);
		try {
			writeLog(e, values);
		} catch (IOException e1) {
			System.out.println("There was an error completing an operation.");
		}
	}
	
	public void writeLog(Exception e, String... values) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter("ErrorLog.txt", true));
		try {
			SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' hh:mm:ss a z");
			Date date = new Date(System.currentTimeMillis());
			writer.append(formatter.format(date));
			
			StackTraceElement[] stackTrace = e.getStackTrace();

			for (StackTraceElement element : stackTrace) {
				if (element.getClassName().contains("com.main")) {
					System.out.println("Class name: " + element.getClassName());
					System.out.println("Line number: " + element.getLineNumber());
				}
			}
//			writer.append("\n");
//			writer.append("VALUES[0] " + values[0]);
//			writer.append("\n");
//			writer.append("VALUES[1] " + values[1]);
//			writer.append("\n");
//			writer.append("Message: " + e.getMessage());
//			writer.append("\n\n");
		} catch (IOException e2) {
			System.out.println("There was an error completing an operation.");
		} finally {
			writer.close();
		}
	}
}
