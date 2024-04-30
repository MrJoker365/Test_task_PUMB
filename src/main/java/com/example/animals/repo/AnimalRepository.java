package com.example.animals.repo;

import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.models.Animal;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Boolean existsByNameAndTypeAndSexAndWeightAndCost(String name, String type, String sex, Integer weight, Integer cost);

}

