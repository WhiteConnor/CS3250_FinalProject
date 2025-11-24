import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * VBox page to welcome user after login
 */
public class WelcomePage extends VBox {
	public WelcomePage() {
		Label titleLabel = new Label("Login Successful!");
		getChildren().add(titleLabel);
		
		DB db = new DB();
		
		int count = db.getLowInvCount();
		Label lowInvLbl = new Label("There are " + count + " item(s) with low inventory!");
		getChildren().add(lowInvLbl);
		
	}
}
