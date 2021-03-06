package jeu;

import java.io.Serializable;
import java.util.ArrayList;

public class Cavalier extends Piece implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3888581433467813812L;
	public Cavalier(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}
	
    public void calculCoups(Echiquier e) {
    	Position[] deplacements = new Position[8];
    	Position positionDepart = super.getPosition();
    	
    	deplacements[0] = new Position( 1,  2);
    	deplacements[1] = new Position( 2,  1);
    	deplacements[2] = new Position(-1,  2);
    	deplacements[3] = new Position( 2, -1);
    	deplacements[4] = new Position( 1, -2);
    	deplacements[5] = new Position(-2,  1);
    	deplacements[6] = new Position(-1, -2);
    	deplacements[7] = new Position(-2, -1);
    	
    	for (int i = 0; i < 8; i++) {
    		Position nouvellePosition = positionDepart.addition(deplacements[i]);
    		if (Position.positionValide( nouvellePosition ) ) {
    			if (e.containsPiece(nouvellePosition)) {
    				if (e.getPiece(nouvellePosition).getCouleur() != this.getCouleur()) {
    					super.addCoup(nouvellePosition);
    				}
    			} else {
    				super.addCoup(nouvellePosition);
    			}
    		}
    	}
    }
    public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(couleurPion == Couleur.NOIR){
			return "c";
		}
		else {
			return "C";
		}
    }
    public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	e.setPiece(0, 0, new Cavalier(Couleur.BLANC, 0, 0));
    	((Cavalier) e.getPiece(0, 0)).calculCoups(e);
    	ArrayList<Position> a = e.getPiece(0, 0).getListeCoups();
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
    }
}