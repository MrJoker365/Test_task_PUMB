package com.example.animals.controller;

import com.example.animals.controllers.AnimalController;
import com.example.animals.dto.AnimalDto;
import com.example.animals.dto.AnimalFilterDto;
import com.example.animals.models.Animal;
import com.example.animals.services.AnimalService;
import com.example.animals.services.implementations.AnimalServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class AnimalControllerTest {

    @Mock
    private AnimalServiceImpl animalService;

    @InjectMocks
    private AnimalController animalController;

    private MockMvc mockMvc;

    private Animal animal1;
    private Animal animal2;

    private String animalJson1;
    private String animalJson2;
    private AnimalDto animalDto1;
    private AnimalDto animalDto2;

    private ObjectMapper objectMapper;


    @BeforeEach
    void setUp() throws JsonProcessingException {
        mockMvc = MockMvcBuilders.standaloneSetup(animalController).build();

        animal1 = new Animal("Sara", "dog", "female", 15, 20, 2);
        animal2 = new Animal("Lora", "cat", "female", 15, 20, 2);

        animalDto1 = new AnimalDto("Sara","dog", "female", 15, 20);
        animalDto2 = new AnimalDto("Lara","cat", "female", 15, 20);

        objectMapper = new ObjectMapper();

        animalJson1 = objectMapper.writeValueAsString(animal1);
        animalJson2 = objectMapper.writeValueAsString(animal2);
    }


    @Test
    void AnimalController_SaveAll_GetSavedData () throws Exception {
        Mockito.when(animalService.addAnimals(Mockito.any(List.class))).thenReturn(List.of(animal1, animal2));


        String res = "[" + animalJson1 + "," + animalJson2 + "]";

        System.out.println(res);

        mockMvc.perform(post("/files/uploads")
                .contentType(MediaType.APPLICATION_JSON)
                .content(res))
                .andExpect(status().isOk());


        Mockito.verify(animalService, Mockito.times(1)).addAnimals(Mockito.any(List.class));


    }


    @Test
    void AnimalController_GetWithFilter_GetFilteredData () throws Exception {
        Mockito.when(animalService.getAllWithFilterAndSort(Mockito.any(AnimalFilterDto.class))).thenReturn(List.of(animal1, animal2));

        String res = "[" + animalJson1 + "," + animalJson2 + "]";

        mockMvc.perform(get("/files/get")
                .contentType(MediaType.APPLICATION_JSON)
                .content(res))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$").value(List.of(animal1, animal2)));
//        Mockito.verify(animalService, Mockito.times(1)).addAnimals(Mockito.any(List.class));


    }

    @Test
    void AnimalController_GetWithFilter_GetFilteredData_WithError () throws Exception {
        Mockito.when(animalService.getAllWithFilterAndSort(Mockito.any(AnimalFilterDto.class))).thenThrow(new IllegalArgumentException("Test error"));

        mockMvc.perform(get("/files/get"))
                .andExpect(status().is4xxClientError());

    }


}
