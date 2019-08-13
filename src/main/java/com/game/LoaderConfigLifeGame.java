package com.game;

import java.util.Properties;

public interface LoaderConfigLifeGame {
    void loadProperties();
    void checkProperties(Properties prop);
    int getSizeX();
    int getSizeY();
    int getCountTicks();
    String getFileMatrix();
}
