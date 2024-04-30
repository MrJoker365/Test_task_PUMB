package com.example.animals.enums;

public enum Sex {
    MALE("male"),
    FEMALE("female");

    private String description;

    Sex(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public static Sex fromDescription(String description) {
        if (description == null) return null;
        for (Sex type : values()) {
            if (type.description.equalsIgnoreCase(description)) return type;

        }
        throw new IllegalArgumentException("No found Sex with parameter '" + description + "' !!!");
    }

    public static String verify(String description) {
        if (description == null) return null;
        Sex sex = Sex.fromDescription(description);
        return sex.getDescription();
    }
}
