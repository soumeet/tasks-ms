package net.todo.tasksms.dao;

import com.todoapp.todo.model.Priority;
import net.todo.tasksms.dao.model.Task;
import net.todo.tasksms.dao.model.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.OffsetDateTime;

@SpringBootTest
class TaskRepositoryTests {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void testCreate() {
        Task task = Task.builder()
                .name("task 1")
                .priority(Priority.MINOR)
                .completionDate(OffsetDateTime.now())
                .dueDate(OffsetDateTime.now())
                .build();

        taskRepository.insert(task);

    }
}
