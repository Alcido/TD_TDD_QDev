package org.iut.mastermind;

import org.iut.mastermind.domain.Mastermind;
import org.iut.mastermind.infra.repository.MotsRepositoryPostgres;
import org.iut.mastermind.infra.repository.PartieRepositoryPostgres;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class MastermindConfiguration {

    @Bean
    public DataSource dataSource(
            @Value("${spring.datasource.url}") String url,
            @Value("${spring.datasource.username}") String username,
            @Value("${spring.datasource.password}") String password) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }


        @Bean
        public PartieRepositoryPostgres partieRepository (DataSource dataSource){
            return new PartieRepositoryPostgres(dataSource);
        }

        @Bean
        public MotsRepositoryPostgres motsRepository (DataSource dataSource){
            return new MotsRepositoryPostgres(dataSource);
        }

        @Bean
        public ProductionNombreAleatoire productionNombreAleatoire () {
            return new ProductionNombreAleatoire();
        }

        @Bean
        public Mastermind mastermind (PartieRepositoryPostgres partieRepository,
                MotsRepositoryPostgres motsRepository,
                ProductionNombreAleatoire productionNombreAleatoire){
            return new Mastermind(partieRepository, motsRepository, productionNombreAleatoire);
        }
    }