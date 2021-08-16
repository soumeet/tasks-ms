package io.todo.task.dao;

import io.todo.task.dao.entity.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<TaskEntity, String> {
}
