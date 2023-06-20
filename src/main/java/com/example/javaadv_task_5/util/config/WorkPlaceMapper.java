package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.WorkPlace;
import com.example.javaadv_task_5.dto.WorkPlaceDto;
import com.example.javaadv_task_5.dto.WorkPlaceReadDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {WorkPlaceMapper.class})
public interface WorkPlaceMapper {
  WorkPlaceMapper INSTANCE = Mappers.getMapper(WorkPlaceMapper.class);

  WorkPlace fromWorkPlaceDto(WorkPlaceDto workPlaceDto);
  WorkPlaceDto toWorkPlaceDto(WorkPlace workPlace);
  WorkPlace fromWorkPlaceReadDto(WorkPlaceReadDto workPlaceReadDto);
  WorkPlaceReadDto toWorkPlaceReadDto(WorkPlace workPlace);
}
