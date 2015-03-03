package pacman.menus;

import Libreria.Actions;
import Libreria.Respuesta;
import Libreria.Sala;
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
import java.util.ArrayList;
import java.util.Arrays;
import javax.imageio.ImageIO;
import pacman.musica.Sonidos;
import pacman.principal.Juego;
import pacman.principal.Panel;

public class MenuTorneosExistentesState extends GameState
{
    private String[] opciones = {"Atras"};
    private itemMenu[] menu = new itemMenu[opciones.length];

    private Font regFont = new Font("Arial", Font.BOLD, 16);
    private FontMetrics fMet;

    private BufferedImage bg, buttonSet, campoSet;
    private BufferedImage[] buttonFrames = new BufferedImage[3];
    private BufferedImage[] campoFrames = new BufferedImage[4];

    private Thread listenLobby = null;
    private ArrayList<salaMenu> salasBoton = null;
    private ArrayList<itemMenu> botones = null;

    public MenuTorneosExistentesState(GameStateManager gsm)
    {
        this.gsm = gsm;

        salasBoton = new ArrayList<salaMenu>();
        botones = new ArrayList<>();

        try
        {
            bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
            buttonSet = ImageIO.read(getClass().getResource("/Sprites/boton.png"));
            campoSet = ImageIO.read(getClass().getResource("/Sprites/campo.png"));

            for (int i = 0; i < 3; i++)
            {
                buttonFrames[i] = buttonSet.getSubimage(0, 40 * i, 160, 40);
            }
            
            for (int i = 0; i < 4; i++)
            {
                campoFrames[i] = campoSet.getSubimage(0, 36 * i, 237, 36);
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
        }

        for (int i = 0; i < opciones.length; i++)
        {
            menu[i] = new botonMenu();
            botonMenu boton = (botonMenu) menu[i];
            boton.texto = opciones[i];
            boton.X = (Panel.ANCHO / 2) - (buttonFrames[0].getWidth() / 2);
            boton.Y = (Panel.ALTO / 2) - 26;
            boton.ancho = buttonFrames[0].getWidth();
            boton.alto = buttonFrames[0].getHeight();
            boton.buttonPos = 0;
        }

        for (int i = 0; i < opciones.length; i++)
        {
            botonMenu boton = (botonMenu) menu[i];
            boton.rect = new Rectangle(boton.X, boton.Y, boton.ancho, boton.alto);
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
        for (int i = 0; i < botones.size(); i++)
        {
            itemMenu boton = botones.get(i);
            boton.rect = new Rectangle(boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto);

            if (boton instanceof salaMenu)
                g.drawImage(campoFrames[boton.buttonPos], boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto, null);
            else
                g.drawImage(buttonFrames[boton.buttonPos], boton.X, boton.Y - 30 + (60 * i), boton.ancho, boton.alto, null);

            g.drawString(boton.texto, Panel.ANCHO / 2 - (fMet.stringWidth(boton.texto) / 2), Panel.ALTO / 2 - 32 + (60 * i));
        }

    }

    @Override
    public void init()
    {
        listenLobby = new Thread(() ->
        {
            try
            {
                Juego.cliente.getOut().writeObject(Actions.GETLOBBYSstream);

                while (true)
                {
                    ArrayList<Sala> salas;
                    Object obj = Juego.cliente.getIn().readObject();
                    
                    if (obj instanceof ArrayList)
                    {
                        salas = (ArrayList<Sala>) obj;
                    }
                    else if (obj instanceof Respuesta)
                    {
                        Thread.currentThread().interrupt();
                        break;
                    }
                    else
                    {
                        continue;
                    }

                    salasBoton.clear();

                    for (Sala sala : salas)
                    {
                        salaMenu boton = new salaMenu();
                        boton.IDSala = sala.idSala;
                        boton.texto = sala.nombreSala;
                        boton.rect = new Rectangle(boton.X, boton.Y, boton.ancho, boton.alto);
                        boton.X = (Panel.ANCHO / 2) - (campoFrames[0].getWidth() / 2);
                        boton.Y = (Panel.ALTO / 2) - 26;
                        boton.ancho = campoFrames[0].getWidth();
                        boton.alto = campoFrames[0].getHeight();

                        if (sala.empezado)
                            boton.inactivo = true;
                        else
                            boton.inactivo = false;

                        salasBoton.add(boton);
                    }
                }
            }
            catch (IOException | ClassNotFoundException ex)
            {
                System.err.println(ex.getMessage());
            }
        });

        listenLobby.start();
    }

    @Override
    public void keyPressed(KeyEvent ke){}

    @Override
    public void keyReleased(KeyEvent ke){}

    @Override
    public void keyTyped(KeyEvent ke){}

    @Override
    public void mouseClicked(MouseEvent me)
    {
        ArrayList<itemMenu> items = (ArrayList<itemMenu>) botones.clone();

        for (itemMenu itm : items)
        {
            if (itm.rect.contains(Panel.mouseX, Panel.mouseY))
            {
                if (itm instanceof salaMenu)
                {
                    if (itm.inactivo)
                        continue;

                    salaMenu salaBoton = (salaMenu) itm;

                    Sonidos.MENUIN.play();
                    boolean joined = false;

                    try
                    {
                        Juego.cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
                        while (listenLobby.isAlive());

                        Juego.cliente.getOut().writeObject(Actions.JoinSALA);
                        Juego.cliente.getOut().writeObject(salaBoton.IDSala);
                        joined = (boolean) Juego.cliente.getIn().readObject();

                        if (joined)
                            gsm.setStateSalaEspera(salaBoton.IDSala);
                        else
                            gsm.setStateMensaje("Sala Llena", "La sala se encuentra llena, escoga otra o intente mas tarde", GameStateManager.MENULISTATORNEOSSTATE);
                    }
                    catch (IOException | ClassNotFoundException e)
                    {
                        gsm.setState(GameStateManager.LOGINSTATE);
                    }
                    
                }
                else if (itm instanceof botonMenu)
                {
                    botonMenu boton = (botonMenu) itm;

                    if (boton.texto.equals("Atras"))
                    {
                        Sonidos.MENUOUT.play();
                        
                        try
                        {
                            Juego.cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
                            while (listenLobby.isAlive());
                        }
                        catch (IOException e)
                        {
                            System.err.println(e.getMessage());
                        }
                        
                        gsm.setState(GameStateManager.MENUTORNEOSTATE);
                    }
                    else
                    {
                        Sonidos.MENUIN.play();
                    }
                }
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent me){}

    @Override
    public void mouseEntered(MouseEvent me){}

    @Override
    public void mouseExited(MouseEvent me){}

    @Override
    public void mouseMoved(MouseEvent me){}

    @Override
    public void mousePressed(MouseEvent me){}

    @Override
    public void mouseReleased(MouseEvent me){}

    @Override
    public void update()
    {
        botones.clear();
        botones.addAll(Arrays.asList(menu));

        for (itemMenu mn : salasBoton)
        {
            botones.add(mn);
        }

        for (itemMenu itm : botones)
        {
            if (itm.inactivo)
                itm.buttonPos = 3;
            else
                itm.buttonPos = botonMouse(itm.rect, itm.buttonPos);
        }
    }
}
