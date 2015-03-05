package pacman.menus;

import Libreria.Actions;
import Libreria.Cell;
import Libreria.Pacman;
import Libreria.Respuesta;
import Libreria.Sala;
import gamestate.GameState;
import gamestate.GameStateManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import pacman.musica.Sonidos;
import pacman.principal.Juego;
import pacman.principal.Panel;

public class MapaState extends GameState
{
    private int tileHeight;
    private int tileWidth;
    private Cell[][] cellsMapa;
    private Cell[][] cellsServidor;
    final static int CELL = Cell.CELL;
    private BufferedImage bg;

    private Font regFont = new Font("Arial", Font.BOLD, 16);
    private FontMetrics fMet;
	
    private Thread listenSala = null;
    private Pacman miPacman;
    private Pacman pacman2;
    private Pacman pacman3;
    private Pacman pacman4;
    
    private Sonidos sonidos;

    private long idSala;
    private Sala sala = null;

    public Cell[][] getCells()
    {
        return cellsMapa;
    }

    private void drawPacman(Pacman pacman, Graphics2D g) throws NullPointerException
    {
        g.setColor(pacman.color);
        int start = 0;
        int size = 300;
        
        switch(pacman.direccion)
        {
            case Arriba:
                start = 110;
                break;
            case Izquierda:
                start = 210;
                break;
            case Derecha:
                start = 30;
                break;
            case Abajo:
                start = 300;
                break;
        }
        
        g.fillArc(cellsMapa[pacman.pacmanRow][pacman.pacmanCol].getX() * 18, cellsMapa[pacman.pacmanRow][pacman.pacmanCol].getY() * 18, 22, 22, start, size);
        
        if(pacman.powerUP)
        {
            g.setColor(Color.RED);
            g.drawArc(cellsMapa[pacman.pacmanRow][pacman.pacmanCol].getX() * 18, cellsMapa[pacman.pacmanRow][pacman.pacmanCol].getY() * 18, 22, 22, start, size);
        }
    }

    private void updateCellArray()
    {
        int anchoTablero = tileWidth * CELL;

        // Create the cells
        cellsMapa = new Cell[tileHeight][tileWidth];

        for (int row = 0; row < tileHeight; row++)
        {
            for (int column = 0; column < tileWidth; column++)
            {
                cellsMapa[row][column] = cellsServidor[row][column];
                cellsMapa[row][column].setSeparacion((Panel.ANCHO - anchoTablero) / (2 * CELL), 50 / CELL);
            }
        }
    }

    public MapaState(GameStateManager gsm)
    {
        this.gsm = gsm;

        try
        {
            bg = ImageIO.read(getClass().getResource("/Backgrounds/fondoTablero.jpg"));
        }
        catch (IOException e)
        {
        }
    }

