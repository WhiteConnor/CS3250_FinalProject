import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ViewTransactionsSection extends StackPane {
	
	public ViewTransactionsSection(MainPage page) {
		DB db = new DB();
		VBox root = new VBox();
		this.getChildren().add(root);
		
		Label testLbl = new Label("test");
		root.getChildren().add(testLbl);
	}
}
