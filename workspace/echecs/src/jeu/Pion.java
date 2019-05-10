package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

   

public class Pion extends Piece {
    public Pion(boolean uneCouleur, int uneLigne, int uneColonne) {
    	super(uneCouleur, uneLigne, uneColonne);
    }
    	
    public Pion(boolean uneCouleur, Position unePosition) {
    	this(uneCouleur, unePosition.x, unePosition.y);
    }
    	
public boolean premierRang;

		
	public ArrayList<Position> listeCoupValide(Echiquier e) {
		ArrayList<Position> listeCoupPossible = new ArrayList<Position>();
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition = new Position[3];
		boolean couleurPion = super.getCouleur(); 
		int indiceDirectionVecteur = -1;
		int indiceCouleur = 1;
		
		if(couleurPion == Couleur.NOIR) { // Noir: il descend, c'est a dire 1 (car y = 1 == descente)
			// Un pion noir descent, cad vecteur = 1
			indiceCouleur = 1;
		} 
		else 
		{
			indiceCouleur = -1; // -1 le pion monte et la direction est vers le haut
		}
		
		for (int i =0 ; i<3; i++ ) {
			vecteurPosition[i] = new Position(indiceDirectionVecteur,indiceCouleur);
			/*
			 * indiceDirectionVeceteur variant de -1,0,1 car on y ajoute +1 a chaque iteration
			 * deuxieme variable ne variant pas car elle est determine  avant la boucle grace a la couleur    
			 */
			indiceDirectionVecteur++;
		}
		
		for (int i =0 ; i<3; i++ ) {
			Position nouvellePosition = positionDeDepart;
			nouvellePosition = nouvellePosition.addition(vecteurPosition[i]);
			
			// Si la position d'arrivee du pion est correcte..
			if (Position.positionValide(nouvellePosition)) {
				if(i == 1) { // Cas deplacement vertical 
					if (!(e.containsPiece(nouvellePosition))){ // Est-ce que le mouvement n'est PAS bloqu� � la case?
						listeCoupPossible.add(nouvellePosition);
						
					}
					
				}
				else if(i==0 || i==2) { // Cas d�placement en diagonal
					if (e.getPiece(nouvellePosition).getCouleur() != couleurPion ){ // Est-ce il y a une pi�ce de couleur adverse � la case?
						listeCoupPossible.add(nouvellePosition);
					}
				}
			}
		}
		return listeCoupPossible;
	}


public static void main(String args[]) {
	Echiquier e = new Echiquier(true); // Echiquier vide
	
	e.setPiece(0,1, new Pion(Couleur.NOIR, 0,1));    	
	e.setPiece(1,2, new Cavalier(Couleur.BLANC,1,2));
	ArrayList<Position> a = e.getPiece(0,1).listeCoupValide(e);
	
	
	for (Position p : a) {
		System.out.println(p);
	}
}
}