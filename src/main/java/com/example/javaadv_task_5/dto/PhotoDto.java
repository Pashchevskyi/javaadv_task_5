package com.example.javaadv_task_5.dto;

import java.util.Date;

public record PhotoDto(
    Long id,
    String href,
    Date date,
    Boolean isAttached
) {

}
