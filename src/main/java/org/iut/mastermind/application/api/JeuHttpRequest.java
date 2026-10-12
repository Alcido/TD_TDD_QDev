package org.iut.mastermind.application.api;


import org.iut.mastermind.domain.partie.Joueur;

public record JeuHttpRequest(Joueur joueur, String essai) {
}
