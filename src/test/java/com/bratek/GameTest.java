package com.bratek;

import com.bratek.communication.UIMessenger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.InputMismatchException;

import static org.testng.Assert.assertTrue;

/**
 * Created by bratek on 29.06.17.
 */
@Test
public class GameTest {

    private Game game;

    private UIMessenger uiMessenger;
    @BeforeTest
    public void prepare(){
        this.uiMessenger = new UIMessenger(System.out, System.in);
        this.game = new Game(uiMessenger);
    }

    @DataProvider(name = "correctNames")
    public static Object[][] correctNames(){
        return new Object[][]{
                {"Matt", true},
                {"Kasia", true},
                {"Janek", true},
                {"marco", true}
        };
    }

    @DataProvider(name = "incorrectNames")
    public static Object[][] incorrectNames(){
        return new Object[][]{
                {"Mate.12", "ASdqw123"},
                {"mate.12", "asdo2pwqe "},
                {"mate*/", "asd~"}
        };
    }

    @Test(expectedExceptions = InputMismatchException.class, dataProvider = "incorrectNames")
    public void shouldThrowExceptionIfNameIsNotValid(String incorrectName, String incorrectName2){
        InputStream stream = new ByteArrayInputStream(incorrectName.getBytes());
        game.createPlayer(stream);
        stream = new ByteArrayInputStream(incorrectName2.getBytes());
        game.createPlayer(stream);
    }

    @Test(dataProvider = "correctNames")
    public void shouldCreateUserIfNameIsValid(String correctName, boolean result){
        game.createPlayer(new ByteArrayInputStream(correctName.getBytes()));

        assertTrue(game.getPlayers().contains(new Player(correctName)));
    }


}
