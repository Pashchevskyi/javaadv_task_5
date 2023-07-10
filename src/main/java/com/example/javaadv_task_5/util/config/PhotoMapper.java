package com.example.javaadv_task_5.util.config;

import com.example.javaadv_task_5.domain.Photo;
import com.example.javaadv_task_5.dto.PhotoDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PhotoMapper {

  PhotoMapper INSTANCE = Mappers.getMapper(PhotoMapper.class);
  PhotoDto photoToPhotoDto(Photo photo);
  Photo photoDtoToPhoto(PhotoDto photoDto);
}
