package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("ab15064c-a9c6-4b11-8c9a-b3b59cedfe29")
public class Joueur {
    @objid ("3e632699-2af9-4e19-b3ba-d6f1aa7e3900")
    private boolean couleur;
    private Echiquier echiquier;
    
    public Joueur(boolean uneCouleur) {
    	this.couleur = uneCouleur;
    }
    
    /*
     * Vérification que le coup soit correct. Si l'entrée de l'utilisateur est incorrecte,
     * on retourne false.
     */
    public void deplacement(Position posDepart, Position posArrivee) {
    	
    }
}
