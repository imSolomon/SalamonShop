package com.example.demo.service.impl;

import com.example.demo.entity.Gender;
import com.example.demo.repository.GenderRepository;
import com.example.demo.service.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {
    private final GenderRepository genderRepository;

    @Override
    public Gender getGenderById(Long id) {
        return genderRepository.getById(id);
    }

    @Override
    public void createGender(Gender gender) {
        genderRepository.save(gender);
    }

    @Override
    public List<Gender> getAllGender() {
        return genderRepository.findAll();
    }

    @Override
    public Gender save(Gender gender) {
        return genderRepository.save(gender);
    }

    @Override
    public void deleteGender(Long id) {
        genderRepository.deleteById(id);
    }
}
