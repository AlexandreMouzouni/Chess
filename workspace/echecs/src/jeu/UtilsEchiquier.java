package jeu;

public class UtilsEchiquier {
	public static final boolean positionValide(int pos) {
		return (pos > 0 && pos < 64);
	}
	
	protected static final String printPosition(int pos) {
		int x = (int) Math.floor(pos / 8);
		int y = pos % 8;
		return "[" + x + " ," + y + "]";
	}
	
	public static void main(String[] args) {
		System.out.println(UtilsEchiquier.printPosition(4));
		System.out.println(UtilsEchiquier.printPosition(9));
	}
}
