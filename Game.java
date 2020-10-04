package fishpackage;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import acm.graphics.*;
import acm.program.*;

public class Game extends ConsoleProgram {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final double TOP = 20;
	private static final double BOTTOM = 500;

	public void init() {

		// set console size
		setSize(1200, 800);
		// sets grid layout so its half console and half graphics
		setLayout(new GridLayout (1,2));
		setFont("Arial-18");


		//creates gcanvas
		canvas = new GCanvas();
		// add gcanvas onto the screen
		add (canvas);

		// creates button for when the user wants to collect a pair
		pairButton = new JButton("Pair");
		add (pairButton, SOUTH);
		pairButton.setVisible(false);;

		// creates a start button 
		startButton = new JButton ("Start/Restart");
		add (startButton, NORTH);

		// creates an ask button for when the player wants to ask the computer for a card
		askButton = new JButton ("Ask");
		add (askButton, SOUTH);
		askButton.setVisible(false);

		// creates a text field where user types the card they want
		asking = new JTextField (1); //1 character long
		// Add a label for the text field to the south control strip
		label = new JLabel("Do you have a ");
		add(label, SOUTH); 
		add (asking, SOUTH);
		asking.setVisible(false);
		label.setVisible(false);
		asking.addActionListener(this);

		//creates a yes button
		yesButton = new JButton ("Yes");
		add (yesButton, SOUTH);
		yesButton.setVisible(false);

		//creates a goFish button
		goFish = new JButton ("Go Fish!");
		add (goFish, SOUTH);
		goFish.setVisible(false);

		// creates a quit button
		quitButton = new JButton ("Quit");
		add (quitButton, SOUTH);
		quitButton.setVisible(false);


		// adds action listeners to respond to button clicks
		addActionListeners();

		// prints out the rules 
		printRules();

	}



