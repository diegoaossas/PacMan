package pacman.Mainn;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import pacman.PacMan;
import pacman.Menus.MenuInicial;
import pacman.Menus.MenuPane;
import pacman.gamestate.GameStateManager;

public class Panel extends JPanel implements Runnable, KeyListener
{
	
	//Dimensiones del panel
	public static final int ANCHO = 800;
	public static final int ALTO = 600;
	public static final int ESCALA = 1;
	
	//Ciclo de programa
	private Thread thread;
	private boolean running = false;
	private int FPS = 60;
	private long targetTime = 1000 / FPS;
	
	//Pintado
	private Graphics2D g;
	private BufferedImage image;
	
	//GameStateManager
	private GameStateManager gsm;
	
	public Panel()
	{
		setPreferredSize(new Dimension(ANCHO * ESCALA, ALTO * ESCALA));
		setFocusable(true);
		requestFocus();
	}
	
	public void addNotify()
	{
		super.addNotify();
		
		if(thread == null)
		{
			running = true;
			addKeyListener(this);
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void init()
	{
		image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) image.getGraphics();
		
		gsm = new GameStateManager();
	}
	
	public void update()
	{
		gsm.update();
	}
	
	public void draw()
	{
		g.clearRect(0, 0, ANCHO, ALTO);
		gsm.draw(g);
	}
	
	public void drawToScreen()
	{
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, ANCHO * ESCALA, ALTO * ESCALA, null);
		g2.dispose();
	}
	
	@Override
	public void run()
	{
		init();
		
		long start;
		long elapsed;
		long wait;
		
		while(running)
		{
			start = System.nanoTime();
			update();
			draw();
			drawToScreen();
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0)
				wait = 5;
			
			try
			{
				Thread.sleep(wait);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}

		}
	}

	public void keyPressed(KeyEvent ke)
	{
		gsm.keyPressed(ke);
	}

	public void keyReleased(KeyEvent ke)
	{
		gsm.keyReleased(ke);
	}

	public void keyTyped(KeyEvent ke)
	{
		gsm.keyTyped(ke);
	}

}
