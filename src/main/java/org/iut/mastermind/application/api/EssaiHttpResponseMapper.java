package org.iut.mastermind.application.api;

import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Lettre;
import org.iut.mastermind.domain.proposition.Reponse;

public class EssaiHttpResponseMapper {
    JeuHttpResponse from(ResultatPartie resultatPartie) {
        Reponse reponse = resultatPartie.resultat();
        return new JeuHttpResponse(toEndpointResultsFormat(reponse),
                resultatPartie.isTermine());
    }

    private String toEndpointResultsFormat(Reponse reponse) {
        var resultat = new StringBuilder();
        reponse.lettresResultat().forEach(l -> resultat.append(convert(l)));
        return resultat.toString();
    }

    private char convert(Lettre lettre) {
        return switch(lettre) {
            case PLACEE -> 'P';
            case NON_PLACEE -> 'N';
            case INCORRECTE -> 'X';
        };
    }
}
