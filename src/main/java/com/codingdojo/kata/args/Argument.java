package com.codingdojo.kata.args;

public class Argument {
    private String flag;
    private String valueType;

    public Argument(String flag, String valueType, String value) {
        this.flag = flag;
        this.valueType = valueType;
    }

    public String getFlag() {
        return flag;
    }

    public String getValueType() {
        return valueType;
    }

}
