package it.alten.tirocinio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"it.alten.tirocinio"})
@SpringBootApplication
public class TirocinioApplication {

	public static void main(String[] args) {
		SpringApplication.run(TirocinioApplication.class, args);
	}

}
