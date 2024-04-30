package com.example.animals.dto;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@XmlRootElement(name = "AnimalDto")
public class AnimalDto {

    private String name;

    private String type;

    private String sex;

    private Integer weight;

    private Integer cost;

    public boolean isAllArgsAppear() {
        return  name !=null && type !=null && sex !=null && weight !=null && cost !=null;
    }

    public Integer getCategory () {
        if (cost == null && cost < 0) return null;
        if (cost >= 0 && cost <= 20) return 1;
        else if (cost >= 21 && cost <= 40) return 2;
        else if (cost >= 41 && cost <= 60) return 3;
        else if (cost >= 61) return 4;
        else return null;
    }
}
