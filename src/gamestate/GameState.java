package gamestate;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import pacman.principal.Panel;

public abstract class GameState
{
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
    
    public class botonMenu extends itemMenu {}

    public class campoMenu extends itemMenu
    {
        public boolean seleccionado = false;
        public String contenido = "";
    }

    public class itemMenu
    {
        public int X = 0;
        public int Y = 0;
        public int ancho = 0;
        public int alto = 0;
        public String texto = "";
        public int buttonPos = 0;
        public Rectangle rect = null;
        public boolean inactivo = false;
    }

    public class textoMenu extends itemMenu {}

    public class salaMenu extends itemMenu
    {
        public long IDSala = 0;
    }
    
    public int botonMouse(Rectangle r, int buttonPos)
    {
        if (r.contains(Panel.mouseX, Panel.mouseY))
            buttonPos = 1;
        else
            buttonPos = 0;

        return buttonPos;
    }
}
