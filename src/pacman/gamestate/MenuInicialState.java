package pacman.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import pacman.Mainn.Panel;
import pacman.Musica.Sonidos;

public class MenuInicialState extends GameState
{
	private String[] opciones = {"Login", "Registrar", "Salir"};
	private itemMenu[] menu = new itemMenu[opciones.length];
	
	private Font regFont = new Font("Arial", Font.BOLD, 16);
	private FontMetrics fMet;
	
	private BufferedImage bg, buttonSet;
	private BufferedImage[] buttonFrames = new BufferedImage[2];
	
	public MenuInicialState(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try
		{
			bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
			buttonSet = ImageIO.read(getClass().getResource("/Sprites/boton.png"));
			
			for(int i = 0; i<2; i++)
			{
				buttonFrames[i] = buttonSet.getSubimage(0, 40 * i, 160, 40);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		for(int i = 0; i < opciones.length; i++)
		{
			menu[i] = new botonMenu();
			botonMenu boton = (botonMenu) menu[i];
			boton.texto = opciones[i];
			boton.X = (Panel.ANCHO/2) - (buttonFrames[0].getWidth() /2);
			boton.Y = (Panel.ALTO/2) - 26;
			boton.ancho = buttonFrames[0].getWidth();
			boton.alto = buttonFrames[0].getHeight();
			boton.buttonPos = 0;
		}		
		
		for(int i = 0; i < opciones.length; i++)
		{
			botonMenu boton = (botonMenu) menu[i];
			boton.rect = new Rectangle(boton.X, boton.Y, boton.ancho, boton.alto);
		}
	}
	
	private int botonMouse(Rectangle r, int buttonPos)
	{
		if(r.contains(Panel.mouseX, Panel.mouseY))
			buttonPos = 1;
		else
			buttonPos = 0;
		
		return buttonPos;
	}

	@Override
	public void draw(Graphics2D g) {
		g.drawImage(bg, 0, 0, Panel.ANCHO, Panel.ALTO, null);
		
		g.setColor(Color.YELLOW);
		g.setFont(regFont);
		fMet = g.getFontMetrics(regFont);
		
		//Pintar botones
		for(int i = 0; i < opciones.length; i++)
		{
			botonMenu boton = (botonMenu) menu[i];
			boton.rect = new Rectangle(boton.X, boton.Y - 30 + (80 * i), boton.ancho, boton.alto);
			g.drawImage(buttonFrames[boton.buttonPos], boton.X, boton.Y - 30 + (80 * i), boton.ancho, boton.alto, null);
			g.drawString(opciones[i], Panel.ANCHO/2 - (fMet.stringWidth(opciones[i])/2), Panel.ALTO/2 - 32 + (80 * i));
		}
		
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent me)
	{
		for(itemMenu itm : menu)
		{
			if(itm instanceof botonMenu)
			{
				botonMenu boton = (botonMenu)itm;
				
				if(boton.rect.contains(Panel.mouseX, Panel.mouseY))
				{
	                if(boton.texto.equals("Salir"))
	                {
	                	Sonidos.MENUOUT.play();
	                    System.exit(0);
	                }
	                else
	                {
	                	Sonidos.MENUIN.play();
	                }

	                
	                if(boton.texto.equals("Login"))
	                    gsm.setState(GameStateManager.LOGINSTATE);
	                
	                if(boton.texto.equals("Registrar"))
	                    gsm.setState(GameStateManager.REGISTRARSTATE);
				}
			}
			else
			{
				continue;
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update()
	{
		for(int i = 0; i < opciones.length; i++)
		{
			botonMenu boton = (botonMenu) menu[i];
			boton.buttonPos = botonMouse(boton.rect, boton.buttonPos); 
		}
	}

}
