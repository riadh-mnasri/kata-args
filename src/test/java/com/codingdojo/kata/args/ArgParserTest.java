package com.codingdojo.kata.args;


import com.google.common.collect.Maps;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.Map.Entry;

import static java.lang.System.out;

public class ArgParserTest {

    ArgParser argParser = null;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test
    public void test_one_argument_without_Schema() throws Exception {
        Assertions.assertThat(argParser.validate("-p", new Schema(Collections.emptyMap()))).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_without_argument_with_Schema() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("p", new Argument("p", "boolean", "true"));
        argParser.validate("", new Schema(expectedArguments));
    }


    @Test
    public void test_one_argument_with_Schema() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("p", new Argument("p", "boolean", "true"));
        Assertions.assertThat(argParser.validate("-p", new Schema(expectedArguments))).isTrue();
    }

    @Test
    public void test_many_arguments_with_Schema() throws Exception {
        //-l -p 8080 -d /usr/logs
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("l", new Argument("l", "boolean", "true"));
        expectedArguments.put("p", new Argument("p", "integer", "8080"));
        expectedArguments.put("d", new Argument("d", "string", "/usr/logs"));
        Assertions.assertThat(argParser.validate("-l -p 8080 -d /usr/logs", new Schema(expectedArguments))).isTrue();
    }

    @Test
    public void test_invalid_format_argument() throws Exception {
        Assertions.assertThat(argParser.validate("toto", new Schema(Collections.emptyMap()))).isFalse();
    }

    @Test
    public void test_argument_with_non_corresponding_schema() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("l", new Argument("l", "boolean", "true"));
        Assertions.assertThat(argParser.validate("-t test", new Schema(expectedArguments))).isFalse();
    }

    @Test
    public void test_argument_with_schema_having_types() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("l", new Argument("l", "boolean", "false"));
        Assertions.assertThat(argParser.validate("-l true", new Schema(expectedArguments)));
    }


    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
