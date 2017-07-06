package com.codingdojo.kata.args;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

public class ArgParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArgParser.class);

    private Map<String, String> parsedSchema = Maps.newHashMap();
    private Map<String, String> parsedArguments = Maps.newHashMap();
    private Map<String, Object> typedArguments = Maps.newHashMap();

    public boolean validate(String schema, String arguments) {
        boolean result = false;
        if (StringUtils.isEmpty(schema) || !schema.matches("([a-z]?:[a-z\\s]+)*")) {
            throw new IllegalArgumentException("Bad schema !!");
        }

        if (StringUtils.isEmpty(arguments) || !arguments.matches("(-[a-z]?\\s*[a-z0-9/\\s]*)*")) {
            throw new IllegalArgumentException("Bad arguments !!");
        }

        parsedSchema = parseSchema(schema);

        parsedArguments = parseArguments(arguments);

        typedArguments = parseTypes(parsedArguments);

        result = isArgumentsMatchingSchema(parsedSchema, typedArguments);
        LOGGER.info("Typed arguments :: " + Arrays.asList(typedArguments));

        return result;
    }

    //Schema sample : "a:boolean b:integer c:string"
    private Map<String, String> parseSchema(String schema) {
        Map<String, String> parsedSchema = new HashMap<>();
        List<String> splitSchemTokens = Splitter.on(" ").splitToList(schema);
        for (int i = 0; i < splitSchemTokens.size(); i++) {
            String schemaToken = splitSchemTokens.get(i);
            List<String> arg = Splitter.on(":").splitToList(schemaToken);
            if (arg.size() == 2) {
                parsedSchema.put(arg.get(0), arg.get(1));
            }
        }
        return parsedSchema;
    }

    //Arguments sample : -l -p 8080 -d /usr/logs
    private Map<String, String> parseArguments(String args) {
        Map<String, String> parsedArguments = new HashMap<>();
        List<String> tokens = Splitter.on(" ").splitToList(args);
        for (ListIterator<String> listIterator = tokens.listIterator(); listIterator.hasNext(); ) {
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
        LOGGER.info("Parsed arguments :: " + Arrays.asList(parsedArguments));
        return parsedArguments;
    }

    private Map<String, Object> parseTypes(Map<String, String> parsedArguments) {
        for (Map.Entry<String, String> schemaToken : parsedSchema.entrySet()) {
            Object argumentValue = getArgumentValue(schemaToken.getKey(), schemaToken.getValue());
            typedArguments.put(schemaToken.getKey(), argumentValue);
        }
        return typedArguments;
    }

    private Object getArgumentValue(String arg, String valueType) {
        Object result = null;
        LOGGER.info("Arg ::" + arg + " type :: " + valueType);
        try {
            String argValue = parsedArguments.get(arg);
            if ("boolean".equals(valueType)) {
                if (argValue.isEmpty()) {
                    result = true;
                } else {
                    result = Boolean.valueOf(argValue);
                }
            } else if ("integer".equals(valueType)) {
                if (argValue.isEmpty()) {
                    throw new IllegalArgumentException("Value should not be empty for"+ arg);
                } else {
                    result = Ints.tryParse(argValue);
                }
            } else if ("string".equals(valueType)) {
                if (argValue.isEmpty()) {
                    throw new IllegalArgumentException("Value should not be empty for"+ arg);
                } else {
                    result = argValue;
                }
            } else {
                throw new IllegalArgumentException("Unknown argument type");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Unknown argument type for [" + arg + "]");
        }
        return result;
    }

    public boolean isArgumentsMatchingSchema(Map<String, String> parsedSchema, Map<String, Object> typedArguments) {
        boolean result = false;
        for (Map.Entry<String, Object> typedArgument : typedArguments.entrySet()) {
            result = parsedSchema.containsKey(typedArgument.getKey()) ? true : false;
        }
        return result;
    }

    public static void main(String[] args) {
        ArgParser argParser = new ArgParser();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your schema: ");
        String schema = scanner.nextLine();
        System.out.print("Enter your arguments: ");
        String arguments = scanner.nextLine();
        System.out.println("You have indicated schema :: [" + schema + "] and arguments :: [" + arguments + "]");
        boolean result = argParser.validate(schema, arguments);
        if (result == true) {
            System.out.println("OK - Your arguments are valid");
            System.out.println(argParser.getParsedArguments());
        } else {
            System.err.println("KO - Invalid arguments based on your schema !!");
        }
    }

    public Map<String, String> getParsedSchema() {
        return parsedSchema;
    }

    public Map<String, String> getParsedArguments() {
        return parsedArguments;
    }

    public Map<String, Object> getTypedArguments() {
        return typedArguments;
    }

}
