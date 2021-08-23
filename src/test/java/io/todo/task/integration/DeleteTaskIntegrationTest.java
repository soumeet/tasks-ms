package io.todo.task.integration;

import io.todo.task.TestSetup;
import io.todo.task.dao.entity.TaskEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Status.IN_PROGRESS;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeleteTaskIntegrationTest extends TestSetup {

    TaskEntity insertedTask;
    String taskId;

    @BeforeEach
    void setUp() {
        taskId = UUID.randomUUID().toString();
        insertedTask = TaskEntity.builder()
                .id(taskId)
                .name("Task Name")
                .description("Random Task Description")
                .completionDate(OffsetDateTime.now().toString())
                .dueDate(OffsetDateTime.now().toString())
                .priority(NORMAL)
                .status(IN_PROGRESS)
                .build();

        taskRepository.save(insertedTask);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAllById(Collections.singleton(taskId));
    }

    @Test
    void deleteTaskIT() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/task/" + taskId)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId)))
                .andExpect(jsonPath("$.name", is(insertedTask.getName())))
                .andExpect(jsonPath("$.description", is(insertedTask.getDescription())))
                .andExpect(jsonPath("$.priority", is(insertedTask.getPriority().name())))
                .andExpect(jsonPath("$.status", is(insertedTask.getStatus().name())))
                .andExpect(jsonPath("$.dueDate", is(insertedTask.getDueDate())))
                .andExpect(jsonPath("$.completionDate", is(insertedTask.getCompletionDate())));

        Assertions.assertEquals(taskRepository.findById(taskId), Optional.empty());
    }


    @Test
    void deleteTaskNotFoundIT() throws Exception {
        mockMvc.perform(
                        delete("/api/v1/task/taskId")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("TASK_NOT_FOUND")))
                .andExpect(jsonPath("$.errorMessage", is("The provided Task ID is not found")));
    }
}
