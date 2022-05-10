package se.mbi.be2.trava.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TravaApiApplication {

	@Autowired
	BuildProperties buildProperties;

	@GetMapping("/")
	public String root() {
		return String.format("This is Trava! Version=%s", buildProperties.getVersion());
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s! This is the first version", buildProperties.getVersion());
	}

	public static void main(String[] args) {
		SpringApplication.run(TravaApiApplication.class, args);
	}

}
