package com.game;

import exceptions.ApplicationException;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Properties;

import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestLoaderConfigLifeGame {

    private final LoaderConfigLifeGame loader = new LoaderConfigLifeGame();

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCheckProperties() {
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        try {
            loader.checkProperties(properties);
        } catch (ApplicationException  e) {
            fail("Should not have thrown any exception");
        }
    }

    @Test
    public void testCheckPropertiesExceptionNotDefinedAttribute() {
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("file","5");

        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("is not defined"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotNumberAttribute() {
        Properties properties = new Properties();
        properties.setProperty("X","I5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("is not a number"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotPositiveNumberAttribute() {
        Properties properties = new Properties();
        properties.setProperty("X","-5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");
        properties.setProperty("file","5");

        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("It must be positive"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesExceptionNotDefineFileMatrix() {
        Properties properties = new Properties();
        properties.setProperty("X","5");
        properties.setProperty("Y","5");
        properties.setProperty("count","3");

        thrown.expect(ApplicationException.class);
        thrown.expectMessage(CoreMatchers.containsString("Not defined file with init matrix"));

        loader.checkProperties(properties);
    }

    @Test
    public void testCheckPropertiesLoadProperties() {
        loader.loadProperties();

        Assert.assertEquals(5,loader.getSizeX());
        Assert.assertEquals(5,loader.getSizeY());
        Assert.assertEquals(4,loader.getCountTicks());
        Assert.assertEquals("initMatrix.txt",loader.getFileMatrix());
    }
}
