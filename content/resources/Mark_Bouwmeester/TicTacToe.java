import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TicTacToe extends Application {

	AI agent = new AI();
	
	//TicTacToe game
	Game myGame = new Game();

	//UI Elements
	Button[] board = new Button[9];
	Label status = new Label("Status: New Game");
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		GridPane boardLayout = new GridPane();
		boardLayout.setPadding(new Insets(20, 20, 10, 20));
		boardLayout.setHgap(10);
		boardLayout.setVgap(10);
		
		int loc = 0;
		for (int row = 0; row < 3; row++) {
			for (int col  = 0; col < 3; col++) {
				
				//setup a button for this location
				board[loc] = new Button();
				board[loc].setPrefSize(100, 100);
			
				final int i = loc;
				board[loc].setOnAction( e_-> playLocation(i));
				
				boardLayout.add(board[loc], col, row);
				
				loc++;
			}
		}
		
		
		//Create buttons for new game and AI training
		Button newGame = new Button("New Game");
		newGame.setOnAction( e -> newGame());
		
		Button trainAI = new Button("Train AI");
		trainAI.setOnAction( e -> trainAI());
				
		HBox controls = new HBox(newGame, trainAI);
		controls.setPadding(new Insets(10, 10, 10, 10));
		controls.setSpacing(10);
		controls.setAlignment(Pos.CENTER);
		
		
		
		HBox gameState = new HBox(status);
		gameState.setPadding(new Insets(10, 10, 10, 10));
		gameState.setSpacing(10);
		
		VBox gameBoard = new VBox(boardLayout, controls, gameState);
		
		Scene myScene = new Scene(gameBoard);
		
		primaryStage.setScene(myScene);
		primaryStage.setTitle("Tic Tac Toe");
		primaryStage.show();

	}

	private void newGame() {
		//make a new game
		myGame = new Game();
		
		//reset the board
		for (int i = 0; i < board.length; i++) {
			board[i].setText("");
		}
		
		status.setText("Status: New Game");
	}

	private void trainAI() {
		agent.train(30000);
	}

	private void playLocation(int loc) {
		if (myGame.play(loc)) {
			
			//move was successful, so update the UI
			board[loc].setText(String.valueOf(myGame.getToken(loc)));
			status.setText("Status: " + sayStatus(myGame.getStatus()));
			
			if (myGame.getStatus() == Game.GAME_CONTINUES) {
				//AI Move
				int move = agent.chooseMove(myGame, 0);
				myGame.play(move);
				board[move].setText(String.valueOf(myGame.getToken(move)));
				status.setText("Status: " + sayStatus(myGame.getStatus()));
			}
		}
	}

	private String sayStatus(int gameStatus) {
		switch (gameStatus) {
			case Game.X_WINS: return "X Wins!";
			case Game.O_WINS: return "O Wins!";
			case Game.DRAW: return "Draw!";
			default: return "...";
		}
	}
	
	public static void main(String[] args) {
		launch();
	}

}
