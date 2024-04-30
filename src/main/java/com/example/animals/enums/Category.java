package com.example.animals.enums;

public enum Category {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private Integer description;

    Category(Integer description) {
        this.description = description;
    }

    public Integer getDescription(){
        return description;
    }

    public static Category fromDescription(Integer description) {
        if (description == null) return null;
        for (Category type : values()) {
            if (type.description == description) return type;

        }
        throw new IllegalArgumentException("No found Category with parameter '" + description + "' !!!");
    }

    public static Integer verify(Integer description) {
        if (description == null) return null;
        Category category = Category.fromDescription(description);
        return category.getDescription();
    }
}