	public void actionPerformed (ActionEvent e) {

		// if the start button gets pressed
		if (e.getSource() == startButton) {

			// shows the other buttons
			pairButton.setVisible(true);
			quitButton.setVisible(true);
			askButton.setVisible(true);


			// creates space
			println ("\n");
			// clears the canvas
			canvas.removeAll();
			// clears used arraylists in hands class
			Hands.used.clear();
			Hands.asked.clear();
			//initializes both players hands
			player.initialize();
			computer.initialize();
			//puts all their cards onto the screen
			putAllCards();

			// pairs up the computers cards
			while (computer.pair().equals(" made a pair!")) {
				println ("Computer collected a pair.");
				canvas.removeAll();
				putAllCards();
			}

		}
		//if quit button gets pressed
		if (e.getSource() == quitButton) {
			// clears the screen
			canvas.removeAll();
			// hides all buttons except for start
			pairButton.setVisible(false);
			quitButton.setVisible(false);
			askButton.setVisible(false);
			asking.setVisible(false);
			label.setVisible(false);
			goFish.setVisible(false);
			yesButton.setVisible(false);
			startButton.setVisible(false);


			// writes a message onto the canvas 
			GLabel message = new GLabel("Thank you for playing!"); 
			message.setFont("Arial-20");
			message.move(canvas.getWidth()/2 - message.getWidth()/2,canvas.getHeight()/2 - message.getHeight()/2);
			canvas.add(message);
		}

		// if pair button gets pressed
		if (e.getSource() == pairButton) {
			
			// outputs message if a pair was made or not
			println ("You" + player.pair());
			// removes everything off the canvas
			canvas.removeAll();
			putAllCards();
			
			if (player.getCards().size() == 0 || computer.getCards().size() == 0) {
				
				// sees who the winner is 
				if (player.numPairs > computer.numPairs) {
					println ("\nYou won!! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs +  "pairs");
					hideButtonsForWinner();
				} else if (player.numPairs < computer.numPairs) {
					println ("\nYou lost :( \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
					hideButtonsForWinner();
				} else {
					println ("\nIt was a tie! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
					hideButtonsForWinner();
				}
			}
		}

		// if ask button gets pressed
		if (e.getSource() == askButton) {
			// text box and label shows up
			label.setVisible(true);
			asking.setVisible(true);
		}

		//if something is typed in text field
		if (e.getSource() == asking) {
			//stores the card user is asking for in a string
			String ask = asking.getText();
			//makes it lower case so it can be compared
			ask.toLowerCase();
			//adds what the user asked for to an Arraylist in the Hands class
			Hands.asked.add(ask.charAt(0));
			//prints out what you asked
			println ("You : Do you have a " + ask + "?");
			//keeps track of the answer
			String answer = computer.check(ask.charAt(0));
		
			println (answer);

			// depending on the answer the computer gives the player will either pick up or collect
			if (answer.equals("Yes")) {
				computer.giveCard(ask.charAt(0), player.getCards());
				canvas.removeAll();
				putAllCards();
			} else {
				player.pickUp();
				canvas.removeAll();
				putAllCards();
			}

			// set text field as empty string
			asking.setText("");

			if (player.getCards().size() == 0 || computer.getCards().size() == 0) {
				
				// sees who the winner is 
				if (player.numPairs > computer.numPairs) {
					println ("\nYou won!! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
					hideButtonsForWinner();
				} else if (player.numPairs < computer.numPairs) {
					println ("\nYou lost :( \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
					hideButtonsForWinner();
				} else {
					println ("\nIt was a tie! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
					hideButtonsForWinner();
				}

			} else {
				// shows yes and go fish button
				yesButton.setVisible(true);
				goFish.setVisible(true);

				// hides all the buttons that aren't needed
				askButton.setVisible(false);
				quitButton.setVisible(false);
				asking.setVisible(false);
				label.setVisible(false);
				startButton.setVisible(false);
				
				// stores the string in a variable just in case you have to give that card
				phrase = computer.ask();
				println (phrase);
				givingCard = phrase.charAt(14);
			}	
		}

		// if yes button gets pressed
		if (e.getSource() == yesButton) {
			//makes sure player has card before giving it
			if (player.hasCard(givingCard)) {
			
				// prints yes onto the console
				println ("Yes");
				// player gives the card to the computer's hand
				player.giveCard(givingCard, computer.getCards());
				// computer pairs his cards
				println ("Computer" + computer.pair());
				//readjusts the cards
				canvas.removeAll();
				putAllCards();

				if (player.getCards().size() == 0 || computer.getCards().size() == 0) {
					
					// sees who the winner is 
					if (player.numPairs > computer.numPairs) {
						println ("\nYou won!! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs +  "pairs");
						hideButtonsForWinner();
					} else if (player.numPairs < computer.numPairs) {
						println ("\nYou lost :( \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
						hideButtonsForWinner();
					} else {
						println ("\nIt was a tie! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
						hideButtonsForWinner();
					}
					
				} else {
					//puts other buttons back on
					askButton.setVisible(true);
					quitButton.setVisible(true);
					startButton.setVisible(true);

					//take out yes and go fish buttons
					goFish.setVisible(false);
					yesButton.setVisible(false);
				}
			} else {
				
				// if player does not have this card
				println ("You do not have this card");
			}
			

		}

		//if go fish button gets pressed
		if (e.getSource() == goFish) {

			// if player has the card it sends out a cheating message
			if (player.hasCard(givingCard)) {
				println ("Don't cheat :|");
			} else {
								
				// prints Go Fish onto the console
				println ("Go Fish!");
				//computer picks up a card
				computer.pickUp();
								
				//pairs cards if it has any
				println ("Computer " + computer.pair());
				// readjusts the cards
				canvas.removeAll();
				putAllCards();

				//puts other buttons back on
				askButton.setVisible(true);
				quitButton.setVisible(true);
				startButton.setVisible(true);

				//take out yes and go fish buttons
				goFish.setVisible(false);
				yesButton.setVisible(false);
				
				if (player.getCards().size() == 0 || computer.getCards().size() == 0) {
					
					// sees who the winner is 
					if (player.numPairs > computer.numPairs) {
						println ("\nYou won!! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs +  "pairs");
						hideButtonsForWinner();
					} else if (player.numPairs < computer.numPairs) {
						println ("\nYou lost :( \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
						hideButtonsForWinner();
					} else {
						println ("\nIt was a tie! \nYou : " + player.numPairs + " pairs \nComputer : " + computer.numPairs + " pairs");
						hideButtonsForWinner();

					}
				}
			}
		}
	}




	/**
	 * displays a series of card faces
	 * @param a arraylist of type string with the card names on them
	 * @param position whether its at the top or bottom
	 */
	private void displayCards(ArrayList<String> a, double position) {
		// creates iterator to go through the arrayList
		Iterator<String> itr = a.iterator();
		// counter to count how many cards have been put on the screen
		int counter = 0;
		while (itr.hasNext()) {
			String name = itr.next();
			// adds GImages equally spaced across the canvas
			canvas.add (new GImage (name, canvas.getWidth()/a.size()*counter, position));

			counter++;
		}
	}

	/**
	 * displays a series of card backs
	 * @param a arraylist of type string with the card names on them
	 * @param position whether its at the top or bottom
	 */
	private void displayBackCards(ArrayList<String> a, double position) {
		// creates iterator to go through the arrayList
		Iterator<String> itr = a.iterator();
		// counter to count how many cards have been put on the screen
		int counter = 0;
		while (itr.hasNext()) {
			itr.next();
			// adds GImages equally spaced across the canvas
			canvas.add (new GImage ("images/b2fv.gif", canvas.getWidth()/a.size()*counter, position));

			counter++;
		}
	}

	/**
	 * combines the displayBackCards and displayCards methods to display
	 * all cards at once
	 */
	private void putAllCards() {
		displayCards (player.getCards(), BOTTOM);
		displayBackCards (computer.getCards(), TOP);
	}

	/**
	 * removes everything from the screen except for the start and quit buttons
	 * when announcing the winner
	 */
	private void hideButtonsForWinner() {
		// clears the screen
		canvas.removeAll();
		// hides all buttons except for start and quit
		startButton.setVisible(true);
		quitButton.setVisible(true);
		pairButton.setVisible(false);
		askButton.setVisible(false);
		asking.setVisible(false);
		label.setVisible(false);
		goFish.setVisible(false);
		yesButton.setVisible(false);
	}

	/**
	 * prints rules to the screen
	 */
	private void printRules() {
		println ("Welcome to Go Fish! "
				+ "\nHere's how you play:"
				+ "\n- It will be you against a computer"
				+ "\n- Goal of the game is to obtain the most amount of pairs"
				+ "\n- Pairs can be made if you have two cards of the same value"
				+ "\n- You can collect a pair by clicking the 'Pair' button at the bottom"
				+ "\n- To ask for a card press the ask button and enter the card you want in the text field box (enter 1=Ace, 2-9, t=10, j=Jack, q=Queen, k=King)"
				+ "\n- The computer will give you a response (either 'Yes' or 'Go Fish') and their card if applicable"
				+ "\n- The computer will ask you for a card. You will respond by either clicking the 'Yes' or 'Go Fish' button"
				+ "\n- You and the computer will go back and forth up until one of you runs out of cards"
				+ "\n- The winner will be the player with the most pairs"
				+ "\nClick 'Start' to begin!");
	}

	




	private GCanvas canvas;
	private JButton pairButton;
	private JButton startButton;
	private JButton quitButton;
	private JButton askButton;
	private JTextField asking;
	private JLabel label;
	private JButton yesButton;
	private JButton goFish;
	char givingCard;
	String phrase;
	// computers cards
	private Hands computer = new Hands();
	// players cards
	private Hands player = new Hands();
}

