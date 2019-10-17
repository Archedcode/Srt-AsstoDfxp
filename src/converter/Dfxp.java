package converter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Dfxp {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	private ArrayList<String> Start ;
	private ArrayList<String> End ;
	private ArrayList<String> DfxpFile;
public Dfxp (ArrayList<String> s,ArrayList<String> e,ArrayList<String> sub,String filepath) {
	Start = s;
	End =e;
	DfxpFile =sub;
	FinishedFile(filepath);
	System.out.println(ANSI_GREEN+"Done"+ANSI_RESET);
}
public void FinishedFile(String filepath) {
	String path = filepath;
	try {
		FileWriter file=new FileWriter(path+".dfxp");
		file.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<tt xml:lang='en' xmlns='http://www.w3.org/2006/10/ttaf1' xmlns:tts='http://www.w3.org/2006/10/ttaf1#style'>\n" + "<head></head>\n" + "<body>\n" + "<div xml:id=\"captions\">\n");
		for(int i =0;i<DfxpFile.size();i++)
			file.append("<p begin="+"\""+Start.get(i)+"\" "+"end="+"\""+End.get(i)+"\">"+DfxpFile.get(i)+"</p>\n");
		file.append("</div>\n" + "</body>\n" + "</tt>");
		file.close();
		}catch(IOException e) {
			System.err.print("previous file path not found");
			System.exit(0);
		}
}


}
