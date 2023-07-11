package com.example.javaadv_task_5.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class TooManyRelatedEntityInstancesException extends RuntimeException {

}
