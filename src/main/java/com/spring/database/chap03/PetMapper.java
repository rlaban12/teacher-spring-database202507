package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

// CRUD를 명세
@Mapper
public interface PetMapper {

    // CREATE
    boolean save(Pet pet);

    // READ - SINGLE MATCHING
    Pet findById(Long id);

    // READ - Multiple Matching
    List<Pet> findAll();

    // UPDATE
    boolean update(Pet pet);

    // DELETE
    boolean deleteById(Long id);

    // READ - COUNT
    int petCount();
}