    @Override
    public void draw(Graphics2D g)
    {
        g.setFont(regFont);
        fMet = g.getFontMetrics(regFont);
        
        g.drawImage(bg, 0, 0, Panel.ANCHO, Panel.ALTO, null);

        if(miPacman != null)
        {
            g.setColor(miPacman.color);
            
            g.drawString("Puntos", (145 - fMet.stringWidth("Puntos")) / 2, 80);
            g.drawString(String.valueOf(miPacman.puntos), (145 - fMet.stringWidth(String.valueOf(miPacman.puntos))) / 2, 110);

            g.drawString("Vidas", ((145 - fMet.stringWidth("Puntos")) / 2) + 645, 80);
            g.drawString(String.valueOf(miPacman.livesLeft), ((145 - fMet.stringWidth(String.valueOf(miPacman.livesLeft))) / 2) + 645, 110);
        }
        
        if(sala != null)
        {
            for(int i=0; i < sala.jugadores.size(); i++)
            {
                if(miPacman.equals(sala.jugadores.get(i).paco))
                    continue;
                
                g.setColor(sala.jugadores.get(i).paco.color);
                g.drawString(sala.jugadores.get(i).Nombre+": " + sala.jugadores.get(i).paco.livesLeft + " vidas - " + sala.jugadores.get(i).paco.puntos + " puntos", 5, (i*20)+630);
            }
            
            g.setColor(sala.fant1.color);
            g.fillRect(cellsMapa[sala.fant1.fantasmaRow][sala.fant1.fantasmaCol].getX() * 18, cellsMapa[sala.fant1.fantasmaRow][sala.fant1.fantasmaCol].getY() * 18, 20, 20);            
            g.setColor(sala.fant2.color);
            g.fillRect(cellsMapa[sala.fant2.fantasmaRow][sala.fant2.fantasmaCol].getX() * 18, cellsMapa[sala.fant2.fantasmaRow][sala.fant2.fantasmaCol].getY() * 18, 20, 20);            
            g.setColor(sala.fant3.color);
            g.fillRect(cellsMapa[sala.fant3.fantasmaRow][sala.fant3.fantasmaCol].getX() * 18, cellsMapa[sala.fant3.fantasmaRow][sala.fant3.fantasmaCol].getY() * 18, 20, 20);            
            g.setColor(sala.fant4.color);
            g.fillRect(cellsMapa[sala.fant4.fantasmaRow][sala.fant4.fantasmaCol].getX() * 18, cellsMapa[sala.fant4.fantasmaRow][sala.fant4.fantasmaCol].getY() * 18, 20, 20);
        }
        
        for (int row = 0; row < tileHeight; row++)
        {
            for (int column = 0; column < tileWidth; column++)
            {
                if (cellsMapa[row][column] != null)
                    cellsMapa[row][column].drawBackground(g);
            }
        }
        try
        {
            if (miPacman != null)
            {
                drawPacman(miPacman, g);
            }
            if (pacman2 != null)
            {
                drawPacman(pacman2, g);
            }
            if (pacman3 != null)
            {
                drawPacman(pacman3, g);
            }
            if (pacman4 != null)
            {
                drawPacman(pacman4, g);
            }
            
            g.setColor(Color.WHITE);
        } catch (NullPointerException ex)
        {
        }
    }

    public void init(long idSala)
    {
        sonidos = new Sonidos();
        // TODO Auto-generated method stub
        this.idSala = idSala;

        listenSala = new Thread(() ->
        {
            try
            {
                Juego.cliente.getOut().writeObject(Actions.GETJUEGOstream);
                Juego.cliente.getOut().writeObject(idSala);

                while (true)
                {
                    Object obj = Juego.cliente.getIn().readObject();
                    
                    if(obj instanceof Sala)
                    {
                        sala = (Sala) obj;
                    }
                    else if(obj instanceof Respuesta)
                    {
                        Respuesta resp = (Respuesta)obj;
                        
                        if(resp == resp.PLAYSONIDO)
                        {
                            String cad = (String)Juego.cliente.getIn().readObject();
                            if(cad.equals("POWER"))
                            {
                                Sonidos.INTER.play();
                                continue;
                            }
                            else if(cad.equals("EATPAC"))
                            {
                                Sonidos.EATGHOST.play();
                                continue;
                            }
                            else if(cad.equals("ATE"))
                            {
                                Sonidos.DEATH.play();
                                continue;
                            }
                        }
                    }
                    else
                    {
                        continue;
                    }
                    
                    cellsServidor = sala.cellsMapa;
                    tileHeight = sala.tileHeight;
                    tileWidth = sala.tileWidth;
                    updateCellArray();

                    try
                    {
                        miPacman = (Pacman) Juego.cliente.getIn().readObject();
                        if (miPacman != null)
                        {
                            miPacman.setPos();
                        }
                        pacman2 = (Pacman) Juego.cliente.getIn().readObject();
                        if (pacman2 != null)
                        {
                            pacman2.setPos();
                        }
                        pacman3 = (Pacman) Juego.cliente.getIn().readObject();
                        if (pacman3 != null)
                        {
                            pacman3.setPos();
                        }
                        pacman4 = (Pacman) Juego.cliente.getIn().readObject();
                        if (pacman4 != null)
                        {
                            pacman4.setPos();
                        }
                    }
                    catch (IndexOutOfBoundsException ex)
                    {
                    }
                }
            } catch (IOException | ClassNotFoundException ex)
            {
            }
        });

        listenSala.start();
        
        run(cellsServidor);
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        try
        {
            if (ke.getKeyChar() == 'a')
            {
                miPacman.direccion = Pacman.Direccion.Izquierda;
                miPacman.moviendose = true;
            } else if (ke.getKeyChar() == 'd')
            {
                miPacman.direccion = Pacman.Direccion.Derecha;
                miPacman.moviendose = true;
            } else if (ke.getKeyChar() == 'w')
            {
                miPacman.direccion = Pacman.Direccion.Arriba;
                miPacman.moviendose = true;
            } else if (ke.getKeyChar() == 's')
            {
                miPacman.direccion = Pacman.Direccion.Abajo;
                miPacman.moviendose = true;
            }
            else
            {
                ke.consume();
                return;
            }
            
            Juego.cliente.getOut().reset();
            Juego.cliente.getOut().writeObject(Actions.ActPACMAN);
            Juego.cliente.getOut().writeObject(idSala);
            Juego.cliente.getOut().writeObject(miPacman);

        }
        catch (NullPointerException nex)
        {
            ke.consume();
            return;
        }
        catch (IOException e)
        {
        }
    }

