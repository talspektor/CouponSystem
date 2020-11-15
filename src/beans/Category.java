package beans;

/**
 * @author taltalspektor
 *
 */
public enum Category {
	FOOD(1),
	ELECTRICITY(2),
	RESTAURANT(3),
	VACATION(4);
	
	private final int value;
	Category(final int newValue) {
		value = newValue;
	}
	
	public int getValue() {
		return value;
	}
}