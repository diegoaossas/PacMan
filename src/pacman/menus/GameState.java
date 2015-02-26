package pacman.menus;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GameState
{

    class botonMenu extends itemMenu
    {
    }

    class campoMenu extends itemMenu
    {
        boolean seleccionado = false;
        String contenido = "";
    }

    class itemMenu
    {
        int X = 0;
        int Y = 0;
        int ancho = 0;
        int alto = 0;
        String texto = "";
        int buttonPos = 0;
        Rectangle rect = null;
        boolean inactivo = false;
    }

    class textoMenu extends itemMenu
    {

    }

    class salaMenu extends itemMenu
    {
        long IDSala = 0;
    }

    protected GameStateManager gsm;

    public abstract void draw(Graphics2D g);
    public abstract void init();
    public abstract void keyPressed(KeyEvent ke);
    public abstract void keyReleased(KeyEvent ke);
    public abstract void keyTyped(KeyEvent ke);
    public abstract void mouseClicked(MouseEvent me);
    public abstract void mouseDragged(MouseEvent me);
    public abstract void mouseEntered(MouseEvent me);
    public abstract void mouseExited(MouseEvent me);
    public abstract void mouseMoved(MouseEvent me);
    public abstract void mousePressed(MouseEvent me);
    public abstract void mouseReleased(MouseEvent me);
    public abstract void update();
}