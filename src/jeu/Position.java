package jeu;

public class Position {
	protected int x;
	protected int y ;
	
	public Position (int unX, int unY  ) {
		this.x = unX;
		this.y = unY;
	}
	
	public Position( Position p) {
		this.x = p.x;
		this.y = p.y;
	}
	
	public Position addition(Position pos) {
		return new Position(this.x + pos.x, this.y + pos.y);
	}
	
	public static final boolean positionValide (int x, int y) {
		return ( x < 8 && x >= 0 
			   && y < 8 && y >= 0 );
	}
	
	public static final boolean positionValide (Position pos) {
		return Position.positionValide(pos.x, pos.y);
	}
	
	public static final int tradPosition(int x, int y) {
		return x * 8 + y;
	}
	
	public static final int tradPosition(Position pos) {
		return Position.tradPosition(pos.x, pos.y);
	}
	
	public String toString() {
		return "[" + this.x + ", " + this.y + "]";
	}
}

