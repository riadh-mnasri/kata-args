package com.codingdojo.kata.args;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.*;

public class ArgParser {

    private final static Logger LOGGER = LoggerFactory.getLogger(ArgParser.class);

    private static final String SCHEMA_REGEXP = "([a-z]?:[a-z\\s]+)*";
    private static final String ARGUMENTS_REGEXP = "(-[a-z]?\\s*[a-z0-9/\\s]*)*";

    private Map<String, String> parsedSchema = Maps.newHashMap();
    private Map<String, String> parsedArguments = Maps.newHashMap();
    private Map<String, Object> typedArguments = Maps.newHashMap();


    public boolean validate(String schema, String arguments) {
        boolean result = false;
        if (StringUtils.isEmpty(arguments) || StringUtils.isEmpty(schema)) {
            return false;
        }
        // a:boolean b:boolean
        if (!schema.matches(SCHEMA_REGEXP)) {
            throw new IllegalArgumentException("Bad Schema");
        }
        if (!arguments.matches(ARGUMENTS_REGEXP)) {
            throw new IllegalArgumentException("Bad Argument");
        }

        parsedSchema = parseSchema(schema);
        parsedArguments = parseArguments(arguments);

        for (Map.Entry<String, String> schemaToken : parsedSchema.entrySet()) {
            Object argumentValue = getArgumentValue(schemaToken.getKey(), schemaToken.getValue());
            typedArguments.put(schemaToken.getKey(), argumentValue);
        }
        LOGGER.info("Typed arguments :: " + Arrays.asList(typedArguments));
        return isArgumentsMatchingSchema(parsedSchema, typedArguments);
    }

    public boolean isArgumentsMatchingSchema(Map<String, String> parsedSchema, Map<String, Object> typedArguments) {
        boolean result = false;
        for (Map.Entry<String, Object> parsedArgument : typedArguments.entrySet()) {
            result = parsedSchema.containsKey(parsedArgument.getKey()) ? true : false;
        }
        return result;
    }


    private Object getArgumentValue(String arg, String valueType) {
        Object result = null;
        LOGGER.info("getArgumentValue >>>> arg ::" + arg + " type :: " + valueType);
        String argValue = parsedArguments.get(arg);
        if ("boolean".equals(valueType)) {
            if (argValue.isEmpty()) {
                result = true;
            } else {
                result = Boolean.valueOf(argValue);
            }
        } else if ("integer".equals(valueType)) {
            result = Integer.valueOf(argValue);
        } else if ("string".equals(valueType)) {
            result = argValue;
        } else {
            throw new IllegalArgumentException("Unknown argument type");
        }
        if (String.valueOf(result).isEmpty()) {
            throw new IllegalArgumentException("Argument value couldn't be empty for [" + arg + "]");
        }
        return result;
    }


    //Arguments sample :  -l -p 8080 -d /usr/logs
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

    //Schema sample : "a:boolean b:integer c:string"
    private Map<String, String> parseSchema(String schema) {
        Map<String, String> parsedSchema = new HashMap<>();
        List<String> splitSchemaTokens = Splitter.on(" ").splitToList(schema);
        for (int i = 0; i < splitSchemaTokens.size(); i++) {
            String token = splitSchemaTokens.get(i);
            List<String> arg = Splitter.on(":").splitToList(token);
            if (arg.size() == 2) {
                LOGGER.info("schema flag :: " + arg.get(0) + " schema type :: " + arg.get(1));
                parsedSchema.put(arg.get(0), arg.get(1));
            }
        }
        return parsedSchema;
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
