import java.util.ArrayList;
import java.util.List;

public class Game {

	//Game Status possibilities
	public static final int GAME_CONTINUES = 0;
	public static final int X_WINS = 1;
	public static final int O_WINS = 2;
	public static final int DRAW = 3;
	
	//Create an empty TicTacToe Board to start the game with
	private char[] board = {'.', '.', '.', '.', '.', '.', '.', '.', '.'};
	
	//Games always start with X going first
	private char currentPlayer = 'X';
	
	private int status = GAME_CONTINUES;
	
	//Used to support AI; by maintaining a list of available moves, 
	//	we avoid generating it each time the AI takes a turn
	private List<Integer> availableMoves = new ArrayList<>();
	
	public Game() {
		//create a list of all possible moves 
		//as they are used moves will be removed from the list
		for (int m = 0; m < board.length; m++)
			availableMoves.add(m);
	}

	/**
	 * Attempts to play the current player's token in the given board location
	 * @param location
	 * @return true if the move is made successfully, false otherwise
	 */
	public boolean play(int location) {
		//fail if the game is over...
		if (status != GAME_CONTINUES) return false;
		//fail if this is not valid location
		if (location < 0 || location > 8) return false;
		//fail if the location is not empty
		if (board[location] != '.') return false;
		
		//this is a valid move, so record it
		board[location] = currentPlayer;

		//take the move that was just made out of the available moves list
		availableMoves.remove(((Integer)location)); 
		
		//Update the status of the game (did this move win?...)
		status = updateStatus(location);

		//Switch the current player
		currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

		//return success
		return true;
	}
	
	public int getStatus() { 
		return status; 
	}
	
	/**
	 * Returns the status of the game.  Trying to stay as efficient as possible,
	 * this method only checks lines involving the location played.
	 * 
	 * @param location
	 * @return game state after this move was made
	 */
	private int updateStatus(int location) {
		//test lines involving the played location to see if this was a winning move
		if (location == 0) {
			if (testLine(0, 1, 2)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 3, 6)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 4, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 1) {
			if (testLine(0, 1, 2)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(1, 4, 7)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 2) {
			if (testLine(0, 1, 2)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(2, 5, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(2, 4, 6)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 3) {
			if (testLine(3, 4, 5)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 3, 6)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 4) {
			if (testLine(3, 4, 5)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(1, 4, 7)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 4, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(2, 4, 6)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 5) {
			if (testLine(3, 4, 5)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(2, 5, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 6) {
			if (testLine(6, 7, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 3, 6)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(6, 4, 2)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 7) {
			if (testLine(6, 7, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(7, 4, 1)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		if (location == 8) {
			if (testLine(6, 7, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(2, 5, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
			if (testLine(0, 4, 8)) return (currentPlayer == 'X') ? X_WINS : O_WINS; 
		}
		
		//no winner to report, lets see if there are any empty spaces
		return (availableMoves.size() == 0) ? DRAW : GAME_CONTINUES; 
	}

	/*
	 * Tests three board locations in a line
	 * returns true if they are the same, false otherwise.
	 * The player will have just played one of these spots, 
	 * so we can assume they are not all empty.
	 */
	private boolean testLine(int i, int j, int k) {
		return board[i] == board[j] && board[j] == board[k];
	}

	/**
	 * Return a String representing the current state of the game board
	 */
	public String getState() {
		return String.valueOf(board);
	}
	
	/**
	 * @return all available moves (board location is empty)
	 */
	public List<Integer> availableMoves() {
		return availableMoves;
	}
	
	/**
	 * 
	 * @param location
	 * @return current board token at the give location
	 */
	public char getToken(int location) {
		if (location < 0 || location > 8) return ' ';
		return board[location];
	}
}
