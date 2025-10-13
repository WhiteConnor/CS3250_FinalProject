/**
 * Customer class us used to track and handle customer actions
 * 
 * @author white
 */
public class Customer extends User {
	Customer(int userID) {
		super(userID);
		// TODO Auto-generated constructor stub
	}

	private int points;

	/**
	 * Return Customer points
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Sets customer points
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * Add or subtract points
	 * 
	 * @param points
	 */
	public void addPoints(int points) {
		this.points += points;
	}
	
	
}
