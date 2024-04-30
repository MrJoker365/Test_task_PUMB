package com.example.animals.controllers;

import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.enums.TableColumnName;
import com.example.animals.models.Animal;
import com.example.animals.repo.AnimalRepository;
import com.example.animals.services.implementations.AnimalServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Tag(name = "main")
@RestController
@RequestMapping(value = "/files"/*, consumes = MediaType.APPLICATION_XML_VALUE*/)
@RequiredArgsConstructor
@CrossOrigin
public class AnimalController {

    private final AnimalServiceImpl animalService;



    @Operation(
            summary = "Добавлення інформації про тварин з файлів XML або CSV",

            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Добавлення інформації про тварин лише зі всіма заповненими полями",
            content = {

                    @Content( schema = @Schema(implementation = Animal.class),
                            examples = @ExampleObject(name = "example_1", value = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                                            "<animals>\n" +
                                            "\t<animal>\n" +
                                            "\t\t<name>Milo</name>\n" +
                                            "\t\t<type>cat</type>\n" +
                                            "\t\t<sex>male</sex>\n" +
                                            "\t\t<weight>40</weight>\n" +
                                            "\t\t<cost>51</cost>\n" +
                                            "\t</animal>\n" +
                                            "\t<animal>\n" +
                                            "\t\t<name>Simon</name>\n" +
                                            "\t\t<type>dog</type>\n" +
                                            "\t\t<sex>male</sex>\n" +
                                            "\t\t<weight>45</weight>\n" +
                                            "\t\t<cost>17</cost>\n" +
                                            "\t</animal>\n" +
                                            "</animals>" ),
                            mediaType="application/xml"),

                    @Content( schema = @Schema(implementation = Animal.class),
                            examples = @ExampleObject(name = "example_1", value = "Name,Type,Sex,Weight,Cost\nBuddy,cat,female,41,78\nDuke,cat,male,33,108" ),
                            mediaType="text/csv"),

                    @Content(
                            array = @ArraySchema(schema = @Schema(implementation = AnimalDto.class)),
                            mediaType = "application/json")

            }
            )
    )

    @PostMapping(value = "/uploads")
    public ResponseEntity<?> saveAnimals(@RequestBody List<AnimalDto> animals) {

        List<Animal> savedAnimals = animalService.addAnimals(animals);

        return new ResponseEntity<>(savedAnimals, HttpStatus.OK);
    }





    @PostMapping(value = "/uploads", consumes = "text/csv")
    public ResponseEntity<?> saveAnimalsXml(@RequestBody List<Map<String, String>> csvData) throws IOException {

        List<AnimalDto> animalDtos = csvData.stream()
                .map(map -> {
                    return AnimalDto.builder()
                            .name(map.get("Name"))
                            .type(map.get("Type"))
                            .sex(map.get("Sex"))
                            .weight(map.get("Weight").equals("") ? null : Integer.parseInt(map.get("Weight") ))
                            .cost(map.get("Cost").equals("") ? null : Integer.parseInt(map.get("Cost") ))
                            .build();
                })
                .collect(Collectors.toList());


        List<Animal> savedAnimals = animalService.addAnimals(animalDtos);

        return new ResponseEntity<>(savedAnimals, HttpStatus.OK);
    }








//    @GetMapping("/get")
//    public List<Animal> getWithFilter(@RequestBody AnimalFilterDto animalFilterDto,
//                                      @RequestParam(name = "sort_by", defaultValue = "name") String sort_by) throws IllegalArgumentException {
//        TableColumnName.fromDescription(sort_by);
//
//        Animal animal = new Animal();
//
//        animal.setType(animalFilterDto.getType());
//        animal.setSex(animalFilterDto.getSex());
//        animal.setCategory(animalFilterDto.getCategory());
//
//        Example<Animal> example = Example.of(animal);
//
//
//        return animalRepository.findAll(example, Sort.by(sort_by));
//    }


    @Operation(
            summary = "Отримання відсортованих даних за параметрами",

            parameters = {
                    @Parameter(
                            name = "sort_by",
                            description = "Сортування по назві колонки",
                            example = "name"),
                    @Parameter(
                            name = "type",
                            description = "Фільтрувати за Type",
                            example = "cat"),
                    @Parameter(
                            name = "sex",
                            description = "Фільтрувати за Sex",
                            example = "male"),
                    @Parameter(
                            name = "category",
                            description = "Фільтрувати за Category",
                            example = "3"),
            }
    )

    @GetMapping(value = "/get"/*, produces = "text/csv"*/)
    public ResponseEntity<?> getWithFilter(@RequestParam(name = "sort_by", defaultValue = "name") String sort_by,
                                      @RequestParam(name = "type", required = false) String type,
                                      @RequestParam(name = "sex", required = false) String sex,
                                      @RequestParam(name = "category", required = false) Integer category) throws IllegalArgumentException {

        try {
            AnimalFilterDto animalFilterDto = new AnimalFilterDto(sort_by ,type, sex, category);
            List<Animal> filteredAndSortedAnimals = animalService.getAllWithFilterAndSort(animalFilterDto);
            return new ResponseEntity<>(filteredAndSortedAnimals, HttpStatus.OK);
        }catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Incorrectly entered data: " + e, HttpStatus.BAD_REQUEST);
        }

    }


}
