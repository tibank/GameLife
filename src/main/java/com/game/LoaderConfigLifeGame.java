package com.game;

import exceptions.ApplicationException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class LoaderConfigLifeGame {

    private int sizeX;
    private int sizeY;
    private int countTicks;
    private String fileMatrix;
    private char[][] matrix;


    public void loadConfig() {
        Properties properties = readProperties();
        checkProperties(properties);

        sizeX = Integer.parseInt(properties.getProperty("X"));
        sizeY = Integer.parseInt(properties.getProperty("Y"));
        countTicks = Integer.parseInt(properties.getProperty("count"));
        fileMatrix = properties.getProperty("file");

        try {
            matrix = readMatrix();
        } catch (IOException ex) {
            throw new ApplicationException("Error reaiding file with matrix",ex);
        }
    }

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new ApplicationException("Failed to load property file", e);
        }

        return properties;
    }

    public void checkProperties(Properties prop) {
        String x = prop.getProperty("X");
        String y = prop.getProperty("Y");
        String count = prop.getProperty("count");
        String fileName = prop.getProperty("file");

        checkNumberProperty("X", x);
        checkNumberProperty("Y", y);
        checkNumberProperty("count", count);
        checkInitMatrix(fileName);
    }

    private void checkNumberProperty(String name, String value) {
        int checkValue;
        if (value == null) {
            throw new ApplicationException("Value attribute " + name + " is not defined");
        } else {
            try {
                checkValue = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                throw new ApplicationException("Error value of attribute " + name + " - is not a number");
            }
            if (checkValue <= 0) {
                throw new ApplicationException("Error value of attribute " + name + ". It must be positive");
            }
        }
    }

    private void checkInitMatrix(String fileName) {
        if (fileName == null) {
            throw new ApplicationException("Not defined file with init matrix");
        }
    }

    private char[][] readMatrix() throws IOException {
        URL url = getClass().getResource("/" + fileMatrix);
        if (url == null) {
            throw new ApplicationException("Doesn't exist ini-file " + fileMatrix);
        }

        int numberLine = 0;
        char[][] result = new char[sizeX][sizeY];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }

                for (int i = 0; i < sizeX; i++) {
                    result[numberLine][i] = s.charAt(i);
                }
                numberLine++;
                if (numberLine == sizeY) {
                    break;
                }
            }
        }

        return result;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getCountTicks() {
        return countTicks;
    }

    public String getFileMatrix() {
        return fileMatrix;
    }

    public char[][] getMatrix() {
        return matrix;
    }
}
