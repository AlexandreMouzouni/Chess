package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("7d04237b-e42b-4a58-9729-9ab91afeee7c")
public class Cavalier extends Piece {
	public Cavalier(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}

	public ArrayList<Position> listeCoupValide(Echiquier echiquier) {
		return this.listeCoupValide();
	}
	
    @objid ("667cac35-5223-487f-a37d-c2a99a0a0cb4")
    /*
     * Retourne la liste des déplacements corrects pour ce Cavalier.
     */
    public ArrayList<Position> listeCoupValide() {
    	Position[] deplacements = new Position[8];
    	ArrayList<Position> listeCoupValide = new ArrayList<Position>();
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
    			listeCoupValide.add(nouvellePosition);
    		}
    	}
    	
        return listeCoupValide;
    }
    
    public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	e.setPiece(0, 0, new Cavalier(Couleur.BLANC, 0, 0));
    	ArrayList<Position> a = e.getPiece(0, 0).listeCoupValide(e);
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
    }
}