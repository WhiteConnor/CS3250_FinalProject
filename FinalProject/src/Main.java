import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class to control application
 */
public class Main extends Application {
	/**
	 * Main method to run
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CreateDB.initialize();
		launch(args);
		// start time 12:19
	}
	
	/**
	 * Create visuals for JavaFX
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(new MainPage(),500,700);
		String stylesheet = getClass().getResource("styles/styles.css").toExternalForm();
		scene.getStylesheets().add(stylesheet);
		primaryStage.setScene(scene);
		
		primaryStage.show();
	}

}
