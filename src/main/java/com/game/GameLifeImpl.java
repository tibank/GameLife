package com.game;

import exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;

public class GameLifeImpl implements GameLife {
    private final static Logger log = LoggerFactory.getLogger(GameLifeImpl.class);

    private LoaderConfigLifeGame loader;
    char[][] matrix;

    public GameLifeImpl(LoaderConfigLifeGame loader) {
        this.loader = loader;
    }

    @Override
    public void play() {
        for (int i = 0; i < loader.getCountTicks(); i++) {
            tick();
            log.info("Iteration # " + (i + 1) + "\n " + toString());
        }
    }

    private void tick() {
        char[][] chars = initMatrix();
        for (int i = 0; i < loader.getSizeX(); i++) {
            for (int j = 0; j < loader.getSizeY(); j++) {
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
                if (indexX < 0 || indexX >= loader.getSizeX()) {
                    continue;
                }
                indexY = y + j;
                if (indexY < 0 || indexY >= loader.getSizeY()) {
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
                if (indexX < 0 || indexX >= loader.getSizeX()) {
                    continue;
                }
                indexY = y + j;
                if (indexY < 0 || indexY >= loader.getSizeY()) {
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
        char[][] chars = new char[loader.getSizeX()][loader.getSizeY()];
        for (int i = 0; i < loader.getSizeX(); i++) {
            for (int j = 0; j < loader.getSizeY(); j++) {
                chars[i][j] = ' ';
            }
        }

        return chars;
    }


    @Override
    public void writeToFile() {
        String fileName;
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result.txt"))) {
            for (int i = 0; i < loader.getSizeX(); i++) {
                bufferedWriter.write(matrix[i]);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        checkInitMatrix(loader.getFileMatrix());

        try {
            matrix = readMatrix();
        } catch (IOException e) {
            throw new ApplicationException("Error reading file with init matrix " + loader.getFileMatrix());
        }
    }

    private char[][] readMatrix() throws IOException {
        URL url = getClass().getResource("/" + loader.getFileMatrix());
        if (url == null) {
            throw new ApplicationException("Doesn't exist ini-file " + loader.getFileMatrix());
        }

        int numberLine = 0;
        char[][] result = new char[loader.getSizeX()][loader.getSizeY()];

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(url.getFile()))) {
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                s = s.trim();
                if (s.isEmpty()) {
                    continue;
                }

                for (int i = 0; i < loader.getSizeX(); i++) {
                    result[numberLine][i] = s.charAt(i);
                }
                numberLine++;
                if (numberLine == loader.getSizeY()) {
                    break;
                }
            }
        }

        return result;
    }

    private void checkInitMatrix(String fileName) {
        if (fileName == null) {
            throw new ApplicationException("Not defined file with init matrix");
        }
    }

    @Override
    public String toString() {
        return "GameLifeImpl{" +
                "sizeX=" + loader.getSizeX() +
                ", sizeY=" + loader.getSizeY() +
                ", countTicks=" + loader.getCountTicks() +
                ",\n" +
                printMatrix() +
                '}';
    }

    private String printMatrix() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < loader.getSizeX(); i++) {
            for (int j = 0; j < loader.getSizeY(); j++) {
                result.append(matrix[i][j]);
            }
            result.append("\n");
        }

        return result.toString();
    }
}
