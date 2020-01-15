package converter;

import java.nio.charset.Charset;
import java.util.*;

public class Ass {
	private ArrayList<String> AssFile;
	private ArrayList<String> Dialogueonly = new ArrayList<String>();
	private ArrayList<String> Start = new ArrayList<String>();
	private ArrayList<String> End = new ArrayList<String>();

	public Ass(ArrayList<String> File, String filepath,Charset en) {
		AssFile = File;
		Dialogue();
		cleaner();
		new Dfxp(Start, End, AssFile, filepath,en);
	}

	public void Dialogue() {
//gathering dialogue 
		int Dindex = 0;
		for (int i = 0; i < AssFile.size(); i++) {
			if (AssFile.get(i).startsWith("Dialogue:")) {
				Dindex = i;
				break;
			}
		}
		do {
			Dindex--;
			AssFile.remove(Dindex);
		} while (Dindex != 0);

		for (int i = 0; i < AssFile.size(); i++)
			if (AssFile.get(i).contains("Dialogue:"))
				Dialogueonly.add(AssFile.get(i).substring(0, AssFile.get(i).length()));

	}

	public void cleaner() {
		// remove format and layer
		for (int i = 0; i < Dialogueonly.size(); i++) {
			Dialogueonly.set(i, Dialogueonly.get(i).replace("Dialogue:", "").trim());
			Dialogueonly.set(i,
					Dialogueonly.get(i).substring(Dialogueonly.get(i).indexOf(',') + 1, Dialogueonly.get(i).length()));
		}
		// get start time stamp
		for (int i = 0; i < Dialogueonly.size(); i++) {
			Start.add(Dialogueonly.get(i).substring(0, Dialogueonly.get(i).indexOf(',')));
			Dialogueonly.set(i,
					Dialogueonly.get(i).substring(Dialogueonly.get(i).indexOf(',') + 1, Dialogueonly.get(i).length()));
		}
		// get end time stamp
		for (int i = 0; i < Dialogueonly.size(); i++) {
			End.add(Dialogueonly.get(i).substring(0, Dialogueonly.get(i).indexOf(',')));
			Dialogueonly.set(i,
					Dialogueonly.get(i).substring(Dialogueonly.get(i).indexOf(',') + 1, Dialogueonly.get(i).length()));
		}
		// further cleanup
		for (int j = 0; j < 6; j++)
			for (int i = 0; i < Dialogueonly.size(); i++)
				Dialogueonly.set(i,
						Dialogueonly.get(i).substring(Dialogueonly.get(i).indexOf(',') + 1, Dialogueonly.get(i).length()));
		// text cleanup
		for (int i = 0; i < Dialogueonly.size(); i++)
			Dialogueonly.set(i, Dialogueonly.get(i).replaceAll(
					"\\{[^\\}]*\\\\p[1-9][^\\}]*\\}.*?(\\{[^\\}]*\\\\p0[^\\}]*\\}|$)|\\{.*?\\}".trim(), ""));
		// final reorder
		AssFile.clear();
		for (int i = 0; i < Dialogueonly.size(); i++)
			AssFile.add(Start.get(i) + ";" + Dialogueonly.get(i));
		Collections.sort(AssFile);
		Collections.sort(End);
		Start.clear();
		for (int i = 0; i < AssFile.size(); i++) {
			Start.add(AssFile.get(i).substring(0, AssFile.get(i).indexOf(';')));
			AssFile.set(i, AssFile.get(i).substring(AssFile.get(i).indexOf(';') + 1, AssFile.get(i).length()));
		}
		for (int i = 0; i < AssFile.size(); i++) {
			AssFile.set(i, AssFile.get(i).replace("\\N", "<br />"));
		}

	}
}