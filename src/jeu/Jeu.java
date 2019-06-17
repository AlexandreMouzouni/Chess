package jeu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Jeu {
	private Partie partie;
	private Echiquier e;
	private int gagnant;
	
	public Jeu() {  
		this.partie = new Partie();
	}
	
	/** Boucle principale.
	 * 
	 */
	public void menu() {
		this.initialiser();
		
		while (true) {
			String s = this.demandeMenu();
			this.run(s);
		}
	}
	
	public String demandeMenu() {
		boolean sortie = false; 
		while (! sortie) {
			System.out.println("Entrez [1: Sauvegarder], [2: Charger], [3: Undo], [4: Redo] ou autre pour continuer");
			Scanner sc = new Scanner(System.in);
			
			String s = sc.nextLine();
					
			try {
				int i = Integer.parseInt(s);
				
				if (i == 1) {
					this.sauvergarder();
				} else if (i == 2) {
					this.charger();
				} else if (i == 3) {
					boolean r = this.partie.undo();
					if (r == false) {
						System.out.println("On ne peut pas revenir plus que le coup initial!");
					}
				} else if (i == 4) {
					boolean r = this.partie.redo();
					if (r == false) {
						System.out.println("Aucun coup auquel revenir!");
					}
				} else {
					sortie = true;
					return s;
				}
			} catch (NumberFormatException e) {
				sortie = true;
				return s;
			}
		}
		return "";
	}
	
	/**
	 * Initialisation du jeu.
	 */
	public void initialiser() {
		// Le constructeur Echiquier g�n�re un nouvel �chiquier 
		// (l'état de base) avec toutes les pi�ces plac�es.
		Echiquier etatInitial = new Echiquier();
		partie.addEtat(etatInitial);
	}
	
	/**
	 * Itération de jeu.
	 */
	public void run(String s) {
		// Obtenir l'état actuel du jeu
		this.e = partie.getDernierEtat();
		
		if (e.partieFinie) {
			e.afficher();
			partie.afficherHistoriqueNotationI();
			
			System.out.println("La partie est finie");
			return;
		}
		
		/* Affichage. */
		e.afficher();
		partie.afficherHistoriqueNotationI();
		partie.afficherTour();
		
		/* Calcul des coups des pièces */
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
		
		/* Vérification Echec, Echec et Mat, Pat */
		if (e.verifierEchec()) { // Est-ce que une pièce adverse possède le roi dans son chemin?
			if ( ! (e.deplacementPossibleHorsEchec() ) ) {// Y'a-t-il un déplacement mettant hors échec?
				// Si non, c'est échec et mat
				gagnant = this.getGagnant(); // Le gagnant est le joueur adverse
				e.partieFinie = true;
				this.annoncerWinneur(gagnant);
			} else {
			// Si oui, c'est échec et on l'annonce
				this.annoncerEchec();
			}
		}
			
		if (! e.deplacementPossible()) {
			// Si il n'y a aucun déplacement possible et qu'on est pas en échec, c'est pat
			gagnant = 0;
			e.partieFinie = true;
			this.annoncerWinneur(gagnant);
		}
		
			
		/* Avancement d'un coup */
		if (! e.partieFinie) {
			Coup coup = this.demanderCoup(s);

			Echiquier newEtat;
			
			// Cas spécial: le roque fait deux déplacements en un coup.
			int roque = this.testRoque(coup);
			
			// Cas spécial: la promotion
			if (this.checkPromotion(coup)) { /* Promotion */
				int typePiece = this.demanderPiecePromotion();
				
				newEtat = e.promotion(coup, typePiece);
				
			} else if (roque != 0) { /* Roque */
				System.out.println("coup roque");
				System.out.println(coup);
				newEtat = this.roque(roque, coup); // Au niveau de la partie car on crée deux coups.
				
			} else { /* Coup normal */
				newEtat = e.deplacement(coup);
			}
			
			
			/* Changer de tour de joueur. */
			newEtat.setTourAJouer( ! e.getJoueurActuel() );
			// L'état actuel devient le nouvel état (tour de boucle suivant.)
			partie.addEtat(newEtat);
			
			// Vider les coups de chacune des pièces, pour pas de duplication
			// On met ce code ici car certain calculs de coups ont besoin de l'état entier du jeu,
			// comme le coup précédent
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
	public Coup demanderCoup(String entree) {
		boolean coupValide = false;
		Coup c = null;
		
		while (! coupValide) {
			Scanner sc = new Scanner(System.in);
			
			String s = entree; // l'entrée antérieure faite dans le menu
			
			boolean posCorrecte;
			Position position1 = null;
			Position position2 = null;
			
			System.out.print("Premiere pos : ");
			s = sc.nextLine();
			posCorrecte = validationPosition(s);
			if (posCorrecte) {
				position1 = traduirePosition(s);
			}
			
			System.out.print("Deuxieme pos : ");
			s = sc.nextLine();
			posCorrecte = validationPosition(s);
			if (posCorrecte) {
				position2 = traduirePosition(s);
			}
				
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
	
	public boolean checkPromotion(Coup c) {
		Position p1 = c.pos1;
		Position p2 = c.pos2;
		
		Piece p = e.getPiece(p1);
		
		if (! (p instanceof Pion)) {
			return false;
		}
		
		if (p2.y == 7 && p.getCouleur() == Couleur.BLANC) {
			return true;
		} else if (p2.y == 0 && p.getCouleur() == Couleur.NOIR) {
			return true;
		} else {
			return false;
		}
	}
	
	public int demanderPiecePromotion() {
		System.out.println("Promouvoir en [tapez le chiffre]:");
		System.out.println("Cavalier (1)");
		System.out.println("Fou (2)");
		System.out.println("Tour (3)");
		System.out.println("Reine (4)");
		
		Scanner sc = new Scanner(System.in);
		
		while (true) {
			String s = sc.nextLine();
			
			if (s.length() == 1) {
				int num = Integer.parseInt(s);
						
				if (num > 0 && num <= 4) {
					return num;
				}
			}
		}
	}
	
	public void annoncerEchec() {
		if (e.getJoueurActuel() == Couleur.BLANC) {
			System.out.println("Le joueur blanc est en échec");
		} else {
			System.out.println("Le joueur noir est en échec");
		}
	}
	
	public int getGagnant() {
		if (e.getJoueurActuel() == Couleur.BLANC) {
			return -1;
		} else {
			return 1;
		}
	}
	
	public void annoncerWinneur(int num) {
		// Doit renvoyer le joueur inverse
		if (num == -1) { // -1 : blanc
			System.out.println("Le winneur est le joueur noir");
		} else if (num == 0) {
			System.out.println("Il y a pat: égalité");
		} else if (num == 1) { // 1 : noir
			System.out.println("Le winneur est le joueur noir");
		}
	}
	
	public void sauvergarder() {
		try {
			System.out.println("Nom du fichier ou sauvegarder?");
			Scanner sc = new Scanner(System.in);
			String nomFichier = sc.nextLine();
			
			File fichier = new File(nomFichier);
	
			 // Flux
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichier));
					
			 // Prise de l'objet
			Partie p = this.partie;
	
			 // Sérialization de l'objet
			oos.writeObject(p);
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void charger() {
		try {
			System.out.println("Nom du fichier a charger?");
			Scanner sc = new Scanner(System.in);
			String nomFichier = sc.nextLine();
			
			File fichier = new File(nomFichier);
	
			 // Flux
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichier));
					
			 // Désérialization de l'objet
			Partie p = (Partie) ois.readObject();
			
			// Stockage dans la classe
			this.partie = p;
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Jeu j = new Jeu();
		j.menu();
	}
}
