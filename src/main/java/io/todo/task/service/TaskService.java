package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.mapper.TaskEntityMapper;
import io.todo.task.mapper.TaskEntityTaskMapper;
import io.todo.task.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskEntityTaskMapper taskEntityTaskMapper;
    private final TaskEntityMapper taskEntityMapper;

    public Task deleteTask(String taskId) throws TaskNotFoundException {

        var deletedTask = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.deleteById(taskId);

        return taskEntityTaskMapper.taskEntityToTask(deletedTask);
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

    /**
     * <p>
     * this method generates a rondom UUID and adds task to Tasks Collection
     * </p>
     * @param task
     * @return Task
     * @see <a href="https://task-management-system.atlassian.net/browse/TM-1">TM-1</a>
     * @since 1.0
     * @throws TaskNotCreatedException
     */
    public Task createTask(Task task) throws TaskNotCreatedException {
        task.setId(UUID.randomUUID().toString());
        TaskEntity taskEntity = taskEntityTaskMapper.taskToTaskEntity(task);
        try {
            taskRepository.insert(taskEntity);
        } catch (Exception e) {
            throw new TaskNotCreatedException();
        }
        return task;
    }
}
