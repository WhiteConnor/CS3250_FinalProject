import java.util.Date;

public abstract class User {
	private int userID;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String userName;
	private String salt;
	private String hashedPassword;
	private Sex gender;
	
	public void resetPassword(String salt, String hashedPassword) {
		this.salt = salt;
		this.hashedPassword = hashedPassword;
	}
	
	public boolean verifyPassword(String hashedPassword) {
		return this.hashedPassword == hashedPassword;
	}
	
	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Sex getGender() {
		return gender;
	}

	public void setGender(Sex gender) {
		this.gender = gender;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
}
