package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.hogwarts.school.exception.AvatarException;
import ru.hogwarts.school.exception.FacultyException;
import ru.hogwarts.school.exception.StudentException;

@ControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    @ExceptionHandler({FacultyException.class, StudentException.class, AvatarException.class})
    public ResponseEntity<String> handleHogwartsException (RuntimeException ex) {
        logger.warn(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ex.getMessage());
    }

    @ExceptionHandler (Exception.class)
        public ResponseEntity<String> handleException(Exception exception){
        logger.error(String.valueOf(exception));
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Server error sorry");
    }
}
