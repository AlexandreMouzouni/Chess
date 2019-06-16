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
			
			Echiquier newEtat = e.deplacement(coup);
			// Changer de tour de joueur.
			newEtat.setTourAJouer( ! e.getTourAJouer() );
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
				System.out.print("Premier coup : ");
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
	
	public static void main(String[] args) {
		Jeu j = new Jeu();
		j.run();
	}
}
