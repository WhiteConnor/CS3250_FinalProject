import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateInventoryItemPage  extends VBox{
	public CreateInventoryItemPage(User user) {
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
		
		Label weightKGLabel = new Label("Enter Item Weight (KG):");
		TextField itemWeightTF = new TextField();
		getChildren().addAll(weightKGLabel, itemWeightTF);
		
		Label selectItemLabel = new Label("Select Tax Category:");
		ComboBox<TaxBracket> taxComboBox = new ComboBox<TaxBracket>();
		getChildren().addAll(titleLabel, selectItemLabel, taxComboBox);
		
		Label categoryLabel = new Label("Select Tax Category:");
		ComboBox<Category> categoryBox = new ComboBox<Category>();
		getChildren().addAll(titleLabel, categoryLabel, categoryBox);
		
		Label expirLabel = new Label("Enter Days Until Expired:");
		TextField expirTF = new TextField();
		getChildren().addAll(expirLabel, expirTF);
		
		Label skuLabel = new Label("Enter SKU:");
		TextField skuTF = new TextField();
		getChildren().addAll(skuLabel, skuTF);
		
		Label unitsLabel = new Label("Enter Units Per Bin:");
		TextField unitsTF = new TextField();
		getChildren().addAll(unitsLabel, unitsTF);
		
		Label minTempLabel = new Label("Enter Minimum Temp (Leave blank for NA):");
		TextField minTempTF = new TextField();
		getChildren().addAll(minTempLabel, minTempTF);
		
		Label maxTempLabel = new Label("Enter Maximum Temp (Leave blank for NA):");
		TextField maxTempTF = new TextField();
		getChildren().addAll(maxTempLabel, maxTempTF);
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Form Submitted");
			DB db = new DB();
			db.addItem(
					itemNameTF.getText(),
					user.getUserID(),
					descriptionTA.getText(),
					itemWeightTF.getText(),
					itemPriceTF.getText(),
					taxComboBox.getValue(),
					expirTF.getText(),
					skuTF.getText(),
					categoryBox.getValue(),
					unitsTF.getText(),
					minTempTF.getText(),
					maxTempTF.getText()
					);
		});
	}

}
