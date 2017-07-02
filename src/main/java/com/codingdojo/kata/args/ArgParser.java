package com.codingdojo.kata.args;

import com.google.common.base.Splitter;
import org.springframework.util.StringUtils;

import java.util.List;

public class ArgParser {

    private Schema schema;

    public ArgParser(Schema schema) {
        this.schema = schema;
    }

    public boolean validate(String args) {
        if (isFlag(args)) {
            return true;
        } else {
            List<String> splitArguments = Splitter.on(" ").splitToList(args);
            for (int i = 0; i < splitArguments.size(); i++) {
                if (isFlag(splitArguments.get(i)) && !StringUtils.isEmpty(splitArguments.get(i+1))) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isFlag(String argument) {
        return argument.startsWith("-") && argument.length() == 2;
    }

    private boolean isValue(String argumentValue) {
        if (StringUtils.isEmpty(argumentValue)) {
            return true;
        } else {
            return !argumentValue.startsWith("-");
        }
    }
}
