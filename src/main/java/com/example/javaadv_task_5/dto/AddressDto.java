package com.example.javaadv_task_5.dto;

import java.util.Date;

//@Accessors(chain = true)
public record AddressDto(Long id, Boolean addressHasActive, String country, String city, String street, Date date) {
}
