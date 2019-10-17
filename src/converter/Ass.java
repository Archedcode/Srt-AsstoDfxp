package converter;

import java.util.*;
public class Ass {
	private ArrayList<String> AssFile;
	private ArrayList<String> diologonly=new ArrayList<String>();
	private ArrayList<String> Start=new ArrayList<String>();
	private ArrayList<String> End=new ArrayList<String>();
public Ass(ArrayList<String> File,String filepath) {
	AssFile = File;
	Dialogue();
	cleaner();
	new Dfxp(Start,End,AssFile,filepath);
}
public void Dialogue() {
	int Dindex=0;
	for(int i =0 ;i<AssFile.size();i++) {
		if(AssFile.get(i).contains("Dialogue:")) {
			Dindex=i;
			break;}}
do {
	Dindex--;
	AssFile.remove(Dindex);
}while(Dindex!=0);

for(int i =0 ;i<AssFile.size();i++) 
	if(AssFile.get(i).contains("Dialogue"))
		diologonly.add(AssFile.get(i).substring(0,AssFile.get(i).length()));
		

}
public void cleaner() {
	//remove format and layer
	for(int i =0;i<diologonly.size();i++) {
		diologonly.set(i, diologonly.get(i).replace("Dialogue:", "").trim());
		diologonly.set(i, diologonly.get(i).substring(diologonly.get(i).indexOf(',')+1,diologonly.get(i).length()));
	}
	//get start time stamp
	for(int i =0;i<diologonly.size();i++) {
		Start.add(diologonly.get(i).substring(0,diologonly.get(i).indexOf(',')));
		diologonly.set(i, diologonly.get(i).substring(diologonly.get(i).indexOf(',')+1,diologonly.get(i).length()));
	}
	//get end time stamp
	for(int i =0;i<diologonly.size();i++) {
		End.add(diologonly.get(i).substring(0,diologonly.get(i).indexOf(',')));
		diologonly.set(i, diologonly.get(i).substring(diologonly.get(i).indexOf(',')+1,diologonly.get(i).length()));
	}
	//further cleanup
	for(int j =0;j<6;j++)
		for(int i =0;i<diologonly.size();i++) 
			diologonly.set(i, diologonly.get(i).substring(diologonly.get(i).indexOf(',')+1,diologonly.get(i).length()));
	//text cleanup
	for(int i =0;i<diologonly.size();i++) 
		diologonly.set(i, diologonly.get(i).replaceAll("\\{[^\\}]*\\\\p[1-9][^\\}]*\\}.*?(\\{[^\\}]*\\\\p0[^\\}]*\\}|$)|\\{.*?\\}".trim(), ""));
	//final reorder
	AssFile.clear();
	for(int i =0;i<diologonly.size();i++) 
		AssFile.add(Start.get(i)+";"+diologonly.get(i));
	Collections.sort(AssFile);
	Collections.sort(End);
	Start.clear();
	for(int i =0;i<AssFile.size();i++) {
		Start.add(AssFile.get(i).substring(0,AssFile.get(i).indexOf(';')));
		AssFile.set(i, AssFile.get(i).substring(AssFile.get(i).indexOf(';')+1,AssFile.get(i).length()));
	}
	for(int i =0;i<AssFile.size();i++) {
		AssFile.set(i, AssFile.get(i).replace("\\N", "<br />"));
	}

}
}