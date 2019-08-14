package com.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

public class TestGameLifeImpl {

    @Mock
    private LoaderConfigLifeGame mockLoader;

    @InjectMocks
    private GameLifeImpl gameLife;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        gameLife = new GameLifeImpl(mockLoader);
    }

    @Test
    public void testTick() {
        //GIVEN
        char[][] initMatrix = new char[][] {
                {'O','O','O','O','O'},
                {'O','O','X','O','O'},
                {'O','O','X','O','O'},
                {'O','O','X','O','O'},
                {'O','O','O','O','O'}
        };

        char[][] expectedMatrix = new char[][] {
                {'O','O','O','O','O'},
                {'O','O','O','O','O'},
                {'O','X','X','X','O'},
                {'O','O','O','O','O'},
                {'O','O','O','O','O'}
        };

        when(mockLoader.getSizeX()).thenReturn(5);
        when(mockLoader.getSizeY()).thenReturn(5);
        when(mockLoader.getCountTicks()).thenReturn(1);
        when(mockLoader.getMatrix()).thenReturn(initMatrix);

        //WHEN
        gameLife.init();
        gameLife.play();

        //THEN
        for (int i = 0; i < 5; i++) {
            Assert.assertArrayEquals(expectedMatrix[i],gameLife.getMatrix()[i]);
        }
    }
}
