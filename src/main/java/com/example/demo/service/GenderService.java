package com.example.demo.service;

import com.example.demo.entity.Gender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GenderService {
    Gender getGenderById(Long id);
    void createGender(Gender gender);
    List<Gender> getAllGender();
    Gender save(Gender gender);
    void deleteGender(Long id);
}
