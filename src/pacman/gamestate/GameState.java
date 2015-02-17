package pacman.gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState
{
	protected GameStateManager gsm;

	public abstract void init();
	public abstract void update();
	public abstract void draw(Graphics2D g);
	public abstract void keyPressed(KeyEvent ke);
	public abstract void keyReleased(KeyEvent ke);
	public abstract void keyTyped(KeyEvent ke);
	public abstract void mouseDragged(MouseEvent me);
	public abstract void mouseMoved(MouseEvent me);
	public abstract void mouseClicked(MouseEvent me);
	public abstract void mouseEntered(MouseEvent me);
	public abstract void mouseExited(MouseEvent me);
	public abstract void mousePressed(MouseEvent me);
	public abstract void mouseReleased(MouseEvent me);

	class itemMenu
	{
		int X;
		int Y;
		int ancho;
		int alto;
		String texto;
		int buttonPos;
		Rectangle rect;
	}
	
	class botonMenu extends itemMenu
	{
	}	
	
	class campoMenu extends itemMenu
	{
		boolean seleccionado = false;
		String contenido = "";
	}
	
	class textoMenu extends itemMenu
	{
		
	}
}
