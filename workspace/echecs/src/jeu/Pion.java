package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

public class Pion extends Piece {

    public boolean premierRang;
	
    public Pion(boolean uneCouleur, int x, int y) {
    	super(uneCouleur, x, y);
    	if(y == 1 && uneCouleur == Couleur.NOIR){
    		setPremierRang(true);
    	}
    	else if(y == 7  && uneCouleur == Couleur.BLANC) {
    		setPremierRang(true);	
    	}
    }
    	
    public Pion(boolean uneCouleur, Position unePosition) {
    	this(uneCouleur, unePosition.x, unePosition.y);
    }
    	
	public void setPremierRang(boolean premierRang) {
		this.premierRang = premierRang;
	}
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}
	
	public void calculCoups(Echiquier e) {
		this.calculCoupsDeBase(e);
		this.deplacementDoublePion(e);
	}
	
	public void calculCoupsDeBase(Echiquier e) { 
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition = new Position[3];
		boolean couleurPion = super.getCouleur(); 
		int indiceDirectionVecteur = -1;
		
		int indiceCouleur;
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
					if (!(e.containsPiece(nouvellePosition))){ // Est-ce que le mouvement n'est PAS bloqué à la case?
						super.addCoup(nouvellePosition);
					}
					
				}
				else if(i==0 || i==2) { // Cas déplacement en diagonal
					if (e.containsPiece(nouvellePosition) ) {
						if (e.getPiece(nouvellePosition).getCouleur() != couleurPion ){ 
							// Est-ce il y a une pièce de couleur adverse à la case?
							super.addCoup(nouvellePosition);
						}
					}
				}
			}
		}
	}
	
	public void deplacementDoublePion(Echiquier e) {
		int indiceCouleur;
		if(super.getCouleur() == Couleur.NOIR){ // Noir: il descend, c'est a dire 1 (car y = 1 == descente)
			// Un pion noir descent, cad vecteur = 1
			indiceCouleur = 1;
		} 
		else 
		{
			indiceCouleur = -1; // -1 le pion monte et la direction est vers le haut
		}
		
		if (premierRang == true) {
			Position positionActuelle = super.getPosition();
			int x = positionActuelle.x;
			int y = positionActuelle.y;
			
			Position positionAvance = new Position(x, y + 2*indiceCouleur);
			
			if (Position.positionValide(positionAvance)) {
				super.addCoup(positionAvance);
			}
		}
	}
	
	public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(super.getCouleur() == Couleur.NOIR){
			return "♟";
		}
			else {
				return "♙";
				
			}
			
		}

	public static void main(String args[]) {
		// Test 1: Déplacements de base + manger cavalier sur le coté
		Echiquier e = new Echiquier(true); // Echiquier vide
		
		e.setPiece(0,1, new Pion(Couleur.NOIR, 0,1));    	
		e.setPiece(1,2, new Cavalier(Couleur.BLANC, 1,2));
		((Pion) e.getPiece(0,1)).calculCoups(e);
		ArrayList<Position> a = e.getPiece(0,1).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
		}
		
		System.out.println("--------------");
		//test 2 : Déplacements au milieu de l'échiquier
		e = new Echiquier(true);
		
		e.setPiece(3,3, new Pion(Couleur.NOIR, 3,3));
		((Pion) e.getPiece(3,3)).calculCoups(e);
		a = e.getPiece(3,3).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
		}
		
		
		System.out.println("--------------");
		//test 3 : Déplacements au premier rang
		e = new Echiquier(true);
		
		e.setPiece(3,1, new Pion(Couleur.NOIR, 3,1));
		((Pion) e.getPiece(3,1)).calculCoups(e);
		a = e.getPiece(3,1).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
			
		}
		
		System.out.println( e.getPiece(3,1).affiche() );
	}
}