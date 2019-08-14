package com.game;

import exceptions.ApplicationException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Properties;

import static org.junit.Assert.fail;

public class TestLoaderConfigLifeGame {

    private final LoaderConfigLifeGame loader = new LoaderConfigLifeGame();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckProperties() {
        //GIVEN
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        //THEN
        try {
            loader.checkProperties(properties);
        } catch (ApplicationException  e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testCheckPropertiesExceptionNotDefinedAttribute() {
        //GIVEN
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("file","5");

        //THEN
        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("is not defined"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotNumberAttribute() {
        //GIVEN
        Properties properties = new Properties();
        properties.setProperty("X","I5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        //THEN
        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("is not a number"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotPositiveNumberAttribute() {
        //GIVEN
        Properties properties = new Properties();
        properties.setProperty("X","-5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        //THEN
        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("It must be positive"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotDefineFileMatrix() {
        //GIVEN
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");

        //THEN
        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("Not defined file with init matrix"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesLoadProperties() {
        //WHEN
        loader.loadConfig();

        //THEN
        Assert.assertEquals(5,loader.getSizeX());
        Assert.assertEquals(5,loader.getSizeY());
        Assert.assertEquals(2,loader.getCountTicks());
        Assert.assertEquals("initMatrix.txt",loader.getFileMatrix());
    }
}
