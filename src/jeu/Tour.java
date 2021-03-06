package jeu;

import java.io.Serializable;
import java.util.ArrayList;

public class Tour extends Piece implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9167291371256637675L;
	public boolean premierDeplacement = true;
	
	@Override
	// Si la pièce se déplace pour la première fois (avec setPosition),
	// alors elle n'est plus au premier rang
	// On ne peut ainsi plus faire de roques dès que la tour est déplacé
	public void setPosition(Position unePosition) {
    	super.setPosition(unePosition);
    	this.premierDeplacement = false;
    }
	
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
			return "t";
		}
		else {
			return "T";
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