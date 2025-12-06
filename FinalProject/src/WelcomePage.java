import java.util.ArrayList;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * VBox page to welcome user after login
 */
public class WelcomePage extends VBox {
	public WelcomePage() {
		DB db = new DB();
		
		int count = db.getLowInvCount();
		Label lowInvLbl = new Label("There are " + count + " item(s) with low inventory!");
		getChildren().add(lowInvLbl);
		
		ArrayList<Pair<java.util.Date, Integer>> salesData = db.getSalesData();
		System.out.println(salesData);
		
		// used copilot to display Sales volume chart. 
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();

		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Sales Volume");

		// Track min/max while populating
		long minEpoch = Long.MAX_VALUE;
		long maxEpoch = Long.MIN_VALUE;

		for (Pair<java.util.Date, Integer> entry : salesData) {
		    long epochMillis = entry.getKey().getTime(); // Date → milliseconds
		    series.getData().add(new XYChart.Data<>(epochMillis, entry.getValue()));

		    if (epochMillis < minEpoch) minEpoch = epochMillis;
		    if (epochMillis > maxEpoch) maxEpoch = epochMillis;
		}

		lineChart.getData().add(series);

		// Format x-axis ticks as dates
		xAxis.setTickLabelFormatter(new javafx.util.StringConverter<Number>() {
		    private final java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM-dd");
		    @Override
		    public String toString(Number object) {
		        return sdf.format(new java.util.Date(object.longValue()));
		    }
		    @Override
		    public Number fromString(String string) { return null; }
		});

		// Apply range with padding
		long oneDayMillis = 24L * 60 * 60 * 1000;
		xAxis.setAutoRanging(false);
		xAxis.setLowerBound(minEpoch - oneDayMillis);
		xAxis.setUpperBound(maxEpoch + oneDayMillis);
		xAxis.setTickUnit(oneDayMillis);

		getChildren().add(lineChart);
		
	}
}
