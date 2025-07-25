package com.spring.database.chap03;

import com.spring.database.chap03.entity.Pet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetMapperTest {

    @Autowired PetMapper petMapper;


    @Test
    @DisplayName("save test")
    void saveTest() {
        //given
        Pet newPet = Pet.builder()
                .petName("꿀꿀이")
                .petAge(8)
                .injection(true)
                .build();
        //when
        boolean save = petMapper.save(newPet);
        //then
        assertTrue(save);
    }


}