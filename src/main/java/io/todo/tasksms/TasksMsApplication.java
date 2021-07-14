package io.todo.tasksms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class TasksMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TasksMsApplication.class, args);
	}

}
