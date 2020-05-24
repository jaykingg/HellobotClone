package app.backend.hellobotclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HellobotcloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(HellobotcloneApplication.class, args);
    }

}
