package jeu;

import java.util.Scanner;

public class Jeu {
	private Joueur[] joueurs;
	private Partie partie;
	private Echiquier e;
	
	public Jeu() {  
		this.partie = new Partie();
		// on a itinialise ici dans le constructeur car c'est unique a l'objet et nn a tous les objets
		this.joueurs = new Joueur[]{ new Joueur(Couleur.BLANC),new Joueur(Couleur.NOIR) };
	}
	
	/**
	 * Point d'entr�e.
	 */
	public void run() {
		// Menu tout ca 
		boolean partieFinie = false;
		
		// Le constructeur Echiquier g�n�re un nouvel �chiquier 
		// (l'état de base) avec toutes les pi�ces plac�es.
		Echiquier etatInitial = new Echiquier();
		partie.addEtat(etatInitial);
		
		while (! partieFinie) {
			// Obtenir l'état actuel du jeu
			this.e = partie.getDernierEtat();
			
			/* Affichage. */
			e.afficher();
			partie.afficherHistoriqueNotationI();
			partie.afficherTour();
			
			// Calcul des roques
			e.updateRoques();
			// Calculer les coups pour chacune des pi�ces
			// On met ce code ici car certain calculs de coups ont besoin de l'état entier du jeu,
			// comme le coup pr�c�dent
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.containsPiece(i, j)) {
						Piece p = e.getPiece(i, j); // Obtenir la pi�ce..
						p.calculCoups(e, partie); // et calculer ses coups
					}
				}
			}
			
			/*
			// obtenir le roi de la couleur qui correspond
			Roi r = e.getRoi(Couleur.BLANC);
			// Roi en échec?
			boolean echec = e.verifierEchec(couleur);
			// Déplacement du roi impossible?
			boolean deplacementPossible = e.deplacementRoiPossible(couleur);
			
			if (echec && (! deplacementPossible)) {
				partieFinie = true;
				gagnant = joueur;
			}
			*/
			Coup coup = this.demanderCoup();
			
			Echiquier newEtat;
			// Cas spécial: le roque fait deux déplacements en un coup.
			int roque = this.testRoque(coup);
			System.out.println(roque);
			if (roque != 0) {
				System.out.println("coup roque");
				System.out.println(coup);
				newEtat = this.roque(roque, coup);
			} else {
				newEtat = e.deplacement(coup);
			}
			
			// Changer de tour de joueur.
			//newEtat.setTourAJouer( ! e.getTourAJouer() );
			partie.addEtat(newEtat);
			
			// Vider les coups de chacune des pièces, pour pas de duplication
			// On met ce code ici car certain calculs de coups ont besoin de l'�tat entier du jeu,
			// comme le coup pr�c�dent
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (e.containsPiece(i, j)) {
						Piece p = e.getPiece(i, j); // Obtenir la pi�ce..
						p.clearListeCoups();
					}
				}
			}
		}
	}
	
	/**
	 * Demande un coup.
	 * @return Le coup entr� par l'utilisateur
	 */
	public Coup demanderCoup() {
		boolean coupValide = false;
		Coup c = null;
		
		while (! coupValide) {
			Scanner sc = new Scanner(System.in);
			
			boolean posCorrecte;
			String s = "";
			
			posCorrecte = false;
			while ( ! posCorrecte ) {
				System.out.print("Entrez une position: ");
				s = sc.nextLine();
				posCorrecte = validationPosition(s);
			}
			Position position1 = traduirePosition(s);
			
			posCorrecte = false;
			while ( ! posCorrecte ) {
				System.out.print("Deuxieme coup : ");
				s = sc.nextLine();
				posCorrecte = validationPosition(s);
			}
			Position position2 = traduirePosition(s);
			
			c = new Coup(position1, position2);
			
			if (e.deplacementValide(c)) {
				coupValide = true;
			} else {
				System.out.println("Le coup est invalide");
			}
		}

		// On retourne le d�placement si il est correct
		return c;
	}
	
	public boolean validationPosition(String s) {
		String t = s.toLowerCase();
		if (t.length() != 2) {
			return false;
		}
		int x = t.charAt(0) - 'a';
		int y = Integer.parseInt(t.substring(1, 2)) - 1;
		
		if (! Position.positionValide(new Position(x, y))) {
			return false;
		}
		
		return true;
	}
	
	public Position traduirePosition(String s) {
		String t = s.toLowerCase();
		int x = t.charAt(0) - 'a';
		int y = Integer.parseInt(t.substring(1, 2)) - 1;
		return new Position(x, y);
	}
	
	public int testRoque(Coup c) {
		Position p1 = c.pos1;
		Position p2 = c.pos2;
		
		Position posRoiBlanc = new Position(4, 0);
		Position posRoiNoir = new Position(4, 7);
		
		if (p1.equals(posRoiBlanc)) {
			// Petit roque ou grand roque?
			if (p2.equals(new Position(2, 0)) ) { 
				if (e.getRoquesPossibles()[0] == true) { // Grand roque blanc
					return 1;
				}
			} else if (p2.equals(new Position(6, 0)) ) {
				System.out.println("Roque 1" +e.getRoquesPossibles()[1]);
				if (e.getRoquesPossibles()[1] == true) { // Petit roque blanc
					return 2;
				}
			}
		} else if (p1.equals(posRoiNoir) ) {
			// Petit roque ou grand roque?
			if (p2.equals(new Position(6, 7)) )  { 
				if (e.getRoquesPossibles()[2] == true) { // Grand roque blanc
					return 3;
				}
			} else if (p2.equals(new Position(2, 7)) ) {
				if (e.getRoquesPossibles()[3] == true) { // Petit roque blanc
					return 4;
				}
			}
		}
		
		// Aucun roque possible
		return 0;
	}
	
	public Echiquier roque(int typeRoque, Coup c) {
		// 1 = grand roque blanc
		// 2 = petit roque blanc
		// 3 = grnad roque noir
		// 4 = petit roque noir
		
		if (typeRoque == 1) { // petit roque blanc
			Echiquier temp = e.deplacement(c);
			
			Coup coupTour = new Coup(
					new Position(0,0),
					new Position(3,0));
			return temp.deplacement(coupTour);
		} else if (typeRoque == 2) {  // grand roque blanc
			Echiquier temp = e.deplacement(c);
			
			Coup coupTour = new Coup(
					new Position(7,0),
					new Position(5,0));
			return temp.deplacement(coupTour);
		} else if (typeRoque == 3) {
			Echiquier temp = e.deplacement(c);
			
			Coup coupTour = new Coup(
					new Position(0,7),
					new Position(3,7));
			return temp.deplacement(coupTour);
		} else if (typeRoque == 4) {
			Echiquier temp = e.deplacement(c);
			
			Coup coupTour = new Coup(
					new Position(7,7),
					new Position(5,7));
			return temp.deplacement(coupTour);
		}
		return null;
	}
	
	public static void main(String[] args) {
		Jeu j = new Jeu();
		j.run();
	}
}
