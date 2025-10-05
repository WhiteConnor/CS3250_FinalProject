import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * VBox page to create a new user
 */
public class CreateUserSection extends VBox {
	public CreateUserSection(MainPage page) {
		Label titleLabel = new Label("New User");
		
		Label firstNameLabel = new Label("First name:");
		TextField firstNameTF = new TextField();
		getChildren().addAll(titleLabel, firstNameLabel, firstNameTF);
		
		Label lastNameLbl = new Label("Last name:");
		TextField lastNameTF = new TextField();
		getChildren().addAll(lastNameLbl, lastNameTF);
		
		Label birthDateLabel = new Label("Birthdate:");
		DatePicker birthDateDatePicker = new DatePicker();
		getChildren().addAll(birthDateLabel, birthDateDatePicker);
		
		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();
		getChildren().addAll(usernameLabel, usernameTextField);
		
		Label passwordLabel = new Label("Password");
		TextField passwordPasswordField = new PasswordField();
		getChildren().addAll(passwordLabel, passwordPasswordField);
		
		Label selectSexLabel = new Label("Sex:");
		ComboBox<Sex> sexComboBox = new ComboBox<>(FXCollections.observableArrayList(Sex.values()));
		getChildren().addAll(selectSexLabel, sexComboBox);
		
		Label selectRoleLabel = new Label("Role:");
		ComboBox<Role> roleComboBox = new ComboBox<>(FXCollections.observableArrayList(Role.values()));
		getChildren().addAll(selectRoleLabel, roleComboBox);
		
		Button createNewButton = new Button("Create new user");
		getChildren().add(createNewButton);
		
		Button cancelButton = new Button("Cancel");
		getChildren().add(cancelButton);
		
		cancelButton.setOnAction(event -> {
			System.out.println("New user canceled");
			LoginSection loginSection = new LoginSection(page);
			loginSection.getStyleClass().add("loginSection");
			page.setCenter(loginSection);
		});
		
		createNewButton.setOnAction(event -> {
			System.out.println("New User Submitted");
			Authentication auth = new Authentication();
			String pass;
			try {
				pass = auth.newPassword(passwordPasswordField.getText());
				DB db = new DB();
				db.addUser(
						firstNameTF.getText(),
						lastNameTF.getText(),
						birthDateDatePicker.getValue(),
						usernameTextField.getText(),
						auth.getSalt(),
						pass,
						sexComboBox.getValue(),
						roleComboBox.getValue()
						);
				LoginSection loginSection = new LoginSection(page);
				loginSection.getStyleClass().add("loginSection");
				page.setCenter(loginSection);
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			
		});
	}
}
