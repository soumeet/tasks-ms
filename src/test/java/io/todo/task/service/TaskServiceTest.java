package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.mapper.TaskEntityMapper;
import io.todo.task.mapper.TaskEntityTaskMapper;
import io.todo.task.model.Task;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.todo.task.exceptions.TaskNotCreatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static io.todo.task.model.Priority.MINOR;
import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Status.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static io.todo.task.model.Status.NOT_STARTED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TaskServiceTest {
    @Autowired
    TaskService taskService;
    @Autowired
    TaskEntityTaskMapper mapper;
    @Autowired
    TaskEntityMapper taskEntityMapper;
    @MockBean
    TaskRepository taskRepository;

    @Test
    @DisplayName("Successfully update a Task")
    void updateTaskSuccess() throws TaskNotFoundException {
        // given
        var oldTask = TaskEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Task Name")
            .description("Random Task Description")
            .completionDate(OffsetDateTime.now().toString())
            .dueDate(OffsetDateTime.now().toString())
            .priority(NORMAL)
            .status(IN_PROGRESS)
            .build();

        var newTask = new Task()
            .id(UUID.randomUUID().toString())
            .name("Task Name Updated")
            .priority(MINOR);

        var updatedTaskEntity = mapper.taskToTaskEntity(newTask);
        updatedTaskEntity = taskEntityMapper.update(updatedTaskEntity, oldTask);

        // when
        when(taskRepository.findById(any())).thenReturn(Optional.of(oldTask));
        when(taskRepository.save(any())).thenReturn(updatedTaskEntity);

        var updatedTask = taskService.updateTask(oldTask.getId(), newTask);

        // then
        assertEquals(oldTask.getId(), updatedTask.getId());
        assertEquals(newTask.getName(), updatedTask.getName());
        assertEquals(oldTask.getDescription(), updatedTask.getDescription());
        assertEquals(oldTask.getCompletionDate(), updatedTask.getCompletionDate());
        assertEquals(oldTask.getDueDate(), updatedTask.getDueDate());
        assertEquals(newTask.getPriority(), updatedTask.getPriority());
        assertEquals(oldTask.getStatus(), updatedTask.getStatus());
    }

    @Test
    @DisplayName("Unsuccessful update of Task. Invalid Task ID")
    void updateTaskFailureInvalidTaskId() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(
            TaskNotFoundException.class,
            () -> taskService.updateTask(UUID.randomUUID().toString(), new Task())
        );
    }

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

    @Test
    @DisplayName("successfully get a Task")
    void getTaskSuccess() throws TaskNotFoundException {
        var taskEntity = TaskEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Get Task")
            .description("Get Task Unit Test")
            .completionDate(OffsetDateTime.now().toString())
            .dueDate(OffsetDateTime.now().toString())
            .priority(NORMAL)
            .status(IN_PROGRESS)
            .build();

        when(taskRepository.findById(any())).thenReturn(Optional.of(taskEntity));

        var foundTask = taskService.getTaskById(taskEntity.getId());

        assertEquals(taskEntity.getId(), foundTask.getId());
        assertEquals(taskEntity.getName(), foundTask.getName());
        assertEquals(taskEntity.getDescription(), foundTask.getDescription());
        assertEquals(taskEntity.getCompletionDate(), foundTask.getCompletionDate());
        assertEquals(taskEntity.getDueDate(), foundTask.getDueDate());
        assertEquals(taskEntity.getPriority(), foundTask.getPriority());
        assertEquals(taskEntity.getStatus(), foundTask.getStatus());
    }

    @Test
    @DisplayName("unsuccessful get a task. taskId not found")
    void getTaskFailureInvalidTaskId() {
        when(taskRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(
            TaskNotFoundException.class,
            () -> taskService.getTaskById(UUID.randomUUID().toString())
        );
    }
}