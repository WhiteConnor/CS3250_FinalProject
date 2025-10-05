import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
				if (loginAccepted) {
					MenuBanner menuBanner = new MenuBanner(page);
					menuBanner.getStyleClass().add("menuBanner");
					page.setTop(menuBanner);
					page.setCenter(new WelcomePage());
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
			
		});
		
		createNewButton.setOnAction(event -> {
			System.out.println("New user page request");
			CreateUserSection createUserSection = new CreateUserSection(page);
			createUserSection.getStyleClass().add("createUserSection");
			page.setCenter(createUserSection);
		});
	}
}
