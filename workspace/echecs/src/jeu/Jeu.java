package jeu;

import java.util.Scanner;

public class Jeu {
	private Joueur[] joueurs;
	private Partie partie;

	
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
		// (l'�tat de base) avec toutes les pi�ces plac�es.
		Echiquier etatInitial = new Echiquier();
		partie.addEtat(etatInitial);
		
		while (! partieFinie) {
			// Obtenir l'�tat actuel du jeu
			Echiquier e = partie.getDernierEtat();
			
			// Calculer les coups pour chacune des pi�ces
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
			// Roi en �chec?
			boolean echec = e.verifierEchec(couleur);
			// D�placement du roi impossible?
			boolean deplacementPossible = e.deplacementRoiPossible(couleur);
			
			if (echec && (! deplacementPossible)) {
				partieFinie = true;
				gagnant = joueur;
			}
			*/
			Coup coup = this.demanderCoup();
			System.out.println(coup);
			
			Echiquier newEtat = e.deplacement(coup);
			partie.addEtat(newEtat);
		}
	}
	
	/**
	 * Demande un coup.
	 * @return Le coup entr� par l'utilisateur
	 */
	public Coup demanderCoup() {
		System.out.println("jouer to premier coup inchAllah tu mange le pion");
		Scanner sc = new Scanner(System.in);
		String s = sc.nextLine();
		Position position1 =validationPosition(s);
		
		System.out.println("jouer to deuxieme coup inchAllah tu mange le pion");
		Scanner sc2 = new Scanner(System.in);
		String s2 = sc2.nextLine();
		Position position2 = validationPosition(s2);

		return new Coup(position1, position2);
	}
	
	public Position validationPosition(String s) {
		String t = s.toLowerCase();
		int x = Integer.parseInt(t.substring(1, 2));
				// Caster en int retourne 97, la valeur du code ascii de a
		int y = t.charAt(0) - 'a';
		
		return new Position(x, y);
	}
	
	public static void main(String[] args) {
		Jeu j = new Jeu();
		j.run();
	}
}
