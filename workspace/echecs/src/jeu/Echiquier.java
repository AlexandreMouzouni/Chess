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
    private Piece[][] plateau = new Piece[8][8];
    // Les roques possibles. Au début, tout les roques sont possibles.
    private boolean[] roquesPossibles = {true, true, true, true};
    // Le tour actuel. Le premier tour est toujours aux blancs.
    private boolean tourAJouer = Couleur.BLANC;
	private Position enPassant; // Position en passant
    
	public Echiquier() {
    	init();
    }
    
    /**
     * Constructeur par copie.
     * @param echiquier L'échiquier a copier.
     */
    public E	chiquier(Echiquier e) {
    	Piece[][] newPlateau = new Piece[8][8];
    	boolean[] newRoquesPossibles = new boolean[4];
    	
    	this.tourAJouer = e.isTourAJouer();
    	this.enPassant = new Position(e.getEnPassant());
    	
    	for 
    }
    
    public boolean[] getRoquesPossibles() {
		return roquesPossibles;
	}

	public void setRoquesPossibles(boolean[] roquesPossibles) {
		this.roquesPossibles = roquesPossibles;
	}

	public void setPlateau(Piece[][] plateau) {
		this.plateau = plateau;
	}

	// Echiquier vide
    // A UTILISER UNIQUEMENT POUR LES TESTS (unitaires pour les pièces)
    protected Echiquier(boolean test) {}
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
    setPiece(0,0, new Tour(Couleur.NOIR, 0,0));
    setPiece(1,0, new Cavalier(Couleur.NOIR, 1,0));
    setPiece(2,0, new Fou(Couleur.NOIR, 2,0));
    setPiece(3,0, new Roi(Couleur.NOIR, 3,0));
    setPiece(4,0, new Reine(Couleur.NOIR, 4,0));
    setPiece(5,0, new Fou(Couleur.NOIR, 5,0));
    setPiece(6,0, new Cavalier(Couleur.NOIR, 6,0));
    setPiece(7,0, new Tour(Couleur.NOIR, 7,0));
    
    	for (int i =0; i<8;i++) {
    		setPiece(i,1, new Pion(Couleur.NOIR, i,1));
    	}
    	
    	setPiece(0,7, new Tour(Couleur.BLANC, 0,7));
        setPiece(1,7, new Cavalier(Couleur.BLANC, 1,7));
        setPiece(2,7, new Fou(Couleur.BLANC, 2,7));
        setPiece(3,7, new Roi(Couleur.BLANC, 3,7));
        setPiece(4,7, new Reine(Couleur.BLANC, 4,7));
        setPiece(5,7, new Fou(Couleur.BLANC,5,7));
        setPiece(6,7, new Cavalier(Couleur.BLANC, 6,7));
        setPiece(7,7, new Tour(Couleur.BLANC, 7,7));
        
        for (int i =0; i<8;i++) {
    		setPiece(i,6, new Pion(Couleur.BLANC, i,6));
    	}
    	//this.setPiece(0, 0, new Tour);
    }
    
    public Piece[][] getPlateau() {
    	return this.plateau;
    }
    
    public Piece getPiece(Position pos) {
    	return this.getPiece(pos.x, pos.y);
    }
    
    public Piece getPiece(int x, int y) {
    	// Représentation matricielle: les tableaux sont orientés de l'autre sens
    	//, cad on a un tableau composé de tableaux.
    	// [ [0, 1, 2, 3, 4, 5, 6, 7]
    	//   [0, 1, 2, 3, 4, 5, 6, 7] ...
    	//   ...
    	// ..[0, 1, 2, 3, 4, 5, 6, 7] ]
    	// En matricielle, quand on fait tab[0][1] , on accède à la case a x = 1 et y = 0.
    	// Les nombres sont donc dans le sens inversés.
    	return this.plateau[y][x];
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
    
    public void setPiece(int x, int y, Piece p) {
    	this.plateau[y][x] = p;
    }
    
    public Piece prendrePiece(Position pos) {
    	Piece p = this.getPiece(pos);
    	this.setPiece(pos, null);
    	return p;
    }
    
    /**
     * Fait un déplacement avec un coup.
     */
    public boolean deplacementValide(Coup c) {
    	Position positionDepart = c.pos1;
    	Position positionArrivee = c.pos2;
    	
    	// Si ca ne contient pas de pièce, déplacement impossible
    	if ( ! this.containsPiece(positionDepart)) {
    		return false;
    	}
    	
    	// On ne peut pas capturer une pièce de notre couleur
    	if ( this.containsPiece(positionDepart)
    			&& this.getPiece(positionDepart).getCouleur() == this.getPiece(positionArrivee).getCouleur() ) {
    		return false;
    	}
    	
    	return true;
    }
    
    /**
     * Fait un déplacement avec un coup.
     */
    public Echiquier deplacement(Coup c) {
    	Position positionDepart = c.pos1;
    	Position positionArrivee = c.pos2;
    	
    	Echiquier echiquierDeplacement = new Echiquier(this);
    	
    	// Prendre la pièce et la supprimer..
    	Piece pieceADeplacer = echiquierDeplacement.prendrePiece(positionDepart);
    	// et la mettre à la position d'arrivée
    	echiquierDeplacement.setPiece(positionArrivee, pieceADeplacer);
    	
    	return echiquierDeplacement;
    }

    public String afficher() {
    	Piece[][] plateau = this.getPlateau();
    	
    	String s = "";
    	
    	for(int i=0; i<plateau.length;i++) {
    		for (int j=0; j<plateau[i].length;j++) {
    			
    			if (plateau[i].length -1 ==j) {
    				if (plateau[i][j] != null) {
    					s +=  plateau[i][j].affiche();
    				} else {
    					s += " ";
    				}
    			}
    			else {
    				if (plateau[i][j] != null) {
	    				s +=  plateau[i][j].affiche()+" ";
	    			} else {
						s += " ";
					}
    			}
    		}
    		s = s+ "\n";
    	}
    	return s;
    }
    
    public boolean isTourAJouer() {
		return tourAJouer;
	}

	public void setTourAJouer(boolean tourAJouer) {
		this.tourAJouer = tourAJouer;
	}
    
    public Position getEnPassant() {
		return enPassant;
	}

	public void setEnPassant(Position enPassant) {
		this.enPassant = enPassant;
	}

    
    public static void main(String[] args) {
    	Echiquier e = new Echiquier();
    	System.out.println(e.afficher());
    }
}