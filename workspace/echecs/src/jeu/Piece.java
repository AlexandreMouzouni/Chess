package jeu;

import java.util.ArrayList;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

public abstract class Piece {
    private final boolean couleur;
    private Position position;
    
    public abstract ArrayList<Position> listeCoupValide(Echiquier echiquier);

    public Piece(boolean uneCouleur, int uneLigne, int uneColonne) {
    	this(uneCouleur, new Position(uneLigne, uneColonne));
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
    public boolean getCouleur () {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.couleur ;
    }
}
