package com.example.animals.services;

import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.models.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> addAnimals(List<AnimalDto> animals);

    List<Animal> getAllWithFilterAndSort(AnimalFilterDto animalFilterDto);

}
