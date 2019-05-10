package jeu;

import java.util.ArrayList;
import java.util.List;
import com.modeliosoft.modelio.javadesigner.annotations.objid;

@objid ("986f6e8d-eb0d-4f6e-969c-48164f7032dd")

/*
 * Un échiquier contient l'instant du jeu à un instant. Il comporte:
 * - les pièces.
 * - le droit de roque.
 * - le tour actuel.
 * - la case en passant.
 * 
 * Les autres règles relative aux tours, comme la triple répétition 
 */
public class Echiquier {
	// Les pièces.
    private Piece[] plateau = new Piece[64];
    // Les roques possibles. Au début, tout les roques sont possibles.
    private boolean[] roquesPossibles = {true, true, true, true};
    // Le tour actuel. Le premier tour est toujours aux blancs.
    private boolean tourAJouer = Couleur.BLANC;
    private int[] enPassant = new int[2]; // Position en passant
    
    public Echiquier() {
    	//init();
    }
    
    // Constructeur vide (de test).
    protected Echiquier(boolean test) {}
    
    private Piece[] getPlateau() {
    	return this.plateau;
    }
    
    public Piece getPiece(Position pos) {
    	return this.getPiece(pos.x, pos.y);
    }
    
    public Piece getPiece(int ligne, int colonne) {
    	return this.plateau[ Position.tradPosition(ligne, colonne) ];
    }
    
    public boolean containsPiece(Position pos) {
    	return this.containsPiece(pos.x, pos.y);
    }
    
    public boolean containsPiece(int ligne, int colonne) {
    	if (this.getPiece(ligne, colonne) == null) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public void setPiece(Position pos, Piece p) {
    	this.setPiece(pos.x, pos.y, p);
    }
    
    public void setPiece(int ligne, int colonne, Piece p) {
    	this.plateau[ Position.tradPosition(ligne, colonne) ] = p;
    }
    /*
     * Initialise l'échiquer avec un plateau. La position de départ est toujours la même.
     * 
     *   0 1 2 3 4 5 6 7
     * 0 ♜ ♞ ♝ ♚ ♛ ♝ ♞ ♜
     * 1 ♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟
     * 2
     * 3
     * 4
     * 5
     * 6 ♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙
     * 7 ♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖
     */
    public void init() {
    	return;
    	//this.setPiece(0, 0, new Tour);
    }
    public void afficher() {
    	Piece[] plateau = this.getPlateau();
    	
    	String s = "";
    	String substring = "";
    	
    	for (int i = 0; i < 64; i++) {
    		substring += plateau[i] + " ";
    		
    		if ((i + 1) % 8 == 0 || i == 64) {
    			s += substring + "|" + "\n";
    			substring = "";
    		}
    	}
    	System.out.println(s);
    }
    //public Echiquier deplacement(int xDepart, int yDepart, int xArrivee, int yArrivee) {}
    
    public static void main(String[] args) {
    	Echiquier e = new Echiquier();
    	this.setPiece(1,1, new Fou());
    	e.afficher();
    }
}