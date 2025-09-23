import java.util.ArrayList;

public class Manager extends User {
	private String location;
	private ArrayList<Employee> subordinates;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList<Employee> getSubordinates() {
		return subordinates;
	}
	public void setSubordinates(ArrayList<Employee> subordinates) {
		this.subordinates = subordinates;
	}
	public void addSubordinate(ArrayList<Employee> subordinate) {
		subordinates.addAll(subordinate);
	}
	
}
