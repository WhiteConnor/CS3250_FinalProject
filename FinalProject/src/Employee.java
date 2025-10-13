/**
 * Employee Class to handle employee actions
 * 
 * @author white
 */
public class Employee extends User {
	Employee(int userID) {
		super(userID);
		// TODO Auto-generated constructor stub
	}

	private String location;

	/**
	 * Gets string of location of employment
	 * 
	 * @return String: Location of employment
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * Update location of employment
	 * 
	 * @param location String: Location of employment
	 */
	public void setLocation(String location) {
		this.location = location;
	}
}
