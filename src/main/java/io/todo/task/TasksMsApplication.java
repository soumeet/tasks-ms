package io.todo.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({"io.todo.task"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TasksMsApplication {
    public static void main(String[] args) {
        SpringApplication.run(TasksMsApplication.class, args);
    }
}


