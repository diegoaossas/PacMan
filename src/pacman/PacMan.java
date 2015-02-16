package pacman;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;
import pacman.Menus.MenuInicial;
import pacman.Menus.MenuPane;

public class PacMan extends JPanel implements MouseListener, MouseMotionListener, KeyListener
{
    public static int ancho = 800;
    public static int alto = 600;
    
    static String IP = "192.168.1.100";
    static int PUERTO = 3000;
    
    public static Cliente cliente = new Cliente();

    private MenuPane menu = null;
    
    public PacMan()
    {
        menu = new MenuInicial(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
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

    @Override
    public void mouseClicked(MouseEvent me) {
        menu.mouseMovido(me);
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

    @Override
    public void mouseDragged(MouseEvent me) {
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        menu.mouseMovido(me);
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        menu.keyPressed(ke);
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }
}
