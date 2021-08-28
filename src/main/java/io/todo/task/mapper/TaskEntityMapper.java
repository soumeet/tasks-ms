package io.todo.task.mapper;

import io.todo.task.dao.entity.TaskEntity;
import org.mapstruct.*;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TaskEntityMapper {
    
    TaskEntity update(TaskEntity source, @MappingTarget TaskEntity target);
}
