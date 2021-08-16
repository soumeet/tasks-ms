package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;

import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Status.NOT_STARTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    TaskService taskService;
    @MockBean
    TaskRepository taskRepository;

    @Test
    @DisplayName("successfully create task")
    void createTaskSuccess() throws TaskNotCreatedException {
        Task newTask = new Task();
        newTask.setName("Create Task");
        newTask.setDescription("Create Task Unit Test");
        newTask.setCompletionDate(OffsetDateTime.now().toString());
        newTask.setDueDate(OffsetDateTime.now().toString());
        newTask.setStatus(NOT_STARTED);
        newTask.setPriority(NORMAL);

        var createdTask = taskService.createTask(newTask);

        assertNotNull(createdTask.getId());
        assertEquals(createdTask.getName(), newTask.getName());
        assertEquals(createdTask.getDescription(), newTask.getDescription());
        assertEquals(createdTask.getCompletionDate(), newTask.getCompletionDate());
        assertEquals(createdTask.getDueDate(), newTask.getDueDate());
        assertEquals(createdTask.getPriority(), newTask.getPriority());
        assertEquals(createdTask.getStatus(), newTask.getStatus());
    }

    @Test
    @DisplayName("unsuccessful create task")
    void createTaskFailure() {
        when(taskRepository.insert((TaskEntity) any())).thenThrow(new RuntimeException());
        assertThrows(
                TaskNotCreatedException.class,
                () -> taskService.createTask(new Task())
        );
    }
}