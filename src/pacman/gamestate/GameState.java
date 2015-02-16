package pacman.gamestate;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

public abstract class GameState
{
	protected GameStateManager gsm;

	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(KeyEvent ke);
	public abstract void keyReleased(KeyEvent ke);
	public abstract void keyTyped(KeyEvent ke);
	
}
