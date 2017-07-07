package com.codingdojo.kata.args;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class ArgParserTest {

    private ArgParser argParser = null;

    @Before
    public void setUp() throws Exception {
        argParser = new ArgParser();
    }

    @Test(expected = IllegalArgumentException.class)
    @Parameters({
            ",test",
            "test, ",
            ", -l",
            ", -l -p 8080 -d /usr/logs",
            "123, -l",
            "test, ###",
            "a:string b:integer, -a azerty -b toto",
            "x:string y:string, -x toto -y"})
    public void test_limit_cases(String schema, String arguments) throws Exception {
        argParser.validate(schema, arguments);
    }

    @Test
    @Parameters({
            "a:boolean, -a",
            "a:boolean b:boolean, -a true -b false",
            "s:string, -s toto",
            "a:string b:string, -a toto -b titi"
    })
    public void test_nominal_cases(String schema, String arguments) throws Exception {
        Assertions.assertThat(argParser.validate(schema, arguments)).isTrue();
    }

    @After
    public void tearDown() throws Exception {
      argParser = null;
    }

}
