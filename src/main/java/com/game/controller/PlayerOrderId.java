package com.game.controller;

public enum PlayerOrderId {
    ID("id");
    private final String fieldName;

     PlayerOrderId(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
