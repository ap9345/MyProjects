
/**
 * BackToTheFuture.java
 * 
 * Version:
 * 		 v1.1, 09/16/2017, 18:20:18
 *
 * Revision:
 *		Initial revision 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * This program picks up a dictionary word from a file and then picks up a random word and the 
 * player has 9 tries to guess the characters of the word. The game is over, when 
 * the every character of the word has been guessed. This program will show a scene, and after 
 * every wrong guess some parts of the scene will disappear. The scene will completely disappeared 
 * after 9 guesses and your result will be shown.
 * Assumption: The words are case insensitive and all the words of the dictionary are less than 10 letters.
 * 
 * @author Patil, Abhishek Sanjay
 *
 */

public class BackToTheFuture {

	private static ArrayList<String> storeAllWords = new ArrayList<String>();							// holds all strings from the input file
	private static ArrayList<String> storeRandomlySelectedWord = new ArrayList<String>(); 				// holds randomly selected word from the file
	private static ArrayList<Integer> indexOfCharactersThatAreSelected = new ArrayList<Integer>();		// holds the index of the randomly entered letter
	private static String selectedString = null; 														// keeps track of the randomly selected word
	private static String charactersSelected[]; 														// holds the output after each guess
	private static Scanner sc = null;																	// used to take input from user

	/**
	 * This is main program.
	 * 
	 * @param		args		command line argument is ignored
	 */
	public static void main(String[] args) {
		readDictionaryFile();										// read all the words from dictionary file
		
		boolean continueGame = false;								// holds whether the game must be continued or not
		
		do {
			
			selectedString = randomlySelectedWord();				// get random word
			
			// close the game if all the words are used
			if ("noWordLeft".equals(selectedString)) {
				System.out.println("You have completed all words of the dictionary, please restart the game.");
				break;
			}

			generateHangman(0);										// print complete hangman
			
			System.out.println();
			for(int i=0; i<selectedString.length(); i++) {
				System.out.print("  -");
			}
			continueGame = guessNineLetters(selectedString);		// take input guess from the user
		} while (continueGame);
		
		if(sc!=null)
			sc.close();												// close all open snanner connection

	}

	/**
	 * This function reads all the words from the dictionary file and save all the words in an array list.
	 * 
	 */
	public static void readDictionaryFile() {

		String currentWord;							// hold current word from the file						

		try {
			
			File file = new File("BackToTheFuture.txt");
			sc = new Scanner(file);

			while (sc.hasNext() && (currentWord = sc.nextLine()) != null) {
				
				storeAllWords.add(currentWord.toLowerCase());		// add the word to the arraylist
			}

			System.out.println("You are the only player and I name you Abisud.\n" + "Player: Abisud\n");

		} catch (FileNotFoundException e) {

			System.out.println("An error has orrored: " + e.getMessage() + "\nPlease try again after some time.");
		} finally {

			// close all the established scanner connection
			if (sc != null) {
				sc.close();
			}
		}
	}

	/**
	 * This function selects random word from the list of available words.
	 * 
	 * @return 						the random selected word
	 */
	private static String randomlySelectedWord() {
		
		int randomNumber = 0; 							// holds random rumber generated
		Random r = new Random();

		// remove already used word from the list if any
		if (selectedString != null && (!selectedString.equals(""))) {
			
			storeAllWords.remove(selectedString);
		}
		
		// checks whether all the words are used
		if (storeAllWords.size() == 0)
			return "noWordLeft";

		// generate random number
		randomNumber = (storeAllWords.size()==1)? 0 : (r.nextInt(storeAllWords.size()-1));
		
		charactersSelected = new String[storeAllWords.get(randomNumber).toString().length()];
		charactersSelectedFromString(charactersSelected);
		
		return storeAllWords.get(randomNumber).toString().toLowerCase();
	}

	/**
	 * This function just stores "-" in the array of strings
	 * 
	 * @param		charactersSelected		string array that stores correct guessed characters
	 */
	public static void charactersSelectedFromString(String charactersSelected[]) {
		int j = 0;
		for (int i = 0; i < charactersSelected.length; i++) {
			charactersSelected[i] = " - ";
			indexOfCharactersThatAreSelected.add(j);
			j++;
		}
	}

	/**
	 * Given a number, this function generates a hangman with applicable parts
	 * 
	 * @param 		parts		generates hangman for the entered part
	 */
	public static void generateHangman(int parts) {

		switch (parts) {
		case 0:
			System.out.println("         ###");
			System.out.println("        #####");
			System.out.println("         ###");

		case 1:
			System.out.println("          #");

		case 2:
			System.out.print("   #####");

		case 3:
			if (parts == 3)
				System.out.println("   #####");
			else
				System.out.println("     #####");

		case 4:
			System.out.println("       #######");
			System.out.println("        #####");

		case 5:
			System.out.println("        #####");
			System.out.println("       #######");
			System.out.println("      #########");

		case 6:
			System.out.println("     ###########");
			System.out.println("       ##   ##");
			System.out.println("       ##   ##");

		case 7:
			System.out.println("       ##   ##");
			System.out.println("       ##   ##");

		case 8:
			System.out.println("       #### ####");
			System.out.println("       #### ####");
			System.out.println("########################");
			System.out.println("########################");
		}

	}

	/**
	 * Given a randomly selected string, this function gives nine chances to the
	 * user and checks whether the characters guessed by the user are correct or
	 * not. Even asks the winner player,  whether the game is to be continued or not.
	 * 
	 * @param		randomlySelectedWord		word that is randomly selected
	 */
	public static boolean guessNineLetters(String randomlySelectedWord) {

		boolean result = false;
		int i, chance = 9, counter = 0;
		String c;

		sc = new Scanner(System.in);

		// stores the characters from randomlySelectedWord to new arrayList
		for (i = 0; i < randomlySelectedWord.length(); i++)
			storeRandomlySelectedWord.add(Character.toString(randomlySelectedWord.charAt(i)));

		// gives nines chances to user to predict the correct characters of randomly
		// selected string
		for (i = 0; i < 9; i++) {
			System.out.println();
			System.out.println("You have ---> " + chance + " guesses left !!!");
			System.out.println("Guess a letter: ");
			c = Character.toString(sc.next().charAt(0)).toLowerCase();
			if (storeRandomlySelectedWord.contains(c)) {
				generateHangman(counter);

				charactersSelected[(int) indexOfCharactersThatAreSelected
						.get(storeRandomlySelectedWord.indexOf(c))] = " " + c + " ";
				indexOfCharactersThatAreSelected.remove(storeRandomlySelectedWord.indexOf(c));
				storeRandomlySelectedWord.remove(c);

				if (storeRandomlySelectedWord.size() == 0)
					break;
				System.out.println();

			} else {
				counter++;
				generateHangman(counter);
				System.out.println();
			}

			//prints the correctly guessed characters until then.
			for (int k = 0; k < charactersSelected.length; k++)
				System.out.print(charactersSelected[k]);
			System.out.println();
			chance--;
		}

		if (storeRandomlySelectedWord.size() > 0) {
			
			//display message for lost player
			System.out.println("\tThe game is over: You Loose ");
			System.out.println("The word was: " + selectedString);
		} else {
			
			//display message for winning player
			for (int k = 0; k < charactersSelected.length; k++)
				System.out.print(charactersSelected[k]);
			System.out.println();
			System.out.println("\tThis round is over: You Win ");
			System.out.println("The word was: " + selectedString + "\n\n");
			System.out.println("Do you want to continue (yes/no)?");
			result = "yes".equalsIgnoreCase(sc.next()) ? true : false;
		}
		return result;
	}

}
