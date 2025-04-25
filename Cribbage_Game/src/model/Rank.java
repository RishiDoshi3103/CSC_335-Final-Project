package model;

/**
 * The {@code Rank} enum represents the rank of a playing card,
 * along with its associated point value in the game of Cribbage.
 */
public enum Rank {

	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), 
	SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
	JACK(10), QUEEN(10), KING(10);

	/** The point value of the rank in Cribbage (used for scoring). */
	private final int cribVal;

	/**
	 * Constructs a {@code Rank} with the given point value.
	 *
	 * @param value the cribbage point value associated with the rank
	 */
	Rank(int value) {
		this.cribVal = value;
	}

	/**
	 * Returns the Cribbage point value of this rank.
	 *
	 * @return the cribbage value for the rank
	 */
	public int getValue() {
		return cribVal;
	}

	/**
	 * Checks if this rank is sequentially adjacent to another rank.
	 * 
	 * Returns {@code 1} if {@code other} is the next higher rank.
	 * Returns {@code -1} if {@code other} is the next lower rank.
	 * Returns {@code 0} otherwise.
	 * 
	 *
	 * @param other the other {@code Rank} to compare against
	 * @return 1 if next, -1 if previous, 0 if not sequential
	 */
	public int isSequential(Rank other) {
		if (this.cribVal < 9) {
			if (this.cribVal == other.getValue() - 1) {
				return 1;
			}
			if (this.cribVal == other.getValue() + 1) {
				return -1;
			}
			return 0;
		}
		if (this.equals(NINE)) {
			if (other.equals(EIGHT)) {
				return -1;
			}
			if (other.equals(TEN)) {
				return 1;
			}
			return 0;
		}
		if (this.equals(TEN)) {
			if (other.equals(NINE)) {
				return -1;
			}
			if (other.equals(JACK)) {
				return 1;
			}
			return 0;
		}
		if (this.equals(JACK)) {
			if (other.equals(TEN)) {
				return -1;
			}
			if (other.equals(QUEEN)) {
				return 1;
			}
			return 0;
		}
		if (this.equals(QUEEN)) {
			if (other.equals(JACK)) {
				return -1;
			}
			if (other.equals(KING)) {
				return 1;
			}
			return 0;
		}
		if (this.equals(KING)) {
			if (other.equals(QUEEN)) {
				return -1;
			}
			return 0;
		}
		return 0;
	}
}
