package org.iut.mastermind.domain.proposition;

import java.util.*;

public class Reponse {
    private final String motSecret;
    private final List<Lettre> resultat = new ArrayList<>();
    private int position;

    public Reponse(String mot) {
        this.motSecret = mot;
    }

    // on récupère la lettre à la position spécifiée en paramètre dans le résultat
    public Lettre lettre(int position) {
        return resultat.get(position);
    }

    // on construit le résultat en analysant chaque lettre
    // du mot proposé
    public void compare(String essai) {
        if (essai.length() != motSecret.length()) throw new IllegalArgumentException("Mot trop long");
        for (int i = 0; i < motSecret.length(); i++) {
            resultat.add(Lettre.INCORRECTE);
        }
        for (int i = 0; i < motSecret.length(); i++) {
            if (essai.charAt(i) == motSecret.charAt(i)) resultat.set(i, Lettre.PLACEE);
        }
        Map<Character, Integer> dispo = lettresDisponibles();
        for (int i = 0; i < motSecret.length(); i++) {
            char c = essai.charAt(i);
            if (resultat.get(i) != Lettre.PLACEE && dispo.getOrDefault(c, 0) > 0) {
                resultat.set(i, Lettre.NON_PLACEE);
                dispo.merge(c, -1, Integer::sum);
            }
        }
    }

    private Map<Character, Integer> lettresDisponibles() {
        Map<Character, Integer> dispo = new HashMap<>();
        for (int i = 0; i < motSecret.length(); i++) {
            if (resultat.get(i) != Lettre.PLACEE) {
                dispo.merge(motSecret.charAt(i), 1, Integer::sum);
            }
        }
        return dispo;
    }

    // vrai si toutes les lettres sont placées
    public boolean lettresToutesPlacees() {
        if (resultat.isEmpty()) return false;
        return resultat.stream().allMatch(e -> e == Lettre.PLACEE);
    }

	// méthode obligatoire - à ne pas supprimer
    public List<Lettre> lettresResultat() {
        return Collections.unmodifiableList(resultat);
    }

    // renvoie le statut du caractère (incorrect, mal placé, placé)
    private Lettre evaluationCaractere(char carCourant) {
        return null;
    }

    // vrai si le caractère est présent dans le mot secret
    private boolean estPresent(char carCourant) {
        return false;
    }

    // vrai si le caractère est placé dans le mot secret
    private boolean estPlace(char carCourant) {
        return false;
    }
}
