package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
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

    /**
     * This function deletes a task by its task id
     *
     * @param taskId - The Task ID to be deleted
     * @return - The deleted {@link Task}
     * @throws TaskNotFoundException - When the task is not found
     */
    public Task deleteTask(String taskId) throws TaskNotFoundException {

        var deletedTask = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        taskRepository.deleteById(taskId);

        return taskEntityTaskMapper.taskEntityToTask(deletedTask);
    }

    /**
     * <p>
     * this method gets task to Tasks Collection by taskId
     * </p>
     *
     * @param taskId - The task id to be fetched
     * @return Task - The fetched task
     * @throws TaskNotFoundException - When task is not found
     * @see <a href="https://task-management-system.atlassian.net/browse/TM-5">TM-5</a>
     * @since 1.0
     */
    public Task getTaskById(String taskId) throws TaskNotFoundException {
        var taskEntity = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);
        return taskEntityTaskMapper.taskEntityToTask(taskEntity);
    }

    public Task updateTask(String taskId, Task task) throws TaskNotFoundException {
        // Fetch task to update
        var fetchedTask = taskRepository
                .findById(taskId)
                .orElseThrow(TaskNotFoundException::new);

        // Convert received Task to TaskEntity
        var updateTask = taskEntityTaskMapper.taskToTaskEntity(task);

        // Update all fields of the fetched task with provided task
        updateTask = taskEntityMapper.update(updateTask, fetchedTask);

        // Save the updated task
        updateTask = taskRepository.save(updateTask);

        // Return the updated Task object after mapping
        return taskEntityTaskMapper.taskEntityToTask(updateTask);
    }

    /**
     * <p>
     * this method generates a Random UUID and adds task to Tasks Collection
     * </p>
     *
     * @param task - The {@link Task} to be created
     * @return Task - The newly created task
     * @throws TaskNotCreatedException - In case there is an error while creating a task
     * @see <a href="https://task-management-system.atlassian.net/browse/TM-1">TM-1</a>
     * @since 1.0
     */
    public Task createTask(Task task) throws TaskNotCreatedException {
        task.setId(UUID.randomUUID().toString());
        var taskEntity = taskEntityTaskMapper.taskToTaskEntity(task);
        try {
            taskEntity = taskRepository.insert(taskEntity);
        } catch (Exception e) {
            throw new TaskNotCreatedException();
        }
        return taskEntityTaskMapper.taskEntityToTask(taskEntity);
    }

    /**
     * <p>
     * this method gets task from Tasks Collection based on filter
     * </p>
     *
     * @param name           The name of the Task item (optional)
     * @param desc           The description of the Task item (optional)
     * @param status         The status of the Task item (optional)
     * @param priority       The priority of the Task item (optional)
     * @param completionDate The completion date of the Task item (optional)
     * @param dueDate        The due date of the Task item (optional)
     * @return List<Task
            * @ see < a href = " https: / / task-management-system.atlassian.net / browse / TM-3 ">TM-3</a>
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
                .map(taskEntityTaskMapper::taskEntityToTask)
                .collect(Collectors.toList());

        return tasks;
    }
}
