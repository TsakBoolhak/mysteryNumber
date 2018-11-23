package com.ocr.aurelien;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class RunGame {

    Scanner sc = new Scanner(System.in);

    /**
     * Call all the methods used for the game
     */
    public void run() {
        boolean continueGame = true;
        int nbDifficulty = -1;
        int nbMystery = -1;
        int nbScore = -1;
        displayWelcomeSentence();
        do {
            nbDifficulty = askDifficulty();
            nbMystery = pickNumber(nbDifficulty);
            nbScore = askNumber(nbDifficulty, nbMystery);
            displayResult(nbDifficulty, nbMystery, nbScore);
            continueGame = askContinue();
        } while (continueGame);
    }

    /**
     * Display a welcoming message and the rules of the game
     */
    private void displayWelcomeSentence() {
        System.out.println("Bienvenue sur Mystery Number!\nLe principe de ce jeu est simple : je vais choisir un nombre compris entre 0 et un nombre determiné par la difficulté que vous choisirez.\nVous devrez ensuite tenter de deviner quel nombre j'ai choisi.\nJe ne suis pas trop méchant et je vous donnerez un indice après chaque mauvaise réponse!\nBonne chance et amusez vous bien!\n\n");
    }

    /**
     * Display the summary of the result of the last game
     * @param nbDifficulty is used to display in which numbers'fork the number was included
     * @param nbMystery is used to display the right answer
     * @param nbScore is used to display in how many attempts the user found the answer
     */
    public void displayResult(int nbDifficulty, int nbMystery, int nbScore) {
        int nbMax = returnNbMax(nbDifficulty);
        String strCoups = "coups";
        String strNbMax = getFormattedStringFromInt(nbMax);
        String strNbMystery = getFormattedStringFromInt(nbMystery);
        String strNbScore = getFormattedStringFromInt(nbScore);
        if (nbScore == 1) {
            strCoups = "coup";
        }
        System.out.println("Bravo!! Vous deviez trouver un nombre compris entre 0 et " + strNbMax + ".\nVous avez deviné que j'avais choisi le nombre " + strNbMystery + " en seulement " + strNbScore + " " + strCoups + " !");
    }

    /**
     * Ask to user a number  in a loop, compare it to the desired number, display a message depending on the difference between the user's answer and the desired number
     * When the answered number is equal to the desired number the loop stops and the method returns the number of tries of the user before finding the desired answer.
     * @param nbDifficulty is used to determine the number max
     * @param nbMystery is used to compare the answer and the desired number
     * @return the amount of tries before quitting the loop
     */
    public int askNumber(int nbDifficulty, int nbMystery) {
        int nbScore = 0;
        int nbAnswer = -1;
        int nbMax = returnNbMax(nbDifficulty);
        String strNbMax = getFormattedStringFromInt(nbMax);
        do {
            System.out.println("Le nombre mystère est compris entre 0 et " + strNbMax + " (inclus).\nEssayez de le deviner (Tapez votre réponse)");
            nbAnswer = getIntFromInput();
            String strNbAnswer = getFormattedStringFromInt(nbAnswer);
            nbScore++;
            if (nbAnswer > nbMax || nbAnswer < 0) {
                System.out.println("Voyons!! Le nombre ne peut être compris qu'entre 0 et " + strNbMax + " !\n (Cette réponse ne sera pas prise en compte pour votre score)");
                nbScore--;
            }
            else if (nbMystery > nbAnswer) {
                System.out.println("Le nombre mystère est plus GRAND que " + strNbAnswer + " !");
            }
            else if (nbMystery < nbAnswer) {
                System.out.println("Le nombre mystère est plus PETIT que " + strNbAnswer + " !");
            }
            else {
                System.out.println("OUI!! Le nombre mystère est bien " + strNbAnswer + " !");
            }
        } while (nbMystery != nbAnswer);
        return nbScore;
    }

    /**
     * Return a number max depending on the difficulty chosen
     * @param nbDifficulty is the difficulty lvl used to pick the number to return
     * @return the max number
     */
    public int returnNbMax(int nbDifficulty) {
        int nbMax = -1;
        switch (nbDifficulty) {
            case 1 :
                nbMax = 10;
                break;
            case 2 :
                nbMax = 100;
                break;
            case 3 :
                nbMax = 1000;
                break;
            case 4 :
                nbMax = 10000;
                break;
            default :
                nbMax = 100000;
                break;
        }
        return nbMax;
    }

    /**
     *Pick a random number between 0 and a number max
     * @param nbDifficulty is used to determine the max number
     * @return the random number picked
     */
    public int pickNumber(int nbDifficulty) {
        int nbMystery = -1;
        int nbMax = returnNbMax(nbDifficulty);
        Random rand = new Random();
        nbMystery = rand.nextInt(nbMax + 1);
        return nbMystery;
    }

    /**
     * Ask to user which difficulty setting he wants to play with
     * @return the difficulty value
     */
    public int askDifficulty() {
        int nbDifficulty = -1;
        boolean responseIsGood = false;
        do {
            System.out.println("Veuillez choisir un niveau de difficulté :\n1 - entre 0 et 10 (inclus)\n2 - entre 0 et 100 (inclus)\n3 - entre 0 et 1 000 (inclus)\n4 - entre 0 et 10 000 (inclus)\n5 - entre 0 et 100 000 (inclus)");
            nbDifficulty = getIntFromInput();
            if (nbDifficulty > 0 && nbDifficulty < 6) {
                responseIsGood = true;
            }
            else {
                System.out.println("Ce choix n'est pas disponible!\n");
            }
        } while (!responseIsGood);
        displayDifficulty(nbDifficulty);
        return nbDifficulty;
    }

    /**
     * Display the difficulty level
     * @param nbDifficulty = difficulty level
     */
    public void displayDifficulty(int nbDifficulty) {
        System.out.println("Vous avez choisi le niveau " + nbDifficulty + " de difficulté\n");
    }

    /**
     * Get an int from input
     * @return the int entered by user
     */
    public int getIntFromInput() {
        int nbContinue = -1;
        try {
            sc.useDelimiter("\n");
            nbContinue = sc.nextInt();
        } catch (InputMismatchException e) {
            sc.next();
            System.out.println("Votre réponse doit être un nombre correspondant à votre choix.\n(Je ne comprend pas si vous l'ecrivez en toutes lettres ou si vous mettez un espace)");
        }
        return nbContinue;
    }

    /**
     * Get a formatted string from an int to separate each thousands by a space (exemple : value = 15000, formattedValue = "15 000")
     * @param value is the int passed to the method
     * @return the value under a formatted string
     */
    public String getFormattedStringFromInt(int value) {
        String formattedValue = new String("");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        String pattern = "###,###";
        DecimalFormat decimalFormat = new DecimalFormat(pattern, symbols);
        formattedValue = decimalFormat.format(value);
        return formattedValue;
    }
    /**
     * Ask user if he wants to continue
     * @return the answer as a boolean
     */
    public boolean askContinue() {
        int nbContinue = -1;
        boolean responseIsGood = false;
        boolean continueGame = true;
        do {
            System.out.println("Voulez vous continuer à jouer?\n1 - Oui, je veux continuer\n2 - Non , je veux arrêter");
            nbContinue = getIntFromInput();
            switch (nbContinue) {
                case 1 :
                    responseIsGood = true;
                    break;
                case 2 :
                    responseIsGood = true;
                    continueGame = false;
                    break;
                default :
                    System.out.println("Ce choix n'est pas disponible!\n");
                    responseIsGood = false;
                    break;
            }
        } while (!responseIsGood);
        displayContinueSelected(continueGame);
        return continueGame;
    }

    /**
     * Display a sentence depending on if the user want to continue or not
     * @param continueGame : define if the user want to continue playing
     */
    public void displayContinueSelected(boolean continueGame) {
        if (continueGame) {
            System.out.println("Continuons à jouer ensemble!\n");
        }
        else {
            System.out.println("Merci d'avoir joué avec moi!\nA bientôt!");
        }
    }
}