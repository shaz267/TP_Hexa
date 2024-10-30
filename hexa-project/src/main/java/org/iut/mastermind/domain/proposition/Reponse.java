package main.java.org.iut.mastermind.domain.proposition;

import java.util.ArrayList;
import java.util.List;
import static java.util.Collections.unmodifiableList;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private int position;

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // Retourne la lettre à la position donnée dans la liste des résultats
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    /**
     * Analyse chaque caractère du mot proposé et évalue sa présence
     * et son positionnement par rapport au mot secret. Les résultats
     * sont ajoutés à la liste 'resultat'.
     * @param essai Mot proposé par le joueur.
     */
    public void compare(String essai) {
        essai.chars().forEach(c -> {
            resultat.add(evaluationCaractere((char) c));
            position++;
        });
    }

    /**
     * Vérifie si toutes les lettres du mot secret sont correctement placées
     * dans la liste des résultats et qu'il n'y a aucune lettre incorrecte.
     * @return true si toutes les lettres sont placées correctement, sinon false.
     */
    public boolean lettresToutesPlacees() {
        position = 0;
        return motSecret.chars().allMatch(c -> {
            boolean isPlaced = estPlace((char) c);
            position++;
            return isPlaced;
        }) && resultat.stream().noneMatch(lettre -> lettre == Lettre.INCORRECTE);
    }

    // Retourne la liste non modifiable des résultats pour chaque lettre analysée
    public List<Lettre> lettresResultat() {
        return unmodifiableList(resultat);
    }

    /**
     * Évalue le statut d'un caractère par rapport au mot secret :
     * - PLACEE : lettre à la bonne position
     * - NON_PLACEE : lettre présente mais à une autre position
     * - INCORRECTE : lettre absente du mot secret
     * @param carCourant Caractère à évaluer
     * @return Lettre correspondant au statut du caractère
     */
    private Lettre evaluationCaractere(char carCourant) {
        return estPresent(carCourant) ?
                (estPlace(carCourant) ? Lettre.PLACEE : Lettre.NON_PLACEE) :
                Lettre.INCORRECTE;
    }

    // Vérifie si le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        return motSecret.chars().anyMatch(c -> c == carCourant);
    }

    // Vérifie si le caractère est à la bonne position dans le mot secret
    private boolean estPlace(char carCourant) {
        return motSecret.charAt(position) == carCourant;
    }
}