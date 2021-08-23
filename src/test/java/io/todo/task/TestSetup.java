package io.todo.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.todo.task.dao.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class TestSetup {

    @Autowired
    public MockMvc mockMvc;

    @Autowired
    public TaskRepository taskRepository;

    @Autowired
    public ObjectMapper objectMapper;
}
