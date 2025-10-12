import java.util.Date;
/**
 * User class is an abstract class to represent users
 * 
 * @author white
 */
public class User {
	private int userID;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String userName;
	private String salt;
	private String hashedPassword;
	private Sex gender;
	
	User(int userID) {
		setUserID(userID);
	}
	/**
	 * Reset the user password
	 * @param salt String: 16 byte string salt
	 * @param hashedPassword String 128 byte hashed password
	 */
	public void resetPassword(String salt, String hashedPassword) {
		this.salt = salt;
		this.hashedPassword = hashedPassword;
	}
	/**
	 * Verify password
	 * @param hashedPassword String: 128 byte hashed password
	 * @return boolean: Hash matches
	 */
	public boolean verifyPassword(String hashedPassword) {
		return this.hashedPassword == hashedPassword;
	}
	/**
	 * Get salt for verification
	 * @return salt String: 16 byte string salt
	 */
	public String getSalt() {
		return salt;
	}
	/**
	 * Get first name
	 * @return firstName String: first name
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * Update first name
	 * @param firstName String: first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * Get last name
	 * @return lastName String: last name
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * Set last name
	 * @param lastName String: last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * Get birth date
	 * @return birthDate Date: birth date
	 */
	public Date getBirthDate() {
		return birthDate;
	}
	/**
	 * Set birth date
	 * @param birthDate String: birth date
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	/**
	 * Get username
	 * @return userName String: username
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * Set username
	 * @param userName String: username
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	/**
	 * Get gender
	 * @return gender Sex: MALE, FEMALE
	 */
	public Sex getGender() {
		return gender;
	}
	/**
	 * Set gender
	 * @param gender Sex: MALE, FEMALE
	 */
	public void setGender(Sex gender) {
		this.gender = gender;
	}
	/**
	 * Get user id
	 * @return userID Integer: User id
	 */
	public int getUserID() {
		return userID;
	}
	/**
	 * Set user id
	 * @param userID Integer: user id
	 */
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
