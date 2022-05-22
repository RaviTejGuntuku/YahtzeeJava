# Creating Java programs in Codio

### Make a new file
Use **File > New File...** or right-click in the file tree to create a new file. You can right-click in the file tree to rename or delete files.

As Codio detects which file is in focus, simply put your cursor into whichever code editor you want to compile and run.

### Compile, Run or Both
Use the Run button (that looks like a Rocketship) to Compile and Run the file your cursor is in.

![](https://global.codio.com/platform/readme.resources/RunMenuJava.png)

Use the drop-down arrow to the right of the "Compile & Run" option to change the button to just Compile or just Run.

## Yahtzee Project


Yahtzee is a dice game where a player rolls 5 dice and tries to get certain combinations.  A player can roll the dice up to 3 times in a turn.  For example, if a player rolls the dice and gets: 6, 1, 3, 6, 6 they might choose to just roll the 1 and 3 the next time.  If that returns a 2 and a 6 they might roll just the 2 and if that comes up with a 6 they have 6 of a kind, called a Yahtzee, and they would score 50 points (the highest roll possible).

You can learn more about the game here: https://en.wikipedia.org/wiki/Yahtzee
You can play an online version here: https://cardgames.io/yahtzee/


This lab has you start a text based Yahtzee game.  You earn points based on how many parts of this lab you do. 

If desired, work with a friend.  You can get more done and learn more if you work together.  If two of you work together include comments at the start of your code so I know you worked together.  You must do one more part than the number of points you want.

As you work you can change the dice and score classes to work the way you think best.  You can add other classes too.

### Lab Part 1 - 70 points

Create a Dice class that has an integer array  attribute that holds the value of the 5 dice.  A roll() method will set each of them to a random value between 1 and 6 inclusive.   A getValues method will return these values.

Create support code that creates a dice object, rolls the dice, gets and prints the values.

Potential output:

```
---Yahtzee Game---

die 1 is 6
die 2 is 6
die 3 is 6
die 4 is 3
die 5 is 5
```

### Lab Part 2 - 15 points

Enable rolling a 2nd and 3rd time.   This might include an overloaded roll method that takes a boolean for each die and will only roll those that are true.

Update your support code to prompt the user as to what dice they want to reroll.  Do the reroll and print the results.

Add code to do the third roll.  

You will need to design a way to display the values and keep track of what dice are being rolled again.

### Lab Part 3 - 7 Points

Create a class called Score.  This class can figure out if the passed in dice qualify for different scores.  Start with the ability to detect a Yahtzee (all 5 dice match), 3 of a Kind (self explanatory), and 4 of a kind.


Add a method to score and print the possible values for the dice (i.e. the results of the rolls in Part 2.

### Lab Part 4 - 4 points

Add the ability to score these six categories:
1, 2, 3, 4, 5, 6 = This is equal to the sum of the respective number.  For example if a player had the dice: 1, 2, 1, 3, 3 they would score 2 points for the 1s, 2 points for the 2s, and 6 points for the threes.  Fours, Fives, and Sixes would score 0.

### Lab Part 5 - 2 points

Add the ability to score the remaining possible categories:
Three of a Kind - scores the total of all the dice.  (Not just the 3 that match)
Four of a Kind - scores the total of all the dice. (Not just the 4 that match)
Full House - two of one number and three of another scores 25 points.
Small Straight - four sequential dice (1,2,3,4,x) or (2,3,4,5,x) or (3,4,5,6,x) scores 30 points.
Large Straight - five sequential dice (1,2,3,4,5) or (2,3,4,5,6) scores 40 points.
Chance - scores the sum off all the dice.
Yahtzee - all 5 dice match - scores 50 points.

### Lab Part 6 - 1 point

Ask the player what category they want their roll to go towards.  Don’t allow them to use a category twice.  Continue playing until the player has used all categories.

### Lab Part 7 - 1 points

Calculate the Score.

In Yahtzee there is a bonus of 35 points if categories 1 - 6 add up to 63 or more.  Add in the bonus and calculate the final score.

### Lab Part 8 - for fun (or for teams of 2 to get 100)

Enhance the game to be a two or more player game.  Keep track of whose turn it is and declare the final winner.

### Lab Part 9 - for fun

Clean up the display so that it is easy to read the score card and tell whose turn it is etc.


