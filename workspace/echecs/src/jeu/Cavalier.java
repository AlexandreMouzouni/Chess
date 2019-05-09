package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("7d04237b-e42b-4a58-9729-9ab91afeee7c")
public class Cavalier extends Piece {
	public Cavalier(int unX, int unY, Couleur uneCouleur) {
		super(unX, unY, uneCouleur);
	}

	public boolean coupValide(int horizontal, int vertical, Echiquier echiquier) {
		return this.coupValide(horizontal, vertical);
	}
	
    @objid ("667cac35-5223-487f-a37d-c2a99a0a0cb4")
    /*
     * horizontal: déplacement horizontal
     * vertical: déplacement vertical 
     */
    public boolean coupValide(int horizontal, int vertical) {
        return ( (Math.abs(horizontal) == 2 && Math.abs(vertical) == 1) 
			|| (Math.abs(horizontal) == 1 && Math.abs(vertical) == 2) );
    }
}