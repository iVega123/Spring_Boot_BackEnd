package payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(MedicoRepository repository) {

    return args -> {
      log.info("Preloading " + repository.save(new Medico("Caio Viga", "vitor.da.alvorada@gmail.com","804103502")));
      log.info("Preloading " + repository.save(new Medico("Silas", "silas@gmail.com","25410350")));
    };
  }
}