package org.iut.mastermind.infra.repository;

import java.sql.*;
import org.iut.mastermind.domain.tirage.MotsRepository;
import javax.sql.DataSource;

public class MotsRepositoryPostgres implements MotsRepository {
    private static final String FIND_MOT_BY_INDEX = "SELECT mot FROM mot WHERE mot_no = ?";
    private static final String FIND_MAX_INDEX = "SELECT MAX(mot_no) FROM mot";

    private final DataSource dataSource;

    public MotsRepositoryPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String getMotByIndex(int index) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_MOT_BY_INDEX)) {
            pstmt.setInt(1, index);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mot");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du mot par index", e);
        }
        return "";
    }

    @Override
    public int nbMaxMots() {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(FIND_MAX_INDEX);
             ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du nombre de mots", e);
        }
        return 0;
    }
}