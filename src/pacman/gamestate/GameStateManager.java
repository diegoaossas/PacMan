package pacman.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class GameStateManager
{
	private ArrayList<GameState> states;
	private int currentState;
	
	public static final int MENUSTATE = 0;
	public static final int WAVE1STATE = 1; 
	
	public GameStateManager()
	{
		states = new ArrayList<GameState>();
		states.add(new MenuState(this));
		setState(MENUSTATE);
	}

	public void update()
	{
		states.get(currentState).update();
	}

	public void draw(Graphics2D g)
	{
		states.get(currentState).draw(g);
		
	}

	public void keyPressed(KeyEvent ke)
	{
		states.get(currentState).keyPressed(ke);
	}

	public void keyReleased(KeyEvent ke)
	{
		states.get(currentState).keyReleased(ke);
	}

	public void keyTyped(KeyEvent ke)
	{
		states.get(currentState).keyTyped(ke);	
	}
	
	public void setState(int state)
	{
		currentState = state;
		states.get(state).init();
	}
	
	public int getCurrentState()
	{
		return currentState;
	}
}
