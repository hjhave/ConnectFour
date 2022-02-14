import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MyTest {

	static int[] buttons;
	static ArrayList<Integer> moves;
	
	@BeforeAll
	static void setup() {
		buttons = new int[42];
		for(int i = 0; i < 42; i++)
		{
			buttons[i] = 0;
		}
		moves = new ArrayList<Integer>();
	}

	@Test
	static void moveInputAndUndo()
	{
		for(int i = 0; i < 5; i++)
		{
			buttons[41-i] = (moves.size()%2) + 1;
			moves.add(41-i);
			if(i%2 == 0)
			{
				assertEquals(1, buttons[moves.get(i)]);
			}
			else
			{
				assertEquals(2, buttons[moves.get(i)]);
			}
		}
		// Check that only the indexes in the list have a number not 0
		for(int j = 0; j < 42; j++)
		{
			if(moves.contains(j))
			{
				if(j%2 == 0)
				{
					assertEquals(1, buttons[j]);
				}
				else
				{
					assertEquals(2, buttons[j]);
				}
			}
			else
			{
				assertEquals(0, buttons[j]);
			}
		}
		// Undo all moves made
		for(int k = 0; k < moves.size(); k++)
		{
			int index = moves.get(k);
			buttons[index] = 0;
			moves.remove(k);
		}
		// Make sure undo worked
		assertEquals(0, moves.size());
		for(int j = 0; j < 42; j++)
		{
			assertEquals(0, buttons[j]);
		}
	}
	
	@Test
	static void testWin()
	{
		int count = 1;
		int count2 = 1;
		int[] horizButtons = {0,0,0,0,0,0,0,
				   			  0,0,0,0,0,0,0,
				   			  0,0,0,0,0,0,0,
				   			  0,0,0,0,0,0,0,
				   			  0,0,0,0,0,0,0,
				   			  0,1,1,1,1,0,0};
		int row = 5;
		int col = 3;
		int index = 38;
		while(col-(count-1) >= 0 && horizButtons[index-count] == 1)
		{
			count++;
		}
		while(col+(count2-1) < 7 && horizButtons[index-count2] == 1)
		{
			count++;
			count2++;
			if(count == 4)
			{
				break;
			}
		}
		assertEquals(4, count);
		
		count = 1;
		count2 = 1;
		int[] vertButtons = {0,0,0,0,0,0,0,
	   			  			 0,0,0,0,0,0,0,
	   			  			 0,0,0,1,0,0,0,
	   			  			 0,0,0,1,0,0,0,
	   			  			 0,0,0,1,0,0,0,
	   			  			 0,0,0,1,0,0,0};
		
		while(row-(count-1) >= 0 && vertButtons[index-(count*7)] == 1)
		{
			count++;
		}
		while(row+(count2-1) < 6 && vertButtons[index+(count2*7)] == 1)
		{
			count++;
			count2++;
			if(count == 4)
			{
				break;
			}
		}
		assertEquals(4, count);
		
		count = 1;
		count2 = 1;
		index = 31;
		row = 4;
		col = 3;
		int[] diagDownButtons = {0,0,0,0,0,0,0,
	   			  				 0,0,0,0,0,0,0,
	   			  				 0,1,0,0,0,0,0,
	   			  				 0,0,1,0,0,0,0,
	   			  				 0,0,0,1,0,0,0,
	   			  				 0,0,0,0,1,0,0};
		
		while(row-(count-1) >= 0 && col-(count-1) >= 0 && diagDownButtons[index-count-(count*7)] == 1)
		{
			count++;
		}
		while(row+(count2-1) < 6 && col+(count2-1) < 7 && diagDownButtons[index+count2+(count2*7)] == 1)
		{
			count++;
			count2++;
			if(count == 4)
			{
				break;
			}
		}
		assertEquals(4, count);
		
		count = 1;
		count2 = 1;
		index = 24;
		row = 3;
		col = 3;
		int[] diagUpButtons = {0,0,0,0,0,0,0,
	   			  			   0,0,0,0,0,0,0,
	   			  			   0,0,0,0,1,0,0,
	   			  			   0,0,0,1,0,0,0,
	   			  			   0,0,1,0,0,0,0,
	   			  			   0,1,0,0,0,0,0};
		
		while(row-(count-1) >= 0 && col+(count-1) < 7 && diagUpButtons[index+count-(count*7)] == 1)
		{
			count++;
		}
		while(row+(count2-1) < 6 && col-(count2-1) >= 0 && diagUpButtons[index-count2+(count2*7)] == 1)
		{
			count++;
			count2++;
			if(count == 4)
			{
				break;
			}
		}
		assertEquals(4, count);
	}
	
}
