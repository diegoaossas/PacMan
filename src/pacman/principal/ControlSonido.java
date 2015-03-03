package pacman.principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import pacman.musica.Reproductor;

public class ControlSonido implements MouseListener
{
    private ArrayList<Image> botones;
    private ArrayList<Rectangle> botonesRect;
    private Reproductor repro;
    
    public ControlSonido()
    {
        botones = new ArrayList<Image>();
        botonesRect = new ArrayList<Rectangle>();
        repro = new Reproductor();
        
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
    

    public void draw(Graphics2D g)
    {
        if (Reproductor.reproduciendo)
        {
            Font letra = new Font("Arial", Font.PLAIN, 12);
            g.setFont(letra);
            g.setColor(Color.WHITE);
            g.drawString("Reproduciendo: " + repro.getTitulo() + " de " + repro.getAutor(), 5, 17);
        }

        int xoff = 0;

        for(Image imagen : botones)
        {
            if(Reproductor.reproduciendo)
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
    public void mouseClicked(MouseEvent me)
    {
        Point punto = new Point(Panel.mouseX, Panel.mouseY);

        if(me.getClickCount() == 1)
        {
            if(botonesRect.get(0).contains(punto))
                repro.reproduceAnterior();
            if(botonesRect.get(1).contains(punto))
                repro.alternarReproduccion();
            if(botonesRect.get(2).contains(punto))
                repro.detenerReproduccion();
            if(botonesRect.get(3).contains(punto))
                repro.reproduceSiguiente();
        }
    }

    @Override
    public void mousePressed(MouseEvent me){}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}
}
