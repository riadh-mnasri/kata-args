package com.codingdojo.kata.args;


import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgParserTest {

    ArgParser argParser = null;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test
    public void test_with_no_argument_and_no_schema() throws Exception {
        Assertions.assertThat(argParser.validate("", "")).isFalse();
    }

    @Test
    public void test_with_one_argument_and_no_schema() throws Exception {
        Assertions.assertThat(argParser.validate("", "-l")).isFalse();
    }

    @Test
    public void test_with_many_arguments_and_no_schema() throws Exception {
        Assertions.assertThat(argParser.validate("", "-l -p 8080 -d /usr/logs")).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_non_letter_schema() throws Exception {
        Assertions.assertThat(argParser.validate("123", "-l")).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_argument_format() throws Exception {
        argParser.validate("test", "####");
    }

    @Test
    public void test_simple_boolean_value() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-a")).isTrue();
        Boolean arg = (Boolean) argParser.getTypedArguments().get("a");
        Assertions.assertThat(arg).isEqualTo(true);
    }

    @Test
    public void test_multiple_boolean_arguments() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean b:boolean", "-a true -b false")).isTrue();
    }

    @Test
    public void test_simple_string_value() throws Exception {
        Assertions.assertThat(argParser.validate("s:string", "-s toto")).isTrue();
    }

    @Test
    public void test_multiple_string_value() throws Exception {
        Assertions.assertThat(argParser.validate("s:string b:string", "-s toto -b titi")).isTrue();
    }

    @Test
    public void test_multiple_mixed_types() throws Exception {
        Assertions.assertThat(argParser.validate("l:boolean p:integer d:string", "-l -p 8080 -d /usr/logs")).isTrue();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_missing_string_value() throws Exception {
        Assertions.assertThat(argParser.validate("s:string", "-s"));
    }

    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
