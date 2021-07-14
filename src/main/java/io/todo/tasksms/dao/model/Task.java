package io.todo.tasksms.dao.model;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.OffsetDateTime;

@Getter
@Setter
@ToString
@Builder
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private String description;
    private Priority priority;
    private Status status;
    private OffsetDateTime dueDate;
    private OffsetDateTime completionDate;
}
