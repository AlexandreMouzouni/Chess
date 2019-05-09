package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("3de2af4f-e6b9-4420-b4e3-bbf6de074a0a")
public abstract class Piece {
    @objid ("e342f84c-ebae-47a0-b0a4-6dced72f2f0c")
    private final Couleur couleur;
    private int y;
    private int x;

    @objid ("caf8519b-e420-47eb-930a-5eee232c388b")
    public abstract boolean coupValide(int unX, int unY, Echiquier echiquier);

    public Piece(int unX, int unY, Couleur uneCouleur) {
    	this.x = unX;
    	this.y = unY;
    	this.couleur = uneCouleur;
    }
    
    public int getX() {
    	return this.x;
    }
    
    public void setX(int unX) {
    	this.x = unX;
    }
    
    public int getY() {
    	return this.y;
    }
    
    public void setY(int unY) {
    	this.y = unY;
    }
    
    @objid ("b3cc3806-d2b9-4942-be07-1c12f0fad0f3")
    /* 
     * Retourne la pièce mangée.
     */
    public Piece deplacement(int unX, int unY) {
    	if (this.x == unX && this.y == unY) {
    		return null;
    	}
    	//utilisation de coupValide...
		return null;
    }

    @objid ("824d4d6d-8caa-417a-a665-0b23cb5fd821")
    public Couleur getCouleur () {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.couleur ;
    }

    public void setPosition(int unX, int unY) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.x = unX;
        this.y = unY;
    }

}
