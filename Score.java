import java.util.*;

public class Score {
    private static int[] diceValues;

    private Object[][] scoreCard = {
            { "", "PLAYER_1", "PLAYER_2" },
            { "Aces", 0, 0 },
            { "Twos", 0, 0 },
            { "Threes", 0, 0 },
            { "Fours", 0, 0 },
            { "Fives", 0, 0 },
            { "Sixes", 0, 0 },
            { "", "", "" },
            { "Sum", 0, 0 },
            { "Bonus", 0, 0 },
            { "", "", "" },
            { "Three of a kind", 0, 0 },
            { "Four of a kind", 0, 0 },
            { "Full house", 0, 0 },
            { "Small straight", 0, 0 },
            { "Large straight", 0, 0 },
            { "Chance", 0, 0 },
            { "YAHTZEE", 0, 0 },
            { "", "", "" },
            { "Total", 0, 0 }
    };

    public Score(Dice d, String player1, String player2) {

        scoreCard[0][1] = player1;
        scoreCard[0][2] = player2;

        diceValues = d.getDice();
    }

    public static void setDiceValues(int[] vals) {
        for (int i = 0; i < diceValues.length; i++) {
            diceValues[i] = vals[i];
        }
    }

    public Object[][] getTentitativeScore(int playerNumber) {
        Object[][] tentitativeScore = new Object[scoreCard.length][scoreCard[0].length];

        for (int r = 0; r < scoreCard.length; r++) {
            for (int c = 0; c < scoreCard[r].length; c++) {
                tentitativeScore[r][c] = scoreCard[r][c];
            }
        }

        for (int i = 1; i <= 6; i++)
            if ((int) tentitativeScore[i][playerNumber] == 0)
                tentitativeScore[i][playerNumber] = totalOfDice(i);

        if (isThreeOfKind() && (int) tentitativeScore[11][playerNumber] == 0)
            tentitativeScore[11][playerNumber] = totalOfDice();

        if (isFourOfKind() && (int) tentitativeScore[12][playerNumber] == 0)
            tentitativeScore[12][playerNumber] = totalOfDice();

        if (isFullHouse() && (int) tentitativeScore[13][playerNumber] == 0)
            tentitativeScore[13][playerNumber] = 25;

        if (isSmallStraight() && (int) tentitativeScore[14][playerNumber] == 0)
            tentitativeScore[14][playerNumber] = 30;

        if (isLargeStraight() && (int) tentitativeScore[15][playerNumber] == 0)
            tentitativeScore[15][playerNumber] = 40;

        if ((int) tentitativeScore[16][playerNumber] == 0)
            tentitativeScore[16][playerNumber] = totalOfDice();

        if (isYahtzee()) {
            if ((int) tentitativeScore[17][playerNumber] == 0)
                tentitativeScore[17][playerNumber] = 50;
            else
                tentitativeScore[17][playerNumber] = (int) tentitativeScore[17][playerNumber] + 100;
        }

        return tentitativeScore;
    }

    public ArrayList<String> getPossibleCategories(int playerNumber) {

        Object[][] tentitativeObjects = getTentitativeScore(playerNumber);
        ArrayList<String> possibleCategories = new ArrayList<String>();

        for (int r = 1; r < scoreCard.length; r++) {

            String currentElementScoreLabel = (String) scoreCard[r][0];

            if (currentElementScoreLabel.equals("Sum") || currentElementScoreLabel.equals("Bonus")
                    || currentElementScoreLabel.equals("Total"))
                continue;

            Object tentitativeScoreForPlayer = tentitativeObjects[r][playerNumber];
            Object finalScoreForPlayer = scoreCard[r][playerNumber];

            if (!tentitativeScoreForPlayer.getClass().getName().equals("java.lang.Integer"))
                continue;

            int tentitativeScoreForPlayerInt = (int) tentitativeScoreForPlayer;
            int finalScoreForPlayerInt = (int) finalScoreForPlayer;

            if (tentitativeScoreForPlayerInt == 0)
                continue;

            if (finalScoreForPlayerInt != 0) {
                if (!currentElementScoreLabel.equals("YAHTZEE")) {
                    continue;
                }
            }

            currentElementScoreLabel = currentElementScoreLabel.replaceAll("\\s", "");
            currentElementScoreLabel = currentElementScoreLabel.toLowerCase();
            possibleCategories.add(currentElementScoreLabel);

        }

        if (possibleCategories.isEmpty()) {

            for (int r = 1; r < scoreCard.length; r++) {

                String currentElementScoreLabel = (String) scoreCard[r][0];

                if (currentElementScoreLabel.equals("Sum") || currentElementScoreLabel.equals("Bonus")
                        || currentElementScoreLabel.equals("Total"))
                    continue;

                Object tentitativeScoreForPlayer = tentitativeObjects[r][playerNumber];
                Object finalScoreForPlayer = scoreCard[r][playerNumber];

                if (!tentitativeScoreForPlayer.getClass().getName().equals("java.lang.Integer"))
                    continue;

                int finalScoreForPlayerInt = (int) finalScoreForPlayer;

                if (finalScoreForPlayerInt != 0)
                    continue;

                currentElementScoreLabel = currentElementScoreLabel.replaceAll("\\s", "");
                currentElementScoreLabel = currentElementScoreLabel.toLowerCase();
                possibleCategories.add(currentElementScoreLabel);

            }

        }

        return possibleCategories;
    }

