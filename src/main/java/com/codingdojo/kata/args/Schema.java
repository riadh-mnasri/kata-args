package com.codingdojo.kata.args;

import java.util.HashMap;
import java.util.Map;

public class Schema {
    private Map<String, String> expectedArguments = new HashMap<>();

    public Schema(Map<String, String> expectedArguments) {
        this.expectedArguments = expectedArguments;
    }

    public Map<String, String> getExpectedArguments() {
        return expectedArguments;
    }
}
