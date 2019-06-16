package jeu;

import java.util.ArrayList;

public class Fou  extends Piece {
	
	public boolean estDeplace = false;
	
	public Fou(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public Fou(boolean uneCouleur, Position unePosition) {
		this(uneCouleur, unePosition.x, unePosition.y);
	}

	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}
	
	public void calculCoups(Echiquier e) {
		Position positionDeDepart = super.getPosition();
		// Directions: en diagonale
		Position[] vecteurPosition ={new Position(1,-1),new Position(-1,-1),new Position(1,1),new Position(-1,1)};
		
		for (int i =0 ; i<4; i++ ) {
			Position nouvellePosition = positionDeDepart;
			boolean estBloque = false; 
			
			while (Position.positionValide(nouvellePosition) && estBloque == false ) {
				nouvellePosition = nouvellePosition.addition(vecteurPosition[i]);
				
				if (Position.positionValide(nouvellePosition)) {
					// Si la case contient une piece, on ne peut plus avancer
					if (e.containsPiece(nouvellePosition)){
						estBloque = true;
					}
					
					// Si la case contient une piece, quelle est sa couleur?
					// Si la couleur est la même que la notre, on ne peut avancer
					if (e.containsPiece(nouvellePosition)){
						if (e.getPiece(nouvellePosition).getCouleur() != this.getCouleur()) {
							super.addCoup(nouvellePosition);
						}
					} else {
						super.addCoup(nouvellePosition);
					}
				}
			}
		}
	}

	public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(couleurPion == Couleur.NOIR){
			return "f";
		}
		else {
			return "F";
		}
	}
	public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	Position posFou = new Position(1,1);
    	
    	e.setPiece(posFou, new Fou(Couleur.BLANC, posFou));    	
    	e.setPiece(0,0, new Cavalier(Couleur.NOIR, 0,0));
    	((Fou) e.getPiece(posFou)).calculCoups(e);
    	ArrayList<Position> a = e.getPiece(posFou).getListeCoups();
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
    	
    	System.out.println( e.getPiece(posFou).affiche() );
	}
}