package jeu;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Partie {
	private ArrayList<Echiquier> historique = new ArrayList<Echiquier>();
	private Echiquier tempUndo;
	// Un joueur commence toujours en blanc
	private boolean tourAJoueur = Couleur.BLANC;
	
	public void addEtat(Echiquier e) {
		historique.add(e);
		
		// Mise a jour: l'undo ne tient plus
		this.tempUndo = null;
	}
	
	public Echiquier getEtat(int i) {
		return historique.get(i);
	}
	
	public boolean undo() {
		// On ne peut pas undo le premier coup.
		if (historique.size() == 1) {
			return false;
		}
		
		Echiquier e = historique.remove(historique.size() - 1);
		this.tempUndo = e;
		
		return true;
	}
	
	public boolean redo() {
		if (this.tempUndo == null) { // Pas de coup a revenir en avant
			return false;
		}
		
		this.addEtat(this.tempUndo); // Ajouter le coup qu'on vient d'undo..
		this.tempUndo = null; // et l'oublier
		
		return true;
	}
	
	public Echiquier getDernierEtat() {
		return this.getEtat( historique.size() - 1 );
	}
	
	public void afficherHistoriqueNotationI() {
		// Si l'historique est vide, ne rien affcher.
		if (historique.size() == 1) {
			return;
		}
		
		String s = "";
		// On ne prend pas le coup du premier échiquier;
		// il n'a pas de coup le précédant.
		for (int i = 1; i < historique.size(); i++) {
			s += i + ". " +
					historique.get(i)
					.getCoupPrecedant()
					.toNotationI();
			
			if (i != historique.size()) {
				s += "\n";
			}
		}
		
		System.out.println(s);
	}
	
	public void afficherTour() {
		Echiquier e = this.getDernierEtat();
		
		if (e.getJoueurActuel() == Couleur.BLANC ) {
			System.out.println("C'est aux blancs de jouer"); 
		} else {
			System.out.println("C'est aux noirs de jouer"); 
		}
	}

	public boolean getTourAJoueur() {
		return tourAJoueur;
	}

	public void setTourAJoueur(boolean tourAJoueur) {
		this.tourAJoueur = tourAJoueur;
	}
	
}
