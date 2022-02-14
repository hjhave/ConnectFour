import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
public class GameButton extends Button {
	int currIndex;
	int playerOccupied; // 0 if empty, 1 if occupied by player 1, and 2 if occupied by player 2
	String defaultColor;
	
	public GameButton(int index, String defaultColor)
	{
		this.defaultColor = defaultColor;
		currIndex = index;
		setShape(new Circle(45));
		setMinSize(90, 90);
		setMaxSize(90, 90);
		if(defaultColor != "")
		{
			setStyle(
				"-fx-background-color: " + defaultColor
			);
		}
		playerOccupied = 0;
	}
	
	public GameButton(int index)
	{
		currIndex = index;
		setShape(new Circle(45));
		setMinSize(90, 90);
		setMaxSize(90, 90);
		setStyle(
				""
        );
		playerOccupied = 0;
	}
	
	public void setPlayer(int player)
	{
		playerOccupied = player;
		if(player == 1)
		{
			setStyle(
					"-fx-background-color: YELLOW"
	        );
		}
		else if(player == 2)
		{
			setStyle(
					"-fx-background-color: RED"
	        );
		}
		else
		{
			if(defaultColor == "")
			{
				setStyle(
					"-fx-background-color: LIGHTGRAY"
				);
			}
			else
			{
				setStyle(
						"-fx-background-color: " + defaultColor
				);
			}
		}
	}
	
	public int getPlayer()
	{
		return playerOccupied;
	}
	
	public void setIndex(int newIndex)
	{
		currIndex = newIndex;
	}
	
	public int getIndex()
	{
		return currIndex;
	}
	
	public void setColor(String color)
	{
		defaultColor = color;
		setPlayer(playerOccupied);
	}
}
