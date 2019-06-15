package jeu;

import java.util.ArrayList;

public class Roi extends Piece {
	public boolean estDeplace = false;
	public boolean estEchec = false;
	
	public Roi(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public Roi(boolean uneCouleur, Position unePosition) {
		this(uneCouleur, unePosition.x, unePosition.y);
	}
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}

	public void calculCoups(Echiquier e) {
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition ={new Position(1,-1),new Position(-1,-1),new Position(1,1),new Position(-1,1),
				new Position(0,1),new Position(0,-1),new Position(1,0),new Position(-1,0)};
		
		for (int i =0 ; i<8; i++ ) {
			Position nouvellePosition = positionDeDepart;
			boolean estBloque  = false; //????
			
			nouvellePosition = nouvellePosition.addition(vecteurPosition[i]);
			// Est-ce que la position générée n'est pas hors de l'échiquier?
			if (Position.positionValide(nouvellePosition)) {
					
				super.addCoup(nouvellePosition);
				}
			}
	}
	public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(super.getCouleur() == Couleur.NOIR){
			return "♚";
		}
			else {
				return "♔";
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
