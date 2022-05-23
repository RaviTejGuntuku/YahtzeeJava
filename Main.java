import java.util.*;

public class Main {

    static Scanner kb = new Scanner(System.in);

    public static void main(String[] args) {

        String[] players = new String[2];

        System.out.println();
        System.out.println("Welcome to Yahtzee, the fun dice game for all!");
        System.out.println(
                "If you want a quick briefing regarding the game rules, be sure to visit https://en.wikipedia.org/wiki/Yahtzee.");

        System.out.print("\n");

        System.out.println("Please enter the names of the players");

        System.out.print("Player 1 Name: ");
        players[0] = kb.nextLine();

        System.out.print("Player 2 Name: ");
        players[1] = kb.nextLine();

        Dice yahtzeeDice = new Dice();
        Score scoreKeeper = new Score(yahtzeeDice, players[0], players[1]);

        int currentPlayer = 1;

        System.out.println();

        Object[][] scoreCard = scoreKeeper.getScoreCard();

        Table scoreTable = new Table(scoreCard);

        System.out.println("Starting Score:");
        System.out.println(scoreTable);
        System.out.println();

        while (!scoreKeeper.isGameOver()) {

            System.out.println(players[currentPlayer - 1] + ", it is your turn! Hit enter to roll the dice!");

            kb.nextLine();

            yahtzeeDice.roll();
            System.out.println(yahtzeeDice.getValues());

            for (int i = 0; i < 2; i++) {
                boolean[] reRolls = new boolean[5];

                System.out.println("Reroll #" + (i + 1)
                        + ": Which dice would you like to reroll (hit RETURN on a blank line to submit dice for rerolling)? ");

                String diceString = inputLogic();
                ArrayList<Integer> reRolledDiceNumbers = analyzeInput(i + 1, diceString);

                for (int diceN : reRolledDiceNumbers) {
                    reRolls[diceN - 1] = true;
                }

                yahtzeeDice.roll(reRolls);
                System.out.println();
                System.out.println(yahtzeeDice.getValues());
            }

            Object[][] tentativeScore = scoreKeeper.getTentitativeScore(currentPlayer);

            System.out.println(new Table(tentativeScore, "Tentative Score for " + players[currentPlayer - 1] + ":"));
            System.out.println();

            boolean appendedScoreSuccessfully = false;
            do {
                ArrayList<String> possibleCategories = scoreKeeper.getPossibleCategories(currentPlayer);

                if (possibleCategories.size() == 0) {
                    System.out.println("No possible categories");
                    break;
                }

                System.out.println("Possible categories & spellings");
                for (String c : possibleCategories) {
                    System.out.println(c);
                }

                System.out.println();

                System.out.println("Which category do you want to play (enter exact spelling with no spaces)? ");
                String category = kb.nextLine();
                category = category.toLowerCase();

                appendedScoreSuccessfully = scoreKeeper.appendToFinalScore(category, currentPlayer);
            } while (!appendedScoreSuccessfully);

            System.out.println();

            Object[][] finalScore = scoreKeeper.getScoreCard();
            System.out.println(new Table(finalScore, "Current Score: "));

            if (currentPlayer == 1)
                currentPlayer = 2;
            else if (currentPlayer == 2)
                currentPlayer = 1;
        }

        System.out.print("Game Over! ");
        if (scoreKeeper.winningPlayer() == 1) {
            System.out.println(players[0] + " won the game!");
        } else if (scoreKeeper.winningPlayer() == 2) {
            System.out.println(players[1] + " won the game!");
        } else {
            System.out.println("The game was a tie!");
        }

        System.out.println();
    }

    private static String inputLogic() {
        String keptDiceString = "";

        String diceReRoll;
        do {
            diceReRoll = kb.nextLine();
            keptDiceString += diceReRoll;
            keptDiceString = keptDiceString.replaceAll("\\s", "");
        } while (!diceReRoll.equals("") && keptDiceString.length() <= 5);

        if (keptDiceString.length() > 5) {
            String removed = keptDiceString.substring(5);
            keptDiceString = keptDiceString.replaceAll(removed, "");
        }

        return keptDiceString;
    }

    private static ArrayList<Integer> analyzeInput(int rollNumber, String originalInput) {

        String revisedInput = originalInput.replaceAll("\\s", "");
        ArrayList<Integer> diceReRolls = new ArrayList<Integer>();

        try {
            for (int i = 0; i < revisedInput.length(); i++) {
                String character = revisedInput.substring(i, i + 1);
                int diceNumber = Integer.parseInt(character);

                if (diceNumber < 1 || diceNumber > 5)
                    throw new NumberFormatException();

                diceReRolls.add(diceNumber);
            }
        } catch (NumberFormatException e) {
            System.out.print("Enter valid numbers from 1-5 for reroll #" + rollNumber
                    + ": Which dice would you like to reroll (hit RETURN on a blank line to submit dice for rerolling)? ");

            String diceString = inputLogic();
            return analyzeInput(rollNumber, diceString);

        }

        return diceReRolls;
    }
}