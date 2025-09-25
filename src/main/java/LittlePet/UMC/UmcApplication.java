package LittlePet.UMC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling // 스케줄링 기능 활성화
@SpringBootApplication(scanBasePackages = "LittlePet.UMC")
@EnableJpaAuditing
public class UmcApplication {

	public static void main(String[] args) {
//		Dotenv dotenv = Dotenv.configure().load();
//		System.setProperty("DB_URL", dotenv.get("DB_URL"));
//		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
//		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		SpringApplication.run(UmcApplication.class, args);
	}

}