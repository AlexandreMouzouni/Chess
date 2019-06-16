package jeu;

import java.util.ArrayList;

public class Pion extends Piece {

    public boolean premierRang;
	
    public Pion(boolean uneCouleur, int x, int y) {
    	super(uneCouleur, x, y);
    	if(y == 1 && uneCouleur == Couleur.BLANC){
    		setPremierRang(true);
    	}
    	else if(y == 6 && uneCouleur == Couleur.NOIR) {
    		setPremierRang(true);
    	}
    }
    	
    public Pion(boolean uneCouleur, Position unePosition) {
    	this(uneCouleur, unePosition.x, unePosition.y);
    }
    	
	public void setPremierRang(boolean premierRang) {
		this.premierRang = premierRang;
	}
	
	@Override
	// Si la pièce se déplace pour la première fois (avec setPosition),
	// alors elle n'est plus au premier rang
	public void setPosition(Position unePosition) {
    	super.setPosition(unePosition);
    	this.setPremierRang(false);
    }
	
	public void calculCoups(Echiquier e, Partie p) {
		this.calculCoups(e);
	}
	
	public void calculCoups(Echiquier e) {
		this.calculCoupsDeBase(e);
		
		if (this.premierRang) {
			this.deplacementDoublePion(e);
		}
	}
	
	public void calculCoupsDeBase(Echiquier e) { 
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition = new Position[3];
		boolean couleurPion = super.getCouleur(); 
		int indiceDirectionVecteur = -1;
		
		int indiceCouleur;
		if(couleurPion == Couleur.NOIR) { // Noir: il descend, c'est a dire -1 (car y = -1 == descente)
			// Un pion noir descent, cad vecteur = -1
			indiceCouleur = -1;
		} 
		else 
		{
			indiceCouleur = 1; // 1 le pion monte et la direction est vers le haut
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
					if (!(e.containsPiece(nouvellePosition))){ // Est-ce que le mouvement n'est PAS bloquÃ© Ã  la case?
						super.addCoup(nouvellePosition);
					}
					
				}
				else if(i==0 || i==2) { // Cas dÃ©placement en diagonal
					if (e.containsPiece(nouvellePosition) ) {
						if (e.getPiece(nouvellePosition).getCouleur() != couleurPion ){ 
							// Est-ce il y a une piÃ¨ce de couleur adverse Ã  la case?
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
			indiceCouleur = -1;
		} 
		else 
		{
			indiceCouleur = 1; // -1 le pion monte et la direction est vers le haut
		}
		
		Position positionActuelle = super.getPosition();
		int x = positionActuelle.x;
		int y = positionActuelle.y;
		
		Position positionAvance = new Position(x, y + 2*indiceCouleur);
		
		if (Position.positionValide(positionAvance)) {
			super.addCoup(positionAvance);
		}
	}
	
	public String affiche() {
		boolean couleurPion = super.getCouleur(); 
		if(couleurPion == Couleur.NOIR){
			return "p";
		}
		else {
			return "P";	
		}
			
	}

	public static void main(String args[]) {
		// Test 1: DÃ©placements de base + manger cavalier sur le cotÃ©
		Echiquier e = new Echiquier(true); // Echiquier vide
		
		e.setPiece(0,1, new Pion(Couleur.NOIR, 0,1));    	
		e.setPiece(1,2, new Cavalier(Couleur.BLANC, 1,2));
		((Pion) e.getPiece(0,1)).calculCoups(e);
		ArrayList<Position> a = e.getPiece(0,1).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
		}
		
		System.out.println("--------------");
		//test 2 : DÃ©placements au milieu de l'Ã©chiquier
		e = new Echiquier(true);
		
		e.setPiece(3,3, new Pion(Couleur.NOIR, 3,3));
		((Pion) e.getPiece(3,3)).calculCoups(e);
		a = e.getPiece(3,3).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
		}
		
		
		System.out.println("--------------");
		//test 3 : DÃ©placements au premier rang
		e = new Echiquier(true);
		
		e.setPiece(3,6, new Pion(Couleur.BLANC, 3,6));
		((Pion) e.getPiece(3,6)).calculCoups(e);
		a = e.getPiece(3,6).getListeCoups();
		
		for (Position p : a) {
			System.out.println(p);
			
		}
	}
}