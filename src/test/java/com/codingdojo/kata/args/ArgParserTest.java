package com.codingdojo.kata.args;


import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgParserTest {

    private ArgParser argParser = null;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_with_no_argument_and_no_schema() throws Exception {
        Assertions.assertThat(argParser.validate("", ""));
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_with_one_argument_and_no_schema() throws Exception {
        Assertions.assertThat(argParser.validate("","-l")).isFalse();
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_with_many_arguments_and_no_schema() throws Exception {
        argParser.validate("", "-l -p 8080 -d /usr/logs");
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_non_letter_schema() throws Exception {
        Assertions.assertThat(argParser.validate("123", "-l")).isFalse();
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_invalid_argument_format() throws Exception {
        Assertions.assertThat(argParser.validate("test", "###")).isFalse();
    }


    @Test
    public void test_simple_boolean_value() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-a")).isTrue();
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
        Assertions.assertThat(argParser.validate("a:string b:string", "-a toto -b titi")).isTrue();
    }

    @Test
    public void test_multiple_mixed_types() throws Exception {
        Assertions.assertThat(argParser.validate("a:string b:integer", "-a azerty -b 343332"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_missing_string_value() throws Exception {
        Assertions.assertThat(argParser.validate("x:string y:string", "-x toto -y"));
    }

    @After
    public void tearDown() throws Exception {
        argParser = null;
    }

}
