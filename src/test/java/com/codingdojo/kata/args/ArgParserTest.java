package com.codingdojo.kata.args;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

public class ArgParserTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test//"",toto
    public void test_with_no_schema_and_any_argument() throws Exception {
    }

    @Test//toto, ""
    public void test_with_bad_schema_and_no_argument() throws Exception {
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
    }

    @Test//a:boolean b:boolean -a true -b false
    public void test_multiple_boolean_arguments() throws Exception {
    }

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

    @After
    public void tearDown() throws Exception {

    }

}
