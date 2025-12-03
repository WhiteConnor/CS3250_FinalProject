import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * VBox page to welcome user after login
 */
public class WelcomePage extends VBox {
	public WelcomePage() {
		DB db = new DB();
		
		int count = db.getLowInvCount();
		Label lowInvLbl = new Label("There are " + count + " item(s) with low inventory!");
		getChildren().add(lowInvLbl);
		
		NumberAxis xAxis = new NumberAxis();
		NumberAxis yAxis = new NumberAxis();
		
		LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
		
		XYChart.Series<Number, Number> series = new XYChart.Series<>();
		series.setName("Sales");
		series.getData().add(new XYChart.Data<>(1, 200));
		series.getData().add(new XYChart.Data<>(2, 500));
		
		lineChart.getData().add(series);
		
		getChildren().add(lineChart);
		
	}
}
