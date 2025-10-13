import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * A utility class to handle input more cleanly
 */
public final class InputHandling {
	
	/**
	 * Converts a string to cents (an integer equivalent to cents)
	 * Copilot used to generate conversion code, modified after to fit requirements
	 * @param value String: value to convert, should be preprocessed with stringMatchCents
	 * @return int: value in cents
	 */
	public static int stringToCents(String value) {
		if (value == null || value.trim().isEmpty() || value.trim().equals("."))
			return 0;
		
		BigDecimal valDec = new BigDecimal(value.trim());
        BigDecimal cents = valDec.multiply(BigDecimal.valueOf(100));
        return cents.setScale(0, RoundingMode.UP).intValueExact();
	}
	
	/**
	 * Checks if a string matches a float pattern
	 * @param value String: value to check
	 * @return Boolean: matches or not
	 */
	public static Boolean stringMatchFloat(String value) {
		return value.matches("\\d*\\.{0,1}\\d*");
	}
	
	/**
	 * Checks if a string matches a 'cents' pattern, used to precheck before calling stringToCents
	 * @param value String: value to check
	 * @return Boolean: matches or not
	 */
	public static Boolean stringMatchCents(String value) {
		return value.matches("\\d*\\.{0,1}\\d{0,2}");
	}
	
	/**
	 * Checks if a string matches an integer pattern
	 * @param value String: value to check
	 * @return Boolean: matches or not
	 */
	public static Boolean stringMatchInt(String value) {
		return value.matches("\\d*");
	}
}
