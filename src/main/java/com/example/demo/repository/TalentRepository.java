package com.example.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Talent;

public interface TalentRepository extends JpaRepository<Talent, Integer>{
	Page<Talent> findByTalentNameContaining(String keyword, Pageable pageable);
}
