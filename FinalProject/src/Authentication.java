import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.Base64;


/**
 * Authentication class is used to generate a salt, hash a password for verification or new generation
 * www.geeksforgeeks.org/java/sha-256-hash-in-java/ is where I learned how to use hashing, modified for my own use
 * Copilot used to convert sha to String
 */
public class Authentication {
	private String salt;
	private String username;
	
	/**
	 * Default constructor for default
	 */
	public Authentication() {
		
	}
	
	/**
	 * Create an authentication object for username
	 * Saves salt
	 * Saves username
	 * @param username String: username
	 * @throws SQLException 
	 */
	public Authentication(String username) throws SQLException {
		System.out.println("Username: " + username);
		this.username = username;
		DB db = new DB();
		try {
			salt = db.getSalt(username);
			
		} catch (SQLException e) {
			throw e;
		}
	}
	
	/**
	 * Generate a hashed password based on current salt
	 * @param password String: new unhashed password
	 * @return Hashed password
	 * @throws NoSuchAlgorithmException 
	 */
	private String generateHash(String password) throws NoSuchAlgorithmException {
		String saltedPassword = salt + password;
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");
			return Base64.getEncoder().encodeToString(sha.digest(password.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}
	
	
	/**
	 * Generate a new salt using secure random
	 */
	private void generateNewSalt() {
		SecureRandom generator = new SecureRandom();
		byte saltBytes[] = new byte[16];
		generator.nextBytes(saltBytes);
		salt = Base64.getEncoder().encodeToString(saltBytes);
	}
	
	/**
	 * Verify password by comparing db hashed value with new hashed value
	 * @param password String: unhashed password
	 * @return Boolean: verification passed
	 * @throws Exception 
	 */
	public boolean verify(String password) throws Exception {
		DB db = new DB();
		try {
			String hashedPass = db.getHashedPass(username);
			if (hashedPass.isEmpty())
				throw new Exception("No password for this user");
			return hashedPass.equals(this.generateHash(password));
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}
	
	/**
	 * Create and return a new password. Generates a new salt
	 * @param password String: new unhashed password
	 * @return String: new hashed password
	 * @throws NoSuchAlgorithmException
	 */
	public String newPassword(String password) throws NoSuchAlgorithmException {
		generateNewSalt();
		try {
			return generateHash(password);
		} catch (NoSuchAlgorithmException e) {
			throw e;
		}
	}
	
	/**
	 * Get salt
	 * @return Integer: salt
	 */
	public String getSalt() {
		return salt;
	}
	
	
}
