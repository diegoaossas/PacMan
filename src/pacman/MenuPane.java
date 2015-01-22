package pacman;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MenuPane extends JPanel {
       
    public PacMan paquito;
    private Rectangle btonSonido;
    
    public void mouseMovido(MouseEvent me)
    {
        Point punto = new Point(me.getX(), me.getY());
        
        if(me.getClickCount() == 1)
        {
            if(btonSonido.contains(punto))
            {
                if(Sonidos.reproduciendo)
                    Sonidos.detenFondo();
                else
                    Sonidos.reproduceFondo();
            }
        }
    }
    
    public  MenuPane(PacMan paqui)
    {
        paquito = paqui;
    }
    
    public void keyPressed(KeyEvent e)
    {
        //System.out.println("Tecla " + e.getKeyCode() + " Presionada");
    }
    
    public void paint(Graphics2D g)
    {        
        try
        {
            InputStream is;
            Image img;
            
            is = PacMan.class.getResourceAsStream("PacFondo.jpg");
            img = ImageIO.read(is);
            g.drawImage(img, 0, 0, paquito.ancho, paquito.alto, paquito);
            
            if(Sonidos.reproduciendo)
                is = PacMan.class.getResourceAsStream("audio.png");
            else
                is = PacMan.class.getResourceAsStream("audioOff.png");
            
            img = ImageIO.read(is);
            g.drawImage(img, 5, 5, 24, 24, paquito);
            btonSonido = new Rectangle(5, 5, 24, 24);
        }
        catch(IOException e)
        {
            System.out.println("Error cargando fondo...");
            System.out.println("-Mensaje del error: " + e.getMessage());
        }
        
        Font fuente;
        FontMetrics fMet;
        int ancho;
        
        g.setColor(Color.yellow);
        
        fuente = new Font("PacFont", Font.PLAIN, 111);
        fMet = g.getFontMetrics(fuente);
        g.setFont(fuente);
        ancho = fMet.stringWidth("PacMan");
        g.drawString("PacMan", (float)((paquito.ancho-ancho)/2), (float)120);
        
        fuente = new Font("Arial", Font.PLAIN, 12);
        fMet = g.getFontMetrics(fuente);
        g.setFont(fuente);
        ancho = fMet.stringWidth("v1.0.0");
        g.drawString("v1.0.0", (float)(paquito.ancho-ancho-2), (float)(paquito.alto-2));
    }
}
