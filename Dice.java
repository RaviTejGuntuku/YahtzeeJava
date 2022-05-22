class Dice {
    private int[] dice = new int[5];

    public void roll() {
        for (int i = 0; i < dice.length; i++) {
            int randomNumber = (int) (Math.random() * 6) + 1;
            dice[i] = randomNumber;
        }

        Score.setDiceValues(dice);
    }

    public void roll(boolean[] diceRolls) {
        for (int i = 0; i < dice.length; i++) {
            if (diceRolls[i]) {
                int randomNumber = (int) (Math.random() * 6) + 1;
                dice[i] = randomNumber;
            }
        }

        Score.setDiceValues(dice);
    }

    public int[] getDice() {
        return dice;
    }

    public String getValues() {
        String valuesString = "";

        int count = 1;
        for (int roll : dice) {
            valuesString += "die " + count + " is " + roll + "\n";
            count++;
        }

        return valuesString;
    }
}