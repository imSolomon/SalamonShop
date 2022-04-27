package com.example.demo.repository;

import com.example.demo.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface GenderRepository extends JpaRepository<Gender, Long> {


}
