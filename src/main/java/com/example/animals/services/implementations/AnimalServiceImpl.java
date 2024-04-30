package com.example.animals.services.implementations;

import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.enums.TableColumnName;
import com.example.animals.models.Animal;
import com.example.animals.repo.AnimalRepository;
import com.example.animals.services.AnimalService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimalServiceImpl implements AnimalService {


//    @Autowired
    private final AnimalRepository animalRepository;

    @Override
    public List<Animal> addAnimals(List<AnimalDto> animals) {
        List<Animal> newAnimals = new ArrayList<>();

        animals.stream().filter(x ->
                x.isAllArgsAppear() &&
                        !animalRepository.existsByNameAndTypeAndSexAndWeightAndCost(
                                x.getName(), x.getType(), x.getSex(), x.getWeight(), x.getCost())
        ).forEach(x -> newAnimals.add(new Animal(
                        x.getName(),
                        x.getType(),
                        x.getSex(),
                        x.getWeight(),
                        x.getCost(),
                        x.getCategory()
                ))
        );

        return animalRepository.saveAll(newAnimals);
    }

    @Override
    public List<Animal> getAllWithFilterAndSort(AnimalFilterDto animalFilterDto) {

        Example<Animal> example = Example.of(Animal.builder()
                .type(animalFilterDto.getType())
                .sex(animalFilterDto.getSex())
                .category(animalFilterDto.getCategory())
                .build());


        return animalRepository.findAll(example, Sort.by(animalFilterDto.getSort_by()));
    }
}
