package com.codingdojo.kata.args;


import com.google.common.base.Splitter;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgParser {

    public boolean validate(String schema, String arguments) {
        Pattern pattern = Pattern.compile("(.):boolean");
        Matcher matcher = pattern.matcher(schema);
        if (matcher.matches()) {
            List<String> splitArguments = Splitter.on(" ").splitToList(arguments);
            if (splitArguments.size() == 2
                    && isTypeValid(splitArguments)) {
                return false;
            }
            return splitArguments.get(0).equals("-" + matcher.group(1));
        }
        throw new IllegalArgumentException();
    }

    private boolean isTypeValid(List<String> splitArguments) {
        return !splitArguments.get(1).equals("false");
    }
}
