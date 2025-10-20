import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * VBox page to create a new user
 */
public class CreateUserSection extends VBox {
	public CreateUserSection(MainPage page) {
		Label titleLabel = new Label("New User");
		
		Label firstNameLabel = new Label("First name:");
		TextField firstNameTF = new TextField();
		getChildren().addAll(titleLabel, firstNameLabel, firstNameTF);
		firstNameTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 50))
				firstNameTF.setText(oldValue);
		});
		
		Label lastNameLbl = new Label("Last name:");
		TextField lastNameTF = new TextField();
		getChildren().addAll(lastNameLbl, lastNameTF);
		lastNameTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 50))
				lastNameTF.setText(oldValue);
		});
		
		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();
		getChildren().addAll(usernameLabel, usernameTextField);
		usernameTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 50))
				usernameTextField.setText(oldValue);
		});
		
		Label emailLabel = new Label("Email:");
		TextField emailTextField = new TextField();
		getChildren().addAll(emailLabel, emailTextField);
		emailTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 128))
				emailTextField.setText(oldValue);
		});
		
		Label birthDateLabel = new Label("Birthdate:");
		DatePicker birthDateDatePicker = new DatePicker();
		getChildren().addAll(birthDateLabel, birthDateDatePicker);
		
		Label passwordLabel = new Label("Password");
		TextField passwordPasswordField = new PasswordField();
		getChildren().addAll(passwordLabel, passwordPasswordField);
		passwordPasswordField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 50))
				passwordPasswordField.setText(oldValue);
		});
		
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
			getChildren().removeIf(node -> node.getStyleClass().contains("error-label"));
			try {
				List<Pair<Integer, Label>> toAdd = new ArrayList<>();
				Iterator<Node> iterator = getChildren().iterator();
				while (iterator.hasNext()) {
					
					Node node = iterator.next();
					System.out.println(node.getStyleClass());
					if (node.getStyleClass().contains("text-input")) {
						if (((TextInputControl) node).getText().isEmpty()  && !node.getStyleClass().contains("optional-input")) { 
							Label errorLabel = new Label("Value required");
							errorLabel.getStyleClass().add("error-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					} else if (node.getStyleClass().contains("combo-box")) {
						if (((ComboBoxBase) node).getValue() == null) {
							Label errorLabel = new Label("Value required!");
							errorLabel.getStyleClass().add("error-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					} else if (node.getStyleClass().contains("date-picker")) {
						if (((DatePicker) node).getValue() == null) {
							Label errorLabel = new Label("Value required!");
							errorLabel.getStyleClass().add("error-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					}
					
				}
				Boolean incomplete = false;
				incomplete = !toAdd.isEmpty();
				toAdd.forEach(node -> {
					getChildren().add(node.getKey(), node.getValue());
				});
				if (incomplete)
					throw new Exception("Incomplete input");
				Authentication auth = new Authentication();
				String pass;
				pass = auth.newPassword(passwordPasswordField.getText());
				DB db = new DB();
				if (db.verifyUserEmail(emailTextField.getText())) {
					db.addUser(
							firstNameTF.getText(),
							lastNameTF.getText(),
							emailTextField.getText(),
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
				} else {
					Label errorLabel = new Label("Email already in use!");
					errorLabel.getStyleClass().add("error-label");	
					getChildren().add(getChildren().indexOf(emailTextField) + 1, errorLabel);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Incomplete input");
			}
			
		});
	}
}
