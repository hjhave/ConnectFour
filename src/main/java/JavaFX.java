import java.util.ArrayList;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFX extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	public int theme;
	public boolean isValidMove(GameButton buttons[], int index) // Returns true if the selected button press is a valid move
	{
		if(buttons[index].getPlayer() != 0) // Is the selected button already occupied?
		{
			return false;
		}
		if(index >= 35) // Is the selected button at the bottom row?
		{
			return true;
		}
		else if(buttons[index+7].getPlayer() != 0) // Is there an occupied button below the selected button?
		{
			return true;
		}
		return false;
	}
	
	public ArrayList<Integer> checkWin(GameButton buttons[], int row, int col) // Checks adjacent positions of button at specified row and column and returns list containing the winning row
	{
		ArrayList<Integer> win = new ArrayList<Integer>();
		int index = (row*7) + col;
		win.add(index);
		int player = buttons[index].getPlayer();
		int count = 1;
		int count2 = 1;
		// Check horizontal direction
		// Go to the left first
		while((col-(count-1)) >= 0 && buttons[index-count].getPlayer() == player) 
		{
			win.add(index-count);
			count++; 
			if(count == 4)
			{
				return win;
			}
		}
		
		// Go to the right keeping the current count
		while((col+(count2)) < 7 && buttons[index+count2].getPlayer() == player) 
		{
			win.add(index+count2);
			count2++;
			count++;
			if(count == 4)
			{
				return win;
			}
			
		}
		
		win.clear();
		win.add(index);
		count = 1; // reset the counter for next direction check
		count2 = 1;
		// Check vertical direction
		// Go upwards first
		while((row-(count-1)) >= 0 && buttons[index-(count*7)].getPlayer() == player)
		{
			win.add(index-(count*7));
			count++; 
			if(count == 4)
			{
				return win;
			}
		}
		
		// Go downwards keeping the current count
		while((row+(count2-1)) < 6 && buttons[index+(count2*7)].getPlayer() == player) 
		{
			win.add(index+(count2*7));
			count++;
			count2++;
			if(count == 4)
			{
				return win;
			}
		}
		
		win.clear();
		win.add(index);
		count = 1;
		count2 = 1;
		// Check left diagonal
		// Go up and left first
		while((row-(count-1)) >= 0 && (col-(count-1)) >= 0 && buttons[index-count-(count*7)].getPlayer() == player)
		{
			win.add(index-count-(count*7));
			count++;
			if(count == 4)
			{
				return win;
			}
		}
		
		// Go down and right keeping the current count
		while((row+(count2-1)) < 6 && (col+(count2-1)) < 7 && buttons[index+count2+(count2*7)].getPlayer() == player)
		{
			win.add(index+count2+(count2*7));
			count++;
			count2++;
			if(count == 4)
			{
				return win;
			}
		}
		
		win.clear();
		win.add(index);
		count = 1;
		count2 = 1;
		// Check right diagonal
		// Go up and right first
		while((row-(count-1)) >= 0 && (col+(count-1)) < 7 && buttons[index+count-(count*7)].getPlayer() == player)
		{
			win.add(index+count-(count*7));
			count++;
			if(count == 4)
			{
				return win;
			}
		}
		
		// Go down and left keeping the current count
		while((row+(count2-1)) < 6 && (col-(count2-1)) >= 0 && buttons[index-count2+(count2*7)].getPlayer() == player)
		{
			win.add(index-count2+(count2*7));
			count++;
			count2++;
			if(count == 4)
			{
				return win;
			}
		}
		win.clear();
		return win;
	}
	
	public void welcomeScreen(Stage welcomeStage)
	{
		welcomeStage.setTitle("Connect 4");
		
		VBox box = new VBox();
		
		Button startGame = new Button("Start Game");
		startGame.setOnAction(e->{
			welcomeStage.close();
			gamePlay(new Stage());
		});
		startGame.setPrefSize(700, 400);
		startGame.setAlignment(Pos.CENTER);
		
		Text title = new Text("Welcome to Connect 4\n");
		title.setFont(Font.font("Helvetica", FontPosture.REGULAR, 76));
		Text desc = new Text("Press Start Game to begin\n");
		
		TextFlow welcome = new TextFlow(title);
		welcome.getChildren().add(desc);
		welcome.setTextAlignment(TextAlignment.CENTER);
		
		box.getChildren().add(welcome);
		box.getChildren().add(startGame);
		box.setStyle("-fx-background-color: #89cff0");
		
		Scene scene = new Scene(box, 700,700);
		welcomeStage.setScene(scene);
		welcomeStage.show();
	}
	
	public void gamePlay(Stage gameStage)
	{
		gameStage.setTitle("Connect 4");
		GridPane gridpane = new GridPane();
		gridpane.setTranslateY(20);
		ArrayList<Integer> list = new ArrayList<Integer>(); // Can remove from list.remove on input of specific button
		ArrayList<String> moves = new ArrayList<String>(); // List used to display most recent move
		GameButton buttons[] = new GameButton[42];
		
		Text previousMove = new Text(""); // Will be updated on button press to display a move made by text
		Text currPlayer = new Text("Player " + ((list.size()%2) + 1) + "'s turn\n");
		
		Button undo = new Button("reverse move"); // Implement an undo button that will be set up later
		Button newGame = new Button("new game"); // Implement a new game button that will be set up later
		
		for(int i = 0; i < 6; i++)
		{
			for(int j = 0; j < 7; j++)
			{
				int currIndex = (i*7) + j;
				int row = i;
				int col = j;
				buttons[(i*7) + j] = new GameButton(currIndex);
				buttons[(i*7) + j].setOnAction(e->{
					String newMove = "Player " + ((list.size()%2)+1) + " moved to " + row + ", " + col + ".";
					moves.add(newMove);
					if(isValidMove(buttons, currIndex))
					{
						buttons[currIndex].setPlayer((list.size()%2) + 1);
						list.add(currIndex);
						previousMove.setText(newMove);
						currPlayer.setText("Player " + ((list.size()%2) + 1) + "'s turn\n");
						ArrayList<Integer> winningRow = checkWin(buttons, row, col);
						// May want to add a function to check when the board is full as well as a function for checking if there's a winner
						if(winningRow.size() == 4)
						{
							// Highlight the winning row
							for(int k = 0; k < winningRow.size(); k++)
							{
								buttons[winningRow.get(k)].setStyle("-fx-background-color: GREEN");
							}
							// Any button that would cause problems with transition to the victory screen should be disabled before the pause
							for(int k = 0; k < 42; k++)
							{
								buttons[k].setDisable(true);
							}
							undo.setDisable(true);
							newGame.setDisable(true);
							
							// Go to victory screen for player who last played after a pause
							PauseTransition pause = new PauseTransition(Duration.seconds(5));
							pause.setOnFinished(e1->{
								gameStage.close();
								victoryScreen(new Stage(), buttons[list.get(list.size()-1)].getPlayer());
							});
							pause.play();
						}
						else if(list.size() == 42)
						{
							// Got to tie screen due to whole board being full
							gameStage.close();
							victoryScreen(new Stage(), 0);
						}
					}
					else
					{
						moves.remove(moves.size()-1);
						previousMove.setText(newMove + "\nThis is NOT a valid move. Player " + ((list.size()%2)+1) + " pick again.");
					}
				});
				gridpane.add(buttons[(i*7) + j], j, i);
			}
		}
		gridpane.setAlignment(Pos.CENTER);
		gridpane.setHgap(5);
		gridpane.setVgap(5);
		
		VBox box = new VBox();
		if(theme == 0)
		{
			box.setStyle("");
			for(int i = 0; i < 42; i++)
			{
				buttons[i].setColor("");
			}
		}
		if(theme == 1)
		{
			box.setStyle("-fx-background-color: GRAY");
			for(int i = 0; i < 42; i++)
			{
				buttons[i].setColor("#89cff0");
			}
		}
		else if(theme == 2)
		{
			box.setStyle("-fx-background-image: url(\"background.png\")");
			for(int i = 0; i < 42; i++)
			{
				buttons[i].setStyle("-fx-background-image: url(\"button.jpg\")");
			}
			
		}
		
		BorderPane pane = new BorderPane(); // All menus will be aligned with this pane
		
		// Set up game play menu
		VBox gamePlayBox = new VBox();
		Button gamePlayMenu = new Button("Game Play");
		gamePlayMenu.setDisable(true);
		gamePlayBox.getChildren().add(gamePlayMenu);
		
		// Set up undo option
		undo.setOnAction(e->{
			if(list.size() != 0)
			{
				int index = list.get(list.size()-1);
				list.remove(list.size()-1);
				moves.remove(moves.size()-1);
				buttons[index].setStyle("");
				buttons[index].setPlayer(0); // Set button back to default
				currPlayer.setText("Player " + ((list.size()%2) + 1) + "'s turn\n");
				if(theme == 2)
				{
					buttons[index].setStyle("-fx-background-image: url(\"button.jpg\")");
				}
				if(list.isEmpty())
				{
					previousMove.setText("");
				}
				else
				{
					previousMove.setText(moves.get(moves.size()-1));
				}
			}
		});
		gamePlayBox.getChildren().add(undo);
		
		TextFlow playerDisplay = new TextFlow();
		
		playerDisplay.getChildren().add(currPlayer);
		
		playerDisplay.getChildren().add(previousMove);
		
		gamePlayBox.getChildren().add(playerDisplay);
		
		// Set up display for which player's turn it is and the most recent play
		pane.setLeft(gamePlayBox);
		
		// Set up the themes menu
		VBox themesBox = new VBox();
		Button themesMenu = new Button("Themes");
		themesMenu.setDisable(true);
		themesBox.getChildren().add(themesMenu);
		themesBox.setAlignment(Pos.TOP_CENTER);
		pane.setCenter(themesBox);
		
		Button origTheme = new Button("original theme");
		origTheme.setOnAction(e->{
			theme = 0;
			box.setStyle("");
			for(int i = 0; i < 42; i++)
			{
				buttons[i].setColor("");
			}
		});
		themesBox.getChildren().add(origTheme);
		
		Button theme1 = new Button("theme 1");
		theme1.setOnAction(e->{
			theme = 1;
			box.setStyle("-fx-background-color: GRAY");
			for(int i = 0; i < 42; i++)
			{
				buttons[i].setColor("#89cff0");
			}
		});
		themesBox.getChildren().add(theme1);
		
		Button theme2 = new Button("theme 2");
		theme2.setOnAction(e->{
			theme = 2;
			box.setStyle("-fx-background-image: url(\"background.png\")");
			for(int i = 0; i < 42; i++)
			{
				if(buttons[i].getPlayer() == 0)
				{
					buttons[i].setStyle("-fx-background-image: url(\"button.jpg\")");
				}
			}
		});
		themesBox.getChildren().add(theme2);
		
		// Set up text for how to play option
		TextFlow howToPlay = new TextFlow();
		Text howToText = new Text("");
		howToPlay.getChildren().add(howToText);
		
		// Set up options menu
		VBox optionsBox = new VBox();
		Button optionsMenu = new Button("Options");
		optionsMenu.setDisable(true);
		optionsBox.getChildren().add(optionsMenu);
		pane.setRight(optionsBox);
		
		// Set up how to play option
		Button howTo = new Button("how to play");
		howTo.setOnAction(e->{
			String displayedText = "Each player takes turns pressing a button on the grid starting with player one. A move is only valid if the button pressed is either at the bottom row or above a button already pressed. The first player to get a row of 4 buttons of their own color in any direction is the winner.";
			if(howToText.getText() == "")
			{
				howToText.setText(displayedText);
			}
			else
			{
				howToText.setText("");
			}
			
		});
		howToPlay.setTextAlignment(TextAlignment.CENTER);
		themesBox.getChildren().add(howToPlay);
		optionsBox.getChildren().add(howTo);
		
		// Set up new game reset option
		newGame.setOnAction(e->{
			while(list.size() != 0) // Empty out array list of button indexes and set all pressed buttons back to default
			{
				int index = list.get(list.size()-1);
				list.remove(list.size()-1);
				buttons[index].setStyle("");
				buttons[index].setPlayer(0); // Set button back to default
				if(theme == 2)
				{
					buttons[index].setStyle("-fx-background-image: url(\"button.jpg\")");
				}
				previousMove.setText("");
				currPlayer.setText("Player " + ((list.size()%2) + 1) + "'s turn\n");
			}
		});
		optionsBox.getChildren().add(newGame);
		
		// Set up exit option
		Button exit = new Button("exit");
		exit.setOnAction(e->{
			System.exit(0); // Exit program
		});
		optionsBox.getChildren().add(exit);
		
		box.getChildren().add(pane);
		box.getChildren().add(gridpane);
		
		Scene scene = new Scene(box, 700,750);
		
		gameStage.setScene(scene);
		gameStage.show();
	}
	
	public void victoryScreen(Stage victoryStage, int winningPlayer)
	{
		victoryStage.setTitle("Connect 4");
		
		VBox box = new VBox();
		
		VBox buttons = new VBox();
		Button playAgain = new Button("Play Again");
		playAgain.setOnAction(e->{
			victoryStage.close();
			gamePlay(new Stage());
		});
		playAgain.setPrefSize(200, 100);
		buttons.getChildren().add(playAgain);
		
		Button exit = new Button("Exit");
		exit.setOnAction(e->{
			victoryStage.close();
			System.exit(0);
		});
		exit.setPrefSize(200, 100);
		buttons.getChildren().add(exit);
		buttons.setAlignment(Pos.BOTTOM_CENTER);
		buttons.setTranslateY(150);
		
		BorderPane pane = new BorderPane();
		pane.setTop(buttons);
		
		if(theme == 1)
		{
			box.setStyle("-fx-background-color: GRAY");
		}
		else if(theme == 2)
		{
			box.setStyle("-fx-background-image: url(\"background.png\")");
		}
		
		TextFlow winDisplay = new TextFlow();
		Text winner;
		if(winningPlayer == 0)
		{
			winner = new Text("Game was a tie\n");
		}
		else
		{
			winner = new Text("Player " + winningPlayer + " wins!\n");
		}
		winner.setFont(Font.font("Helvetica", FontPosture.REGULAR, 76));
		winDisplay.getChildren().add(winner);
		winDisplay.setTextAlignment(TextAlignment.CENTER);
		
		box.getChildren().add(winDisplay);
		box.getChildren().add(pane);
		
		Scene scene = new Scene(box, 700, 750);
		victoryStage.setScene(scene);
		victoryStage.show();
	}
	
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		theme = 0;
		welcomeScreen(primaryStage);
	}

}
