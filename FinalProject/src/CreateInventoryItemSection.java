import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

/**
 * CreateInventoryItemSection class creates a gui page for adding an item to tb. Connects to DB to insert items
 */
public class CreateInventoryItemSection extends VBox {
	/*
	 * Constructor
	 * @param user User: user item for tracking item creation
	 */
	public CreateInventoryItemSection(MainPage page, User user) {
		Label titleLabel = new Label("Add Inventory Item");

		Label itemNameLabel = new Label("Enter Item Name:");
		TextField itemNameTF = new TextField();
		itemNameTF.getStyleClass().add("input");
		getChildren().addAll(itemNameLabel, itemNameTF);
		itemNameTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 100))
				itemNameTF.setText(oldValue);
		});
		
		Label descriptionLabel = new Label("Enter Description:");
		TextArea descriptionTA = new TextArea();
		descriptionTA.getStyleClass().add("input");
		getChildren().addAll(descriptionLabel, descriptionTA);
		descriptionTA.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 255))
				descriptionTA.setText(oldValue);
		});
		
		
		Label itemPriceLabel = new Label("Enter Item Price:");
		TextField itemPriceTF = new TextField();
		itemPriceTF.getStyleClass().add("input");
		getChildren().addAll(itemPriceLabel, itemPriceTF);
		itemPriceTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchCents(newValue))
		    	itemPriceTF.setText(oldValue);
		});
		
		Label weightKGLabel = new Label("Enter Item Weight (KG):");
		TextField itemWeightTF = new TextField();
		itemWeightTF.getStyleClass().add("input");
		getChildren().addAll(weightKGLabel, itemWeightTF);
		itemWeightTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchFloat(newValue))
		    	itemWeightTF.setText(oldValue);
		});
		
		Label selectItemLabel = new Label("Select Tax Category:");
		ComboBox<TaxBracket> taxComboBox = new ComboBox<>(FXCollections.observableArrayList(TaxBracket.values()));
		taxComboBox.getStyleClass().add("input");
		getChildren().addAll(selectItemLabel, taxComboBox);
		
		Label categoryLabel = new Label("Select Item Category:");
		ComboBox<Category> categoryBox = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
		categoryBox.getStyleClass().add("input");
		getChildren().addAll(categoryLabel, categoryBox);
		
		Label expirLabel = new Label("Enter Days Until Expired:");
		TextField expirTF = new TextField();
		expirTF.getStyleClass().add("input");
		getChildren().addAll(expirLabel, expirTF);
		expirTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	expirTF.setText(oldValue);
		});
		
		Label skuLabel = new Label("Enter SKU:");
		TextField skuTF = new TextField();
		skuTF.getStyleClass().add("input");
		getChildren().addAll(skuLabel, skuTF);
		skuTF.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMaxLength(newValue, 64))
				skuTF.setText(oldValue);
		});
		
		Label unitsLabel = new Label("Enter Units Per Bin:");
		TextField unitsTF = new TextField();
		unitsTF.getStyleClass().add("input");
		getChildren().addAll(unitsLabel, unitsTF);
		unitsTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	unitsTF.setText(oldValue);
		});
		
		Label minTempLabel = new Label("Enter Minimum Temp (Leave blank for NA):");
		TextField minTempTF = new TextField();
		minTempTF.getStyleClass().add("optional-input");
		getChildren().addAll(minTempLabel, minTempTF);
		minTempTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	minTempTF.setText(oldValue);
		});
		
		Label maxTempLabel = new Label("Enter Maximum Temp (Leave blank for NA):");
		TextField maxTempTF = new TextField();
		maxTempTF.getStyleClass().add("optional-input");
		getChildren().addAll(maxTempLabel, maxTempTF);
		maxTempTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	maxTempTF.setText(oldValue);
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
				
				Boolean optionalValues = false;
				optionalValues = !toAdd.isEmpty();
				
				toAdd.forEach(node -> {
					getChildren().add(node.getKey(), node.getValue());
				});
				
				if (incomplete)
					throw new Exception("Incomplete input");
				DB db = new DB();
				if (!optionalValues)
					db.addItem(
						itemNameTF.getText(),
						user.getUserID(),
						descriptionTA.getText(),
						Float.parseFloat(itemWeightTF.getText()),// float
						InputHandling.stringToCents(itemPriceTF.getText()),// float 
						taxComboBox.getValue(),
						Integer.parseInt(expirTF.getText()),
						skuTF.getText(),
						categoryBox.getValue(),
						Integer.parseInt(unitsTF.getText()),
						Integer.parseInt(minTempTF.getText()),
						Integer.parseInt(maxTempTF.getText())
						);
				else
					db.addItem(
							itemNameTF.getText(),
							user.getUserID(),
							descriptionTA.getText(),
							Float.parseFloat(itemWeightTF.getText()),// float
							InputHandling.stringToCents(itemPriceTF.getText()),// float 
							taxComboBox.getValue(),
							Integer.parseInt(expirTF.getText()),
							skuTF.getText(),
							categoryBox.getValue(),
							Integer.parseInt(unitsTF.getText())
							);
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Item Creation");
				alert.setHeaderText("Item \"" + itemNameTF.getText() + "\" created successfully");
				alert.setContentText(descriptionTA.getText());

				alert.showAndWait();
				
				CreateInventoryItemSection invItemPage = new CreateInventoryItemSection(page, user);
				ScrollPane scrollPane = (ScrollPane) page.getCenter();
				scrollPane.setContent(invItemPage);
			} catch (NumberFormatException e) {
				System.out.println("Error adding item - NumberFormatException");
//				e.printStackTrace();
			} catch (SQLException e) {
				System.out.println("Error adding item - SQLException");
//				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("Incomplete input");
			}
		});
	}

}