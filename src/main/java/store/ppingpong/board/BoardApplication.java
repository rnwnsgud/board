package store.ppingpong.board;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
public class BoardApplication {
	public static void main(String[] args) {
		SpringApplication.run(BoardApplication.class, args);
	}

}
