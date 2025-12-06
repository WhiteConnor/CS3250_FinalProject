import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.*;

/**
 * WarehouseReceipt class creates a gui page for adding a warehouse receipt item to table. Connects to DB to insert items
 * 
 * @author white
 */
public class WarehouseReceipt extends VBox {
	public WarehouseReceipt(MainPage page) {
		Label titleLabel = new Label("Warehouse Receipt");
		DB db = new DB();
		
		ArrayList<Pair<String, String>> itemsList;
		try {
			itemsList = db.getItemSKUs();
		} catch (SQLException e) {
			itemsList = new ArrayList<Pair<String, String>>();
			itemsList.add(new Pair<String, String>("Test", "Test"));
			e.printStackTrace();
		}
		
		

		Label selectItemLabel = new Label("Select item received:");
		ComboBox<Pair<String, String>> itemComboBox = new ComboBox<Pair<String, String>>(FXCollections.observableArrayList(itemsList));
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
		stockTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!InputHandling.stringMatchInt(newValue))
				stockTextField.setText(oldValue);
		});
		
		Button submitButton = new Button("Submit");
		getChildren().add(submitButton);
		
		submitButton.setOnAction(event -> {
			System.out.println("Form Submitted");
			db.verifySKU(itemComboBox.getValue().getValue());
			db.insertNewWarehouseReceipt(
					itemComboBox.getValue().getValue(),
					receivedDatePicker.getValue(),
					lotCodeTextField.getText(),
					Integer.parseInt(stockTextField.getText()) // TODO: Parse int here
					);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Receipt Creation");
			alert.setHeaderText("Receipt created successfully");
			alert.setContentText("SKU: " + itemComboBox.getValue().getValue());

			alert.showAndWait();
			
			WarehouseReceipt createTransactionPage = new WarehouseReceipt(page);
			ScrollPane scrollPane = (ScrollPane) page.getCenter();
			scrollPane.setContent(createTransactionPage);
		});
	}
}
