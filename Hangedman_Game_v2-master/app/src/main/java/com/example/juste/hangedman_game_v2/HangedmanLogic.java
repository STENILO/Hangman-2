package com.example.juste.hangedman_game_v2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class HangedmanLogic {
    private ArrayList<String> possibleWord = new ArrayList<String>();
    private String Word;
    private ArrayList<String> UsedLetter = new ArrayList<String>();
    private String visableWord;
    private int numberOfWrongWords;
    private boolean LastLetterWasCorrect;
    private boolean gameIsWon;
    private boolean gameIsLost;
    private int score;

    public ArrayList<String> getUsedLetter() {
        return UsedLetter;
    }

    public String getVisableWord() {
        return visableWord;
    }

    public String getWord() {
        return Word;
    }

    public int getNumberOfWrongWords() {
        return numberOfWrongWords;
    }

    public boolean isLastLetterCorrect() {
        return LastLetterWasCorrect;
    }

    public boolean isTheGameWon() {
        return gameIsWon;
    }

    public boolean isTheGameLost() {
        return gameIsLost;
    }

    public boolean isTheGameDone() {
        return gameIsLost || gameIsWon;
    }

    public int getScore(){
        return score;
    }


    public HangedmanLogic() {
        possibleWord.add("car");
        possibleWord.add("computer");
        possibleWord.add("programing");
        possibleWord.add("highway");
        possibleWord.add("busroute");
        possibleWord.add("sidewalk");
        possibleWord.add("snail");
        possibleWord.add("bird");
        refresh();
    }

    // Har modificeret grundkoden lidt for at hente ord fra allerede brugte objekter istedet for at gentage at hente fra dr
    public ArrayList getpossibleWord(){
        return possibleWord;
    }

    public void refresh() {
        UsedLetter.clear();
        numberOfWrongWords = 0;
        gameIsWon = false;
        gameIsLost = false;
        Word = possibleWord.get(new Random().nextInt(possibleWord.size()));
        updateVisableWord();
        score = 0;
    }
    public void softReset(){
        UsedLetter.clear();
        numberOfWrongWords = 0;
        gameIsWon = false;
        gameIsLost = false;
        Word = possibleWord.get(new Random().nextInt(possibleWord.size()));
        updateVisableWord();
    }

    public void plusPoints(){
        score = score + 100;
    }

    public void minusPoints(){
        score = score - 10;
    }



    private void updateVisableWord() {
        visableWord = "";
        gameIsWon = true;
        for (int n = 0; n < Word.length(); n++) {
            String Letter = Word.substring(n, n + 1);
            if (UsedLetter.contains(Letter)) {
                visableWord = visableWord + Letter;
            } else {
                visableWord = visableWord + "*";
                gameIsWon = false;
            }
        }
    }

    public void guessLetter(String Letter) {
        if (Letter.length() != 1) return;
        if (Letter.equals(" ")) return;
        System.out.println("your guess on the letter: " + Letter);
        if (UsedLetter.contains(Letter)) return;
        if (gameIsWon || gameIsLost) return;

        UsedLetter.add(Letter);

        if (Word.contains(Letter)) {
            LastLetterWasCorrect = true;
            System.out.println("the letter was correct: " + Letter);
        } else {
            // Vi gættede på et Letter der ikke var i Word.
            LastLetterWasCorrect = false;
            System.out.println("the letter was not correct: " + Letter);
            numberOfWrongWords = numberOfWrongWords + 1;
            if (numberOfWrongWords > 6) {
                gameIsLost = true;
            }
        }
        updateVisableWord();
    }

    public void logStatus() {
        System.out.println("---------- ");
        System.out.println("- Word (hidden) = " + Word);
        System.out.println("- visable word = " + visableWord);
        System.out.println("- wrong letter = " + numberOfWrongWords);
        System.out.println("- used letters = " + UsedLetter);
        System.out.println("- score is = " + score);
        if (gameIsLost) System.out.println("- Game is Lost");
        if (gameIsWon) System.out.println("- Game is Won");
        System.out.println("---------- ");
    }


    public static String getUrl(String url) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line + "\n");
            line = br.readLine();
        }
        return sb.toString();
    }

    public void getWordFromDR() throws Exception {
        String data = getUrl("http://dr.dk");
        System.out.println("data = " + data);
        data = data.substring(data.indexOf("<body")).
                replaceAll("<.+?>", " ").toLowerCase().replaceAll("<[^>]+>"," "). // tilføjet lidt tagremoval
                replaceAll("[^a-zæøå]", " ").
                replaceAll(" [a-zæøå] "," "). // fjern 1-Lettersord
                replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-Lettersord

        System.out.println("data = " + data);
        possibleWord.clear();
        possibleWord.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("possible Word = " + possibleWord);
        refresh();
    }

    public void getWordFromTimes() throws Exception {
        String data = getUrl("http://www.thetimes.co.uk/tto/news/");
        System.out.println("data = " + data);
        data = data.substring(data.indexOf("<body")).
                replaceAll("<.+?>", " ").toLowerCase().replaceAll("<[^>]+>"," "). // tilføjet lidt tagremoval
                replaceAll("[^a-zæøå]", " ").
                replaceAll(" [a-zæøå] "," "). // fjern 1-Lettersord
                replaceAll(" [a-zæøå][a-zæøå] "," "); // fjern 2-Lettersord

        System.out.println("data = " + data);
        possibleWord.clear();
        possibleWord.addAll(new HashSet<String>(Arrays.asList(data.split(" "))));

        System.out.println("possible Word = " + possibleWord);
        refresh();
    }


}