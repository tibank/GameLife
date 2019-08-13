package com.game;

import exceptions.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class GameLifeImpl implements GameLife {
    private final static Logger log = LoggerFactory.getLogger(GameLifeImpl.class);

    private final LoaderConfigLifeGame loader;
    private char[][] matrix;

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

                if (matrix[indexX][indexY] == 'X') {
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
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("result.txt"))) {
            for (int i = 0; i < loader.getSizeX(); i++) {
                bufferedWriter.write(matrix[i]);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new ApplicationException("Error writing result to file",e);
        }
    }

    @Override
    public void init() {
        matrix = loader.getMatrix();
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
