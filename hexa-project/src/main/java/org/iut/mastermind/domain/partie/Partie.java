package main.java.org.iut.mastermind.domain.partie;

import main.java.org.iut.mastermind.domain.proposition.MotSecret;
import main.java.org.iut.mastermind.domain.proposition.Reponse;

public class Partie {
    private static final int NB_ESSAIS_MAX = 5;
    private final Joueur joueur;
    private final String motADeviner;
    private int nbEssais;
    private boolean partieTerminee;

    public Partie(Joueur joueur, String motADeviner, int nbEssais, boolean partieTerminee) {
        this.joueur = joueur;
        this.motADeviner = motADeviner;
        this.nbEssais = nbEssais;
        this.partieTerminee = partieTerminee;
    }

    public static Partie create(Joueur joueur, String motADeviner) {
        return new Partie(joueur, motADeviner, 0, false);
    }

    public static Partie create(Joueur joueur, String motADeviner, int nbEssais) {
        return new Partie(joueur, motADeviner, nbEssais, false);
    }

    // Retourne le joueur associé à la partie
    public Joueur getJoueur() {
        return joueur;
    }

    // Retourne le nombre d'essais effectués jusqu'à présent
    public int getNbEssais() {
        return nbEssais;
    }

    // Retourne le mot secret que le joueur doit deviner
    public String getMot() {
        return motADeviner;
    }

    /**
     * Effectue un tour de jeu en comparant la proposition avec le mot secret.
     * Incrémente le nombre d'essais et vérifie si la partie doit se terminer :
     * - Si toutes les lettres sont correctement placées, marque la partie comme terminée.
     * - Si le nombre d'essais maximum est atteint, la partie est également terminée.
     *
     * @param motPropose Le mot proposé par le joueur.
     * @return La réponse indiquant les lettres correctes et leur position.
     * @throws IllegalStateException si la partie est déjà terminée.
     */
    public Reponse tourDeJeu(String motPropose) {
        if (partieTerminee) {
            throw new IllegalStateException("La partie est déjà terminée");
        }
        nbEssais++;
        MotSecret motSecret = new MotSecret(motADeviner);
        Reponse reponse = motSecret.compareProposition(motPropose);
        if (reponse.lettresToutesPlacees()) {
            partieTerminee = true;
        }
        verifieNbEssais();
        return reponse;
    }

    // Vérifie si le nombre d'essais maximum est atteint. Si oui, termine la partie.
    private void verifieNbEssais() {
        if (nbEssais >= NB_ESSAIS_MAX) {
            partieTerminee = true;
        }
    }

    // Indique si la partie est terminée
    public boolean isTerminee() {
        return partieTerminee;
    }

    // Marque explicitement la partie comme terminée
    void done() {
        this.partieTerminee = true;
    }

}