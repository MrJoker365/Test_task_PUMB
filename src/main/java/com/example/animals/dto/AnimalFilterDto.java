package com.example.animals.dto;

import com.example.animals.enums.Category;
import com.example.animals.enums.Sex;
import com.example.animals.enums.TableColumnName;
import com.example.animals.enums.TypeOfAnimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
//@AllArgsConstructor
public class AnimalFilterDto {

    public void setType(String type) {
        TypeOfAnimal.fromDescription(type);
        this.type = type;
    }

    public void setSex(String sex) {
        Sex.fromDescription(sex);
        this.sex = sex;
    }

    public void setCategory(Integer category) {
        Category.fromDescription(category);
        this.category = category;
    }

    public void setSort_by(String sort_by) {
        TableColumnName.fromDescription(sort_by);
        this.sort_by = sort_by;
    }

    public AnimalFilterDto(String sort_by, String type, String sex, Integer category) {
//        TypeOfAnimal.fromDescription(type);
//        Sex.fromDescription(sex);
//        Category.fromDescription(category);

        this.sort_by = TableColumnName.verify(sort_by);
        this.type = TypeOfAnimal.verify(type);
        this.sex = Sex.verify(sex);
        this.category = Category.verify(category);
    }

    private String sort_by;
    private String type;

    private String sex;

    private Integer category;
}
