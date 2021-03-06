package jeu;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Un échiquier contient l'instant du jeu à  un instant. Il comporte:
 * - les pièces.
 * - le droit de roque.
 * - le tour actuel.
 * - la case en passant.
 */
public class Echiquier implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5880895197797252039L;
	// Les pièces.
    private Piece[][] plateau = new Piece[8][8];
    // Le coup précédant.
    private Coup coupPrecedant;
    // Les roques possibles. Au début, tout les roques sont possibles.
    // Grand roque blanc, petit roque blanc,
    // grand roque noir, petit roque noir.
    private boolean[] roquesPossibles = {true, true, true, true};
    // Le tour actuel. Le premier tour est toujours aux blancs.
    private boolean tourAJouer = Couleur.BLANC;
    public boolean partieFinie = false;
    
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
    	this.tourAJouer = e.getJoueurActuel();
    	
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
    	return this.plateau[7-y][x];
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
    	this.plateau[7-y][x] = p;
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
    
    public void updateRoques() {
    	getRoquesPossibles()[0] = this.grandRoqueBlanc();
		getRoquesPossibles()[1] = this.petitRoqueBlanc();
		getRoquesPossibles()[2] = this.grandRoqueNoir();
		getRoquesPossibles()[3] = this.petitRoqueNoir();
		
		/*
    	for (boolean bool : getRoquesPossibles()) {
    		System.out.println(bool);
    	}*/
    }
    
    public boolean petitRoqueBlanc() {
    	
		Position posRoiBlanc = new Position(4, 0);
		Position posTourBlanc = new Position(7, 0);
		
		if (! this.containsPiece(posRoiBlanc)) {
			return false;
		}
		if (! (this.getPiece(posRoiBlanc) instanceof Roi) ) {
			return false;
		}
		if (! this.containsPiece(posTourBlanc)) {
			return false;
		}
		if (! (this.getPiece(posTourBlanc) instanceof Tour) ) {
			return false;
		}
		
		Roi r = (Roi) this.getPiece(posRoiBlanc) ;
		Tour t = (Tour) this.getPiece(posTourBlanc) ;
		
		if ( r.premierDeplacement == false || t.premierDeplacement == false ) {
			return false;
		}
		if ( this.containsPiece(5,0) || this.containsPiece(6,0) ) {
			return false;
		}
		
		return true;
    }
    
    public boolean grandRoqueBlanc() {
    	
		Position posRoiBlanc = new Position(4, 0);
		Position posTourBlanc = new Position(0, 0);
		
		if (! this.containsPiece(posRoiBlanc)) {
			return false;
		}
		if (! (this.getPiece(posRoiBlanc) instanceof Roi) ) {
			return false;
		}
		if (! this.containsPiece(posTourBlanc)) {
			return false;
		}
		if (! (this.getPiece(posTourBlanc) instanceof Tour) ) {
			return false;
		}
		
		Roi r = (Roi) this.getPiece(posRoiBlanc) ;
		Tour t = (Tour) this.getPiece(posTourBlanc) ;
		
		if ( r.premierDeplacement == false || t.premierDeplacement == false ) {
			return false;
		}
		if ( this.containsPiece(3,0) || this.containsPiece(2,0) || this.containsPiece(1,0) ) {
			return false;
		}
		
		return true;
    }
    
    public boolean petitRoqueNoir() {
    	
		Position posRoiNoir = new Position(4, 7);
		Position posTourNoir = new Position(7, 7);
		
		if (! this.containsPiece(posRoiNoir)) {
			return false;
		}
		if (! (this.getPiece(posRoiNoir) instanceof Roi) ) {
			return false;
		}
		if (! this.containsPiece(posTourNoir)) {
			return false;
		}
		if (! (this.getPiece(posTourNoir) instanceof Tour) ) {
			return false;
		}
		
		Roi r = (Roi) this.getPiece(posRoiNoir) ;
		Tour t = (Tour) this.getPiece(posTourNoir) ;
		
		if ( r.premierDeplacement == false || t.premierDeplacement == false ) {
			return false;
		}
		if ( this.containsPiece(5,7) || this.containsPiece(6,7) ) {
			return false;
		}
		
		return true;
    }
    
    public boolean grandRoqueNoir() {
    	
		Position posRoiNoir = new Position(4, 7);
		Position posTourNoir = new Position(0, 7);
		
		if (! this.containsPiece(posRoiNoir)) {
			return false;
		}
		if (! (this.getPiece(posRoiNoir) instanceof Roi) ) {
			return false;
		}
		if (! this.containsPiece(posTourNoir)) {
			return false;
		}
		if (! (this.getPiece(posTourNoir) instanceof Tour) ) {
			return false;
		}
		
		Roi r = (Roi) this.getPiece(posRoiNoir) ;
		Tour t = (Tour) this.getPiece(posTourNoir) ;
		
		if ( r.premierDeplacement == false || t.premierDeplacement == false ) {
			return false;
		}
		if ( this.containsPiece(3,7) || this.containsPiece(2,7) || this.containsPiece(1,7) ) {
			return false;
		}
		
		return true;
    }

    public void afficher() {
    	Piece[][] plateau = this.getPlateau();
    	
    	String s = "";
    	
    	// Première ligne : les chiffres
    	s += "  a b c d e f g h\n";
    	
    	// Affichage dans le sens inverse
    	for(int i=0; i<plateau.length ;i++) {
    		// Numéro de ligne
			s += 7 - (i-1) + " ";
    		
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
	    				s += plateau[i][j].affiche()+ "|";
	    			} else {
						s += " |";
					}
    			}
    		}
    		s = s+ "\n";
    		s += "  -+-+-+-+-+-+-+-\n";
    	}
    	
    	System.out.println(s);
    }
    
    public Echiquier promotion(Coup c, int typeP) {
    	Position positionD = c.pos1;
    	Position positionA = c.pos2;
    	
    	Echiquier echiquierDeplacement = new Echiquier(this);
    	Piece piecePromue = null;
    	// Déterminer la pièce promue
    	if (typeP == 1) {
    		piecePromue = new Cavalier(this.getJoueurActuel(), positionA.x, positionA.y );
    	} else if (typeP == 2) {
    		piecePromue = new Fou(this.getJoueurActuel(), positionA.x, positionA.y );
    	} else if (typeP == 3) {
    		piecePromue = new Tour(this.getJoueurActuel(), positionA.x, positionA.y );
    	} else if (typeP == 4) {
    		piecePromue = new Reine(this.getJoueurActuel(), positionA.x, positionA.y );
    	}
    	
    	// Prendre la pièce et la supprimer..
    	echiquierDeplacement.prendrePiece(positionD);
    	// et mettre la nouvelle piece à la position d'arrivéee
    	echiquierDeplacement.setPiece(positionA, piecePromue); // Association bidirectionnele: Echiquier -> Piece..
    	// Piece -> Echiquier est fait car on vient de créer la pièce
    	
    	// Dans l'ancien échiquier, stocker le coup qui va suivre.
    	// On pourra ainsi obtenir sa notation sous n'importe quelle forme.
    	// Dans Partie, on aura ainsi:
    	//
    	//   E0 (coup vide) -> E1 (Coup 1) -> ... -> EN (coup n)
    	// De l'échiquier E0, on peut utiliser Coup 1 pour passer à E1.
    	echiquierDeplacement.setCoupPrecedant(c);
    	
    	return echiquierDeplacement;
    }
    
    public boolean getJoueurActuel() {
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

	public boolean verifierEchec() {
		// Recherche du roi
		Roi r;
		Position posRoi = null;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.containsPiece(i, j)) {
					Piece p = this.getPiece(i, j);
					if (p.getCouleur() == this.getJoueurActuel() 
							&& p instanceof Roi) {
						r = (Roi) p;
						posRoi = r.getPosition();
					}
				}
			}
		}
		
		boolean couleurEnnemie = ! this.getJoueurActuel();
		
		// Vérifier qu'aucune pièce ennemie ne possède le roi dans ses déplacement possibles
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.containsPiece(i, j)) {
					Piece p = this.getPiece(i, j); // Obtenir la pi�ce..
					
					if (p.getCouleur() == couleurEnnemie) {
						ArrayList<Position> a = p.getListeCoups();
						
						if (a.contains(posRoi)) {
							return true;
						}
					}
				}
			}
		}
		
		// Aucune pièce ne possède le roi dans ses déplacements.
		return false;
	}

	public boolean deplacementPossible() {
		// Pour chaque pièce alliée, 
		// Vérifier qu'elle peut se déplacer
		
		boolean couleurAlliee = this.getJoueurActuel();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.containsPiece(i, j)) {
					Piece p = this.getPiece(i, j); // Obtenir la pi�ce..
					
					if (p.getCouleur() == couleurAlliee) {
						if (p.getListeCoups().size() > 0 ) { // Si la piece peut jouer..
							return true; // alors on a au moins un déplacement à notre disposition
						}
					}
				}
			}
		}
		
		// Aucune possibilité ne permet de nous déplacer: c'est un pat.
		return false;
	}

	public boolean deplacementPossibleHorsEchec() {
		// Pour chaque pièce alliée, vérifier qu'un de ses déplacements ne metterais
		// pas le roi hors échec (y inclus le roi lui-même
		// En effet, une tour peut bloquer l'échec du roi, par exemple
		
		boolean couleurAlliee = this.getJoueurActuel();
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (this.containsPiece(i, j)) {
					Piece p = this.getPiece(i, j); // Obtenir la pi�ce..
					Position posInitiale = p.getPosition();
					
					if (p.getCouleur() == couleurAlliee) {
						if (p.getListeCoups().size() > 0 ) { // Si la piece peut jouer..
							ArrayList<Position> a = p.getListeCoups();
							
							// Pour chacun des coups possibles...
							// .. on regarde un état en avant, et voit si cet état est encore
							// en échec
							for (Position posnPossible : a) {
								Coup c = new Coup(p.getPosition(), posnPossible);
								Echiquier etatPossible = this.deplacement(c);
								
								// Au moins une possibilité nous sort d'échec.
								if (etatPossible.verifierEchec() == false) {
									return true;
								}
								
								this.getPiece(posInitiale).setPosition(posInitiale);
							}
						}
					}
				}
			}
		}
		
		// Aucune possibilité ne nous sort d'échec. On est en échec et mat.
		return false;
	}
}