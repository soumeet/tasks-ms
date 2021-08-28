package io.todo.task.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.todo.task.dao.TaskRepository;
import io.todo.task.dao.entity.TaskEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static io.todo.task.model.Priority.NORMAL;
import static io.todo.task.model.Priority.URGENT;
import static io.todo.task.model.Status.IN_PROGRESS;
import static io.todo.task.model.Status.NOT_STARTED;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GetTaskWithFilterIntegrationTest {
    private static final int TEST_SIZE = 10;
    private static final String TEST_NAME = "GetTaskWithFilter_NAME";
    private static final String TEST_DESC = "GetTaskWithFilter_DESC";
    private static final String TEST_DATE = OffsetDateTime.now().toString();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ObjectMapper objectMapper;
    List<TaskEntity> createdTasks;
    int initialCount;

    @BeforeEach
    void setUp() {
        initialCount = (int) taskRepository.count();
        TaskEntity createdTask;
        createdTasks = new ArrayList<>();
        for(int i = 0; i < TEST_SIZE; i++) {
            createdTask = TaskEntity.builder()
                    .id(UUID.randomUUID().toString())
                    .name(TEST_NAME)
                    .description(TEST_DESC)
                    .completionDate(TEST_DATE)
                    .dueDate(TEST_DATE)
                    .priority(i % 2 == 0 ? NORMAL : URGENT)
                    .status(i % 2 == 0 ? NOT_STARTED : IN_PROGRESS)
                    .build();
            createdTasks.add(createdTask);
        }
        taskRepository.insert(createdTasks);
    }

    @AfterEach
    void tearDown() {
        taskRepository.deleteAllById(
            createdTasks.stream()
                .map(task -> task.getId())
                .collect(Collectors.toList())
        );
    }

    @Test
    @DisplayName("get tasks with no filters")
    void getTaskSuccess() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(initialCount + TEST_SIZE)));
    }

    @Test
    @DisplayName("get tasks with name filter")
    void getTaskSuccessByName() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/?name="+TEST_NAME)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(TEST_SIZE)));
    }

    @Test
    @DisplayName("get tasks with description filter")
    void getTaskSuccessByDesc() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/?desc="+TEST_DESC)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(TEST_SIZE)));
    }

    @Test
    @DisplayName("get tasks with status")
    void getTaskSuccessByStatus() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/?status="+NOT_STARTED)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
        mockMvc.perform(
            get("/api/v1/task/?status="+IN_PROGRESS)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("get tasks with due-date")
    void getTaskSuccessByDueDate() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/?dueDate="+TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(TEST_SIZE)));
    }

    @Test
    @DisplayName("get tasks with completion-date")
    void getTaskSuccessByCompletionDate() throws Exception {
        mockMvc.perform(
            get("/api/v1/task/?completionDate="+TEST_DATE)
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(TEST_SIZE)));
    }
}