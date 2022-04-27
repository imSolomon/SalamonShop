package com.example.demo.service;

import com.example.demo.entity.Size;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SizeService {
    Size addSize(Size size);
    List<Size> getAllSize();
    Size saveSize(Size size);
    void deleteSize(Size size);
}
