package model;

public enum Rank {
	ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), 
	SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
	JACK(10), QUEEN(10), KING(10);
	
	// Set cribbage score value
	private final int cribVal;
	
	Rank(int value) {
		this.cribVal = value;
	}
	
	public int getValue() {
		return cribVal;
	}
	
	// Return 1 if other is sequential and greater,
	// -1 if other is sequential and less,
	// 0 if ranks are not sequential.
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
