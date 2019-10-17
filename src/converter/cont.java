package converter;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class cont{
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		File sub=null;
		do {
		System.out.print(ANSI_BLUE+"Enter File Path (.ass or .srt)*Enter -1 To Exit* :"+ANSI_RESET); 
		sub = new File(read.nextLine());
		if(sub.toString().equals("-1")) {
			read.close();
			System.exit(0);}
		Scanner subread = null;
		boolean con =false;
		do {
		try {
			subread = new Scanner(sub);
			con =false;
		}catch(java.io.FileNotFoundException e) {
			System.err.println("No File Found");
			con = true;
			sub = new File(read.nextLine());
			if(sub.toString().equals("-1")) {
				read.close();
				System.exit(0);}
				
		}
		}while(con);
		
		
		
		
		ArrayList<String> file = new ArrayList<String>();
		while(subread.hasNext()) {
			file.add(subread.nextLine());
		}
		String type = sub.toString().substring(sub.toString().lastIndexOf('.'),sub.toString().length());
		if(type.equalsIgnoreCase(".ass"))
			new Ass(file,sub.getAbsolutePath().toString());
		else
			if(type.equalsIgnoreCase(".srt"))
				new Srt(file,sub.getAbsolutePath().toString());
			else
				System.err.println("File type not supported");
		
		}while(0<1);

	
	}

}
