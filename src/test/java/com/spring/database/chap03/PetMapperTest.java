package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetMapperTest {

    @Autowired PetMapper petMapper;


    @Test
    @DisplayName("save test")
    void saveTest() {
        //given
        Pet newPet = Pet.builder()
                .petName("타이거")
                .petAge(30)
                .injection(false)
                .build();
        //when
        boolean save = petMapper.save(newPet);
        //then
        assertTrue(save);
    }


    @Test
    @DisplayName("update test")
    void updateTest() {
        //given
        Pet updatePet = Pet.builder()
                .petName("랑이랑이")
                .petAge(20)
                .injection(true)
                .id(4L)
                .build();
        //when
        boolean update = petMapper.update(updatePet);
        //then
        assertTrue(update);
    }


    @Test
    @DisplayName("delete test")
    void deleteTest() {
        //given
        Long id = 2L;
        //when
        boolean b = petMapper.deleteById(id);
        //then
        assertTrue(b);
    }


    @Test
    @DisplayName("findAll test")
    void findAllTest() {
        //given

        //when
        List<Pet> petList = petMapper.findAll();
        //then
        petList.forEach(System.out::println);
        assertEquals(3, petList.size());
    }


    @Test
    @DisplayName("findOne test")
    void findOneTest() {
        //given
        Long id = 3L;
        //when
        Pet foundPet = petMapper.findById(id);
        //then
        System.out.println("foundPet = " + foundPet);
        assertEquals("멍멍멍", foundPet.getPetName());
    }


    @Test
    @DisplayName("count test")
    void countTest() {
        //given

        //when
        int count = petMapper.petCount();
        //then
        assertEquals(3, count);
    }


}