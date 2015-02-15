package pacman.Menus;

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
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import pacman.Musica.Sonidos;
import pacman.PacMan;

public class MenuPane extends JPanel
{
    protected PacMan paquito = null;
    protected ArrayList<Rectangle> botonesRect = null;
    protected ArrayList<Image> botones = null;
    protected Image fondo = null;
    
    protected class Boton
    {
        public Rectangle contenedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
    
    protected class BotonSala extends Boton
    {
        public long salaID;
    }
        
    protected class Campo
    {
        public String texto;
        public String textoContenedor;
        public Rectangle contenedor;
        public int anchoTexto;
        public boolean mouse;
        public boolean seleccionado;
    }
    
    protected int espaciadoContenedor = 20;
    protected int bordeOvalado = 10;
    protected int separacionTope = 220;
    protected int thickActivo = 7;
    protected int thickNormal = 4;
    //Pasar variables comunes de los menus aqui
    
    public void mouseMovido(MouseEvent me)
    {
        Point punto = new Point(me.getX(), me.getY());
        
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
    
    public  MenuPane(PacMan paqui)
    {
        paquito = paqui;
        botones = new ArrayList<Image>();
        botonesRect = new ArrayList<Rectangle>();
        
        try
        {
            fondo = ImageIO.read(MenuPane.class.getResourceAsStream("PacFondo.jpg"));

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
    
    public void keyPressed(KeyEvent e)
    {
    }
    
    public void paint(Graphics2D g)
    {       
        g.drawImage(fondo, 0, 0, PacMan.ancho, PacMan.alto, paquito);

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
            
            g.drawImage(imagen, 5 + xoff, 5, 24, 24, paquito);
            Rectangle rect  = new Rectangle(5 + xoff, 5, 24, 24);
            botonesRect.add(rect);

            xoff += 26;
        }
        
        Font fuente;
        FontMetrics fMet;
        int ancho;

        g.setColor(Color.yellow);

        fuente = new Font("PacFont", Font.PLAIN, 111);
        fMet = g.getFontMetrics(fuente);
        g.setFont(fuente);
        ancho = fMet.stringWidth("PacMan");
        g.drawString("PacMan", (PacMan.ancho-ancho)/2, 135);

        fuente = new Font("Arial", Font.PLAIN, 12);
        fMet = g.getFontMetrics(fuente);
        g.setFont(fuente);
        ancho = fMet.stringWidth("v1.0.0");
        g.drawString("v1.0.0", PacMan.ancho - ancho - 2, PacMan.alto - 2);
    }
    

    @Override
    public void repaint()
    {
        if(paquito != null)
            paquito.repaint();
    }
        
    protected void cambiarMenu(MenuPane menu)
    {
        if(paquito != null)
            paquito.cambiarMenu(menu);
    }     
}
