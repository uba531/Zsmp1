package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Talent;

public interface TalentRepository extends JpaRepository<Talent, Integer>{
	
}
