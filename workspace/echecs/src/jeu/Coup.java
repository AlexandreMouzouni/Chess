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
}
