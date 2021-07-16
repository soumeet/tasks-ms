package io.todo.tasksms.dao;

import io.todo.tasksms.dao.model.TaskEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends MongoRepository<TaskEntity, String> {
}
