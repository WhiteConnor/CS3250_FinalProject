import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateInventoryItemSection extends VBox{
	public CreateInventoryItemSection(User user) {
		Label titleLabel = new Label("Add Inventory Item");
		

		Label itemNameLabel = new Label("Enter Item Name:");
		TextField itemNameTF = new TextField();
		getChildren().addAll(itemNameLabel, itemNameTF);
		
		
		Label descriptionLabel = new Label("Enter Description:");
		TextArea descriptionTA = new TextArea();
		getChildren().addAll(descriptionLabel, descriptionTA);
		
		
		Label itemPriceLabel = new Label("Enter Item Price:");
		TextField itemPriceTF = new TextField();
		getChildren().addAll(itemPriceLabel, itemPriceTF);
		itemPriceTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchCents(newValue))
		    	itemPriceTF.setText(oldValue);
		});
		
		Label weightKGLabel = new Label("Enter Item Weight (KG):");
		TextField itemWeightTF = new TextField();
		getChildren().addAll(weightKGLabel, itemWeightTF);
		itemWeightTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchFloat(newValue))
		    	itemWeightTF.setText(oldValue);
		});
		
		Label selectItemLabel = new Label("Select Tax Category:");
		ComboBox<TaxBracket> taxComboBox = new ComboBox<>(FXCollections.observableArrayList(TaxBracket.values()));
		getChildren().addAll(selectItemLabel, taxComboBox);
		
		Label categoryLabel = new Label("Select Tax Category:");
		ComboBox<Category> categoryBox = new ComboBox<>(FXCollections.observableArrayList(Category.values()));
		getChildren().addAll(categoryLabel, categoryBox);
		
		Label expirLabel = new Label("Enter Days Until Expired:");
		TextField expirTF = new TextField();
		getChildren().addAll(expirLabel, expirTF);
		expirTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	expirTF.setText(oldValue);
		});
		
		Label skuLabel = new Label("Enter SKU:");
		TextField skuTF = new TextField();
		getChildren().addAll(skuLabel, skuTF);
		
		Label unitsLabel = new Label("Enter Units Per Bin:");
		TextField unitsTF = new TextField();
		getChildren().addAll(unitsLabel, unitsTF);
		unitsTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	unitsTF.setText(oldValue);
		});
		
		Label minTempLabel = new Label("Enter Minimum Temp (Leave blank for NA):");
		TextField minTempTF = new TextField();
		getChildren().addAll(minTempLabel, minTempTF);
		minTempTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	minTempTF.setText(oldValue);
		});
		
		Label maxTempLabel = new Label("Enter Maximum Temp (Leave blank for NA):");
		TextField maxTempTF = new TextField();
		getChildren().addAll(maxTempLabel, maxTempTF);
		maxTempTF.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	maxTempTF.setText(oldValue);
		});
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Form Submitted");
			System.out.println(InputHandling.stringToCents(itemPriceTF.getText()));
//			DB db = new DB();
//			db.addItem(
//					itemNameTF.getText(),
//					user.getUserID(),
//					descriptionTA.getText(),
//					itemWeightTF.getText(),// float
//					itemPriceTF.getText(),// float 
//					taxComboBox.getValue(),
//					expirTF.getText(),
//					skuTF.getText(),
//					categoryBox.getValue(),
//					unitsTF.getText(),
//					minTempTF.getText(),
//					maxTempTF.getText()
//					);
		});
	}

}
