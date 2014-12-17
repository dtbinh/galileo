/*This class implements the functionality of writing data into a .csv file
 * 
 */

package util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public final class CSVFile {
	private static String filename = "./res/robotdata.csv";
	
	// private constructor, so you cannot create an instance of it
	private CSVFile(){
	}
	
	public static ArrayList<String[]> readComplete() {
		ArrayList<String[]> filedata = new ArrayList<String[]>();
		
		File file = new File(filename);
		try {
			Scanner inputStream = new Scanner(file);
			
			while(inputStream.hasNext()){
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

	public static void write(String packet) {
		try {
			FileWriter writer = new FileWriter(filename, true);
			
			 	// check if file already exists / file empty, if so create header
			File file = new File(filename);
			if (file.length() == 0) {
					// create header
				String[] packetOrder = UDP_Packet.getOrder();
				for (String element : packetOrder) {
					if (!(element == packetOrder[0]))
						writer.append(',');
					writer.append(element);
				}
				writer.append("\r\n");
			}
			
				// write content into file
			writer.append(packet);
			writer.append("\r\n");
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			System.err.println("Couldn't open file " + filename
					+ "\r\n  Message: " + e.getMessage());
		}
	}
	
	public static void print() {
		ArrayList<String[]> filecontent = readComplete();
		
		for (String[] line : filecontent) {
			for (String s : line){
				System.out.print(s + "\t");
			}
			System.out.println();
		}
	}

}
