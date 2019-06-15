package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

public class Cavalier extends Piece {
	public Cavalier(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups();
	}
	
    public void calculCoups() {
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
    			super.addCoup(nouvellePosition);
    		}
    	}
    }
    public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(super.getCouleur() == Couleur.NOIR){
			return "♞";
		}
			else {
				return "♘";
			}
    }
    public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	e.setPiece(0, 0, new Cavalier(Couleur.BLANC, 0, 0));
    	((Cavalier) e.getPiece(0, 0)).calculCoups();
    	ArrayList<Position> a = e.getPiece(0, 0).getListeCoups();
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
    }
}