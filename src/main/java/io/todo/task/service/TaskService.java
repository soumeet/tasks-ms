package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.exceptions.TaskNotFoundException;
import io.todo.task.mapper.TaskEntityMapper;
import io.todo.task.mapper.TaskEntityTaskMapper;
import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.task.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static io.todo.task.util.CommonMethods.*;

@Log4j2
@RequiredArgsConstructor
@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskEntityTaskMapper taskEntityTaskMapper;
    private final TaskEntityMapper taskEntityMapper;

    public Task deleteTask(String taskId) {
        return null;
    }

    /**
     * <p>
     * this method gets task to Tasks Collection by taskId
     * </p>
     * @param taskId
     * @return Task
     * @see <a href="https://task-management-system.atlassian.net/browse/TM-5">TM-5</a>
     * @since 1.0
     * @throws TaskNotFoundException
     */
    public Task getTaskById(String taskId) throws TaskNotFoundException {
        Task task = null;
        TaskEntity taskEntity = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        task = taskEntityTaskMapper.taskEntityToTask(taskEntity);
        return task;
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

    /**
     * <p>
     * this method gets task from Tasks Collection based on filter
     * </p>
     * @param name The name of the Task item (optional)
     * @param desc The description of the Task item (optional)
     * @param status The status of the Task item (optional)
     * @param priority The priority of the Task item (optional)
     * @param completionDate The completion date of the Task item (optional)
     * @param dueDate The due date of the Task item (optional)
     * @return List<Task
     * @see <a href="https://task-management-system.atlassian.net/browse/TM-3">TM-3</a>
     * @since 1.0
     */
    public List<Task> getTasksWithFilter(String name,
       String desc, Status status, Priority priority, OffsetDateTime completionDate, OffsetDateTime dueDate) {
        List<Task> tasks;

        tasks = taskRepository
            .findAll()
            .stream()
            .filter(taskEntity -> filterUsing(taskEntity.getName(), name))
            .filter(taskEntity -> filterUsing(taskEntity.getDescription(), desc))
            .filter(taskEntity -> filterUsing(taskEntity.getStatus(), status))
            .filter(taskEntity -> filterUsing(taskEntity.getPriority(), priority))
            .filter(taskEntity -> filterUsing(taskEntity.getCompletionDate(), completionDate))
            .filter(taskEntity -> filterUsing(taskEntity.getDueDate(), dueDate))
            .map(taskEntity -> taskEntityTaskMapper.taskEntityToTask(taskEntity))
            .collect(Collectors.toList());

        return tasks;
    }
}
