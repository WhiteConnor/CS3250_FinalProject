import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewItemsSection extends StackPane {
	private ArrayList<String> propertyNames = new ArrayList<>(Arrays.asList(
		    "itemName",
		    "description",
		    "weightKG",
		    "price",
		    "inventory",
		    "taxBracket",
		    "expirationTime",
		    "SKU",
		    "category",
		    "unitsPerBin",
		    "dateAdded",
		    "lastUpdated",
		    "minTemp",
		    "maxTemp"
		));
	
	public ViewItemsSection(MainPage page, User user) {
		DB db = new DB();
		VBox root = new VBox();
		this.getChildren().add(root);
		Label titleLabel = new Label("View Inventory Items");
		root.getChildren().add(titleLabel);
		
		
//		date by range, ints by equals or range, floats by range,
//		sort dates, ints and floats, sort strings alphabetically
//		minimum 
		HBox filtersHB = new HBox();
		/* Category Select */
		VBox categoryVB = new VBox();
		FlowPane categoryFP = new FlowPane();
//		root.getChildren().add(categoryFP);
		Button addCatFiltersBtn = new Button("Add Categories");
		categoryVB.getChildren().add(categoryFP);
		categoryVB.getChildren().add(addCatFiltersBtn);
		filtersHB.getChildren().add(categoryVB);
		
		/* Tax filters */
		Label tbLbl = new Label("Tax Bracket:");
		CheckBox tbFoodCB = new CheckBox("Food");
		CheckBox tbMedicalCB = new CheckBox("Medical");
		CheckBox tbGeneralCB = new CheckBox("General");
		
		VBox taxBracketVBox = new VBox(10, tbLbl, tbFoodCB, tbMedicalCB, tbGeneralCB);
		
		filtersHB.getChildren().add(taxBracketVBox);
		
		/* Individual Items */
		VBox individItemVB = new VBox();
		// low inventory
		CheckBox lowInvCB = new CheckBox("Low Inventory");
		individItemVB.getChildren().add(lowInvCB);
		// search limit
		Label searchLimitLbl = new Label("Search limit: ");
		TextField searchLimitTB = new TextField("50");
		searchLimitTB.textProperty().addListener((observable, oldValue, newValue) -> {
		    if (!InputHandling.stringMatchInt(newValue))
		    	searchLimitTB.setText(oldValue);
		});
		individItemVB.getChildren().addAll(searchLimitLbl, searchLimitTB);
		
		filtersHB.getChildren().add(individItemVB);
		root.getChildren().add(filtersHB);
		
		
		Button applyFiltersBtn = new Button("Apply Filters");
		
		
		
		root.getChildren().add(applyFiltersBtn);
		
		Popup categoryRoot = new Popup();
		FlowPane categoryVBox = new FlowPane();

		categoryVBox.setPrefSize(200,200);
		categoryVBox.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");

		Category[] categoryVals = Category.values();
		for (Category category : categoryVals) {
		    Button button = new Button(category.name());
		    button.getStyleClass().add("select-item");
		    button.setOnAction(e -> {
		    	categoryFP.getChildren().add(new CategoryButton(category.name(), button));
		    	button.setVisible(false);
		        categoryRoot.hide();
		    });

		    categoryVBox.getChildren().add(button);
		    
		}
		
		categoryRoot.getContent().add(categoryVBox);
		
		addCatFiltersBtn.setOnAction(event -> {
			Bounds bounds = categoryFP.localToScreen(categoryFP.getBoundsInLocal());
			categoryRoot.show(categoryFP, bounds.getMinX(), bounds.getMaxY());
		});
		
		
		/* Search Bar */
		HBox searchBar = new HBox();
		
		TextField titleTextTF = new TextField();
		Button searchBtn = new Button("Search");
		
		
		searchBar.getChildren().addAll(titleTextTF, searchBtn);
		root.getChildren().add(searchBar);
		
		
		TableView table = new TableView();
        table.setEditable(true);
        
        ArrayList<String> tableCols = new ArrayList<>(Arrays.asList("Item Name", "Description", "Weight (kg)", "Price", "Inventory", "Tax Bracket", "Expiration Time", "SKU", "Category", "Units Per Bin", "Date Added", "Last Updated", "Min temp", "Max temp"));
        for (int i = 0; i < tableCols.size(); i++) {
            TableColumn<InventoryItem, Object> column = new TableColumn<>(tableCols.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames.get(i)));
            table.getColumns().add(column);
        }

        root.setSpacing(5);
        root.getChildren().addAll(table);
        
        
        try {
        	ArrayList<InventoryItem> allItems = db.getItems();
        	for (InventoryItem item : allItems)
        		table.getItems().add(item);
//			System.out.println(allItems);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        applyFiltersBtn.setOnAction(e -> {
			ArrayList<Category> selectedCategories = new ArrayList<>();
        	categoryFP.getChildren().forEach(child -> {
        		String text = ((CategoryButton) child).getText();
        		System.out.println(text);
        		Category category = Category.valueOf(text);
        		selectedCategories.add(category);
        	});
        	if (selectedCategories.isEmpty()) {
        		selectedCategories.addAll(List.of(Category.values()));
//        		System.out.println(selectedCategories);
        	} else {
//        		System.out.println(selectedCategories);
        	}
        	try {
        		ArrayList<String> taxBracketList = new ArrayList<>();
        		if (tbFoodCB.isSelected()) taxBracketList.add("FOOD");
        		if (tbMedicalCB.isSelected())taxBracketList.add("MEDICAL");
        		if (tbGeneralCB.isSelected())taxBracketList.add("GENERAL");
        		
        		table.getItems().clear();
        		String text = titleTextTF.getText();
	        	if (text.equals(""))
	        		text = "%";
	        	ArrayList<InventoryItem> allItems = db.getItemsFiltered(
	        			text,
	        			selectedCategories,
	        			taxBracketList,
	        			lowInvCB.isSelected(),
	        			Integer.parseInt(searchLimitTB.getText().equals("") ? "0" : searchLimitTB.getText()));
	        	for (InventoryItem item : allItems)
	        		table.getItems().add(item);
        	} catch (Exception ex) {
        		ex.printStackTrace();
        	}
		});
        
        searchBtn.setOnAction(event -> {
        	ArrayList<Category> selectedCategories = new ArrayList<>();
        	
        	categoryFP.getChildren().forEach(child -> {
        		String text = ((CategoryButton) child).getText();
        		System.out.println(text);
        		Category category = Category.valueOf(text);
        		selectedCategories.add(category);
        	});
        	
        	if (selectedCategories.isEmpty())
        		selectedCategories.addAll(List.of(Category.values()));
        		
	        try {
	        	ArrayList<String> taxBracketList = new ArrayList<>();
        		if (tbFoodCB.isSelected()) taxBracketList.add("FOOD");
        		if (tbMedicalCB.isSelected())taxBracketList.add("MEDICAL");
        		if (tbGeneralCB.isSelected())taxBracketList.add("GENERAL");
        		
	        	String text = titleTextTF.getText();
	        	if (text.equals(""))
	        		text = "%";
	        	
        		table.getItems().clear();
        		ArrayList<InventoryItem> allItems = db.getItemsFiltered(
	        			titleTextTF.getText(),
	        			selectedCategories,
	        			taxBracketList,
	        			lowInvCB.isSelected(),
	        			Integer.parseInt(searchLimitTB.getText()));
	        	for (InventoryItem item : allItems)
	        		table.getItems().add(item);
				System.out.println(allItems);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
 
	}
}
