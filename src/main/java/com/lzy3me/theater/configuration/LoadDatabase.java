package com.lzy3me.theater.configuration;

import com.lzy3me.theater.model.Movies;
import com.lzy3me.theater.repository.MoviesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Date;

@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(MoviesRepo m_repo) {
        return args -> {
            log.info("Preloaded: " + m_repo.save(new Movies("The Equalizer 3", "Robert McCall finds himself at home in Southern Italy but he discovers his friends are under the control of local crime bosses. As events turn deadly, McCall ...", (short) 109, "action, crime, thriller", "Denzel Washington, Dakota Fanning, Eugenio Mastrandrea", LocalDate.parse("2023-08-30"))));
        };
    }
}
