package pacman;

import pacman.Menus.MenuInicial;
import pacman.Menus.MenuPane;
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
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.JPanel;

public class PacMan extends JPanel{
    
    public Socket sock = null;
    public Cliente cliente = null;
    public String IP = "192.168.1.100";
    public int PUERTO = 3000;

    private MenuPane menu;
    public int ancho = 800;
    public int alto = 600;
    
    private KeyListener kListener;
    private MouseListener mListener;
    private MouseMotionListener mmListener;
    
    public PacMan() throws FontFormatException, IOException
    {
        menu = new MenuInicial(this);
        GraphicsEnvironment ge =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        InputStream is = PacMan.class.getResourceAsStream("PAC-FONT.TTF");

        Font fuente = Font.createFont(Font.TRUETYPE_FONT, is);
        ge.registerFont(fuente);

        kListener = new KeyListener() {
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

        };
        
        mListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                menu.mouseMovido(event);
            }
        };

        mmListener = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent event) {
                menu.mouseMovido(event);
            }
            
            @Override
            public void mouseDragged(MouseEvent event) {
            }
        };
        
        addKeyListener(kListener);
        addMouseListener(mListener);
        addMouseMotionListener(mmListener);
        setFocusable(true);
    }

    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Dimension tamano = super.getSize();
        ancho = tamano.width;
        alto = tamano.height;
        
        menu.paint(g2d);
    }
    
    public void cambiarMenu(MenuPane menu)
    {
        this.menu = menu;
        this.repaint();
    }
}
