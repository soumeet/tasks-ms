package io.todo.task.dao.entity;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "tasks")
public class TaskEntity {
    @Id
    private String id;
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private String dueDate;
    private String completionDate;
}
