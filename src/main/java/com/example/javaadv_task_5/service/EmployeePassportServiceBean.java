package com.example.javaadv_task_5.service;

import com.example.javaadv_task_5.domain.EmployeePassport;
import com.example.javaadv_task_5.domain.Photo;
import com.example.javaadv_task_5.repository.EmployeePassportRepository;
import com.example.javaadv_task_5.repository.PhotoRepository;
import com.example.javaadv_task_5.util.exception.OneToOneRelationException;
import com.example.javaadv_task_5.util.exception.ResourceNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class EmployeePassportServiceBean implements EmployeePassportService {
  private final EmployeePassportRepository employeePassportRepository;
  private final PhotoRepository photoRepository;

  public EmployeePassportServiceBean(EmployeePassportRepository employeePassportRepository,
      PhotoRepository photoRepository) {
    this.employeePassportRepository = employeePassportRepository;
    this.photoRepository = photoRepository;
  }

  @Override
  public EmployeePassport create(EmployeePassport employeePassport) {
    return employeePassportRepository.save(employeePassport);
  }

  @Override
  public List<EmployeePassport> readAll() {
    return employeePassportRepository.findAll();
  }

  @Override
  public EmployeePassport read(Long id) {
    return employeePassportRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
  }

  @Override
  public List<EmployeePassport> delete(Long id) {
    EmployeePassport employeePassport = employeePassportRepository.findById(id)
        .orElseThrow(ResourceNotFoundException::new);
    employeePassport.deprive();
    employeePassportRepository.save(employeePassport);
    return employeePassportRepository.findAll();
  }

  public List<EmployeePassport> getaEmployeePassportsHistory() {
    return employeePassportRepository.findAll().stream()
        .filter(ep -> ep.getPreviousPassportId() != null)
        .toList();
  }

  @Override
  public EmployeePassport updatePhoto(Long passportId, Long photoId) {
    EmployeePassport employeePassport = employeePassportRepository.findById(passportId)
        .orElseThrow(ResourceNotFoundException::new);
    Photo photo = photoRepository.findById(photoId).orElseThrow(ResourceNotFoundException::new);
    if (photo.isAttached()) {
      throw new OneToOneRelationException();
    }
    photo.attach();
    photoRepository.save(photo);
    if (employeePassport.getPhoto() == null) {
      employeePassport.setPhoto(photo);
    } else {
      if (employeePassport.getPhoto().getDate().before(photo.getDate())) {
        employeePassport.setPhoto(photo);
      }
    }
    return employeePassportRepository.save(employeePassport);
  }

  @Override
  public EmployeePassport detachPhoto(Long passportId) {
    EmployeePassport employeePassport = employeePassportRepository.findById(passportId)
        .orElseThrow(ResourceNotFoundException::new);
    Photo photo = employeePassport.getPhoto();
    if (photo != null) {
      photo.detach();
      photoRepository.save(photo);
      employeePassport.setPhoto(null);
    }
    return employeePassportRepository.save(employeePassport);
  }
}
