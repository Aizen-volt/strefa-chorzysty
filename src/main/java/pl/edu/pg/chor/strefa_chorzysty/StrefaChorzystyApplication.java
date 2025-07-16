package pl.edu.pg.chor.strefa_chorzysty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class StrefaChorzystyApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrefaChorzystyApplication.class, args);
	}

}
