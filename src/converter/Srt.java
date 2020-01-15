package converter;

import java.nio.charset.Charset;
import java.util.ArrayList;

public class Srt {
	private ArrayList<String> Srtfile;
	private ArrayList<String> Start = new ArrayList<String>();
	private ArrayList<String> End = new ArrayList<String>();
	private ArrayList<String> Dialogueonly = new ArrayList<String>();

	public Srt(ArrayList<String> File, String filepath,Charset en) {
		Srtfile = File;
		Start_End();
		Dialogue();
		new Dfxp(Start, End, Dialogueonly, filepath,en);
	}

	public void Start_End() {
//getting start and end stamps
		for (int i = 0; i < Srtfile.size(); i++)
			if (Srtfile.get(i).matches(
					"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]"))
				Start.add(Srtfile.get(i));
		for (int i = 0; i < Start.size(); i++)
			Start.set(i, Start.get(i).replaceAll("[\u0020][\u002D][\u002D][\u003E][\u0020]", "_"));
//storing end stamp 
		for (int i = 0; i < Start.size(); i++)
			End.add(Start.get(i).substring(Start.get(i).indexOf('_') + 1, Start.get(i).length()));
//storing Start stamp
		for (int i = 0; i < Start.size(); i++)
			Start.set(i, Start.get(i).substring(0, Start.get(i).indexOf('_') - 1));
//replacing "," with "."
		for (int i = 0; i < Start.size(); i++) {
			Start.set(i, Start.get(i).replace(',', '.'));
			End.set(i, End.get(i).replace(',', '.'));
		}

	}

	public void Dialogue() {
		// filling empty dialogue with {space}
		for (int i = 0; i < Srtfile.size(); i++)
			if (Srtfile.get(i).matches(
					"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]"))
				if ((i + 1) < Srtfile.size())
					if (Srtfile.get(i + 1).isEmpty())
						Srtfile.set(i + 1, "{space}");
//cleanup
		Srtfile.remove(0);
		for (int i = 0; i < Srtfile.size(); i++) {
			if (Srtfile.get(i).matches(
					"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]")) {
				int lines = 0;
				for (int j = i; j < Srtfile.size(); j++) {
					if (Srtfile.get(j).matches("^[\\d]+"))
						;
					else if (Srtfile.get(j).matches(
							"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]"))
						;
					else if (Srtfile.get(j).isEmpty() != true) {
						lines++;
					} else if (Srtfile.get(j).isEmpty()) {
						if (lines >= 2)
							do {
								Srtfile.set(i + 1, Srtfile.get(i + 1) + "<br />" + Srtfile.get(j - lines + 1));
								Srtfile.set(j - lines + 1, "");
								lines--;
							} while (lines > 1);
						break;
					}
				}
			}
		}
//rearrange 
		for (int i = 0; i < Srtfile.size(); i++) {
			if (Srtfile.get(i).matches(
					"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]"))
				for (int j = i; j < Srtfile.size(); j++) {
					if (Srtfile.get(j).matches("^[\\d]+"))
						;
					else if (Srtfile.get(j).matches(
							"[\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d][\u0020][\u002D][\u002D][\u003E][\u0020][\\d][\\d]:[\\d][\\d]:[\\d][\\d],[\\d][\\d][\\d]"))
						;
					else if (Srtfile.get(j).isEmpty() != true) {
						Dialogueonly.add(Srtfile.get(j));
						break;
					}
				}
		}
//removal of {space}
		for (int i = 0; i < Dialogueonly.size(); i++)
			if (Dialogueonly.get(i).equals("{space}"))
				Dialogueonly.set(i, "");

	}

}
