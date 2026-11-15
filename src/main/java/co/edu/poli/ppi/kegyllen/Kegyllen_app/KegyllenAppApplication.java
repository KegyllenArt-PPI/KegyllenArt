package co.edu.poli.ppi.kegyllen.Kegyllen_app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KegyllenAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KegyllenAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("APP INICIADA CORRECTAMENTE");
	}
}
