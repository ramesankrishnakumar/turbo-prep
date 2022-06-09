package code.krishna.turboprep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {RabbitAutoConfiguration.class})
public class TurboPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(TurboPrepApplication.class, args);
	}

}
