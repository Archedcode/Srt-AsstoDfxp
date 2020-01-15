package converter;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Scanner;

public class Entery {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";

	public static void main(String[] args) {
		Scanner read = new Scanner(System.in);
		File sub = null;
		Charset Encoding = Charset.forName("UTF-8");
		try {
			System.out.print("Enter The File/Files Encoding For This Session:*Default Is UTF-8*");
			Encoding = Charset.forName(read.next());
		} catch (java.nio.charset.UnsupportedCharsetException fail) {
			System.out.println("Encoding not supported");
			System.out.println("Do You Want to Continue With The Default Encoding?(Y/N)");
			if (read.next().toUpperCase().charAt(0) == 'Y') {
				System.out.println("OK Continue With The Defaults");
			} else {
				System.out.print("Exiting.....");
				System.exit(0);
			}
		}
		System.gc();
		do {
			System.out.print(ANSI_BLUE + "Enter File Path (.ass or .srt)*Enter -1 To Exit* :" + ANSI_RESET);
			read.nextLine();
			sub = new File(read.nextLine());
			if (sub.toString().equals("-1")) {
				read.close();
				System.exit(0);
			}
			BufferedReader subread = null;
			boolean con = false;

			do {
				try {
					subread = new BufferedReader(new FileReader(sub, Encoding));
					con = false;
				} catch (java.io.FileNotFoundException e) {
					System.err.println("No File Found");
					con = true;
					sub = new File(read.nextLine());
					if (sub.toString().equals("-1")) {
						read.close();
						System.exit(0);
					}

				} catch (IOException e) {
					System.out.println("Wrong Encoding ,Exiting...");
					System.exit(0);
				}
			} while (con);
			ArrayList<String> file = new ArrayList<String>();
			String content = null;
			try {
				while ((content = subread.readLine()) != null) {
					file.add(content);
				}
			} catch (IOException ER) {
				System.out.println("couldn't read file");
			}
			String type = sub.toString().substring(sub.toString().lastIndexOf('.'), sub.toString().length());
			if (type.equalsIgnoreCase(".ass") || type.equalsIgnoreCase(".ssa"))
				new Ass(file, sub.getAbsolutePath().toString(),Encoding);
			else if (type.equalsIgnoreCase(".srt"))
				new Srt(file, sub.getAbsolutePath().toString(),Encoding);
			else
				System.err.println("File type not supported");

		} while (0 < 1);

	}

}
