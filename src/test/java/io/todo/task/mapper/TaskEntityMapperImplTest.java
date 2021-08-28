package io.todo.task.mapper;


import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.model.Priority;
import io.todo.task.model.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TaskEntityMapperImplTest {

    TaskEntityMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new TaskEntityMapperImpl();
    }

    @DisplayName("Map when source object is NULL")
    @Test
    void updateTestWhenSourceIsNull() {
        var result = mapper.update(null, TaskEntity.builder().build());
        assertNull(result);
    }

    @DisplayName("Map when source object is not NULL and all fields are populated")
    @Test
    void updateTestWhenAllFieldsInSourceArePopulated() {
        var source = TaskEntity.builder()
                .id(UUID.randomUUID().toString())
                .name("Name")
                .description("Description")
                .status(Status.COMPLETED)
                .priority(Priority.URGENT)
                .completionDate(OffsetDateTime.now().toString())
                .dueDate(OffsetDateTime.now().toString())
                .build();

        var result = mapper.update(source, TaskEntity.builder().build());

        assertEquals(source, result);
    }
}
