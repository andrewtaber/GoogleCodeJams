package com.andy;

import java.io.*;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        File input = new File("A-large-practice.in");
        File output = new File("A-large-practice.out");
        Scanner in = null;

        try {
            in = new Scanner(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int wordLength = in.nextInt();
        int lines = in.nextInt();
        int numTestCases = in.nextInt();

        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> tests = new ArrayList<>();
        ArrayList<ArrayList<String>> testCasesTokenized = new ArrayList<>();
        HashMap<Integer, Integer> testCaseMatches = new HashMap<>();

        int currWordCount = 0;

        //Get list of words from file
        while(currWordCount < lines) {
            String word = in.nextLine();
            if(!word.isEmpty()) {
                words.add(word);
                currWordCount++;
            }
        }

        //Get list of tests from file
        for(int i = 0; i < numTestCases; i++) {
            tests.add(in.nextLine());
        }

        in.close();

        //Compile list of test cases into list of tokens for each case
        for(String testCase : tests) {
            ArrayList<String> tokens = tokenizeTestCase(testCase);
            testCasesTokenized.add(tokens);
        }

        /**
         * Now we start matching the tokens from the test cases against the letters
         * in the words in order. As long as at least one letter from a token has a match with
         * the letter at the same index in the word we're looking at, it counts as a match.
         * If all tokens match with all letters of the word, we count that as a match for the
         * test case.
         */
        for(ArrayList<String> testCase : testCasesTokenized) {
            int fullMatchCount = 0;

            //Iterate over all words in word list for each test case
            for(int currWordNum = 0; currWordNum < words.size(); currWordNum++) {
                String currWord = words.get(currWordNum);
                boolean fullWordMatch = true;

                checkWordMatch:
                for (int tokenNum = 0; tokenNum < testCase.size(); tokenNum++) {
                    String currentToken = testCase.get(tokenNum);

                    //Tokens longer than just a character will need to be iterated over to match with single letter from word
                    if (currentToken.length() > 1) {
                        for (int j = 0; j < currentToken.length(); j++) {
                            if (currentToken.charAt(j) == currWord.charAt(tokenNum)) {
                                break;

                                /**
                                 * Short-circuit. If no letter of the token matches with the letter in the word where we
                                 * are checking, there is no need to check the rest of the word.
                                 */
                            } else if (j == currentToken.length() - 1 && currentToken.charAt(j) != currWord.charAt(tokenNum)) {
                                fullWordMatch = false;
                                break checkWordMatch;
                            }
                        }

                    } else if (currentToken.charAt(0) != currWord.charAt(tokenNum)) {
                        fullWordMatch = false;
                        break checkWordMatch;
                    }
                }

                if(fullWordMatch) fullMatchCount++;

            }

            testCaseMatches.put(testCasesTokenized.indexOf(testCase) + 1, fullMatchCount);
        }

        FileWriter fileWriter = new FileWriter(output);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        for(Integer caseNumber : testCaseMatches.keySet()) {
            printWriter.println("Case #" + caseNumber + ": " + testCaseMatches.get(caseNumber));
        }

        printWriter.close();
        /**
         *
         * Various little Tests to ensure pieces working while building algorithm.
         *
         * **/
//        System.out.println("Words captured:");
//        for(String word : words) {
//            System.out.println(word);
//        }
//
//        System.out.println("Test cases:");
//        for(String testCase : tests) {
//            System.out.println(testCase);
//        }
//
//
//        System.out.println("\nTokenized first String");
//        ArrayList<String> tokens = tokenizeTestCase(tests.get(2));
//        for(String token : tokens) {
//            System.out.println(token);
//        }

    }

    public static ArrayList<String> tokenizeTestCase(String testCase) {
        int currChar = 0;

        ArrayList<String> tokenList = new ArrayList<>();

        while(currChar < testCase.length()) {
            String tempString = "";
            if (testCase.charAt(currChar) == '(') {
                currChar++;
                while(testCase.charAt(currChar) != ')') {
                    tempString += testCase.charAt(currChar);
                    currChar++;
                }

                tokenList.add(tempString);
                currChar++;

            } else {
                tempString += testCase.charAt(currChar);
                tokenList.add(tempString);
                currChar++;
            }
        }

        return tokenList;
    }

}
