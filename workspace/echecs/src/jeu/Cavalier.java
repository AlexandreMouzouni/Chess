package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("7d04237b-e42b-4a58-9729-9ab91afeee7c")
public class Cavalier extends Piece {
    @objid ("667cac35-5223-487f-a37d-c2a99a0a0cb4")
    /*
     * horizontal: déplacement horizontal
     * vertical: déplacement vertical 
     */
    public void coupValide(int horizontal, int vertical) {
        return ( (Math.abs(horizontal) == 2 && Math.abs(vertical) == 1) 
			|| (Math.abs(horizontal) == 1 && Math.abs(vertical) == 2) );
    }
}
