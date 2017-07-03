package com.codingdojo.kata.args;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import org.springframework.util.StringUtils;

import java.util.*;

public class ArgParser {

    public boolean validate(String args, Schema schema) {
        if (StringUtils.isEmpty(args)) {
            throw new IllegalArgumentException("Arguments could not be empty !");
        }
        Preconditions.checkNotNull(schema);
        Map<String, String> expectedArguments = schema.getExpectedArguments();
        Map<String, String> parsedArguments = parseArguments(args);

        return isMatchingSchema(expectedArguments, parsedArguments);
    }

    private boolean isMatchingSchema(Map<String, String> expectedArguments, Map<String, String> parsedArguments) {
        for (Map.Entry<String, String> entry : parsedArguments.entrySet()) {
            System.out.println("Option : " + entry.getKey() + " Value : " + entry.getValue());
            if (!expectedArguments.containsKey(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    private Map<String, String> parseArguments(String s) {
        Map<String, String> parsedArguments = new HashMap<>();
        List<String> splitArguments = Splitter.on(" ").splitToList(s);

        for (ListIterator<String> listIterator = splitArguments.listIterator(); listIterator.hasNext(); ) {
            String element = listIterator.next();
            if (element.startsWith("-")) {
                String nextElement = listIterator.next();
                if (!nextElement.startsWith("-")) {
                    parsedArguments.put(element.substring(1), String.valueOf(nextElement));
                } else {
                    parsedArguments.put(element.substring(1), "");
                }
                listIterator.previous();
            }
        }

        return parsedArguments;
    }
}
