public class Coordinate {
	/** The number (row) */
	public int num;
	/** The letter (column) */
	public int letter;

	/**
	 * @desc Constructs a Coordinate object
	 * 
	 * @param num
	 *            the numb (row) of this Coordinate
	 * @param letter
	 *            the letter (column) of this Coordinate
	 */
	public Coordinate(int num, int letter) {
		this.num = num;
		this.letter = letter;
	}

	/**
	 * @desc A method that returns the notated version of the Coordinate object provided.
	 * 
	 * @param pos
	 *            the Coordinate
	 * @return Notated String of Coordinate (IE: a8 or d5)
	 */
	public static String notatedPos(Coordinate pos) {
		char ch = 'a';
		ch += pos.letter;
		return "" + ch + (pos.num + 1);
		// chess board starts at 1
	}

	/**
	 * @param num
	 *            the number (row) of the Coordinate
	 * @param letter
	 *            the letter (column) of the Coordinate
	 * @return Notated String of Coordinate (IE: a8 or d5)
	 */
	public static String notatedPos(int num, int letter) {
		return notatedPos(new Coordinate(num, letter));
	}

	/** @return the notated String (IE: a8 or d5) */
	public String getNotation() {
		return notatedPos(this);
	}
	
	/**
	 * @desc A method that returns a Coordinate object of the notation provided.
	 * 
	 * @param Notated String object of coordinates.
	 * 
	 * @return Coordinate object of coordinates provided.
	 */ 
	public static Coordinate decode(String notation) {
		int letter = 'a' - notation.charAt(0);
		int num = Integer.valueOf(notation.charAt(1)) - 1;
		return new Coordinate(num, letter);
	}
	
	/** 
	 * @desc Accessor method, returns the int number val
	 * 
	 * @return Number integer
	 */
	public int getNum() {
		return num;
	} 
	
	/** 
	 * @desc Accessor method, returns the int letter val
	 * 
	 * @return Letter integer
	 */
	public int getLetter() {
		return letter;
	}
	
	/**
	 * @desc Compares the Coordinates and returns true if they are the same.
	 * 
	 * @param Coordinate object being compared
	 * 
	 * @return true if coordinates are alike, false if not
	 */ 
	public boolean equals(Coordinate c) {
		return num == c.getNum() && letter == c.getLetter();
	}
	
	/** 
	 * @desc When the object is called upon as a String object, it returns the notated version of the object
	 * 
	 * @return Notated version of the coordinates of the object
	 */ 
	@Override
	public String toString() {
		return getNotation();
	}
}
