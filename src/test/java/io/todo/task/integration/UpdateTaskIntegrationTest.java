package io.todo.task.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.todo.task.TestSetup;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import io.todo.task.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UpdateTaskIntegrationTest extends TestSetup {

    String taskId;
    TaskEntity insertTask;
    Task updateTask;
    String updateTaskJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        taskId = UUID.randomUUID().toString();

        insertTask = TaskEntity.builder()
                .id(taskId)
                .name("Dummy Task")
                .description("Dummy Description")
                .dueDate(OffsetDateTime.now().toString())
                .priority(Priority.URGENT)
                .status(Status.IN_PROGRESS)
                .build();

        taskRepository.save(insertTask);

        var updatedName = "Updated Dummy Name";
        var updatedStatus = Status.COMPLETED;
        var updatedCompletionDate = OffsetDateTime.now().toString();

        updateTask = new Task()
                .name(updatedName)
                .status(updatedStatus)
                .completionDate(updatedCompletionDate);

        updateTaskJson = objectMapper.writeValueAsString(updateTask);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAllById(Collections.singleton(taskId));
    }

    @DisplayName("Integration test for successful Update Task")
    @Test
    void updateTaskIT() throws Exception {


        var updateTaskJson = objectMapper.writeValueAsString(updateTask);

        mockMvc.perform(
                        patch("/api/v1/task/" + taskId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateTaskJson)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(taskId)))
                .andExpect(jsonPath("$.name", is(updateTask.getName())))
                .andExpect(jsonPath("$.description", is(insertTask.getDescription())))
                .andExpect(jsonPath("$.priority", is(insertTask.getPriority().name())))
                .andExpect(jsonPath("$.status", is(updateTask.getStatus().name())))
                .andExpect(jsonPath("$.dueDate", is(insertTask.getDueDate())))
                .andExpect(jsonPath("$.completionDate", is(updateTask.getCompletionDate())));
    }

    @DisplayName("Integration Test for Unsuccessful update. TaskNotFound")
    @Test
    void updateTaskNotFoundIT() throws Exception {
        mockMvc.perform(
                        patch("/api/v1/task/taskId")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(updateTaskJson)
                )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorCode", is("TASK_NOT_FOUND")))
                .andExpect(jsonPath("$.errorMessage", is("The provided Task ID is not found")));
    }
}
