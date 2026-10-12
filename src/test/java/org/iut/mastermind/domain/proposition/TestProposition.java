package org.iut.mastermind.domain.proposition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.iut.mastermind.domain.proposition.Lettre.*;

@DisplayName("Test si la proposition:")
public class TestProposition {

    @Test
    @DisplayName("contient une lettre incorrecte")
    public void casLettreIncorrecte() {
        var mot = new MotSecret("S");
        var reponse = mot.compareProposition("Z");
        assertResultat(reponse, INCORRECTE);
    }

    @Test
    @DisplayName("contient une lettre placée")
    public void casLettrePlacee() {
        var mot = new MotSecret("S");
        var reponse = mot.compareProposition("S");
        assertResultat(reponse, PLACEE);
    }

    @Test
    @DisplayName("contient une lettre incorrecte et une non placée")
    public void casDeuxiemeLettreMalPlacee() {
        var mot = new MotSecret("SO");
        var reponse = mot.compareProposition("ZS");
        assertResultat(reponse,  INCORRECTE, NON_PLACEE);
    }

    @Test
    @DisplayName("contient une lettre incorrecte, une non placée et une placée")
    public void casCombinaisons() {
        var mot = new MotSecret("SOL");
        var reponse = mot.compareProposition("ZSL");
        assertResultat(reponse,  INCORRECTE, NON_PLACEE, PLACEE);
    }

    @Test
    @DisplayName("contient que des lettre correctes avec des lettres répétées")
    public void casCombinaisons2() {
        var mot = new MotSecret("ERROR");
        var reponse = mot.compareProposition("ERROR");
        assertResultat(reponse,  PLACEE, PLACEE,PLACEE, PLACEE, PLACEE);
    }

    @Test
    @DisplayName("contient une lettre mal placée et le reste correcte")
    public void casCombinaisons3() {
        var mot = new MotSecret("ERROR");
        var reponse = mot.compareProposition("ERORR");
        assertResultat(reponse,  PLACEE, PLACEE,NON_PLACEE, NON_PLACEE, PLACEE);
    }

    @Test
    @DisplayName("contient 2 lettres mal pacées identiques (RR) et le reste mal placé")
    public void casCombinaisons4() {
        var mot = new MotSecret("TEST");
        var reponse = mot.compareProposition("DTTD");
        assertResultat(reponse, INCORRECTE, NON_PLACEE, NON_PLACEE, INCORRECTE);
    }

    @Test
    @DisplayName("contient 2 lettres mal pacées identiques (RR) et le reste mal placé")
    public void casCombinaisons5() {
        var mot = new MotSecret("SLEEP");
        var reponse = mot.compareProposition("TESTS");
        assertResultat(reponse, INCORRECTE, NON_PLACEE, NON_PLACEE, INCORRECTE, INCORRECTE);
    }



    @Test
    @DisplayName("contient toutes les lettres placées")
    void casToutesLettresPlacees() {
        var mot = new MotSecret("SOLID");
        var reponse = mot.compareProposition("SOLID");
        assertThat(reponse.lettresToutesPlacees()).isTrue();
    }

    @Test
    @DisplayName("n'a pas toutes les lettres placées")
    void casLettresIncorrectes() {
        var mot = new MotSecret("SOLID");
        var reponse = mot.compareProposition("SOL*D");
        assertThat(reponse.lettresToutesPlacees()).isFalse();
    }

    @Test
    @DisplayName("possède le bon nombre de caractères")
    void casAccesLettres() {
        var mot = new MotSecret("SOLID");
        var reponse = mot.compareProposition("SOL*D");
        assertThat(reponse.lettresResultat()).hasSize(5);
    }


    private void assertResultat(Reponse reponse, Lettre... resultatAttendu) {
        for (int position = 0; position < resultatAttendu.length; position++) {
            Lettre attendue = resultatAttendu[position];
            assertThat(reponse.getLettreAtIndex(position)).isEqualTo(attendue);
        }
    }
}
