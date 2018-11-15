package com.ocr.aurelien;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RunGameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpStreams() {System.setOut(new PrintStream(outContent));}

    @AfterEach
    public void restoreStreams() {System.setOut(System.out);}

    RunGame runGame = new RunGame();

    @Test
    public void Given_Continue_When_DisplayContinueSelected_Then_DisplayContinueSentence(){
        runGame.displayContinueSelected(true);
        assertEquals("Continuons à jouer ensemble!\n\n", outContent.toString().replace("\r\n", "\n"));
    }
    @Test
    public void Given_Quit_When_DisplayContinueSelected_Then_DisplayQuitSentence() {
        runGame.displayContinueSelected(false);
        assertEquals("Merci d'avoir joué avec moi!\nA bientôt!\n", outContent.toString().replace("\r\n", "\n"));
    }
    @Test
    public void Given_ContinueInStandardInput_When_AskContinue_Then_Continue() {
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));
        runGame = new RunGame();
        runGame.askContinue();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Continuons à jouer ensemble!", output[3]);
    }
    @Test
    public void Given_QuitInStandardInput_When_AskContinue_Then_Quit() {
        System.setIn(new ByteArrayInputStream("2\n".getBytes()));
        runGame = new RunGame();
        runGame.askContinue();
        String[] output = outContent.toString().replace("\r\n","\n").split("\n");
        assertEquals("Merci d'avoir joué avec moi!", output[3]);
    }
    @Test
    public void Given_BadIntInStandardInput_When_AskContinue_Then_AskAgain() {
        System.setIn(new ByteArrayInputStream("3\n1\n".getBytes()));
        runGame = new RunGame();
        runGame.askContinue();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Ce choix n'est pas disponible!", output[3]);
        assertEquals("Voulez vous continuer à jouer?", output[5]);
    }
    @Test
    public void Given_StringInStandardInput_When_AskContinue_Then_AskAgain() {
        System.setIn(new ByteArrayInputStream("un\n1\n".getBytes()));
        runGame = new RunGame();
        runGame.askContinue();
        String[] output = outContent.toString().replace("\r\n", "\n").split("\n");
        assertEquals("Votre réponse doit être un nombre correspondant à votre choix!", output[3]);
        assertEquals("Voulez vous continuer à jouer?", output[6]);
    }
}
