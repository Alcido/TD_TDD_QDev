package org.iut.mastermind;

import org.iut.mastermind.domain.tirage.ServiceNombreAleatoire;
import java.util.Random;

class ProductionNombreAleatoire implements ServiceNombreAleatoire {
    private final Random random = new Random();

    @Override
    public int next(int borneSup) {
        int borneSupEx = borneSup + 1;
        return random.nextInt(borneSupEx);
    }
}
