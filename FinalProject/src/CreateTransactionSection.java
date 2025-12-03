import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Pair;

public class CreateTransactionSection extends VBox {
	
	public CreateTransactionSection(MainPage page, User user) {
		Label titleLabel = new Label("Create Transaction");
		
		DB db = new DB();
		
		ArrayList<Pair<String, String>> itemsList;
		try {
			itemsList = db.getItemSKUs();
		} catch (SQLException e) {
			itemsList = new ArrayList<Pair<String, String>>();
//			itemsList.add(new Pair<String, String>("Test", "Test"));
			e.printStackTrace();
		}
		
		Label selectItemLabel = new Label("Select item:");
		ComboBox<Pair<String, String>> itemComboBox = new ComboBox<Pair<String, String>>(FXCollections.observableArrayList(itemsList));
		getChildren().addAll(titleLabel, selectItemLabel, itemComboBox);
		
		Label descriptionLabel = new Label("Enter Description:");
		TextArea descriptionTA = new TextArea();
		descriptionTA.getStyleClass().add("input");
		getChildren().addAll(descriptionLabel, descriptionTA);
		descriptionTA.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 255))
				descriptionTA.setText(oldValue);
		});
		
		Label quantityLabel = new Label("Enter Quantity:");
		TextField quantityTF = new TextField();
		quantityTF.getStyleClass().add("input");
		getChildren().addAll(quantityLabel, quantityTF);
		quantityTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	quantityTF.setText(oldValue);
		});
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Form Submitted");
			getChildren().removeIf(node -> node.getStyleClass().contains("error-label"));
			getChildren().removeIf(node -> node.getStyleClass().contains("warning-label"));
			try {
				List<Pair<Integer, Label>> toAdd = new ArrayList<>(); 
				Iterator<Node> iterator = getChildren().iterator();
				while (iterator.hasNext()) {
				    Node node = iterator.next();

					if (node.getStyleClass().contains("text-input")) {
						if (((TextInputControl) node).getText().isEmpty()  && !node.getStyleClass().contains("optional-input")) { 
							Label errorLabel = new Label("Value required");
							errorLabel.getStyleClass().add("error-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					}
					else if (node.getStyleClass().contains("combo-box")) {
						if (((ComboBoxBase) node).getValue() == null) {
							Label errorLabel = new Label("Value required!");
							errorLabel.getStyleClass().add("error-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					}
				};
				toAdd.forEach(node -> {
					getChildren().add(node.getKey(), node.getValue());
				});
				
				Boolean incomplete = false;
				for (Object item : toAdd)
					System.out.println(item);
				incomplete = !toAdd.isEmpty();
					
				toAdd = new ArrayList<>(); 
				iterator = getChildren().iterator();
				while (iterator.hasNext()) {
				    Node node = iterator.next();
				    if (node.getStyleClass().contains("optional-input") && node.getStyleClass().contains("text-input")) {
						if (((TextInputControl) node).getText().isEmpty()) {
							Label errorLabel = new Label("Optional Value");
							errorLabel.getStyleClass().add("warning-label");	
							int index = getChildren().indexOf(node);
							toAdd.add(0,new Pair<>(index + 1, errorLabel));
						}
					}
				}
				toAdd.forEach(node -> {
					getChildren().add(node.getKey(), node.getValue());
				});
				
				if (incomplete)
					throw new Exception("Incomplete input");
				
				int itemQuantity = db.getItemQuantity(itemComboBox.getValue().getValue());
				
				if (Integer.parseInt(quantityTF.getText()) > itemQuantity) {
					Alert quantityAlert = new Alert(AlertType.ERROR);
					quantityAlert.setTitle("Transaction Creation Failed");
					quantityAlert.setHeaderText("Quantity must be lower than stocked inventory!");
					quantityAlert.setContentText("Inventory: " + itemQuantity);
	
					quantityAlert.showAndWait();
					throw new Exception("Quantity must be lower than stocked inventory!\nInventory: " + itemQuantity);
				}
				
				db.addTransaction(
						user.getUserID(),
						itemComboBox.getValue().getValue(),
						descriptionTA.getText(),
						Integer.parseInt(quantityTF.getText())
						);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Transaction Creation");
				alert.setHeaderText("Transaction created successfully");
				alert.setContentText(descriptionTA.getText());

				alert.showAndWait();
				
				CreateTransactionSection createTransactionPage = new CreateTransactionSection(page, user);
				ScrollPane scrollPane = (ScrollPane) page.getCenter();
				scrollPane.setContent(createTransactionPage);
			} catch (NumberFormatException e) {
				System.out.println("Error adding item - NumberFormatException");
//				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Error adding item - SQLException");
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Incomplete input");
			}
		});
	}
}
