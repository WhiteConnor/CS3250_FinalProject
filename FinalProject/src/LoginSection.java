import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;

/**
 * LoginSection is a page to verify user before access to application is granted
 * 
 * @author white
 */
public class LoginSection extends VBox {
	public LoginSection(MainPage page) {
		Label titleLabel = new Label("Login");
		
		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();
		getChildren().addAll(titleLabel, usernameLabel, usernameTextField);
		
		Label passwordLabel = new Label("Password");
		TextField passwordPasswordField = new PasswordField();
		getChildren().addAll(passwordLabel, passwordPasswordField);
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		Button createNewButton = new Button("Create new user");
		getChildren().add(createNewButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Login Submitted");
			Authentication auth;
			try {
				auth = new Authentication(usernameTextField.getText());
				boolean loginAccepted = auth.verify(passwordPasswordField.getText());
				System.out.println("Login accepted: " + loginAccepted);
				DB db = new DB();
				User user = db.getUser(usernameTextField.getText());
				if (loginAccepted) {
					System.out.println("Made it here");
					MenuBanner menuBanner = new MenuBanner(page, user);
					menuBanner.getStyleClass().add("menuBanner");
					page.setTop(menuBanner);
					System.out.println(page.getCenter());
					ScrollPane scrollPane = (ScrollPane) page.getCenter();
					scrollPane.setContent(new WelcomePage());
				} else {
					Label errorLabel = new Label("Incorrect username or password");
					errorLabel.getStyleClass().add("error-label");
					getChildren().add(errorLabel);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				Boolean existingError[] = new Boolean[1];
				existingError[0] = false;
				getChildren().forEach(child -> {
					if (child.getStyleClass().contains("error-label"))
						existingError[0] = true;
				});
				if (!existingError[0]) {
					Label errorLabel = new Label("Incorrect username or password");
					errorLabel.getStyleClass().add("error-label");
					getChildren().add(errorLabel);
				}
			} catch (NoSuchAlgorithmException e) {
				Boolean existingError[] = new Boolean[1];
				existingError[0] = false;
				getChildren().forEach(child -> {
					if (child.getStyleClass().contains("error-label"))
						existingError[0] = true;
				});
				if (!existingError[0]) {
					Label errorLabel = new Label("No Such Algorithm Exception, please contact developer");
					errorLabel.getStyleClass().add("error-label");
					getChildren().add(errorLabel);
				}
			} catch (Exception e) {
				e.printStackTrace();
				Boolean existingError[] = new Boolean[1];
				existingError[0] = false;
				getChildren().forEach(child -> {
					if (child.getStyleClass().contains("error-label"))
						existingError[0] = true;
				});
				if (!existingError[0]) {
					Label errorLabel = new Label("Incorrect username or password");
					errorLabel.getStyleClass().add("error-label");
					getChildren().add(errorLabel);
				}
			}
			
			
		});
		
		createNewButton.setOnAction(event -> {
			System.out.println("New user page request");
			page.setCenterContent(new CreateUserSection(page));
		});
	}
}
