package io.todo.task.aspect;

import io.todo.task.exceptions.TaskNotCreatedException;
import io.todo.task.model.Error;
import io.todo.task.exceptions.TaskNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static io.todo.task.util.TaskConstants.Exceptions.TASK_NOT_CREATED;
import static io.todo.task.util.TaskConstants.Exceptions.TASK_NOT_FOUND;

@Log4j2
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiExceptionAspect {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleTaskNotFoundError() {
        LOGGER.info("Entry into ApiExceptionAdvice#handleTaskNotFoundError");
        var error = new Error();
        error.errorCode(TASK_NOT_FOUND).errorMessage("The provided Task ID is not found");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @ExceptionHandler(TaskNotCreatedException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Error> handleTaskNotCreatedError() {
        LOGGER.info("Entry into ApiExceptionAdvice#handleTaskNotCreatedError");
        Error error = new Error();
        error.errorCode(TASK_NOT_CREATED).errorMessage("The provided Task is not created");
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error);
    }
}
