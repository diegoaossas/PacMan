package pacman;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class PacMan extends JPanel{

    public MenuPane menu;
    public int ancho = 800;
    public int alto = 600;
    
    public PacMan()
    {
        menu = new MenuInicial(this);
        
        try
        {
            GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
            InputStream is = PacMan.class.getResourceAsStream("PAC-FONT.TTF");
            
            Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
            ge.registerFont(fuente);
        }
        catch (IOException|FontFormatException e)
        {
            System.out.println("Error registrando fuente...");
            System.out.println("-Mensaje del error: " + e.getMessage());
        }
        
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                menu.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                menu.mouseMovido(event);
            }
        });

        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent event) {
                menu.mouseMovido(event);
            }
            
            @Override
            public void mouseDragged(MouseEvent event) {
            }
        });
        
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Dimension tamano = super.getSize();
        ancho = tamano.width;
        alto = tamano.height;
        
        menu.paint(g2d);
        //System.out.println("-Tamano Ventana: " + ancho + "x" + alto);
    }

    public static void main(String[] args)
    {
	JFrame frame = new JFrame("PacMan");
        PacMan juego = new PacMan();
        frame.add(juego);
        frame.setSize(juego.ancho, juego.alto);
        frame.setVisible(true);
        
        //Cambiar a DISPOSE_ON_CLOSE y manejar el cierre del programa
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Sonidos.reproduceFondo();
    }
    
}
