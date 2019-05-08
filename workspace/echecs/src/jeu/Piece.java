package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("3de2af4f-e6b9-4420-b4e3-bbf6de074a0a")
public abstract class Piece {
    @objid ("e342f84c-ebae-47a0-b0a4-6dced72f2f0c")
    // Pour pas d'erreurs de compilation, à enlever (mettre dans constructeur)
    private final Couleur couleur = Couleur.Blanc;

    @objid ("fb069b15-27d6-424f-b528-16449d086382")
    public Position position;

    @objid ("caf8519b-e420-47eb-930a-5eee232c388b")
    public abstract void coupValide(Position pos);

    @objid ("b3cc3806-d2b9-4942-be07-1c12f0fad0f3")
    public void deplacement(Position pos) {
    }

    @objid ("824d4d6d-8caa-417a-a665-0b23cb5fd821")
    Couleur getCouleur () {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.couleur ;
    }

    @objid ("c3070193-71f5-4853-a459-c2c2ef8de6e7")
    Position getPosition() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.position;
    }

    @objid ("ed154ffe-7c93-4a3e-81cf-273473a477df")
    void setPosition(Position value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.position = value;
    }

}