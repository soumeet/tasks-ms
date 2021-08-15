package io.todo.task.api;

import io.todo.task.model.Task;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskApiImpl implements TaskApiDelegate {

    private final TaskService taskService;

    @Override
    public ResponseEntity<Task> createTask(Task task) {
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
    public ResponseEntity<Task> updateTask(String taskId, Task task) throws TaskNotFoundException {
        return ResponseEntity
                .status(OK)
                .body(taskService.updateTask(taskId, task));
    }
}
