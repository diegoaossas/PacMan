package pacman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import pacman.Menus.MenuInicial;
import pacman.Menus.MenuPane;

public class PacMan extends JPanel
{
    static String IP = "192.168.1.100";
    static int PUERTO = 3000;
    public static int ancho = 800;
    public static int alto = 600;
    
    public static Cliente cliente = new Cliente();

    private MenuPane menu = null;
    private KeyListener kListener = null;
    private MouseListener mListener = null;
    private MouseMotionListener mmListener = null;
    
    public PacMan()
    {
        menu = new MenuInicial(this);

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
    }
}
