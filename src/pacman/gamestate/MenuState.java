package pacman.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import pacman.Mainn.Panel;

public class MenuState extends GameState
{
	private String[] menu = {"Login", "Registrar", "Salir"};
	private Font regFont = new Font("Arial", Font.PLAIN, 16);
	
	private BufferedImage bg, buttonSet;
	private BufferedImage[] buttonFrames = new BufferedImage[4];
	
	private int loginButtonpos = 0, registrarButtonpos = 0, salirButtonpos = 0;
	private int powerUpTicks = 0, powerUpMax = 20;
	
	private Rectangle loginR, registrarR, salirR;
	
	public MenuState(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try
		{
			bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
			buttonSet = ImageIO.read(getClass().getResource("/Sprites/OptionBar_Grid.png"));
			
			for(int i = 0; i<4; i++)
			{
				buttonFrames[i] = buttonSet.getSubimage(0, 30 * i, 110, 30);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		loginR = new Rectangle( (Panel.ANCHO/2) - ((int)buttonFrames[loginButtonpos].getWidth() /2), (Panel.ALTO/2) - 26, (int)buttonFrames[loginButtonpos].getWidth(), (int)buttonFrames[loginButtonpos].getHeight() );
		registrarR = new Rectangle( (Panel.ANCHO/2) - ((int)buttonFrames[registrarButtonpos].getWidth() /2), (Panel.ALTO/2) + 8, (int)buttonFrames[registrarButtonpos].getWidth(), (int)buttonFrames[registrarButtonpos].getHeight() );
		salirR = new Rectangle( (Panel.ANCHO/2) - ((int)buttonFrames[salirButtonpos].getWidth() /2), (Panel.ALTO/2) + 42, (int)buttonFrames[salirButtonpos].getWidth(), (int)buttonFrames[salirButtonpos].getHeight() );
	}
	
	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void update() {
		// TODO Auto-generated method stub
		
	}

	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, Panel.ANCHO, Panel.ALTO, null);
		
		//Pintar botones
		g.drawImage(buttonFrames[loginButtonpos], (Panel.ANCHO/2) - ((int)buttonFrames[loginButtonpos].getWidth() /2), (Panel.ALTO/2) - 26, (int)buttonFrames[loginButtonpos].getWidth(), (int)buttonFrames[loginButtonpos].getHeight(), null);
		g.drawImage(buttonFrames[registrarButtonpos], (Panel.ANCHO/2) - ((int)buttonFrames[registrarButtonpos].getWidth() /2), (Panel.ALTO/2) + 8, (int)buttonFrames[registrarButtonpos].getWidth(), (int)buttonFrames[registrarButtonpos].getHeight(), null);
		g.drawImage(buttonFrames[salirButtonpos], (Panel.ANCHO/2) - ((int)buttonFrames[salirButtonpos].getWidth() /2), (Panel.ALTO/2) + 42, (int)buttonFrames[salirButtonpos].getWidth(), (int)buttonFrames[salirButtonpos].getHeight(), null);
		
		g.setColor(Color.WHITE);
		g.setFont(regFont);
		
		for(int i = 0; i < menu.length; i++)
		{
			g.drawString(menu[i], Panel.ANCHO/2 - 19, Panel.ALTO/2 - 5 + (i *35));
		}
	}

	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

}
