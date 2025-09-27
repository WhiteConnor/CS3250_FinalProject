import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import javafx.scene.control.TextField;

import javafx.collections.*;

/**
 * WarehouseReceipt class creates a gui page for adding a warehouse receipt item to table. Connects to DB to insert items
 * 
 * @author white
 */
public class WarehouseReceipt extends VBox {
	public WarehouseReceipt() {
		Label titleLabel = new Label("Warehouse Receipt");
		
		String itemsList[] = {"Food", "Water", "Hardware", "Milk", "Floor"};

		Label selectItemLabel = new Label("Select item received:");
		ComboBox<String> itemComboBox = new ComboBox<String>(FXCollections.observableArrayList(itemsList));
		getChildren().addAll(titleLabel, selectItemLabel, itemComboBox);
		
		Label dateLabel = new Label("Select date of receipt:");
		DatePicker receivedDatePicker = new DatePicker();
		getChildren().addAll(dateLabel, receivedDatePicker);
		
		Label lotCodeLabel = new Label("Enter in lot code:");
		TextField lotCodeTextField = new TextField();
		getChildren().addAll(lotCodeLabel, lotCodeTextField);
		
		Label stockLabel = new Label("Enter in received stock count");
		TextField stockTextField = new TextField();
		getChildren().addAll(stockLabel, stockTextField);
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Form Submitted");
			DB db = new DB();
			db.insertNewWarehouseReceipt(
					itemComboBox.getValue(),
					receivedDatePicker.getValue(),
					lotCodeTextField.getText(),
					stockTextField.getText()
					);
		});
	}
}
