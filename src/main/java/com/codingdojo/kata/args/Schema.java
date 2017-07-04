package com.codingdojo.kata.args;

import java.util.HashMap;
import java.util.Map;

public class Schema {
    private Map<String, Argument> expectedArguments = new HashMap<>();

    public Schema(Map<String, Argument> expectedArguments) {
        this.expectedArguments = expectedArguments;
    }

    public Map<String, Argument> getExpectedArguments() {
        return expectedArguments;
    }
}
