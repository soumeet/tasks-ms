package io.todo.task.mapper;

import io.todo.task.dao.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper
public interface TaskEntityMapper {

    TaskEntity update(TaskEntity source, @MappingTarget TaskEntity target);
}
