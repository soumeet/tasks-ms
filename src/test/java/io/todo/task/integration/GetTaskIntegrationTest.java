package io.todo.task.integration;

import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Status.IN_PROGRESS;
import static io.todo.task.util.TaskConstants.Exceptions.TASK_NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTaskIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskRepository taskRepository;
    TaskEntity createdTask;

    @BeforeEach
    void setUp() {
        createdTask = TaskEntity.builder()
            .id(UUID.randomUUID().toString())
            .name("Get Task")
            .description("Get Task Integration Test")
            .completionDate(OffsetDateTime.now().toString())
            .dueDate(OffsetDateTime.now().toString())
            .priority(NORMAL)
            .status(IN_PROGRESS)
            .build();
    }

    @Test
    @DisplayName("integration test for successful get")
    void getTaskSuccess() throws Exception {
        when(taskRepository.findById(any())).thenReturn(Optional.of(createdTask));
        mockMvc.perform(
            get("/api/v1/task/" + createdTask.getId())
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.name", is(createdTask.getName())))
        .andExpect(jsonPath("$.description", is(createdTask.getDescription())))
        .andExpect(jsonPath("$.priority", is(createdTask.getPriority().name())))
        .andExpect(jsonPath("$.status", is(createdTask.getStatus().name())))
        .andExpect(jsonPath("$.dueDate", is(createdTask.getDueDate())))
        .andExpect(jsonPath("$.completionDate", is(createdTask.getCompletionDate())));
    }

    @Test
    @DisplayName("integration Test for unsuccessful get")
    void getTaskFailure() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/"+ UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errorCode", is(TASK_NOT_FOUND)))
        .andExpect(jsonPath("$.errorMessage", is("The provided Task ID is not found")));
    }
}