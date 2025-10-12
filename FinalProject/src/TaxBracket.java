/**
 * Enum for sales tax brackets in Utah
 * Copilot used on 10/12/2025 to assign values to an enum eg set FOOD to 0.03fand compile 
 * different sales tax brackets and items in Utah
 */
public enum TaxBracket {
	FOOD(0.03f),
	MEDICAL(0.0f),
	GENERAL(0.0685f);
	
	private float rate;

	TaxBracket(float rate) {
		this.rate = rate;
	}
	
	public float getRate() {
		return rate;
	}
}
