package bullscows;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numDigits;
        int numSymbols;
        System.out.println("Input the length of the secret code: ");
        String strNumDigits = scanner.nextLine();
        try {
            numDigits = Integer.parseInt(strNumDigits);
        } catch (Exception e) {
            System.out.println("Error: \""+ strNumDigits +"\" isn't a valid number.");
            return;
        }
        if (numDigits == 0) {
            System.out.println("Error");
            return;
        }
        System.out.println("Input the number of possible symbols in the code: ");
        String strNumSymbols = scanner.nextLine();
        try {
            numSymbols = Integer.parseInt(strNumSymbols);
        } catch (Exception e) {
            System.out.println("Error: \""+ strNumSymbols +"\" isn't a valid number.");
            return;
        }
        if (numSymbols < numDigits) {
            System.out.println("Error: it's not possible to generate a code with a length of "
            + numDigits + " with " + numSymbols + " unique symbols.");
            return;
        } else if (numSymbols > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        StringBuilder possibleChoices = new StringBuilder("0123456789abcdefghijklmnopqrstuvwxyz");
        possibleChoices.delete(numSymbols,36);

        StringBuilder starString = new StringBuilder("");
        for (int i = 0; i < numDigits; i++) {
            starString.append("*");
        }
        if (numSymbols <= 10) {
            System.out.println("The secret code is prepared: " + starString
            + " (0-" + possibleChoices.charAt(numSymbols-1) + ").");
        } else {
            System.out.println("The secret code is prepared: " + starString
                    + " (0-9, a-" + possibleChoices.charAt(numSymbols-1) +")." );
        }


        /* if (numDigits > 36) {
            System.out.println("Error: can't generate a secret number with a  length of " +
                numDigits + " because there aren't enough unique digits.");
            System.out.println("Please, enter the secret code's length:");
            numDigits = scanner.nextInt();
        } */
        String password = randomNumberGenerator(numDigits, numSymbols, possibleChoices);
        int cowCounter = 0;
        int bullCounter = 0;
        int turnCounter = 0;


        while (bullCounter < numDigits) {
            turnCounter++;
            System.out.println("Turn " + turnCounter + ":");
            String guess = scanner.nextLine();
            char[] guessArray = guess.toCharArray();
            String uniqueGuess = "";
            cowCounter = 0;
            bullCounter = 0;

            for (char ch: guessArray) {
                if (!uniqueGuess.contains(Character.toString(ch))) {
                    uniqueGuess += ch;
                }
            }

            for (int i = 0; i < guess.length(); i++) {
                if (password.charAt(i) == guess.charAt(i)) {
                    bullCounter++;
                }
                for (int j = 0; j < uniqueGuess.length(); j++) {
                    if (uniqueGuess.charAt(j) == password.charAt(i)) {
                        cowCounter++;
                    }
                }
            }
            cowCounter -= bullCounter;

            if (bullCounter == 0) {
                if (cowCounter == 0) {
                    System.out.println("Grade: None");
                } else if (cowCounter == 1){
                    System.out.println("Grade: 1 cow");
                } else {
                    System.out.println("Grade: " + cowCounter + " cows");
                }
            } else if (bullCounter == 1) {
                if (cowCounter == 0) {
                    System.out.println("Grade: 1 bull");
                } else if (cowCounter == 1){
                    System.out.println("Grade: 1 bull and 1 cow");
                } else {
                    System.out.println("Grade: 1 bull and " + cowCounter + " cows");
                }
            } else {
                if (cowCounter == 0) {
                    System.out.println("Grade: " + bullCounter + " bulls");
                } else if (cowCounter == 1){
                    System.out.println("Grade: "+ bullCounter + " bulls and 1 cow");
                } else {
                    System.out.println("Grade: "+ bullCounter + " bulls and " + cowCounter + " cows");
                }
            }
        }

        System.out.println("Congratulations! You guessed the secret code.");

    }
    public static String randomNumberGenerator(int numDigits, int numSymbols, StringBuilder possibleChoices) {

        Random random = new Random();
        StringBuilder password = new StringBuilder();
        StringBuilder possbileChoices = possibleChoices;


        for (int i = 0; i < numDigits; i++) {
            int randomSize = numSymbols - i;
            int randomDraw = random.nextInt(randomSize);
            password.append(possbileChoices.charAt(randomDraw));
            possbileChoices.delete(randomDraw,randomDraw+1);
        }
        return password.toString();
    }

}
