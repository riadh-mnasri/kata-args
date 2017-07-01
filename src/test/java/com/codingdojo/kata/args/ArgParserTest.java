package com.codingdojo.kata.args;


import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgParserTest {
    
    ArgParser argParser;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test
    public void should_flag_with_minus_sign() throws Exception {
        Assertions.assertThat(argParser.validate("-p")).isTrue();
    }


    @Test
    public void should_flag_be_one_character_with_minus_sign() throws Exception {
        Assertions.assertThat(argParser.validate("-gg")).isFalse();
    }


    @Test
    public void should_flag_have_one_value() throws Exception {
        Assertions.assertThat(argParser.validate("-p toto")).isTrue();
    }

    @Test
    public void should_have_many_flags_with_many_arguments() throws Exception {
        Assertions.assertThat(argParser.validate("-p toto -t titi")).isTrue();
    }

    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
