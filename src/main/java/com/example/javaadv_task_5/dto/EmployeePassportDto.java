package com.example.javaadv_task_5.dto;

import java.time.LocalDateTime;
import java.util.Date;

public record EmployeePassportDto(
    Long id,
    String uuid,
    String series,
    String number,
    String bodyHanded,
    Date handDate,
    LocalDateTime expireDate,
    Boolean isHanded,
    PhotoDto photo
) {

}
