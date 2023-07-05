package com.example.javaadv_task_5.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
        RequestDescriptionParser requestDescriptionParser = new RequestDescriptionParser(request);
        ErrorDetails errorDetails = new ErrorDetails("Unknown exception",
            requestDescriptionParser.getResourceURN(),
            requestDescriptionParser.getClientIP(), requestDescriptionParser.getSessionId(),
            requestDescriptionParser.getUserName());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validationExceptionHandler(MethodArgumentNotValidException ex, WebRequest request) {
        RequestDescriptionParser requestDescriptionParser = new RequestDescriptionParser(request);
        ErrorDetails errorDetails = new ErrorDetails("Some field value is invalid",
            requestDescriptionParser.getResourceURN(), requestDescriptionParser.getClientIP(),
            requestDescriptionParser.getSessionId(), requestDescriptionParser.getUserName());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        /* ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));*/
        RequestDescriptionParser requestDescriptionParser = new RequestDescriptionParser(request);
        ErrorDetails errorDetails = new ErrorDetails("Employee was not found",
            requestDescriptionParser.getResourceURN(), requestDescriptionParser.getClientIP(),
            requestDescriptionParser.getSessionId(), requestDescriptionParser.getUserName());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceWasDeletedException.class)
    protected ResponseEntity<?> handleDeleteException(WebRequest request) {
        RequestDescriptionParser requestDescriptionParser = new RequestDescriptionParser(request);
        ErrorDetails errorDetails = new ErrorDetails("This user was deleted",
            requestDescriptionParser.getResourceURN(), requestDescriptionParser.getClientIP(),
            requestDescriptionParser.getSessionId(), requestDescriptionParser.getUserName());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OneToOneRelationException.class)
    public ResponseEntity<?> handleOneToOneRelationException(WebRequest request) {
        RequestDescriptionParser requestDescriptionParser = new RequestDescriptionParser(request);
        ErrorDetails errorDetails = new ErrorDetails("Passport is already handled",
            requestDescriptionParser.getResourceURN(), requestDescriptionParser.getClientIP(),
            requestDescriptionParser.getSessionId(), requestDescriptionParser.getUserName());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
