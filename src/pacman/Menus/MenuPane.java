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
import pacman.PacMan;
import pacman.Musica.Sonidos;

public class MenuPane extends JPanel {
       
    public final PacMan paquito;
    private final ArrayList<Rectangle> botonesRect;
    private final ArrayList<Image> botones;
    private Image fondo = null;
    
    public class Boton
    {
        public Rectangle contenedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
    
    public class BotonSala extends Boton
    {
        public long salaID;
    }
        
    public class Campo
    {
        public String texto;
        public String textoContenedor;
        public Rectangle contenedor;
        public int anchoTexto;
        public boolean mouse;
        public boolean seleccionado;
    }
    
    public int espaciadoContenedor = 20;
    public int bordeOvalado = 10;
    public int separacionTope = 220;
    public int thickActivo = 7;
    public int thickNormal = 4;
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
    
    public  MenuPane(PacMan paqui) throws IOException
    {
        paquito = paqui;
        botones = new ArrayList<>();
        botonesRect = new ArrayList<>();
        
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
    
    public void keyPressed(KeyEvent e)
    {
    }
    
    public void paint(Graphics2D g)
    {       
        g.drawImage(fondo, 0, 0, paquito.ancho, paquito.alto, paquito);

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
        g.drawString("PacMan", (paquito.ancho-ancho)/2, 135);

        fuente = new Font("Arial", Font.PLAIN, 12);
        fMet = g.getFontMetrics(fuente);
        g.setFont(fuente);
        ancho = fMet.stringWidth("v1.0.0");
        g.drawString("v1.0.0", paquito.ancho - ancho - 2, paquito.alto - 2);
    }
}
