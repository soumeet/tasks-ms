package io.todo.tasksms.api;

import io.todo.task.api.TaskApiDelegate;
import io.todo.task.model.Task;
import io.todo.tasksms.dao.TaskDAO;
import io.todo.tasksms.exceptions.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TaskApiImpl implements TaskApiDelegate {

    private final TaskDAO taskDAO;

    @Override
    public ResponseEntity<Task> createTask(Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.createTask(task));
    }

    @Override
    public ResponseEntity<Task> deleteTask(String taskIdToDelete) throws TaskNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.deleteTask(taskIdToDelete));

    }

    @Override
    public ResponseEntity<Task> getTaskById(String taskId) throws TaskNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.readTask(taskId));
    }

    @Override
    public ResponseEntity<Task> updateTask(String taskIdToUpdate, Task task) throws TaskNotFoundException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskDAO.updateTask(taskIdToUpdate, task));

    }
}
