package com.codingdojo.kata.args;


import com.google.common.base.Splitter;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

public class ArgParserTest {
    
    ArgParser argParser;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should_flag_with_minus_sign() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("-p", new Argument('p', true, Arrays.asList("toto")));
        Schema schema = new Schema(expectedArguments);
        argParser = new ArgParser(schema);
        Assertions.assertThat(argParser.validate("-p")).isTrue();
    }


    @Test
    public void should_flag_be_one_character_with_minus_sign() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("-g", new Argument('g', false, Lists.emptyList()));
        Schema schema = new Schema(expectedArguments);
        argParser = new ArgParser(schema);
        Assertions.assertThat(argParser.validate("-gg")).isFalse();
    }


    @Test
    public void should_flag_have_one_value() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("-p", new Argument('p', true, Arrays.asList("toto")));
        Schema schema = new Schema(expectedArguments);
        Assertions.assertThat(argParser.validate("-p toto")).isTrue();
    }

    @Test
    public void should_have_many_flags_with_many_arguments() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("-p", new Argument('p', true, Arrays.asList("toto")));
        expectedArguments.put("-t", new Argument('t', true, Arrays.asList("titi")));
        Schema schema = new Schema(expectedArguments);
        Assertions.assertThat(argParser.validate("-p toto -t titi")).isTrue();
        //Assertions.assertThat(argParser.validate("-p toto -s")).isTrue();
    }

    @Test
    public void should_arguments_match_schema() throws Exception {
        Map<String, Argument> expectedArguments = new HashMap<>();
        expectedArguments.put("-p", new Argument('p', true, Arrays.asList("toto")));
        Schema schema = new Schema(expectedArguments);
        ArgParser argParser = new ArgParser(schema);


    }

    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
