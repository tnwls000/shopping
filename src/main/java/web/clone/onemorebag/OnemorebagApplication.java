package web.clone.onemorebag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class OnemorebagApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnemorebagApplication.class, args);
	}

}