    public boolean appendToFinalScore(String categoryName, int currentPlayer) {

        categoryName = categoryName.toLowerCase();
        categoryName = categoryName.replaceAll("\\s", "");

        Object[][] tentitativeScore = getTentitativeScore(currentPlayer);

        for (int r = 0; r < tentitativeScore.length; r++) {
            for (int c = 0; c < tentitativeScore[r].length; c++) {
                Object currentElement = tentitativeScore[r][c];
                String elementClass = currentElement.getClass().getName();

                if (!elementClass.equals("java.lang.String"))
                    continue;

                if (((String) currentElement).equals("Sum") || ((String) currentElement).equals("Bonus")
                        || ((String) currentElement).equals("Total"))
                    continue;

                String currentElementFormatted = ((String) currentElement).replaceAll("\\s", "");
                currentElementFormatted = currentElementFormatted.toLowerCase();

                tentitativeScore[r][c] = currentElementFormatted;
            }
        }

        ArrayList<String> possibleCategories = getPossibleCategories(currentPlayer);

        boolean flag = false;

        if (possibleCategories.indexOf(categoryName) != -1) {

            for (int r = 1; r < tentitativeScore.length; r++) {

                String currentScoreLabel = (String) tentitativeScore[r][0];

                if (currentScoreLabel.equals(categoryName)) {
                    scoreCard[r][currentPlayer] = tentitativeScore[r][currentPlayer];
                    flag = true;
                    break;
                }
            }
        }

        if (flag != true)
            return false;

        scoreCard[8][currentPlayer] = sumOfTopCard()[currentPlayer - 1];

        if ((int) scoreCard[8][currentPlayer] > 63)
            scoreCard[9][currentPlayer] = 35;

        scoreCard[scoreCard.length - 1][currentPlayer] = totalScore()[currentPlayer - 1];

        return flag;
    }

    public Object[][] getScoreCard() {
        return scoreCard;
    }

    private int[] totalScore() {

        int[] totalPlayerScores = new int[2];

        for (int c = 1; c < scoreCard[0].length; c++) {
            for (int r = 8; r < scoreCard.length - 1; r++) {

                Object currentElement = scoreCard[r][c];
                String elementClass = currentElement.getClass().getName();

                if (!elementClass.equals("java.lang.Integer"))
                    continue;

                int integerCurrentElement = (int) currentElement;
                totalPlayerScores[c - 1] += integerCurrentElement;
            }
        }

        return totalPlayerScores;
    }

    private int[] sumOfTopCard() {
        int[] playerSums = new int[2];

        for (int r = 1; r < 7; r++) {
            for (int c = 1; c < scoreCard[0].length; c++) {

                Object currentElement = scoreCard[r][c];
                String elementClass = currentElement.getClass().getName();

                if (!elementClass.equals("java.lang.Integer"))
                    continue;

                int integerCurrentElement = (int) currentElement;
                playerSums[c - 1] += integerCurrentElement;
            }
        }

        return playerSums;
    }

    public boolean isGameOver() {
        for (Object[] r : scoreCard) {
            for (Object e : r) {
                String elementClass = e.getClass().getName();
                if (!elementClass.equals("java.lang.Integer"))
                    continue;

                if ((int) e == 0)
                    return false;
            }
        }

        return true;
    }

