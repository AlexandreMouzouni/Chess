package jeu;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class Piece implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2406138544679367393L;
	private final boolean couleur;
    private Position position;
    private ArrayList<Position> listeCoups = new ArrayList<Position>();
    
    public ArrayList<Position> getListeCoups() {
		return listeCoups;
	}

	public void setListeCoups(ArrayList<Position> listeCoups) {
		this.listeCoups = listeCoups;
	}
	
	public void addCoup(Position coup) {
		this.listeCoups.add(coup);
	}
	
	public void clearListeCoups() {
		this.listeCoups.clear();
	}

	public abstract void calculCoups(Echiquier e, Partie p);

    public Piece(boolean uneCouleur, int x, int y) {
    	this(uneCouleur, new Position(x, y));
    }
    
    public Piece(boolean uneCouleur, Position unePosition) {
    	this.couleur = uneCouleur;
    	this.position = unePosition;
    }
   
    public Position getPosition() {
    	return this.position;
    }
    public void setPosition(Position unePosition) {
    	this.position = unePosition;
    }

    public boolean getCouleur() {
        return this.couleur ;
    }
    
    /**
     * Cr�e un String affichable pour chaque pi�ce.
     * @return
     */
    public abstract String affiche();
}
