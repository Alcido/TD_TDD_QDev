package org.iut.mastermind.application.api;

import org.iut.mastermind.domain.Mastermind;
import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MastermindEndpoint {

    private final Mastermind mastermind;

    public MastermindEndpoint(Mastermind mastermind) {
        this.mastermind = mastermind;
    }

    @PostMapping("/debut")
    public ResponseEntity<Void> debutPartie(@RequestBody Joueur joueur) {
        boolean isOk = mastermind.nouvellePartie(joueur);
        HttpStatus status = isOk ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(status).build();
    }

    @PostMapping("/essai")
    public ResponseEntity<JeuHttpResponse> devineMot(@RequestBody JeuHttpRequest jhr) {
        ResultatPartie result = mastermind.evaluation(jhr.joueur(), jhr.essai());
        if (result.isError()) {
            return ResponseEntity.internalServerError().build();
        }
        JeuHttpResponse httpResponse = new EssaiHttpResponseMapper().from(result);
        return ResponseEntity.ok(httpResponse);
    }
}
