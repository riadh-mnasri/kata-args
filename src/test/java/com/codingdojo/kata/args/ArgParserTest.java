package com.codingdojo.kata.args;


import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ArgParserTest {

    private ArgParser argParser;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }


    @Test(expected = IllegalArgumentException.class)
    public void test_with_no_schema_and_any_argument() throws Exception {
       argParser.validate("","toto");
    }



    @Test(expected = IllegalArgumentException.class)
    public void test_with_bad_schema_and_no_argument() throws Exception {
        argParser.validate("toto","");
    }


    @Test//"",-l
    public void test_with_one_argument_and_no_schema() throws Exception {
    }

    @Test//"", -l -p 8080 -d /usr/logs
    public void test_with_many_arguments_and_no_schema() throws Exception {
    }

    @Test//123, -l
    public void test_non_letter_schema() throws Exception {
    }

    @Test//test ###
    public void test_invalid_argument_format() throws Exception {
    }

    @Test//a:boolean -a
    public void test_simple_boolean_value() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-a")).isTrue();
    }

    @Test//a:boolean -a
    public void return_false_when_argument_doesnt_match_schema() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-b")).isFalse();
    }

    @Test//a:boolean -a
    public void return_false_when_argument_doesnt_have_bad_type() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-a 3")).isFalse();
    }

    @Test
    public void return_true_when_argument_have_correct_type() throws Exception {
        Assertions.assertThat(argParser.validate("a:boolean", "-a false")).isTrue();
    }

    @Test//a:boolean b:boolean -a true -b false
    public void test_multiple_boolean_arguments() throws Exception {}


    /*
    @Test//s:string -s toto
    public void test_simple_string_value() throws Exception {
    }

    @Test//a:string b:string -a toto -b titi
    public void test_multiple_string_value() throws Exception {
    }

    @Test//a:string b:integer -a azerty -b 343332
    public void test_multiple_mixed_types_with_bad_type() throws Exception {
    }

    @Test//x:string y:string -x toto -y
    public void test_missing_string_value() throws Exception {
    }
    */

    @After
    public void tearDown() throws Exception {

    }

}
