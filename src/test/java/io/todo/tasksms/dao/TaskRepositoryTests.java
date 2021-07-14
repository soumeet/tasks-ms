package io.todo.tasksms.dao;

import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.tasksms.dao.model.Task;
import io.todo.tasksms.dao.model.TaskRepository;
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
        Task task = Task.builder()
                .name("task-name-" + new Random().nextLong())
                .description("task-desc-" + new Random().nextLong())
                .priority(Priority.MINOR)
                .status(Status.NOT_STARTED)
                .completionDate(null)
                .dueDate(null)
                .build();

        taskRepository.insert(task);
    }
}
