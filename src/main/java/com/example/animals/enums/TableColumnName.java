package com.example.animals.enums;

public enum TableColumnName {

    NAME("name"),
    TYPE("type"),
    SEX("sex"),
    WEIGHT("weight"),
    COST("cost"),
    CATEGORY("category");



    private String description;

    TableColumnName(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public static TableColumnName fromDescription(String description) {
        for (TableColumnName type : values()) {
            if (type.description.equalsIgnoreCase(description)) return type;

        }
        throw new IllegalArgumentException("No found TableColumnName with parameter '" + description + "' !!!");
    }

    public static String verify(String description) {
        TableColumnName tableColumnName = TableColumnName.fromDescription(description);
        return tableColumnName.getDescription();
    }
}
