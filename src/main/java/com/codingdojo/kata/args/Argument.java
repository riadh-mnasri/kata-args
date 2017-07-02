package com.codingdojo.kata.args;

import java.util.ArrayList;
import java.util.List;

public class Argument {

    private char token;
    private boolean hasValue;
    private List<String> values = new ArrayList<>();

    public Argument(char token, boolean hasValue, List<String> values) {
        this.token = token;
        this.hasValue = hasValue;
        this.values = values;
    }
}
