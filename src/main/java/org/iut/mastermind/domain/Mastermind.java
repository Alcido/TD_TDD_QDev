package org.iut.mastermind.domain;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import org.iut.mastermind.domain.partie.ResultatPartie;
import org.iut.mastermind.domain.proposition.Reponse;
import org.iut.mastermind.domain.tirage.MotsRepository;
import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import org.iut.mastermind.domain.tirage.ServiceTirageMot;
import java.util.Optional;

public class Mastermind {
    private final PartieRepository partieRepository;
    private final ServiceTirageMot serviceTirageMot;

    public Mastermind(PartieRepository pr, MotsRepository mr, ServiceNombreAleatoire na) {
        this.partieRepository = pr;
        this.serviceTirageMot = new ServiceTirageMot(mr, na);
    }

    public boolean nouvellePartie(Joueur joueur) {
        Optional<Partie> partieEnCours = partieRepository.getPartieEnregistree(joueur);
        if (partieEnCours.isEmpty()) {
            String mot = serviceTirageMot.tirageMotAleatoire();
            Partie p = Partie.nouvelle(joueur, mot);
            partieRepository.create(p);
            return true;
        } else return false;
    }

    public ResultatPartie evaluation(Joueur joueur, String motPropose) {
        Optional<Partie> partieEnCours = partieRepository.getPartieEnregistree(joueur);
        if (partieEnCours.isEmpty()) {
            return ResultatPartie.ERROR;
        } else {
            Partie p = partieEnCours.get();
            return calculeResultat(p, motPropose);
        }
    }


    private ResultatPartie calculeResultat(Partie partie, String motPropose) {
        Reponse rep = partie.tourDeJeu(motPropose);
        partieRepository.update(partie);
        if (partie.isTerminee()) return new ResultatPartie(rep, partie.isTerminee(), true);
        return ResultatPartie.create(rep, partie.isTerminee());
    }
}
