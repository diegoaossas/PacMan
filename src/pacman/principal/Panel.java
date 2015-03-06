package pacman.principal;

import gamestate.GameStateManager;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable, KeyListener, MouseListener, MouseMotionListener
{
    private static final long serialVersionUID = 1L;

    //Dimensiones del panel
    public static final int ANCHO = 800;
    public static final int ALTO = 900;

    //Posicion del mouse en la ventana
    public static int mouseX;
    public static int mouseY;

    //Ciclo de programa
    private Thread thread;
    private boolean running;
    private final int FPS;
    private final long targetTime;

    //Pintado
    private Graphics2D g;
    private BufferedImage image;

    //GameStateManager
    private GameStateManager gsm;
    private ControlSonido cSonido;

    public Panel()
    {
        mouseX = 0;
        mouseY = 0;
        thread = null;
        running = false;
        FPS = 24;
        targetTime = 1000 / FPS;
        g = null;
        image = null;
        gsm = null;
        cSonido = null;
        
        setFocusable(true); 
        requestFocus();
    }

    @Override
    public void addNotify()
    {
        super.addNotify();

        if (thread == null)
        {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    public void draw()
    {
        g.clearRect(0, 0, ANCHO, ALTO);
        gsm.draw(g);
        cSonido.draw(g);
    }

    public void drawToScreen()
    {
        Graphics g2 = getGraphics();
        g2.drawImage(image, 0, 0, ANCHO, ALTO, null);
        g2.dispose();
    }

    public void init()
    {
        image = new BufferedImage(ANCHO, ALTO, BufferedImage.TYPE_INT_ARGB);
        g = (Graphics2D) image.getGraphics();

        gsm = new GameStateManager();
        cSonido = new ControlSonido();

        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        gsm.keyPressed(ke);
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        gsm.keyReleased(ke);
    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        gsm.keyTyped(ke);
    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        gsm.mouseClicked(me);
        cSonido.mouseClicked(me);
    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
        gsm.mouseDragged(me);
    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        gsm.mouseEntered(me);
    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        gsm.mouseExited(me);
    }

    @Override
    public void mouseMoved(MouseEvent me)
    {
        mouseX = me.getX();
        mouseY = me.getY();

        gsm.mouseMoved(me);
    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        gsm.mousePressed(me);
    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        gsm.mouseReleased(me);
    }

    @Override
    public void run()
    {
        init();

        long start;
        long elapsed;
        long wait;

        while (running)
        {
            start = System.nanoTime();
            update();
            draw();
            drawToScreen();
            elapsed = System.nanoTime() - start;
            wait = targetTime - elapsed / 1000000;

            if (wait < 0)
                wait = 5;

            try
            {
                Thread.sleep(wait);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void update()
    {
        gsm.update();
    }

}
