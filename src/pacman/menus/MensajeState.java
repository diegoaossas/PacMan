package pacman.menus;

import gamestate.GameState;
import gamestate.GameStateManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import pacman.musica.Sonidos;
import pacman.principal.Panel;

public class MensajeState extends GameState
{
    private String[] opciones = {"", "", "Atras"};
    private itemMenu[] menu = new itemMenu[opciones.length];

    private Font regFont = new Font("Arial", Font.BOLD, 16);
    private FontMetrics fMet;

    private BufferedImage bg, buttonSet, campoSet;
    private BufferedImage[] buttonFrames = new BufferedImage[2];
    private BufferedImage[] campoFrames = new BufferedImage[3];

    private int stateAnterior;

    public MensajeState(GameStateManager gsm)
    {
        this.gsm = gsm;

        try
        {
            bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
            buttonSet = ImageIO.read(getClass().getResource("/Sprites/boton.png"));
            campoSet = ImageIO.read(getClass().getResource("/Sprites/campo.png"));

            for(int i = 0; i<2; i++)
            {
                buttonFrames[i] = buttonSet.getSubimage(0, 40 * i, 160, 40);
            }
            
            for(int i = 0; i<3; i++)
            {
                campoFrames[i] = campoSet.getSubimage(0, 36 * i, 237, 36);
            }
        }
        catch(IOException e)
        {
            System.err.println(e.getMessage());
        }

        for(int i = 0; i < opciones.length; i++)
        {
            if(i == 0 || i == 1)
                menu[i] = new textoMenu();
            else
                menu[i] = new botonMenu();

            itemMenu item = menu[i];

            if(item instanceof textoMenu)
            {
                item.X = (Panel.ANCHO/2) - (campoFrames[0].getWidth() /2);
                item.ancho = Panel.ANCHO;
                item.alto = Panel.ALTO;
            }
            else if(item instanceof botonMenu)
            {
                item.X = (Panel.ANCHO/2) - (buttonFrames[0].getWidth() /2);
                item.ancho = buttonFrames[0].getWidth();
                item.alto = buttonFrames[0].getHeight();
            }

            item.texto = opciones[i];
            item.Y = (Panel.ALTO/2) - 26;
            item.buttonPos = 0;
            item.rect = new Rectangle(item.X, item.Y, item.ancho, item.alto);
        }
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.drawImage(bg, 0, 0, Panel.ANCHO, Panel.ALTO, null);

        g.setColor(Color.YELLOW);
        g.setFont(regFont);
        fMet = g.getFontMetrics(regFont);

        //Pintar botones
        for (int i = 0; i < opciones.length; i++)
        {
            if (menu[i] instanceof textoMenu)
            {
                textoMenu campo = (textoMenu) menu[i];
                campo.rect = new Rectangle(campo.X, campo.Y - 30 + (80 * i), campo.ancho, campo.alto);
                g.drawString(opciones[i], Panel.ANCHO / 2 - (fMet.stringWidth(opciones[i]) / 2), Panel.ALTO / 2 - 64 + (80 * i));
            }
            else if (menu[i] instanceof botonMenu)
            {
                botonMenu boton = (botonMenu) menu[i];
                boton.rect = new Rectangle(boton.X, boton.Y - 30 + (80 * i), boton.ancho, boton.alto);
                g.drawImage(buttonFrames[boton.buttonPos], boton.X, boton.Y - 30 + (80 * i), boton.ancho, boton.alto, null);
                g.drawString(opciones[i], Panel.ANCHO / 2 - (fMet.stringWidth(opciones[i]) / 2), Panel.ALTO / 2 - 32 + (80 * i));
            }
        }
    }

    @Override
    public void init() {}

    public void init(String titulo, String mensaje, int stateAnterior)
    {
        opciones[0] = titulo;
        opciones[1] = mensaje;
        this.stateAnterior = stateAnterior;
    }

    @Override
    public void keyPressed(KeyEvent ke){}

    @Override
    public void keyReleased(KeyEvent ke) {}

    @Override
    public void keyTyped(KeyEvent ke) {}

    @Override
    public void mouseClicked(MouseEvent me)
    {
        for (itemMenu itm : menu)
        {
            if(itm.rect.contains(Panel.mouseX, Panel.mouseY))
            {
                if (itm instanceof botonMenu)
                {
                    botonMenu boton = (botonMenu) itm;                    
                    
                    if (boton.texto.equals("Atras"))
                    {
                        Sonidos.MENUOUT.play();
                        gsm.setState(stateAnterior);
                    }
                    else
                    {
                        Sonidos.MENUIN.play();
                    }
                    
                    break;
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me) {}

    @Override
    public void mouseEntered(MouseEvent me) {}

    @Override
    public void mouseExited(MouseEvent me) {}

    @Override
    public void mouseMoved(MouseEvent me) {}

    @Override
    public void mousePressed(MouseEvent me) {}

    @Override
    public void mouseReleased(MouseEvent me) {}

    @Override
    public void update()
    {
        for (int i = 0; i < opciones.length; i++)
        {
            itemMenu boton = menu[i];
            boton.buttonPos = botonMouse(boton.rect, boton.buttonPos);
        }
    }

}
