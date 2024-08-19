import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class AI {
	
	private final Random RNG = new Random();
	private final double LEARNING_RATE = 0.75;
	private final double DISCOUNT_FACTOR = 0.5;
	
	HashMap<String, Double> memory = new HashMap<>();
	
	public int chooseMove(Game game, double exploreProbability) {
		//first make sure that the game is still going
		if (game.getStatus() != Game.GAME_CONTINUES) return -1;
		
		//get a string representing the current board state
		String state = game.getState();
		
		//Check the memory to see if we have ever encountered this state before
		List<Entry<String, Double>> previousActions = memory.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith(state))
				.toList();
		
		
		//choose a random move if we have never seen this state before, 
		// or randomly choose to explore
		if (previousActions.isEmpty() || RNG.nextDouble() < exploreProbability) {
			List<Integer> moves = game.availableMoves();
			return moves.get(RNG.nextInt(moves.size()));
		}
		
		//if we have seen this state before, look for the best known action
		double maxQuality = previousActions.stream()
				.map(Map.Entry::getValue)
				.max(Double::compare)
				.orElse(0.0);
		
		//create a list of all previous actions tied for the highest quality
		List<String> tiedActions = previousActions.stream()
				.filter(entry -> entry.getValue().equals(maxQuality))
				.map(Map.Entry::getKey)
				.toList();
		
		//Choose an action off this list
		String chosenAction = tiedActions.get(RNG.nextInt(tiedActions.size()));
		
		//Chosen action will be the last digit of the chosenAction, converted to int
		return Integer.parseInt(chosenAction.substring(chosenAction.length()-1));
	}
	
	/**
	 * Make an update to the quality stored in the memory table based on the reward for a move
	 * @param stateAction	- starting state and action chosen
	 * @param reward		- value for making this action
	 * @param newState		- state after taking this action
	 */
	public void updateQualityValue(String stateAction, double reward, String newState) {
		//Check the memory for the known quality of the given state/action string (defaulting as 0.0 if it is unknown)
		double currentQuality = memory.getOrDefault(stateAction, 0.0);
		
		//find out the quality of the best known "next action"
		double bestNextMove = memory.entrySet().stream()
				.filter(entry -> entry.getKey().startsWith(newState))
				.map(Map.Entry::getValue)
				.max(Double::compare)
				.orElse(0.0);

		double newQuality = currentQuality + LEARNING_RATE * (reward - DISCOUNT_FACTOR * bestNextMove - currentQuality);

		//if there is a change to make, make it
		if (currentQuality != newQuality) {
			memory.put(stateAction, newQuality);			
		}
	}
	
	
	/**
	 * Trains the AI by playing games against itself
	 * @param games number of games to play for training
	 */
	public void train(int games) {
		//handy to have some type of progress displayed on screen for this
		//because it can be lengthy
		System.out.printf("Starting training of %d games.\n", games);
		System.out.println("---------1---------2---------3---------4---------5");
		
		//train for the give number of games
		for (int i = 0; i < games; i++) {
			playOneGame();
			if ( i % 1000 == 0) System.out.print("*");
		}
		
		//give some idea of how the memory has changed because of this training
		System.out.println();
		System.out.println("Training completed.");
		System.out.println("Memory now contains " + memory.size() + " quality values");		
	}
	
	/**
	 * play one training game, updating rewards memory when the game finishes
	 */
	private void playOneGame() {
		Game game = new Game();
		
		//get the initial state of the game (blank board...)
		String startingState = game.getState();
		
		//play the game until a draw or one player wins
		while (game.getStatus() == Game.GAME_CONTINUES) {
			//while training, we give the AI at least some chance to choose 
			//random moves so that it explores moves it may not have tried
			int move = chooseMove(game, 0.50);
			game.play(move);

			//calculate a reward for this move
			double reward = getReward(game.getStatus());
			String newState = game.getState();
			
			//update the Quality table with results of this move
			updateQualityValue(startingState + ":" + move, reward, newState);
			
			//remember this as the starting state for the next move
			startingState = newState;
		}
		
	}
	
	/**
	 * Calculate the reward for a move given the status of the game after
	 * making the move.
	 * @param status
	 * @return 10.0 for a win, 5.0 for a draw, 0.0 otherwise
	 */
	private double getReward(int status) {
		switch(status) {
			case Game.X_WINS:
			case Game.O_WINS:
				return 10.0;
			case Game.DRAW:
				return 5.0;
			default:
				return 0.0;
		}
	}
}
