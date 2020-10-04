# GoFish
virtual one player go fish card game

This game uses 4 classes to run a virtual one player game of the traditional card game Go Fish using the acm.jar Stanford package which gives simplified methods
and commands.

Cards class:
initializes the strings of all of the different card faces in an array 'cards'.

Hands class:
Controls the cards in each persons hand. This class controls when the user and the computer pick up, pair, check, gets cards, gives cards, asks for cards, 
and has a card.

PairsException class:
Extends the exception class and is raised when the player or the computer has no more cards to pair.

Game class:
Uses GUI to create the environment in which the game is played. There is a graphics screen on the right and a console screen on the left. There are buttons,
text insert boxes, and the console screen where the user can interact with the game. GCanvas allows the card pictures to be on the graphics screen. List of 
buttons include: start/restart, pair, ask, yes, GoFish and quit. There is a JTextfield that allows the user to ask for the card they want. This class extends the 
Hands class so it uses all of the methods to control both the user's and the computer's hands. 
