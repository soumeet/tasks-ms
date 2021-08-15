package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.mapper.TaskEntityMapper;
import io.todo.task.mapper.TaskEntityTaskMapper;
import io.todo.task.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@RequiredArgsConstructor
@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskEntityTaskMapper taskEntityTaskMapper;
    private final TaskEntityMapper taskEntityMapper;

    public Task createTask(Task newTask) {
        return null;
    }

    public Task deleteTask(String taskId) {
        return null;
    }

    public Task getTaskById(String taskId) {
        return null;
    }

    public Task updateTask(String taskId, Task task) throws TaskNotFoundException {

        // Fetch task to update
        var fetchedTask = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        // Convert received Task to TaskEntity
        var updateTask = taskEntityTaskMapper.taskToTaskEntity(task);
        LOGGER.info("After mapping : {}", updateTask);

        // Update all fields of the fetched task with provided task
        updateTask = taskEntityMapper.update(updateTask, fetchedTask);

        LOGGER.info("Before update : {}", updateTask);
        // Save the updated task
        updateTask = taskRepository.save(updateTask);
        LOGGER.info("After update : {}", updateTask);

        // Return the updated Task object after mapping
        return taskEntityTaskMapper.taskEntityToTask(updateTask);
    }
}