    public int winningPlayer() {

        int player1Points = (int) scoreCard[scoreCard.length - 1][1];
        int player2Points = (int) scoreCard[scoreCard.length - 1][2];

        if (player1Points > player2Points)
            return 1;
        if (player1Points < player2Points)
            return 2;
        else
            return 0;
    }

    private boolean isThreeOfKind() {

        int similarCountOuter = 1;
        for (int i = 0; i < diceValues.length; i++) {
            int similarCountInner = 1;
            for (int x = i + 1; x < diceValues.length; x++) {
                if (diceValues[i] == diceValues[x])
                    similarCountInner++;
            }
            if (similarCountInner > similarCountOuter)
                similarCountOuter = similarCountInner;
        }

        if (similarCountOuter >= 3)
            return true;
        return false;

    }

    private boolean isFourOfKind() {

        int similarCountOuter = 1;
        for (int i = 0; i < diceValues.length; i++) {
            int similarCountInner = 1;
            for (int x = i + 1; x < diceValues.length; x++) {
                if (diceValues[i] == diceValues[x])
                    similarCountInner++;
            }
            if (similarCountInner > similarCountOuter)
                similarCountOuter = similarCountInner;
        }

        if (similarCountOuter >= 4)
            return true;
        return false;

    }

    private boolean isYahtzee() {

        int firstValue = diceValues[0];

        for (int d : diceValues) {
            if (d != firstValue)
                return false;
        }

        return true;
    }

    private boolean isFullHouse() {
        ArrayList<Integer> diceNumbers = new ArrayList<Integer>();
        ArrayList<Integer> frequencies = new ArrayList<Integer>();

        for (int i = 0; i < diceValues.length; i++) {

            int number = diceValues[i];
            int frequency = 1;

            for (int x = i + 1; x < diceValues.length; x++) {
                int currentNumber = diceValues[x];

                if (currentNumber == number)
                    frequency++;
            }

            if (diceNumbers.indexOf(number) == -1) {
                diceNumbers.add(number);
                frequencies.add(frequency);
            }
        }

        if (frequencies.size() == 2) {
            if ((frequencies.get(0) == 2 && frequencies.get(1) == 3)
                    || (frequencies.get(0) == 3 && frequencies.get(1) == 2))
                return true;
            return false;
        }
        return false;

    }

    private ArrayList<Integer> removeDuplicates(ArrayList<Integer> list) {

        ArrayList<Integer> newList = new ArrayList<Integer>();

        for (Integer element : list) {

            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        return newList;
    }

    private boolean isSmallStraight() {

        Integer[] diceValues = new Integer[5];

        for (int i = 0; i < Score.diceValues.length; i++) {
            diceValues[i] = Score.diceValues[i];
        }

        ArrayList<Integer> diceValuesCopy = new ArrayList<Integer>();

        Collections.addAll(diceValuesCopy, diceValues);
        Collections.sort(diceValuesCopy);

        diceValuesCopy = removeDuplicates(diceValuesCopy);

        int exceptionCounter = 0;

        if (diceValuesCopy.size() < 4)
            return false;

        for (int i = 0; i < diceValuesCopy.size() - 1; i++) {

            int currentValue = diceValuesCopy.get(i);
            int nextValue = diceValuesCopy.get(i + 1);
            if (nextValue != currentValue + 1) {
                exceptionCounter++;
            }

        }

        if (diceValuesCopy.size() - exceptionCounter >= 4)
            return true;
        else
            return false;
    }

    private boolean isLargeStraight() {

        Integer[] diceValues = new Integer[5];

        for (int i = 0; i < Score.diceValues.length; i++) {
            diceValues[i] = Score.diceValues[i];
        }

        ArrayList<Integer> diceValuesCopy = new ArrayList<Integer>();

        Collections.addAll(diceValuesCopy, diceValues);
        Collections.sort(diceValuesCopy);

        diceValuesCopy = removeDuplicates(diceValuesCopy);

        if (diceValuesCopy.size() < 5)
            return false;

        for (int i = 0; i < diceValuesCopy.size() - 1; i++) {

            int currentValue = diceValuesCopy.get(i);
            int nextValue = diceValuesCopy.get(i + 1);
            if (nextValue != currentValue + 1) {
                return false;
            }

        }

        return true;
    }

    private int totalOfDice(int diceNumber) {

        int total = 0;

        for (int d : diceValues) {
            if (d == diceNumber)
                total += d;
        }

        return total;
    }

    private int totalOfDice() {

        int total = 0;

        for (int d : diceValues) {
            total += d;
        }

        return total;

    }
}
