package pacman.menus;

import Libreria.Actions;
import Libreria.Cell;
import Libreria.Pacman;
import Libreria.Respuesta;
import Libreria.Sala;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import pacman.main.Panel;
import pacman.musica.Sonidos;

public class PruebaMapa extends GameState
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

    public Pacman moverDerPacman(Pacman pacman) throws NullPointerException
    {
        if ((cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'm') || (cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'n')
                || (cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'v')|| (cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'y'))
        {
            if((cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'm'))
                sonidos.reproduceSonido(Sonidos.EAT);
            
            if((cellsMapa[pacman.pacmanRow][pacman.pacmanCol + 1].getType() == 'y'))
            {
                pacman.pacmanCol = 0;
            }
            else
            {
                pacman.pacmanCol++;
            }
        }

        pacman.direccion = Pacman.Direccion.Derecha;
        return pacman;
    }

    public Pacman moverIzqPacman(Pacman pacman) throws NullPointerException
    {
        if ((cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'm') || (cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'n')
                || (cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'v')  || (cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'z'))
        {
            if((cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'm'))
                sonidos.reproduceSonido(Sonidos.EAT);
                        
            if((cellsMapa[pacman.pacmanRow][pacman.pacmanCol - 1].getType() == 'z'))
            {
                pacman.pacmanCol = 27;
            }
            else
            {            
                pacman.pacmanCol--;
            }
        }
        
        pacman.direccion = Pacman.Direccion.Izquierda;
        return pacman;
    }

    public Pacman moverArrPacman(Pacman pacman) throws NullPointerException
    {
        if ((cellsMapa[pacman.pacmanRow - 1][pacman.pacmanCol].getType() == 'm') || (cellsMapa[pacman.pacmanRow - 1][pacman.pacmanCol].getType() == 'n')
                || (cellsMapa[pacman.pacmanRow - 1][pacman.pacmanCol].getType() == 'v'))
        {
            if((cellsMapa[pacman.pacmanRow-1][pacman.pacmanCol].getType() == 'm'))
                sonidos.reproduceSonido(Sonidos.EAT);
            pacman.pacmanRow--;
        }

        pacman.direccion = Pacman.Direccion.Arriba;
        return pacman;
    }

    public Pacman moverAbaPacman(Pacman pacman) throws NullPointerException
    {
        if ((cellsMapa[pacman.pacmanRow + 1][pacman.pacmanCol].getType() == 'm') || (cellsMapa[pacman.pacmanRow + 1][pacman.pacmanCol].getType() == 'n')
                || (cellsMapa[pacman.pacmanRow + 1][pacman.pacmanCol].getType() == 'v'))
        {
            if((cellsMapa[pacman.pacmanRow+1][pacman.pacmanCol].getType() == 'm'))
                sonidos.reproduceSonido(Sonidos.EAT);
            pacman.pacmanRow++;
        }

        pacman.direccion = Pacman.Direccion.Abajo;
        return pacman;
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

    public PruebaMapa(GameStateManager gsm)
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
                GameStateManager.cliente.getOut().writeObject(Actions.GETJUEGOstream);
                GameStateManager.cliente.getOut().writeObject(idSala);

                while (true)
                {
                    Object obj = GameStateManager.cliente.getIn().readObject();
                    
                    if(obj instanceof Sala)
                    {
                        sala = (Sala) obj;
                    }
                    else if(obj instanceof Respuesta)
                    {
                        Respuesta resp = (Respuesta)obj;
                        
                        if(resp == resp.PLAYSONIDO)
                        {
                            String cad = (String)GameStateManager.cliente.getIn().readObject();
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
                        miPacman = (Pacman) GameStateManager.cliente.getIn().readObject();
                        if (miPacman != null)
                        {
                            miPacman.setPos();
                        }
                        pacman2 = (Pacman) GameStateManager.cliente.getIn().readObject();
                        if (pacman2 != null)
                        {
                            pacman2.setPos();
                        }
                        pacman3 = (Pacman) GameStateManager.cliente.getIn().readObject();
                        if (pacman3 != null)
                        {
                            pacman3.setPos();
                        }
                        pacman4 = (Pacman) GameStateManager.cliente.getIn().readObject();
                        if (pacman4 != null)
                        {
                            pacman4.setPos();
                        }
                    } catch (IndexOutOfBoundsException ex)
                    {
                    }
                }
            } catch (IOException | ClassNotFoundException ex)
            {
            }
        });

        listenSala.start();
    }

    @Override
    public void keyPressed(KeyEvent ke)
    {
        // TODO Auto-generated method stub
        Pacman nuevo = null;

        try
        {
            nuevo = miPacman;

            if (ke.getKeyChar() == 'a')
            {
                nuevo = moverIzqPacman(miPacman);
            } else if (ke.getKeyChar() == 'd')
            {
                nuevo = moverDerPacman(miPacman);
            } else if (ke.getKeyChar() == 'w')
            {
                nuevo = moverArrPacman(miPacman);
            } else if (ke.getKeyChar() == 's')
            {
                nuevo = moverAbaPacman(miPacman);
            } else
            {
                ke.consume();
                return;
            }
            
            GameStateManager.cliente.getOut().reset();
            GameStateManager.cliente.getOut().writeObject(Actions.ActPACMAN);
            GameStateManager.cliente.getOut().writeObject(idSala);
            GameStateManager.cliente.getOut().writeObject(nuevo);

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

}
