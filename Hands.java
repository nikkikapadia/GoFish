package fishpackage;

import java.util.*;

import acm.util.RandomGenerator;

public class Hands extends Cards {

	/**
	 * initializes the player's hand:
	 * gives the player 7 different cards, and none of the cards in the deck repeat
	 */
	public void initialize() {
		// makes sure arraylists are clear
		cardsInHand.clear();
		// makes sure number of pairs is 0
		numPairs = 0;
		// cycles 7 times to get each card
		for (int i = 0; i < 7; i++) {
			// will be assigned an index value for the corresponding card in the cards array
			int cardNumber;
			while (true) {
				// assigns it a random number
				cardNumber = rgen.nextInt(0, 51);
				if (used.size() == 0) break;
				//checks if the card is in the used arraylist (with every card thats used in it)
				else if (used.indexOf(cards[cardNumber]) < 0) break;
			}
			//adds the card to the persons hand 
			cardsInHand.add(cards[cardNumber]);
			// adds the card to the arrayList that contains all the cards that are used
			used.add(cards[cardNumber]);

		}
	}

	/**
	 * when player has to pick up a card:
	 * adds a card that has not already been used to their hand's arraylist
	 */
	public void pickUp() {
		// will be assigned an index value for the corresponding card in the cards array
		int cardNumber;
		while (true) {
			// assigns it a random number (index value for array)
			cardNumber = rgen.nextInt(0, 51);
			//checks if the card is in the used arraylist (with every card thats used in it)
			if (used.indexOf(cards[cardNumber]) < 0) break;
		}
		//adds the card to the persons hand 
		cardsInHand.add(cards[cardNumber]);
		// adds the card to the arrayList that contains all the cards that are used
		used.add(cards[cardNumber]);
	}


	/**
	 * checks a players entire hand for pairs
	 * @return message if computer made a pair or not
	 */
	public String pair() {

		try {
			int num = 0;
			// searches through arraylist for cards that are the same
			for (int i = 0; i < cardsInHand.size() - 1; i++) {
				char ch = cardsInHand.get(i).charAt(8);
				num++;
				for (int j = num; j < cardsInHand.size(); j++) {
					//sees if the cards are of the same value
					if (cardsInHand.get(j).charAt(8) == ch) {
						// removes card from the players hand
						cardsInHand.remove(cardsInHand.get(j));
						cardsInHand.remove(cardsInHand.get(i));				
						// adds to the number of pairs the player/computer has
						numPairs++;
						//returns message 
						return " made a pair!";
					}
				}
			}
			throw new PairsException();
		}
		catch (PairsException e) {
			return (e.toString());
		}
	}
	
	/**
	 * checks a players deck to see if they have a card
	 * @param card - character corresponding to a card
	 * @return a string response to see if they have the specified card
	 */
	public String check(char card) {
		for (int i = 0; i < cardsInHand.size(); i++) {
			if (cardsInHand.get(i).charAt(8) == card) {
				return "Yes";
			}
		}
		return "Go Fish!";
	}

	/**
	 * gettor method for cardsInHand
	 * @return ArrayList of type string which contains a players hand
	 */
	public ArrayList<String> getCards() {
		return cardsInHand;
	}
	
	/**
	 * 
	 * @param cardToGive - char of the chard that the opponent asked for
	 * @param opponent - ArrayList<String> containing the opponents cards
	 */
	public void giveCard(char cardToGive, ArrayList<String> opponent) {
		// creates iterator to cycle through arraylist
		Iterator<String> itr = cardsInHand.iterator();
		while (itr.hasNext()) {
			// assigns that element to temporary variable
			String card = itr.next();
			// when it finds the card it...
			if (card.charAt(8) == cardToGive) {
				// removes the card from the player who is giving the card's hand
				cardsInHand.remove(card);
				// and adds it to the opponents hand
				opponent.add(card);
				//breaks out of the loop to cycle through the whole hand
				break;
			}
		}
	}
	
	/**
	 * asks the user for a card that they have in their hand
	 * @return a phrase asking the user for a card
	 */
	public String ask() {
		// creates listiterator to cycle through the arraylist for the cards that the user asked for
		ListIterator<Character> itr = asked.listIterator();
		// goes through arraylist to get to the end of the arrayList so they can start from the end
		while (itr.hasNext()) {
			itr.next();
		}
		//checks from the end of the arraylist (most recent) to see if they have a card that the user recently asked for
		while (itr.hasPrevious()) {
			// stores the card value in a variable so it can be compared to the cards that the 
			char ch = itr.previous();
			// sees if any of the cards the computer has matched any that the player asked for
			for (int i = cardsInHand.size() - 1; i >= 0; i--) {
				if (cardsInHand.get(i).charAt(8) == ch) {
					return ("Do you have a " + ch + "?");
				}
			}
		}
		// if the player has none of the cards they asked for it will generate a random card from their deck
		
		// generates a random index for a card
		int randCard = rgen.nextInt(0, cardsInHand.size() - 1);
		// take the value of the card as a string
		char card = cardsInHand.get(randCard).charAt(8);
		return ("Do you have a " + card + "?");
		
	}
	
	/**
	 * tells if a player has a specified card
	 * @param card - char
	 * @return boolean 
	 */
	public boolean hasCard(char card) {
		// creates iterator to cycle through arraylist
		Iterator<String> itr = cardsInHand.iterator();
		//cycles through the arraylist
		while (itr.hasNext()) {
			//if player has card return true
			if (itr.next().charAt(8) == card) {
				return true;
			}
		}
		// otherwise it will return false
		return false;
	}


	//arraylist of type string too keep track of the card in the hand
	ArrayList<String> cardsInHand = new ArrayList<String>(7);
	// variable to keep track of the number of pairs the player has
	public int numPairs = 0;
	//array of strings with named of the card files that have already been used
	public static ArrayList<String> used = new ArrayList<String>(52);
	//arraylist of type Character that keeps track of the cards that the player has asked
	public static ArrayList<Character> asked = new ArrayList<Character>();
	
	
	//random generator
	private RandomGenerator rgen = new RandomGenerator();




}
