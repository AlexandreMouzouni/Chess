package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("67ce68ce-210f-46e8-bfe1-320ea8b46f5b")
public class Fou extends Piece {
	
	public Fou(int unX, int unY, Couleur uneCouleur) {
		super(unX, unY, uneCouleur);
	}

	private boolean directionValide(int horizontal, int vertical) {
		return (Math.abs(horizontal) == Math.abs(vertical));
	}

	@Override
	public boolean coupValide(int unX, int unY, Echiquier echiquier) {
		int xDepart = super.getX();
		int yDepart = super.getY();
		
		return this.coupValide(xDepart, yDepart, unX, unY, echiquier); 
	}
	
    @objid ("ef00cb71-13e6-4828-89d1-0193e67a062b")
    public boolean coupValide(int xDepart, int yDepart, int xArrivee, int yArrivee, Echiquier echiquier) {
    	// Calcul des mouvements
    	int horizontal = xArrivee - xDepart;
		int vertical = yArrivee - yDepart;
		
		// Si la direction est invalide..
		if ( ! (this.directionValide(horizontal, vertical)) ) {
    		return false;
    	}
		
		// Création des vecteurs directions
		int directionHorizontal;
		int directionVertical;
		if (horizontal > 0) {
			directionHorizontal = 1;
		} else {
			directionHorizontal = -1;
		}
		
		if (vertical > 0) {
			directionVertical = 1;
		} else {
			directionVertical = -1;
		}
		
		// Boucle de vérification de présence de pion
		int x = xDepart;
		int y = yDepart;
        while( x != xArrivee ) {
        	// Si la case n'est PAS vide, il y a une pièce qui bloque le chemin
        	if ( echiquier.containsPiece(x,y) ) {
        		return false;
        	}
        	
        	x += directionHorizontal;
        	y += directionVertical;
        }
        
        // Fin de la boucle: le fou a pu se dÃ©placer jusqu'a la fin
        return true;
    }
}
