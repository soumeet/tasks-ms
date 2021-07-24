package io.todo.task.dao;

import io.todo.task.model.Task;
import io.todo.task.exceptions.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class TaskDAO {

    private final List<Task> taskList;

    public Task createTask(Task task) {
        task.setId(UUID.randomUUID().toString());
        taskList.add(task);
        return task;
    }

    public Task readTask(String taskId) throws TaskNotFoundException {
        return taskList.stream()
                .filter(t -> t.getId().equals(taskId))
                .findFirst()
                .orElseThrow(TaskNotFoundException::new);
    }

    public Task updateTask(String taskId, Task newTask) throws TaskNotFoundException {
        taskList.stream()
                .filter(t -> t.getId().equals(taskId))
                .forEach(oldTask -> this.modifyTask(newTask, oldTask));

        return readTask(taskId);
    }

    public Task deleteTask(String taskId) throws TaskNotFoundException {
        var readTask = readTask(taskId);
        taskList.remove(readTask);
        return readTask;
    }

    private Task modifyTask(Task newTask, Task oldTask) {
        return oldTask.name(newTask.getName())
                .description(newTask.getDescription())
                .priority(newTask.getPriority())
                .status(newTask.getStatus())
                .dueDate(newTask.getDueDate())
                .completionDate(newTask.getCompletionDate());
    }
}
