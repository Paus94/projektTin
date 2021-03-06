package projekt.tin.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GNR {

	/**
	 * pierwszy kwadrans godziny najwiekszego ruchu
	 */
	private int firstQuarterIndex;

	/**
	 * umiejscowienie gnr w czasie, np. 13:15 - 14:15
	 */
	private String hour;

	/**
	 * liczba wywo�a� w godzinie najwiekszego ruchu
	 */
	private double calls;


	/**
	 * metoda wyznaczajaca gnr za pomoca metody TCBH
	 * 
	 * @param thirtyDaysCallsInQuarters
	 *            Lista 30 dni z kt�rych ka�dy jest list� ilo�ci wywo�a� w
	 *            kolejnych kwadransach
	 */
	public void methodTCBH(List<List> thirtyDaysCallsInQuarters, Integer startFrom, Integer endIn) {
		findGNRQuarter(averageDay(thirtyDaysCallsInQuarters), startFrom, endIn);
		System.out.println("ind " +firstQuarterIndex);
		setHour(firstQuarterIndex);
	}
	

	/**
	 * metoda wyznaczajaca gnr za pomoca metody TCBH
	 * 
	 * @param thirtyDaysCallsInQuarters
	 *            Lista 30 dni z kt�rych ka�dy jest list� ilo�ci wywo�a� w
	 *            kolejnych kwadransach
	 */
	public List<Double> methodADPQH(List<List> thirtyDaysCallsInQuarters) {
		List<Double> gnrInEachDay = new ArrayList<>();
		for (int i = 0; i < thirtyDaysCallsInQuarters.size(); i++) {
			findGNRQuarter(thirtyDaysCallsInQuarters.get(i), null, null);
			gnrInEachDay.add(calls);
		}
		calls = averageGNR(gnrInEachDay);
		
		return gnrInEachDay;
	}
	
	public List<Double> methodADPFH(List<List> thirtyDaysCallsInHour){
		List<Double> gnrInEachDay = new ArrayList<>();
		for(int i = 0; i<thirtyDaysCallsInHour.size(); i++){
			findGNRHour(thirtyDaysCallsInHour.get(i));
			gnrInEachDay.add(calls);
		}
		calls = averageGNR(gnrInEachDay);
		
		return gnrInEachDay;
	}
	
	/**
	 * metoda licz�ca �redni dzie� na podstawie danych z 30 dni
	 * 
	 * @param thirtyDaysCallsInQuarters
	 *            Lista 30 dni z kt�rych ka�dy jest list� ilo�ci wywo�a� w
	 *            kolejnych kwadransach
	 * @return Lista ilo�ci wywo�a� w kolejnych kwadransach
	 */
	private List<Double> averageDay(List<List> thirtyDaysCallsInQuarters) {
		List<Double> averagedDay = new ArrayList<>();
		double sum = 0;
		double average = 0;
		for (int j = 0; j < thirtyDaysCallsInQuarters.get(0).size(); j++) {
			for (int i = 0; i < thirtyDaysCallsInQuarters.size(); i++) {
				sum += (double) thirtyDaysCallsInQuarters.get(i).get(j);
			}
			average = sum / thirtyDaysCallsInQuarters.size();
			averagedDay.add(average);
			sum = 0;
		}
		return averagedDay;
	}

	/**
	 * metoda obliczaj�ca gnr
	 * 
	 * @param oneDayCallsInQuarter
	 *            Lista ilo�ci wywo�a� w kolejnych kwadransach
	 */
	private void findGNRQuarter(List<Double> oneDayCallsInQuarter, Integer startFrom, Integer endIn) {
		double max = 0;
		double sum = 0;
		
		firstQuarterIndex = 0;
		if (startFrom == null || endIn == null) {
			for (int i = 0; i < oneDayCallsInQuarter.size() - 4; i++) {
				for (int j = i; j < i + 4; j++) {
					sum += oneDayCallsInQuarter.get(j);
				}
				if (sum > max) {
					max = sum;
					firstQuarterIndex = i;
				}
				sum = 0;
			}
		} else {
			for (int i = 0; i < oneDayCallsInQuarter.size() - 4; i++) {
				if ((i  >= startFrom / 15) && (i  <= endIn / 15)) {
					for (int j = i; j < i + 4; j++) {
						sum += oneDayCallsInQuarter.get(j);
					}
					if (sum > max) {
						max = sum;
						firstQuarterIndex = i;
					}
					sum = 0;
				}
			}
		}
		calls = max;
	}

	private void findGNRHour(List<Double> oneDayCallsInHour){
		firstQuarterIndex = 0;
		Collections.sort(oneDayCallsInHour);
		Collections.reverse(oneDayCallsInHour);
		calls = oneDayCallsInHour.get(0);
	}
	
	

	/**
	 * metoda u�rednia gnr'y z kilku dni
	 * 
	 * @param gnrInEachDay
	 *            lista gnr'�w w kolejnych dniach
	 * @return �rednia warto�� gnr
	 */
	private double averageGNR(List<Double> gnrInEachDay) {
		int sum = 0;
		double result;
		for (int i = 0; i < gnrInEachDay.size(); i++) {
			sum += gnrInEachDay.get(i);
		}
		result = sum / gnrInEachDay.size();
		return result;
	}


	/**
	 * metoda konwertuje godzine do formatu 00:00
	 * @param firstQuarterIndex
	 * indeks pierwszego kwadransu
	 * @return String
	 */
	private String convertHour(int firstQuarterIndex){
		int hourInt = (int) ((firstQuarterIndex) / 4);
		int minutesInt = ((firstQuarterIndex) % 4) * 15;
		String hourString, minutesString;
		if (hourInt < 10) {
			hourString = "0" + String.valueOf(hourInt);
		}
		else if(hourInt >= 24){
			hourString = "00";
		}
		else {
			hourString = String.valueOf(hourInt);
		}
		if (minutesInt == 0) {
			minutesString = "00";
		}
		else {
			minutesString = String.valueOf(minutesInt);
		}
		return hourString+":"+minutesString;
	}
	
	public String toString(){
		if(getHour()!=null){
			return "Godzina najwi�kszego ruchu: "+getHour()+"\n �rednie nat�enie ruchu: "+getCalls();
		}
		else{
			return "\n�rednie nat�enie ruchu: "+getCalls();
		}
		
	}

	public int getFirstQuarterIndex() {
		return firstQuarterIndex;
	}
	
	public double getCalls() {
		return calls;
	}
	
	public String getHour() {
		return hour;
	}
	
	public void setHour(int firstQuarterIndex) {
		hour = convertHour(firstQuarterIndex) + " - " + convertHour(firstQuarterIndex+4);
	}
}
