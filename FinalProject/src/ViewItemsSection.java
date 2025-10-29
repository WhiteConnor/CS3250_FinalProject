import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.util.ArrayList;
import java.util.Arrays;

public class ViewItemsSection extends VBox {
	public ViewItemsSection(MainPage page, User user) {
		Label titleLabel = new Label("View Inventory Items");
		getChildren().add(titleLabel);
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList("Item Name", "Description", "Weight (kg)", "Price", "Tax Bracket", "Expiration Time", "SKU", "Category", "Units Per Bin", "Date Added", "Last Updated", "Min temp", "Max temp"));
        for (String colStr : tableCols) {
        	TableColumn column = new TableColumn(colStr);
        	table.getColumns().add(column);
        }
        setSpacing(5);
        getChildren().addAll(table);
 
	}
}
