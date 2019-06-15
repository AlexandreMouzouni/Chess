package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

public abstract class Piece {
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

    @objid ("824d4d6d-8caa-417a-a665-0b23cb5fd821")
    public boolean getCouleur() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.couleur ;
    }
}
