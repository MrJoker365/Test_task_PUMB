package com.example.animals.repository;

import com.example.animals.models.Animal;
import com.example.animals.repo.AnimalRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AnimalRepositoryTests {

    @Autowired
    private AnimalRepository animalRepository;

    Animal animal1 = new Animal("Sara", "dog", "female", 15, 20, 2);
    Animal animal2 = new Animal("Lora", "cat", "female", 15, 20, 2);




    @Test
    public void AnimalRepository_SaveAll_ReturnSavingData() {


        List<Animal> savedAnimals = animalRepository.saveAll(Arrays.asList(animal1, animal2));

        Assertions.assertThat(savedAnimals).isNotNull();
        Assertions.assertThat(savedAnimals.get(1).getId()).isGreaterThan(0);
        Assertions.assertThat(savedAnimals.size()).isEqualTo(2);
    }


    @Test
    public void AnimalRepository_FindAll_ReturnFilteredData(){
        Example<Animal> example1 = Example.of(Animal.builder()
                .type("dog")
                .sex("female")
                .category(2)
                .build());

        Example<Animal> example2 = Example.of(Animal.builder()
                .sex("male")
                .build());

        List<Animal> getAnimals1 = animalRepository.findAll(example1, Sort.by("type"));
        List<Animal> getAnimals2 = animalRepository.findAll(example2, Sort.by("name"));

        Assertions.assertThat(getAnimals1).isNotNull();
        Assertions.assertThat(getAnimals2).isNotNull();

    }



}
