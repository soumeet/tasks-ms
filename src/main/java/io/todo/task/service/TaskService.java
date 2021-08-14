package io.todo.task.service;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.mapper.TaskEntityTaskMapper;
import io.todo.task.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskEntityTaskMapper taskEntityTaskMapper;
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
