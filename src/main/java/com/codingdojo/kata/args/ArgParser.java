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
        Map<String, Argument> expectedArguments = schema.getExpectedArguments();
        Map<String, String> parsedArguments = parseArguments(args);

        return isMatchingSchema(expectedArguments, parsedArguments);
    }

    private boolean isMatchingSchema(Map<String, Argument> expectedArguments, Map<String, String> parsedArguments) {
        boolean result = true;
        if(expectedArguments.isEmpty() || parsedArguments.isEmpty()){
            return false;
        }
        result = isFlagAndTypeMatching(expectedArguments, parsedArguments);

        return result;
    }

    private boolean isFlagAndTypeMatching(Map<String, Argument> expectedArguments, Map<String, String> parsedArguments) {
        //TODO Rework
        boolean result = false;
        for (Map.Entry<String, String> parsedArgument : parsedArguments.entrySet()) {
            String key = parsedArgument.getKey();
            String value = parsedArgument.getValue();
            System.out.println("Option : " + key + " Value : " + value);
            if (!expectedArguments.containsKey(key)) {
                result = false;
                break;
            }else{
                Argument expectedArg = expectedArguments.get(key);
                expectedArg.getValueType();
            }
        }
        return result;
    }

    private Map<String, String> parseArguments(String s) {
        Map<String, String> parsedArguments = new HashMap<>();
        List<String> splitArguments = Splitter.on(" ").splitToList(s);

        for (ListIterator<String> listIterator = splitArguments.listIterator(); listIterator.hasNext(); ) {
            String element = listIterator.next();
            if (element.startsWith("-")) {
                if (listIterator.hasNext()) {
                    String nextElement = listIterator.next();
                    if (!nextElement.startsWith("-")) {
                        parsedArguments.put(element.substring(1), String.valueOf(nextElement));
                    } else {
                        parsedArguments.put(element.substring(1), "");
                    }
                    listIterator.previous();
                } else {
                    parsedArguments.put(element.substring(1), "");
                }
            }
        }
        return parsedArguments;
    }
}
