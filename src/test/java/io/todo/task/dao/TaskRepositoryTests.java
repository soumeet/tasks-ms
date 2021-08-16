package io.todo.task.dao;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.task.dao.entity.TaskEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class TaskRepositoryTests {
    @Autowired
    TaskRepository taskRepository;
    TaskEntity taskEntity;

    @AfterEach
    void tearDown() {
        taskRepository.deleteAllById(Collections.singleton(taskEntity.getId()));
    }

    @Test
    void testCreate() {
        taskEntity = TaskEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Task Repository")
            .description("Task Repository Test")
            .priority(Priority.MINOR)
            .status(Status.NOT_STARTED)
            .completionDate(null)
            .dueDate(null)
            .build();
        Assert.notNull(taskRepository.insert(taskEntity));
    }
}
