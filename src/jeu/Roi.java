package jeu;

import java.util.ArrayList;

public class Roi extends Piece {
	public boolean premierDeplacement = true;
	
	public Roi(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public Roi(boolean uneCouleur, Position unePosition) {
		this(uneCouleur, unePosition.x, unePosition.y);
	}
	
	@Override
	// Si la pièce se déplace pour la première fois (avec setPosition),
	// alors elle n'est plus au premier rang
	// On ne peut ainsi plus faire de roques dès que le roi est déplacé
	public void setPosition(Position unePosition) {
    	super.setPosition(unePosition);
    	this.premierDeplacement = false;
    }
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}

	public void calculCoups(Echiquier e) {
		this.calculCoupsDeBase(e);
		this.calculCoupsRoques(e);
	}
	
	public void calculCoupsDeBase(Echiquier e) {
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition ={new Position(1,-1),new Position(-1,-1),new Position(1,1),new Position(-1,1),
				new Position(0,1),new Position(0,-1),new Position(1,0),new Position(-1,0)};
		
		for (int i =0 ; i<8; i++ ) {
			Position nouvellePosition = positionDeDepart;
			
			nouvellePosition = nouvellePosition.addition(vecteurPosition[i]);
			
			if (Position.positionValide(nouvellePosition)) {
				// Si la case contient une piece, quelle est sa couleur?
				// Si la couleur est la même que la notre, on ne peut jouer
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
	
	public void calculCoupsRoques(Echiquier e) {
		if (e.getTourAJouer() == Couleur.BLANC) {
			if (e.getRoquesPossibles()[0] == true) { // Petit roque blanc
				super.addCoup(new Position(2,0));
			} 
			if (e.getRoquesPossibles()[1] == true) { // Grand roque blanc
				super.addCoup(new Position(6,0));
			}
		} else if (e.getTourAJouer() == Couleur.NOIR) {
			if (e.getRoquesPossibles()[2] == true) { // Petit roque noir
				super.addCoup(new Position(2,7));
			} 
			if (e.getRoquesPossibles()[3] == true) { // Grand roque noir
				super.addCoup(new Position(6,7));
			}
		}
	}
	
	public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(couleurPion == Couleur.NOIR){
			return "r";
		}
		else {
			return "R";
		}
    }

	public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	Position posFou = new Position(1,1);
    	
    	e.setPiece(posFou, new Roi(Couleur.BLANC, posFou));
    	e.setPiece(0,0, new Cavalier(Couleur.NOIR, 0,0));
    	
    	((Roi) e.getPiece(posFou)).calculCoups(e);
    	ArrayList<Position> a = e.getPiece(posFou).getListeCoups();
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
	}
}
