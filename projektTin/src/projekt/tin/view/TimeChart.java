package projekt.tin.view;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeChart {
	public ChartPanel addData(List<Double> oneDayCallsInQuarter) {
		TimeSeries quartersSeries = new TimeSeries("Nat�enie ruchu");
		
		Minute time = new Minute();
		int minute = 0;
		int hour = 0;
		for (Double myQuarter : oneDayCallsInQuarter) {
			time = new Minute(minute,hour,1,12,2015);
			quartersSeries.add(time, myQuarter);
			if (minute == 45) {
				minute = 0;
				hour++;
			} else {
				minute += 15;
			}
		}

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(quartersSeries);

		JFreeChart chart = ChartFactory.createTimeSeriesChart("Nat�enie ruchu", "Godzina", "Nat�enie", dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		
		return chartPanel;
	}
	
	public ChartPanel barChart(List<Double> callsList) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int day = 1;
		
		for (Double myDay : callsList) {
			dataset.setValue(myDay, "Nat�enie", Integer.toString(day));
			day++;
		}
		
		JFreeChart chart = ChartFactory.createBarChart("Nat�enie ruchu", "Dzie�", "Nat�enie", dataset, PlotOrientation.VERTICAL,
				false, true, false);
		
		ChartPanel chartPanel = new ChartPanel(chart);
		
		return chartPanel;
	}

}
