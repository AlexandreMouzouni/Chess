package jeu;


import java.util.ArrayList;
import java.util.List;

public class Partie {
	public ArrayList<Echiquier> historique ;
	
	public Position[] deplacementDoublePion(){
		Echiquier positionDeJeuActuelle = historique.get(historique.size()-1);
		
		Piece[][] plateau = positionDeJeuActuelle.getPlateau();
				
		for(int i=0;i<plateau.length;i++) {
			for(int j =0 ; j<plateau[i].length;j++) {
				if(plateau[i][j] instanceof Pion){
					if( ((Pion) plateau[i][j]).premierRang == true) {
						Position position = new Position(i, j);
						Position[] listePositions = { position };
					}
				}
				
			}
			
		}
		return null;
	}
	
	
}