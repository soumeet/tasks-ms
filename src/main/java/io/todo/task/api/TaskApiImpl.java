package io.todo.task.api;

import io.todo.task.model.Task;
import io.todo.task.dao.TaskDAO;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskApiImpl implements TaskApiDelegate {
    private final TaskDAO taskDAO;
    private final TaskService taskService;

    @Override
    public ResponseEntity<Task> createTask(Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.createTask(task));
    }

    @Override
    public ResponseEntity<Task> deleteTask(String taskId) throws TaskNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.deleteTask(taskId));
    }

    @Override
    public ResponseEntity<Task> getTaskById(String taskId) throws TaskNotFoundException {
        LOGGER.info("Entry into getTaskById");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.readTask(taskId));
    }

    @Override
    public ResponseEntity<Task> updateTask(String taskId, Task task) throws TaskNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.updateTask(taskId, task));
    }
}
