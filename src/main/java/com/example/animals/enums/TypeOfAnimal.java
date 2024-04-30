package com.example.animals.enums;

public enum TypeOfAnimal {
    CAT("cat"),
    DOG("dog");

    private String description;

    TypeOfAnimal(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public static TypeOfAnimal fromDescription(String description) {
        if (description == null) return null;
        for (TypeOfAnimal type : values()) {
            if (type.description.equalsIgnoreCase(description)) return type;

        }
        throw new IllegalArgumentException("No found TypeOfAnimal with parameter '" + description + "' !!!");
    }

    public static String verify(String description) {
        if (description == null) return null;
        TypeOfAnimal aypeOfAnimal = TypeOfAnimal.fromDescription(description);
        return aypeOfAnimal.getDescription();
    }

}
