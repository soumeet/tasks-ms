package io.todo.task.dao;

import io.todo.task.model.Task;
import io.todo.task.exceptions.TaskNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class TaskDAO {

    private final List<Task> taskList;

    public Task createTask(Task task) {
        Random random = new Random();
        task.setId("T" + random.nextInt());
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
        Task readTask = readTask(taskId);
        taskList.remove(readTask);
        return readTask;
    }

    private Task modifyTask(Task newTask, Task oldTask) {
        return oldTask.name(newTask.getName())
                .description(oldTask.getDescription())
                .priority(oldTask.getPriority())
                .status(oldTask.getStatus())
                .dueDate(oldTask.getDueDate())
                .completionDate(oldTask.getCompletionDate());
    }
}
