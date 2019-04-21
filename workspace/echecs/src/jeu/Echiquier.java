package jeu;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("986f6e8d-eb0d-4f6e-969c-48164f7032dd")
public class Echiquier {
    @objid ("3aec3aec-52c3-4e5c-afd9-c8dc4167e952")
    public List<Piece> piece = new ArrayList<Piece> ();

    @objid ("9016a5d3-3842-4506-969e-b89d9f3e74e2")
    public List<Joueur> joueur = new ArrayList<Joueur> ();

}
