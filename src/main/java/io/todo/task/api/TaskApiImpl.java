package io.todo.task.api;

import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.task.model.Task;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskApiImpl implements TaskApiDelegate {
    private final TaskService taskService;

    @Override
    public ResponseEntity<Task> createTask(Task task) throws TaskNotCreatedException {
        return ResponseEntity
            .status(CREATED)
            .body(taskService.createTask(task));
    }

    @Override
    public ResponseEntity<Task> deleteTask(String taskId) throws TaskNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(taskService.deleteTask(taskId));
    }

    @Override
    public ResponseEntity<Task> getTaskById(String taskId) throws TaskNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(taskService.getTaskById(taskId));
    }

    @Override
    public ResponseEntity<List<Task>> getTasksWithFilter(String name,
        String desc, Status status, Priority priority, OffsetDateTime completionDate, OffsetDateTime dueDate) {
        return ResponseEntity
            .status(OK)
            .body(taskService.getTasksWithFilter(name, desc, status, priority, completionDate, dueDate));
    }

    @Override
    public ResponseEntity<Task> updateTask(String taskId, Task task) throws TaskNotFoundException {
        return ResponseEntity
            .status(OK)
            .body(taskService.updateTask(taskId, task));
    }
}
