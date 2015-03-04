package pacman.menus;

import Libreria.Respuesta;
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
import pacman.principal.ProcesaLogin;

public class MenuLoginState extends GameState
{
    private String[] opciones = {"Usuario", "Clave", "Atras", "Entrar!"};
    private itemMenu[] menu = new itemMenu[opciones.length];

    private Font regFont = new Font("Arial", Font.BOLD, 16);
    private FontMetrics fMet;

    private BufferedImage bg, buttonSet, campoSet;
    private BufferedImage[] buttonFrames = new BufferedImage[2];
    private BufferedImage[] campoFrames = new BufferedImage[3];

    private String usuario = "";
    private String clave = "";

    public MenuLoginState(GameStateManager gsm)
    {
        this.gsm = gsm;

        try
        {
            bg = ImageIO.read(getClass().getResource("/Backgrounds/fondo.jpg"));
            buttonSet = ImageIO.read(getClass().getResource("/Sprites/boton.png"));
            campoSet = ImageIO.read(getClass().getResource("/Sprites/campo.png"));

            for (int i = 0; i < 2; i++)
            {
                buttonFrames[i] = buttonSet.getSubimage(0, 40 * i, 160, 40);
            }
            
            for (int i = 0; i < 3; i++)
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
            if (i == 0 || i == 1)
                menu[i] = new campoMenu();
            else
                menu[i] = new botonMenu();

            itemMenu item = menu[i];

            if (item instanceof campoMenu)
            {
                item.X = (Panel.ANCHO / 2) - (campoFrames[0].getWidth() / 2);
                item.ancho = campoFrames[0].getWidth();
                item.alto = campoFrames[0].getHeight();
            }
            else if (item instanceof botonMenu)
            {
                item.X = (Panel.ANCHO / 2) - (buttonFrames[0].getWidth() / 2);
                item.ancho = buttonFrames[0].getWidth();
                item.alto = buttonFrames[0].getHeight();
            }

            item.texto = opciones[i];
            item.Y = (Panel.ALTO / 2) - 26;
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
            if (menu[i] instanceof campoMenu)
            {
                campoMenu campo = (campoMenu) menu[i];
                campo.rect = new Rectangle(campo.X, campo.Y - 30 + (80 * i), campo.ancho, campo.alto);
                g.drawImage(campoFrames[campo.buttonPos], campo.X, campo.Y - 30 + (80 * i), campo.ancho, campo.alto, null);
                g.drawString(opciones[i], Panel.ANCHO / 2 - (fMet.stringWidth(opciones[i]) / 2), Panel.ALTO / 2 - 64 + (80 * i));
                g.drawString(campo.contenido, Panel.ANCHO / 2 - (campo.ancho / 2) + 8, Panel.ALTO / 2 - 34 + (80 * i));
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
    public void init(){}

    @Override
    public void keyPressed(KeyEvent ke)
    {
        for (itemMenu cmp : menu)
        {
            if (cmp instanceof campoMenu)
            {
                campoMenu campo = (campoMenu) cmp;

                if (campo.seleccionado)
                {
                    if (ke.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                    {
                        if (campo.contenido.length() <= 0)
                            return;

                        campo.contenido = campo.contenido.substring(0, campo.contenido.length() - 1);
                    }
                    else
                    {
                        if (campo.contenido.length() >= 20)
                        {
                            Sonidos.MENUOUT.play();
                            return;
                        }

                        campo.contenido += ke.getKeyChar();
                    }

                    if (campo.texto.equals("Usuario"))
                        usuario = campo.contenido;
                    else
                        clave = campo.contenido;
                    
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke){}

    @Override
    public void keyTyped(KeyEvent ke){}

    @Override
    public void mouseClicked(MouseEvent me)
    {
        for (itemMenu itm : menu)
        {
            if(itm.rect.contains(Panel.mouseX, Panel.mouseY))
            {
                if (itm instanceof campoMenu)
                {
                    campoMenu campo = (campoMenu) itm;
                    campo.seleccionado = true;
                }
                else if (itm instanceof botonMenu)
                {
                    botonMenu boton = (botonMenu) itm;
                    
                    if (boton.texto.equals("Atras"))
                    {
                        Sonidos.MENUOUT.play();
                        gsm.setState(GameStateManager.MENUSTATE);
                    }
                    else
                    {
                        Sonidos.MENUIN.play();

                        if (boton.texto.equals("Entrar!"))
                        {
                            ProcesaLogin login = new ProcesaLogin(usuario, clave);
                            Respuesta respuesta;

                            try
                            {
                                respuesta = login.procesaDatos();
                            }
                            catch (IOException | ClassNotFoundException e)
                            {
                                respuesta = Respuesta.ERRORCONECTANDO;
                            }
                            
                            if (respuesta == Respuesta.NOLOGGED)
                                gsm.setStateMensaje("Login", "Datos incorrectos, intente de nuevo...", GameStateManager.LOGINSTATE);
                            else if (respuesta == Respuesta.LOGGED)
                                gsm.setState(GameStateManager.MENUPRINCIPALSTATE);
                            else
                                gsm.setStateMensaje("Error", "Ocurrio un error conectando al servidor...", GameStateManager.LOGINSTATE);
                        }
                    }
                }
                
                break;
            }
            else
            {                
                if (itm instanceof campoMenu)
                {
                    campoMenu campo = (campoMenu) itm;
                    campo.seleccionado = false;
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
        for (int i = 0; i < opciones.length; i++)
        {
            itemMenu boton = menu[i];
            if (boton instanceof campoMenu)
            {
                if (((campoMenu) boton).seleccionado == true)
                {
                    boton.buttonPos = 2;
                    continue;
                }
            }

            boton.buttonPos = botonMouse(boton.rect, boton.buttonPos);
        }
    }

}
