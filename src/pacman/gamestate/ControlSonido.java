package pacman.gamestate;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import pacman.Mainn.Panel;
import pacman.Menus.MenuPane;
import pacman.Musica.Sonidos;

public class ControlSonido extends GameState
{
	
	private ArrayList<Image> botones;
    private ArrayList<Rectangle> botonesRect;
    
    public ControlSonido()
    {
		botones = new ArrayList<Image>();
        botonesRect = new ArrayList<Rectangle>();
        
        try
        {
            Image img;

            img = ImageIO.read(MenuPane.class.getResourceAsStream("audioPrevious.png"));
            botones.add(img);

            img = ImageIO.read(MenuPane.class.getResourceAsStream("audioPause.png"));
            botones.add(img);

            img = ImageIO.read(MenuPane.class.getResourceAsStream("audioPlay.png"));
            botones.add(img);

            img = ImageIO.read(MenuPane.class.getResourceAsStream("audioStop.png"));
            botones.add(img);

            img = ImageIO.read(MenuPane.class.getResourceAsStream("audioNext.png"));
            botones.add(img);
        }
        catch(IOException ex)
        {
            Sonidos.detenerReproduccion();
        }
	}
    
	@Override
	public void draw(Graphics2D g)
	{
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
            
            g.drawImage(imagen, 5 + xoff, 5, 24, 24, null);
            Rectangle rect  = new Rectangle(5 + xoff, 5, 24, 24);
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
                Sonidos.reproduceAnterior();
            if(botonesRect.get(1).contains(punto))
                Sonidos.pausarReproduccion();
            if(botonesRect.get(2).contains(punto))
                Sonidos.detenerReproduccion();
            if(botonesRect.get(3).contains(punto))
                Sonidos.reproduceSiguiente();
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
