package com.example.javaadv_task_5.util.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private ErrorDetailedDescription details;

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    private static class ErrorDetailedDescription {
        private String resourceURN = "";
        private String clientIP;
        private String sessionId = "";
        private String userName = "";
    }

    public ErrorDetails(String message, String resourceURN, String clientIP, String sessionId, String userName) {
        super();
        this.timestamp = new Date();
        this.message = message;

        this.details = new ErrorDetailedDescription(resourceURN, clientIP, sessionId, userName);
    }
}
