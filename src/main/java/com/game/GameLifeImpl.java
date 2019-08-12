package com.game;

import exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class GameLifeImpl implements GameLife {
    private final static Logger log = LoggerFactory.getLogger(GameLifeImpl.class);

    private int sizeX;
    private int sizeY;
    private int countTicks;
    private String fileMatrix;
    char[][] matrix;

    @Override
    public void play() {
        for (int i = 0; i < countTicks; i++) {
            tick();
            log.info("Iteration # " + (i + 1) + "\n " + toString());
        }
    }

    private void tick() {
        char[][] chars = initMatrix();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                if (matrix[i][j] == 'X') {
                    chars[i][j] = checkAliveCell(i, j);
                } else {
                    chars[i][j] = checkDeadCell(i, j);
                }
            }
        }
        matrix = chars;
    }

    private char checkAliveCell(int x, int y) {
        int countAliveCells = 0;
        int indexX, indexY;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                indexX = x + i;
                if (indexX < 0 || indexX >= sizeX) {
                    continue;
                }
                indexY = y + j;
                if (indexY < 0 || indexY >= sizeY) {
                    continue;
                }

                if (indexX == x && indexY == y) {
                    continue;
                }

                if (matrix[indexX][indexY] == 'X') {
                    countAliveCells++;
                }
            }
        }

        if (countAliveCells == 2 || countAliveCells == 3) {
            return 'X';
        } else {
            return 'O';
        }
    }

    private char checkDeadCell(int x, int y) {
        int countDeadCells = 0;
        int indexX, indexY;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                indexX = x + i;
                if (indexX < 0 || indexX >= sizeX) {
                    continue;
                }
                indexY = y + j;
                if (indexY < 0 || indexY >= sizeY) {
                    continue;
                }

                if (indexX == x && indexY == y) {
                    continue;
                }

                    if (matrix[indexX][indexY] == 'O') {
                        countDeadCells++;
                    }
            }
        }

        if (countDeadCells == 3) {
            return 'X';
        } else {
            return 'O';
        }
    }

    private char[][] initMatrix() {
        char[][] chars = new char[sizeX][sizeY];
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                chars[i][j] = ' ';
            }
        }

        return chars;
    }

    @Override
    public void init() {

        Properties properties = readProperties();
        checkProperties(properties);

        sizeX = Integer.valueOf(properties.getProperty("X"));
        sizeY = Integer.valueOf(properties.getProperty("Y"));
        countTicks = Integer.valueOf(properties.getProperty("count"));
        fileMatrix = properties.getProperty("file");
        checkInitMatrix(fileMatrix);

        try {
            matrix = readMatrix();
        } catch (IOException e) {
            throw new ApplicationException("Error reading file with init matrix " + fileMatrix);
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

    private Properties readProperties() {
        Properties properties = new Properties();
        try (InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties")) {
            properties.load(stream);
        } catch (IOException e) {
            throw new ApplicationException("Failed to load property file", e);
        }

        return properties;
    }

    private void checkProperties(Properties prop) {
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
                checkValue = Integer.valueOf(value);
            } catch (ArithmeticException e) {
                throw new ApplicationException("Error value of attribute " + name + " is not a number");
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

    @Override
    public String toString() {
        return "GameLifeImpl{" +
                "sizeX=" + sizeX +
                ", sizeY=" + sizeY +
                ", countTicks=" + countTicks +
                ",\n" +
                printMatrix() +
                '}';
    }

    private String printMatrix() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                result.append(matrix[i][j]);
            }
            result.append("\n");
        }

        return result.toString();
    }
}
