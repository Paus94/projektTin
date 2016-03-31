package projekt.tin.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class TextFileReader {
	private String path;
	private FileInputStream input = null;
	private BufferedReader br;
	private int numberOfCallsInDay = 0;

	public TextFileReader(String path) {
		this.path = path;
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Brakuje pliku z danymi.");
		}
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
		try {
			input = new FileInputStream(path);
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Brakuje pliku z danymi.");
		}
	}

	public int countFileLines() {
		int linesCounter = 0;
		try {
			br = new BufferedReader(new InputStreamReader(input));
			while (br.readLine() != null) {
				linesCounter++;
			}
			br.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e);
		}
		numberOfCallsInDay = linesCounter;
		return linesCounter;
	}

	// zwraca list� kt�rej indexy s� numerami kwadrans�w, warto�ci ilo�ci�
	// wywo�a� w kwadransach
	public List<Integer> numberOfCallsInEachMinute() {
		int minute = 0;
		int value = 0;
		int tempValue = 0;
		String sCurrentLine;
		List<Integer> calls = new ArrayList<Integer>();
		int whichStep = 0;
		int whichQuarter = 1;

		try {
			while ((sCurrentLine = br.readLine()) != null) {
				br = new BufferedReader(new InputStreamReader(input));
				for (String tempString : sCurrentLine.split("\\t")) {
					if (whichStep == 0) {
						minute = Integer.parseInt(tempString);
					} else if (whichStep == 1) {
						tempValue = Integer.parseInt(tempString)
								* numberOfCallsInDay;
						if (minute <= whichQuarter * 15) {
							value += tempValue;
						} else if (minute > whichQuarter * 15) {
							calls.add(value);
							value = 0;
							value += tempValue;
							whichQuarter++;
						}
					}
					whichStep++;
					if (whichStep >= 2)
						whichStep = 0;
				}
			}
			br.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
		return calls;
	}

}
