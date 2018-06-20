package examples;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class python_test {

	public static void main(String[] args) {
		try {
			Process p = Runtime.getRuntime()
					.exec("python \"C:\\Users\\baval"
							+ "\\OneDrive\\Documents v2\\2017 Summer research\\chesec"
							+ "\\HelloWorld.py\""); // runs the Python code located at the file path
	        p.waitFor();
	        p.destroy();
	        
		} catch (IOException e) {
			System.out.println("Python File not located");
			e.printStackTrace();
			
		} catch (InterruptedException e) {
			System.out.println("Python Process was interrupted");
			e.printStackTrace();
		}
		
		File file = new File("C:\\Users\\baval\\eclipse-workspace\\HyFlex\\HelloWorld.txt");
	    Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			System.out.println("Something went wrong when locating the file");
			e.printStackTrace();
		}
		sc.useDelimiter("\n");
	    System.out.println(sc.next()); //prints out the line in the file
	    System.out.println(sc.next()); //prints out the line in the file
	    String path = "C:\\Users\\baval\\OneDrive\\Documents v2\\2017 Summer research\\chesec\\table.html";
	    FileReader fileReader;
	    int linecount = 0;
		try {
			fileReader = new FileReader(path);
			BufferedReader bf = new BufferedReader(fileReader);
			String line = bf.readLine().trim();
			System.out.println(line);
			while(!line.equals( "if ( ! ie )")){
				System.out.println(line);
				line = bf.readLine().trim();
					
			}
			while(!line.equals("}")) {
				System.out.println("inside linecounter");
				linecount++;
				line = bf.readLine().trim();
			}
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Number of violations" + (linecount-2)/3);

	    
	    
	    
	    
	}
}
