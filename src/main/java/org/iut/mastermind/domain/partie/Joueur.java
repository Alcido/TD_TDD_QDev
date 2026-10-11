package org.iut.mastermind.domain.partie;

public record Joueur(String nom) {

    public String getNom() {
        return this.nom;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Joueur j)) return false;
        return j.getNom().equals(this.getNom());
    }

}
