package com.codingdojo.kata.args;


import com.google.common.base.Splitter;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Maps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ArgParserTest {

    ArgParser argParser = null;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test
    public void test_one_argument_without_Schema() throws Exception {
        argParser = new ArgParser();
        Assertions.assertThat(argParser.validate("-p", new Schema(Collections.emptyMap()))).isFalse();
    }

    @Test
    public void test_one_argument_with_Schema() throws Exception {
        Map<String, String> expectedArguments = new HashMap<>();
        expectedArguments.put("p", "boolean");
        argParser = new ArgParser();
        Assertions.assertThat(argParser.validate("-p", new Schema(expectedArguments))).isTrue();
    }

    @Test
    public void test_many_arguments_with_Schema() throws Exception {
        //-l -p 8080 -d /usr/logs
        Map<String, String> expectedArguments = new HashMap<>();
        expectedArguments.put("l", "boolean");
        expectedArguments.put("p", "integer");
        expectedArguments.put("d", "string");
        argParser = new ArgParser();
        Assertions.assertThat(argParser.validate("-l -p 8080 -d /usr/logs", new Schema(expectedArguments))).isTrue();
    }

    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
