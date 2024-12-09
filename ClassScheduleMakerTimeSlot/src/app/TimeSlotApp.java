package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
// this will disable the DB initialization which we do not need at this point
public class TimeSlotApp {

	public static void main(String[] args) {
		SpringApplication.run(TimeSlotApp.class, args);
	}
}