    @Override
    public void keyReleased(KeyEvent ke)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(KeyEvent ke)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseClicked(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseDragged(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseMoved(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent me)
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void update()
    {
        // TODO Auto-generated method stub

    }

    @Override
    public void init()
    {
    }
    
    public void run(Cell[][] cells)
    {
        Thread movimiento = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    if(miPacman == null)
                        continue;
                    
                    try
                    {
                        try
                        {
                            if(miPacman.powerUP)
                                Thread.sleep(260);
                            else
                                Thread.sleep(300);
                        }
                        catch (InterruptedException ex)
                        {
                            Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        if (!miPacman.moviendose)
                            continue;
                        
                        switch (miPacman.direccion)
                        {
                            case Arriba:
                                miPacman.direccion = Pacman.Direccion.Arriba;
                                miPacman.moviendose = true;
                                if(miPacman.moveRow(-1, cellsMapa))
                                {            
                                    if((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'm'))
                                        sonidos.reproduceSonido(Sonidos.EAT);
                                }
                                break;
                                
                            case Abajo:
                                miPacman.direccion = Pacman.Direccion.Abajo;
                                miPacman.moviendose = true;
                                if(miPacman.moveRow(+1, cellsMapa))
                                {            
                                    if((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'm'))
                                        sonidos.reproduceSonido(Sonidos.EAT);
                                }
                                break;
                                
                            case Izquierda:
                                miPacman.direccion = Pacman.Direccion.Izquierda;
                                miPacman.moviendose = true;
                                if(miPacman.moveCol(-1, cellsMapa))
                                {
                                    if ((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'm'))
                                        sonidos.reproduceSonido(Sonidos.EAT);

                                    if ((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'z'))
                                        miPacman.pacmanCol = 27;
                                }
                                break;
                                
                            case Derecha:
                                miPacman.direccion = Pacman.Direccion.Derecha;
                                miPacman.moviendose = true;
                                if(miPacman.moveCol(+1, cellsMapa))
                                {            
                                    if ((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'm'))
                                        sonidos.reproduceSonido(Sonidos.EAT);

                                    if ((cellsMapa[miPacman.pacmanRow][miPacman.pacmanCol].getType() == 'y'))
                                        miPacman.pacmanCol = 0;
                                }
                                break;
                        }
                        
                        if(sala.compruebaColision(miPacman))
                        {
                            if (!miPacman.powerUP)
                            {
                                miPacman.moviendose = false;
                                miPacman.ubicados = false;
                                Sonidos.DEATH.play();
                                
                                if(miPacman.livesLeft < 1)
                                {
                                    if(miPacman.puntos >= 1000)
                                        miPacman.puntos -= 1000;
                                    else
                                        miPacman.puntos -= miPacman.puntos;
                                }
                                else
                                    miPacman.livesLeft--;
                            }
                            else
                            {
                                sala.resetFantasma(miPacman);
                                Sonidos.EATGHOST.play();
                                miPacman.powerUP = false;
                            }
                        }
                        
                        Juego.cliente.getOut().reset();
                        Juego.cliente.getOut().writeObject(Actions.ActPACMAN);
                        Juego.cliente.getOut().writeObject(idSala);
                        Juego.cliente.getOut().writeObject(miPacman);
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MapaState.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        movimiento.start();
        
    }
}
