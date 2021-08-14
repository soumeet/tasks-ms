package io.todo.task.mapper;

import io.todo.task.dao.entity.TaskEntity;
import io.todo.task.model.Task;
import org.mapstruct.Mapper;

@Mapper
public interface TaskEntityTaskMapper {

    TaskEntity taskToTaskEntity(Task task);
    Task taskEntityToTask(TaskEntity taskEntity);
}
