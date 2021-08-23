package io.todo.task.dao;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.task.dao.entity.TaskEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class TaskRepositoryTests {
    @Autowired
    TaskRepository taskRepository;

    @Test
    void testCreate() {
        TaskEntity taskEntity = TaskEntity.builder()
                .name("task-name-" + new Random().nextLong())
                .description("Task Repository Test")
                .priority(Priority.MINOR)
                .status(Status.NOT_STARTED)
                .completionDate(null)
                .dueDate(null)
                .build();

        taskRepository.insert(taskEntity);
    }
}
