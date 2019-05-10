package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;



public class Fou  extends Piece {
	public Fou(boolean uneCouleur, int uneLigne, int uneColonne) {
		super(uneCouleur, uneLigne, uneColonne);
	}
	
	public Fou(boolean uneCouleur, Position unePosition) {
		this(uneCouleur, unePosition.x, unePosition.y);
	}
	
	public boolean estDeplace = false;

	public ArrayList<Position> listeCoupValide(Echiquier e) {
		ArrayList<Position> listeCoupPossible = new ArrayList<Position>();
		Position positionDeDepart = super.getPosition();
		Position[] vecteurPosition ={new Position(1,-1),new Position(-1,-1),new Position(1,1),new Position(-1,1)};
		
		for (int i =0 ; i<4; i++ ) {
			Position nouvellePosition = positionDeDepart;
			boolean estBloque  = false; 
			
			while (Position.positionValide(nouvellePosition) && estBloque == false ) {
				nouvellePosition = nouvellePosition.addition(vecteurPosition[i]);
				
				if (Position.positionValide(nouvellePosition)) {
					if (e.containsPiece(nouvellePosition)){
						estBloque = true;
					}
					
					listeCoupPossible.add(nouvellePosition);
				}
			}
		}
		
		return listeCoupPossible;
	}

	public static void main(String args[]) {
    	Echiquier e = new Echiquier(true); // Echiquier vide
    	
    	Position posFou = new Position(1,1);
    	
    	e.setPiece(posFou, new Fou(Couleur.BLANC, posFou));    	
    	e.setPiece(0,0, new Cavalier(Couleur.NOIR, 0,0));
    	ArrayList<Position> a = e.getPiece(posFou).listeCoupValide(e);
    	
    	for (Position p : a) {
    		System.out.println(p);
    	}
	}
}