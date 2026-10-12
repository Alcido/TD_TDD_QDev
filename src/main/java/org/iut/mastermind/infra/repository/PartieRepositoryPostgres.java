package org.iut.mastermind.infra.repository;

import org.iut.mastermind.domain.partie.Joueur;
import org.iut.mastermind.domain.partie.Partie;
import org.iut.mastermind.domain.partie.PartieRepository;
import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class PartieRepositoryPostgres implements PartieRepository {

    private static final String INSERT_NOUVELLE_PARTIE =
            "INSERT INTO partie (nom_joueur, mot, nb_essais, termine) VALUES (?, ?, ?, ?)";
    private static final String FIND_PARTIE_POUR_JOUEUR =
            "SELECT nom_joueur, mot, nb_essais FROM partie WHERE nom_joueur = ? AND termine = false";
    private static final String UPDATE_PARTIE_POUR_JOUEUR =
            "UPDATE partie SET nb_essais = ?, termine = ? WHERE nom_joueur = ? AND termine = false";

    private final DataSource dataSource;

    public PartieRepositoryPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void create(Partie partie) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(INSERT_NOUVELLE_PARTIE)) {
            pstmt.setString(1, partie.getJoueur().getNom());
            pstmt.setString(2, partie.getMot());
            pstmt.setInt(3, partie.getNbEssais());
            pstmt.setBoolean(4, partie.isTerminee());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la création de la partie", e);
        }
    }

    @Override
    public Optional<Partie> getPartieEnregistree(Joueur joueur) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_PARTIE_POUR_JOUEUR)) {
            pstmt.setString(1, joueur.getNom());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom_joueur");
                    String mot = rs.getString("mot");
                    int essais = rs.getInt("nb_essais");
                    return Optional.of(Partie.reprendre(new Joueur(nom), mot, essais));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération de la partie", e);
        }
        return Optional.empty();
    }

    @Override
    public void update(Partie partie) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(UPDATE_PARTIE_POUR_JOUEUR)) {
            pstmt.setInt(1, partie.getNbEssais());
            pstmt.setBoolean(2, partie.isTerminee());
            pstmt.setString(3, partie.getJoueur().getNom());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la mise à jour de la partie", e);
        }
    }
}