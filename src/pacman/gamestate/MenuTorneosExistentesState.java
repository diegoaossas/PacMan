package pacman.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import pacman.Main.Panel;
import pacman.Musica.Sonidos;
import Libreria.Actions;
import Libreria.Sala;

public class MenuTorneosExistentesState extends GameState
{
	private String[] opciones = {"Atras"};
	private itemMenu[] menu = new itemMenu[opciones.length];
	
	private Font regFont = new Font("Arial", Font.BOLD, 16);
	private FontMetrics fMet;
	
	private BufferedImage bg, buttonSet, campoSet;
	private BufferedImage[] buttonFrames = new BufferedImage[2];
	private BufferedImage[] campoFrames = new BufferedImage[3];
	
    private Thread listenLobby = null;
    private ArrayList<salaMenu> salasBoton = null;
    private ArrayList<itemMenu> botones = null;
    
	public MenuTorneosExistentesState(GameStateManager gsm)
	{
		this.gsm = gsm;
		
		try
		{
			bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
			buttonSet = ImageIO.read(getClass().getResource("/Sprites/boton.png"));
			campoSet = ImageIO.read(getClass().getResource("/Sprites/campo.png"));
			
			for(int i = 0; i<2; i++)
			{
				buttonFrames[i] = buttonSet.getSubimage(0, 40 * i, 160, 40);
			}
			for(int i = 0; i<3; i++)
			{
				campoFrames[i] = campoSet.getSubimage(0, 36 * i, 237, 36);
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
		for(int i = 0; i < botones.size(); i++)
		{
			//botonMenu boton = (botonMenu) itm;
			itemMenu boton =  botones.get(i);
			boton.rect = new Rectangle(boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto);
			
			if(boton instanceof salaMenu)
				g.drawImage(campoFrames[boton.buttonPos], boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto, null);
			else
				g.drawImage(buttonFrames[boton.buttonPos], boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto, null);
			
			g.drawString(boton.texto, Panel.ANCHO/2 - (fMet.stringWidth(boton.texto)/2), Panel.ALTO/2 - 32 + (60 * i));
		}
		
	}
	
	@Override
	public void init()
	{
		salasBoton = new ArrayList<salaMenu>();
		botones = new ArrayList<>();

		
        listenLobby = new Thread(()->
        {
            try {
                
                GameStateManager.cliente.getOut().writeObject(Actions.GETLOBBYSstream);
                
                while (true)
                {                        
                    ArrayList<Sala> salas = (ArrayList<Sala>) GameStateManager.cliente.getIn().readObject();
                    System.out.println("Obtenidas " + salas.size() + " salas");

                    for(Sala sala : salas)
                    {
                        System.out.println("MenuTorneoExistente::listenLobby -> Sala recibida" + sala.nombreSala + " con" + sala.jugadores.size() + " de " + sala.maxjugadores);   
                    }

                    salasBoton.clear();

                    for(Sala sala : salas)
                    {
                        salaMenu boton = new salaMenu();
                        boton.IDSala = sala.idSala;
                        boton.texto = sala.nombreSala;
            			boton.rect = new Rectangle(boton.X, boton.Y, boton.ancho, boton.alto);
            			boton.X = (Panel.ANCHO/2) - (campoFrames[0].getWidth() /2);
            			boton.Y = (Panel.ALTO/2) - 26;
            			boton.ancho = campoFrames[0].getWidth();
            			boton.alto = campoFrames[0].getHeight();
            			boton.buttonPos = 0;
                        
                        salasBoton.add(boton);
                    }

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch ( InterruptedException e)
                    {
                        Thread.currentThread().interrupt(); // restore interrupted status
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex)
            {
            }
        });
        
        listenLobby.start();
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
		ArrayList<itemMenu> items = (ArrayList<itemMenu>) botones.clone();
		
		for(itemMenu itm : items)
		{
			if(itm instanceof salaMenu)
			{
				salaMenu salaBoton = (salaMenu)itm;
				
				if(salaBoton.rect.contains(Panel.mouseX, Panel.mouseY))
				{
					Sonidos.MENUIN.play();                            
					boolean joined = false;
					
					try
					{
						GameStateManager.cliente.getOut().writeObject(Actions.JoinSALA);
						GameStateManager.cliente.getOut().writeObject(salaBoton.IDSala);
						joined = (boolean)GameStateManager. cliente.getIn().readObject();
                
	                    this.listenLobby.interrupt();
	                    this.listenLobby = null;
	                    GameStateManager.cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
	                    
		                if(joined)
		    				gsm.setStateSalaEspera(salaBoton.IDSala);
		                else
		                	gsm.setStateMensaje("Sala Llena", "La sala se encuentra llena, escoga otra o intente mas tarde", GameStateManager.MENULISTATORNEOSSTATE);
					}
					catch (IOException | ClassNotFoundException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else if(itm instanceof botonMenu)
			{
				botonMenu boton = (botonMenu)itm;
				
				if(boton.rect.contains(Panel.mouseX, Panel.mouseY))
				{
	                if(boton.texto.equals("Atras"))
	                {
	                	Sonidos.MENUOUT.play();
                        this.listenLobby.interrupt();
                        this.listenLobby = null;
                        try {
							GameStateManager.cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        gsm.setState(GameStateManager.MENUTORNEOSTATE);
	                }
	                else
	                {
	                	Sonidos.MENUIN.play();
	                }
	                
	                if(boton.texto.equals("Torneo"))
	                {
	                	gsm.setState(GameStateManager.MENUTORNEOSTATE);
	                }
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
		botones.clear();
		
		for(itemMenu mn : menu)
		{
			botones.add(mn);
		}
		
		for(itemMenu mn : salasBoton)
		{
			botones.add(mn);
		}
		
		for(itemMenu itm : botones)
		{
			itm.buttonPos = botonMouse(itm.rect, itm.buttonPos); 
		}
	}

}