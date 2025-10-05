import javafx.scene.layout.BorderPane;

/**
 * MainPage class to handle main page content
 * 
 * @author white
 */
public class MainPage extends BorderPane {
	/**
	 * MainPage constructor builds main page
	 */
	public MainPage() {
		
		LoginSection loginSection = new LoginSection(this);
		setCenter(loginSection);
	}
}
