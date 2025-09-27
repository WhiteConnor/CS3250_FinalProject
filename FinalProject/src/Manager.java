import java.util.ArrayList;

/**
 * Manager Class to handle manager actions
 * 
 * @author white
 */
public class Manager extends User {
	private String location;
	private ArrayList<Employee> subordinates;
	
	/**
	 * Get manager location of employment
	 * @return location String: location of employment
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Set employment location
	 * @param location String: employment location
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Get list of subordinate employees
	 * @return subordinates ArrayList<Employee>: Subordinates
	 */
	public ArrayList<Employee> getSubordinates() {
		return subordinates;
	}
	
	/**
	 * Set subordinates
	 * @param subordinates ArrayList<Employee>: Subordinates
	 */
	public void setSubordinates(ArrayList<Employee> subordinates) {
		this.subordinates = subordinates;
	}
	
	/**
	 * Add subordinates
	 * @param subordinates ArrayList<Employee>: subordinates
	 */
	public void addSubordinates(ArrayList<Employee> subordinates) {
		subordinates.addAll(subordinates);
	}
	
}
