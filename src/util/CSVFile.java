/*This class implements the functionality of writing data into a .csv file
 * 
 */

package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Scanner;

public final class CSVFile {
	private static final String[] PACKET_ORDER = new String[] { "robot",
			"timestamp", "data" };
	public static final String robotDataFilename = "./res/robotdata.csv";

	private CSVFile() {
		// private constructor, so you cannot create an instance of it
	}
	
	public static void write(String filename, String robot, byte[] packetdata) {
		try {
			File file = new File(filename);
			FileWriter writer = new FileWriter(filename, true);
			
			// create header if file is empty / doesn't exist
			if (file.length() == 0) {
				for (String header : PACKET_ORDER) {
					if (header != PACKET_ORDER[0])
						writer.append(',');
					writer.append(header);
				}
				writer.append("\r\n");
			}

			// write content into file
			writer.append(robot + ",");
			writer.append(new Timestamp(System.currentTimeMillis()).toString() + ",");
			writer.append(PacketHandler.getContent(packetdata));
			
			writer.append("\r\n");
			
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.err.println("Couldn't open file " + filename
					+ "\r\n  Message: " + e.getMessage());
		}
	}
	
	public static ArrayList<String[]> readComplete(String filename) {
		ArrayList<String[]> filedata = new ArrayList<String[]>();

		File file = new File(filename);
		try {
			Scanner inputStream = new Scanner(file);

			while (inputStream.hasNext()) {
				String line = inputStream.nextLine();
				String[] lineArray = line.split(",");

				filedata.add(lineArray);
			}

			inputStream.close();
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't open file " + filename
					+ "\r\n  Message: " + e.getMessage());
		}
		return filedata;
	}

	public static void print(String filename) {
		ArrayList<String[]> filecontent = readComplete(filename);

		for (String[] line : filecontent) {
			for (String s : line) {
				System.out.print(s + "\t");
			}
			System.out.println();
		}
	}

}
