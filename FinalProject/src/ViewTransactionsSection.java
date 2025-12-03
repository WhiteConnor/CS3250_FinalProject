import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class ViewTransactionsSection extends StackPane {
	private ArrayList<String> propertyNames = new ArrayList<>(Arrays.asList(
			"transaction_id",
			"item_name",
			"user_id",
			"SKU",
			"quantity",
			"total_price",
			"transaction_date",
			"description"));
	
	public ViewTransactionsSection(MainPage page) {
		DB db = new DB();
		VBox root = new VBox();
		this.getChildren().add(root);
		
		Label testLbl = new Label("View Transactions");
		root.getChildren().add(testLbl);
		
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList(
        		"Transaction ID",
        		"Item Name",
        		"User ID",
        		"SKU",
        		"Quantity",
        		"Total Price",
        		"Transaction Date",
        		"Description"));
        for (int i = 0; i < tableCols.size(); i++) {
            TableColumn<Transaction, Object> column = new TableColumn<>(tableCols.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames.get(i)));
            table.getColumns().add(column);
        }

        root.setSpacing(5);
        root.getChildren().addAll(table);
        
        
        try {
        	ArrayList<Transaction> allTransactions = db.getTransactions();
        	for (Transaction transaction : allTransactions)
        		table.getItems().add(transaction);
//			System.out.println(allItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
