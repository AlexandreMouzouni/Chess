package jeu;

import java.util.ArrayList;

public class Tour extends Piece {
	public boolean estDeplace = false;
	
	public Tour(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public Tour(boolean uneCouleur, Position unePosition) {
		this(uneCouleur, unePosition.x, unePosition.y);
	}

	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}
	
	public void calculCoups(Echiquier e) {
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition ={new Position(0,1),new Position(0,-1),new Position(1,0),new Position(-1,0)};
		
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
			return "♜";
		}
		else {
			return "♖";
		}
	}
	
	public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	e.setPiece(1,1, new Tour(Couleur.BLANC, 1,1));    	
    	e.setPiece(1,2, new Cavalier(Couleur.NOIR, 1,2));
    	((Tour) e.getPiece(1,1)).calculCoups(e);
    	ArrayList<Position> a = e.getPiece(1,1).getListeCoups();
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
	}
}