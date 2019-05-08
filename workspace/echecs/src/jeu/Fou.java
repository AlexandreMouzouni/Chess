package jeu;

import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("67ce68ce-210f-46e8-bfe1-320ea8b46f5b")
public class Fou extends Piece {
    @objid ("ef00cb71-13e6-4828-89d1-0193e67a062b")
    public boolean coupValide(int xDepart, int yDepart, int horizontal, int vertical, Plateau plateau) {
        // Si les deux valeurs ne sont pas égales, le déplacement n'est pas diagonal
        // 1 2 n'est pas un déplacement diagonal
        if (Math.abs(horizontal) != Math.abs(vertical)) return false;
        
        // Prend la plus grande valeur
        // Le vecteur déplacement peut être -4, 4 par exemple
        // Dans ce cas, on regarde chaque case qui suit de notre position en 
        // "marchant" en diagonale
        deplacementHorizontal = 0
        deplacementVertical = 0
        for (int i = 0, i <= Math.abs(horizontal), i++) {
            // deplacementHroziontal et deplacementVertical sont des vecteurs 
            // intermédiaires
            // Si le deplacmeent est [4, -4]
            // la valeur de deplacemntHorizontal et deplacmentVertical est, successivement:
            // [1, -1]
            // [2, -2]
            // [3, -3]
            // [4, -4]
            if (horizontal < 0) {
                deplacementHorizontal--
            } else {
                deplacementHorizontal++
            }
            
            if (vertical < 0) {
                deplacementHorizontal--
            } else {
                deplacementHorizontal++
            }
            
            // Si le fou est "arrété" par une piece, alors il ne peut pas se déplacer
            // jusqu'a la fin
            if plateau.getCase(xDepart + deplacementHorizontal, yDepart + deplacementVertical).contientPiece() {
                return false
            }
        }
        
        // Fin de la boucle: le fou a pu se déplacer jusqu'a la fin
        return true
    }
}
