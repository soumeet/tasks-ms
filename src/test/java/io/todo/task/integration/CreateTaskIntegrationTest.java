package io.todo.task.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.model.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.time.OffsetDateTime;
import java.util.Collections;

import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Status.NOT_STARTED;
import static io.todo.task.util.TaskConstants.Exceptions.TASK_NOT_CREATED;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CreateTaskIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskRepository taskRepository;
    @Autowired
    private ObjectMapper objectMapper;
    String createdTaskId;
    Task newTask;
    String newTaskJson;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        newTask = new Task();
        newTask.setName("Create Task");
        newTask.setDescription("Create Task Integration Test");
        newTask.setCompletionDate(OffsetDateTime.now().toString());
        newTask.setDueDate(OffsetDateTime.now().toString());
        newTask.setStatus(NOT_STARTED);
        newTask.setPriority(NORMAL);

        newTaskJson = objectMapper.writeValueAsString(newTask);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAllById(Collections.singleton(createdTaskId));
    }

    @Test
    @DisplayName("integration Test for unsuccessful create")
    void createTaskFailure() throws Exception {
        when(taskRepository.insert((TaskEntity) any())).thenThrow(new RuntimeException());
        mockMvc.perform(
                post("/api/v1/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTaskJson)
        )
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorCode", is(TASK_NOT_CREATED)))
                .andExpect(jsonPath("$.errorMessage", is("The provided Task is not created")));
    }

    @Test
    @DisplayName("integration test for successful create task")
    void createTaskSuccess() throws Exception {
        MvcResult result = mockMvc.perform(
                post("/api/v1/task/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newTaskJson)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is(newTask.getName())))
                .andExpect(jsonPath("$.description", is(newTask.getDescription())))
                .andExpect(jsonPath("$.priority", is(newTask.getPriority().name())))
                .andExpect(jsonPath("$.status", is(newTask.getStatus().name())))
                .andExpect(jsonPath("$.dueDate", is(newTask.getDueDate())))
                .andExpect(jsonPath("$.completionDate", is(newTask.getCompletionDate())))
                .andReturn();
        createdTaskId = objectMapper.readValue(result.getResponse().getContentAsString(), Task.class).getId();
    }
}