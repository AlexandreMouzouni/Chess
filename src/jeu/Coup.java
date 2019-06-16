package jeu;

public class Coup {
	public Position pos1;
	public Position pos2;
	
	public Coup(Position pos1, Position pos2) {
		this.pos1 = pos1;
		this.pos2 = pos2;
	}
	
	public String toString() {
		return pos1.toString() + pos2.toString();
	}
	
	public String toNotationI() {
		// En notation internationale, l'indice commence a 1
		String s = "";
		s += "" + (pos1.x + 1) + (pos1.y + 1) 
				+ (pos2.x + 1) + (pos2.y + 1);
		return s;
	}
	
	public static void main(String[] args) {
		Position p1 = new Position(0,0);
		Position p2 = new Position(1,2);
		Coup c = new Coup(p1, p2);
		System.out.println(c.toNotationI());
	}
}
