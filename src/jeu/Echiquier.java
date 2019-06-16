package jeu;

import java.util.ArrayList;

/*
 * Un échiquier contient l'instant du jeu à  un instant. Il comporte:
 * - les pièces.
 * - le droit de roque.
 * - le tour actuel.
 * - la case en passant.
 */
public class Echiquier {
	// Les pièces.
    private Piece[][] plateau = new Piece[8][8];
    // Le coup précédant.
    private Coup coupPrecedant;
    // Les roques possibles. Au début, tout les roques sont possibles.
    private boolean[] roquesPossibles = {true, true, true, true};
    // Le tour actuel. Le premier tour est toujours aux blancs.
    private boolean tourAJouer = Couleur.BLANC;
    
	public Echiquier() {
    	init();
    }
    
    /**
     * Constructeur par copie. On ne copie pas le coup.
     * @param echiquier L'échiquier a copier.
     */
    public Echiquier(Echiquier e) {
    	this.plateau = new Piece[8][8];
    	this.roquesPossibles = new boolean[4];
    	this.tourAJouer = e.getTourAJouer();
    	
    	for (int i = 0; i < e.getPlateau().length; i++) {
    		for (int j = 0; j < e.getPlateau()[i].length; j++) {
    			this.plateau[i][j] = e.getPlateau()[i][j];
    		}
    	}
    	
    	for (int i = 0; i < e.getRoquesPossibles().length; i++) {
    		this.roquesPossibles[i] = e.getRoquesPossibles()[i];
    	}
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
     * Initialise l'échiquer avec un plateau. La position de départ est toujours la màªme.
     * 
     *   0 1 2 3 4 5 6 7
     * 0 â™œ â™ž â™� â™š â™› â™� â™ž â™œ
     * 1 â™Ÿ â™Ÿ â™Ÿ â™Ÿ â™Ÿ â™Ÿ â™Ÿ â™Ÿ
     * 2
     * 3
     * 4
     * 5
     * 6 â™™ â™™ â™™ â™™ â™™ â™™ â™™ â™™
     * 7 â™– â™˜ â™— â™• â™” â™— â™˜ â™–
     */
    public void init() {
	    setPiece(0,0, new Tour(Couleur.BLANC, 0,0));
	    setPiece(1,0, new Cavalier(Couleur.BLANC, 1,0));
	    setPiece(2,0, new Fou(Couleur.BLANC, 2,0));
	    setPiece(3,0, new Reine(Couleur.BLANC, 3,0));
	    setPiece(4,0, new Roi(Couleur.BLANC, 4,0));
	    setPiece(5,0, new Fou(Couleur.BLANC, 5,0));
	    setPiece(6,0, new Cavalier(Couleur.BLANC, 6,0));
	    setPiece(7,0, new Tour(Couleur.BLANC, 7,0));
    
    	for (int i =0; i<8;i++) {
    		setPiece(i,1, new Pion(Couleur.BLANC, i,1));
    	}
    	
    	setPiece(0,7, new Tour(Couleur.NOIR, 0,7));
        setPiece(1,7, new Cavalier(Couleur.NOIR, 1,7));
        setPiece(2,7, new Fou(Couleur.NOIR, 2,7));
        setPiece(3,7, new Reine(Couleur.NOIR, 3,7));
        setPiece(4,7, new Roi(Couleur.NOIR, 4,7));
        setPiece(5,7, new Fou(Couleur.NOIR,5,7));
        setPiece(6,7, new Cavalier(Couleur.NOIR, 6,7));
        setPiece(7,7, new Tour(Couleur.NOIR, 7,7));
        
        for (int i =0; i<8;i++) {
    		setPiece(i,6, new Pion(Couleur.NOIR, i,6));
    	}
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
    	// En matricielle, quand on fait tab[0][1] , on accède à  la case a x = 1 et y = 0.
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
     * Vérifie si un déplacement est possible.
     */
    public boolean deplacementValide(Coup c) {
    	Position positionDepart = c.pos1;
    	Position positionArrivee = c.pos2;
    	
    	// Si la case ne contient pas de pièce, déplacement impossible
    	if ( ! this.containsPiece(positionDepart)) {
    		return false;
    	}
    	
    	Piece p = this.getPiece(positionDepart);
    	
    	// Est-ce que la pièce nous appartient?
    	if ( p.getCouleur() != this.tourAJouer ) {
    		return false;
    	}
    	
    	/*
		ArrayList<Position> a = p.getListeCoups();
		System.out.println("--");
		for (Position pos : a) {
			System.out.println(pos);
		}*/
    	
    	// Est-ce que le déplacement est dans la liste de déplacement de la pièce sélectionnée?
    	// Si elle n'est pas contenue dedans, le coup est impossible
    	// Chaque pièce s'occupe de calculer ses propres coups possibles
    	if ( ! p.getListeCoups().contains(positionArrivee) ) {
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
    	echiquierDeplacement.setPiece(positionArrivee, pieceADeplacer); // Association bidirectionnele: Echiquier -> Piece..
    	echiquierDeplacement.getPiece(positionArrivee).setPosition(positionArrivee); // Piece -> Echiquier
    	
    	// Dans l'ancien échiquier, stocker le coup qui va suivre.
    	// On pourra ainsi obtenir sa notation sous n'importe quelle forme.
    	// Dans Partie, on aura ainsi:
    	//
    	//   E0 (coup vide) -> E1 (Coup 1) -> ... -> EN (coup n)
    	// De l'échiquier E0, on peut utiliser Coup 1 pour passer à E1.
    	echiquierDeplacement.setCoupPrecedant(c);
    	
    	return echiquierDeplacement;
    }

    public void afficher() {
    	Piece[][] plateau = this.getPlateau();
    	
    	String s = "";
    	
    	// Première ligne : les chiffres
    	s += "  a b c d e f g h\n";
    	
    	// Affichage dans le sens inverse
    	for(int i=7; i >= 0 ;i--) {
    		// Numéro de ligne
			s += 7 - (i - 1) + " ";
    		
    		for (int j=0; j<plateau[i].length;j++) {
    			
    			if (plateau[i].length -1 ==j) {
    				if (plateau[i][j] != null) {
    					s += plateau[i][j].affiche();
    				} else {
    					s += " ";
    				}
    			}
    			else {
    				if (plateau[i][j] != null) {
	    				s += plateau[i][j].affiche()+" ";
	    			} else {
						s += " ";
					}
    			}
    		}
    		s = s+ "\n";
    	}
    	
    	System.out.println(s);
    }
    
    public boolean getTourAJouer() {
		return tourAJouer;
	}

	public void setTourAJouer(boolean tourAJouer) {
		this.tourAJouer = tourAJouer;
	}
    
    public Coup getCoupPrecedant() {
		return coupPrecedant;
	}

	private void setCoupPrecedant(Coup coupSuivant) {
		this.coupPrecedant = coupSuivant;
	}

	public static void main(String[] args) {
    	Echiquier e = new Echiquier();
    	e.afficher();
    }
}