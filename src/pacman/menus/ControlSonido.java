package pacman.menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import pacman.main.Panel;
import pacman.musica.Sonidos;

public class ControlSonido extends GameState
{
    private ArrayList<Image> botones;
    private ArrayList<Rectangle> botonesRect;
    private Sonidos repro;
    
    public ControlSonido()
    {
        botones = new ArrayList<Image>();
        botonesRect = new ArrayList<Rectangle>();
        repro = new Sonidos();
        repro.inicializar();
        repro.reproduceMusica();
        
        try
        {
            Image img;

            img = ImageIO.read(getClass().getResourceAsStream("/Sprites/audioPrevious.png"));
            botones.add(img);

            img = ImageIO.read(getClass().getResourceAsStream("/Sprites/audioPause.png"));
            botones.add(img);

            img = ImageIO.read(getClass().getResourceAsStream("/Sprites/audioPlay.png"));
            botones.add(img);

            img = ImageIO.read(getClass().getResourceAsStream("/Sprites/audioStop.png"));
            botones.add(img);

            img = ImageIO.read(getClass().getResourceAsStream("/Sprites/audioNext.png"));
            botones.add(img);
        }
        catch(IOException ex)
        {
            repro.detenerReproduccion();
        }
    }
    
	@Override
	public void draw(Graphics2D g)
	{
        if(Sonidos.reproduciendo)
        {
	        Font letra = new Font("Arial", Font.PLAIN, 12);
	        g.setFont(letra);
	        g.setColor(Color.WHITE);
	        g.drawString("Reproduciendo: " + repro.getTitulo() + " de " + repro.getAutor() , 5, 17 );
        }
        
        int xoff = 0;
        
        for(Image imagen : botones)
        {
            if(Sonidos.reproduciendo)
            {
                if(botones.get(2).equals(imagen))
                    continue;
            }
            else
            {
                if(botones.get(1).equals(imagen))
                    continue;
            }
            
            g.drawImage(imagen, 5 + xoff, 17, 24, 24, null);
            Rectangle rect  = new Rectangle(5 + xoff, 17, 24, 24);
            botonesRect.add(rect);

            xoff += 26;
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
	public void mouseClicked(MouseEvent me) {
        Point punto = new Point(Panel.mouseX, Panel.mouseY);
        
        if(me.getClickCount() == 1)
        {
            if(botonesRect.get(0).contains(punto))
            	repro.reproduceAnterior();
            if(botonesRect.get(1).contains(punto))
            	repro.pausarReproduccion();
            if(botonesRect.get(2).contains(punto))
            	repro.detenerReproduccion();
            if(botonesRect.get(3).contains(punto))
            	repro.reproduceSiguiente();
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
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
