package com.example.animals.service;

import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.models.Animal;
import com.example.animals.repo.AnimalRepository;
import com.example.animals.services.implementations.AnimalServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
public class AnimalServiceTest {

    @Mock
    AnimalRepository animalRepository;

    @InjectMocks
    private AnimalServiceImpl animalService;


    private Animal animal1;
    private Animal animal2;

    private AnimalDto animalDto1;
    private AnimalDto animalDto2;

    private AnimalFilterDto animalFilterDto1;

    @BeforeEach
    void setUp(){
        animal1 = new Animal("Sara", "dog", "female", 15, 20, 2);
        animal2 = new Animal("Lora", "cat", "female", 15, 20, 2);

        animalDto1 = new AnimalDto("Sara","dog", "female", 15, 20);
        animalDto2 = new AnimalDto("Lara","cat", "female", 15, 20);

        animalFilterDto1 = new AnimalFilterDto("name", "dog", "male", 3);
    }


    @Test
    public void AnimalService_AddAnimals_ReturnAddedData(){


        Mockito.when(animalRepository.saveAll(Mockito.any(List.class))).thenReturn(List.of(animal1, animal2));


        List<Animal> savedAnimals = animalService.addAnimals(List.of(animalDto1, animalDto2));

        Assertions.assertThat(savedAnimals).isNotNull();
        Assertions.assertThat(savedAnimals.size()).isEqualTo(2);
        Assertions.assertThat(savedAnimals).isEqualTo(List.of(animal1, animal2));

    }



//    @Test
//    public void AnimalService_getAllWithFilterAndSort_ReturnFilteredData(){
//
//
//        Mockito.when(animalRepository.findAll(Mockito.any(Example.class))).thenReturn(List.of(animal1, animal2));
//
//
//        List<Animal> filteredAnimals = animalService.getAllWithFilterAndSort(animalFilterDto1);
//
//        Assertions.assertThat(filteredAnimals).isNotNull();
//
//    }
}
