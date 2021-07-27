package io.todo.task.aspect;

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

@Log4j2
@ControllerAdvice
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiExceptionAspect {

    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public ResponseEntity<Error> handleTaskNotFoundError() {
        LOGGER.info("Entry into ApiExceptionAdvice#handleTaskNotFoundError");
        Error error = new Error();
        error.errorCode("TSKNTFND").errorMessage("The provided Task ID is not found");
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
}
