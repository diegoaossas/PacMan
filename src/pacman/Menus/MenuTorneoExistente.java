package pacman.Menus;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pacman.Cliente;
import pacman.PacMan;
import pacman.Musica.Sonidos;
import Libreria.Actions;
import Libreria.Sala;

public class MenuTorneoExistente extends MenuPane
{
    private ArrayList<BotonSala> lista = null;
    private Thread listenLobby = null;
    private Cliente cliente = null;
    
    public MenuTorneoExistente(PacMan paqui)
    {
        super(paqui);
        
        ArrayList<Sala> lobbys1 = new ArrayList<Sala>();
        lista = new ArrayList<BotonSala>();
        cliente = PacMan.cliente;
        
        listenLobby = new Thread(()->
        {
            try {
                
                cliente.getOut().writeObject(Actions.GETLOBBYSstream);
                
                while (true)
                {                        
                    ArrayList<Sala> salas = (ArrayList<Sala>) cliente.getIn().readObject();
                    System.out.println("Obtenidas " + salas.size() + " salas");

                    for(Sala sala : salas)
                    {
                        System.out.println("MenuTorneoExistente::listenLobby -> Sala recibida" + sala.nombreSala + " con" + sala.jugadoresEnSala + " de " + sala.maxjugadores);   
                    }

                    lobbys1.clear();
                    lista.clear();

                    BotonSala boton;

                    boton = new BotonSala();
                    boton.texto = "Atras";
                    boton.salaID = -1;
                    lista.add(boton);

                    for(Sala sala : salas)
                    {
                        lobbys1.add(sala);
                        boton = new BotonSala();
                        boton.texto = "[" + sala.jugadoresEnSala + "/" + sala.maxjugadores + "] " + sala.nombreSala;
                        boton.salaID = sala.idSala;
                        lista.add(boton);
                    }

                    paquito.repaint();            

                    try
                    {
                        Thread.sleep(1000);
                    }
                    catch ( InterruptedException e)
                    {
                        Thread.currentThread().interrupt(); // restore interrupted status
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(MenuTorneoExistente.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        listenLobby.start();
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        for(BotonSala btn : lista)
        {
            if( (btn == null) || (btn.contenedor == null) )
                continue;
            
            if( btn.contenedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {
                    Sonidos.FRUIT.play();
                    
                    try
                    {
                        if( btn.texto.equals("Atras") && (btn.salaID == -1) )
                        {
                            this.listenLobby.interrupt();
                            this.listenLobby = null;
                            cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
                            cambiarMenu(new MenuTorneo(paquito));
                        }
                        else
                        {
                            
                            cliente.getOut().writeObject(Actions.JoinSALA);
                            cliente.getOut().writeObject(btn.salaID);
                            boolean joined = (boolean) cliente.getIn().readObject();
                            
                            if(joined)
                            {
                                this.listenLobby.interrupt();
                                this.listenLobby = null;
                                cliente.getOut().writeObject(Actions.GETLOBBYSstreamStop);
                                MenuTorneoSalaEspera espera = new MenuTorneoSalaEspera(paquito, btn.salaID);
                                cambiarMenu(espera);
                            }
                        }
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MenuTorneoExistente.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(MenuTorneoExistente.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
            else
            {
                btn.mouse = false;
            }
        }
        
        repaint();
    }
    
    @Override
    public void paint(Graphics2D g)
    {
        super.paint(g);
        
        Font fuente = new Font("Verdana", Font.BOLD, 24);
        g.setFont(fuente);
        FontMetrics fMet = g.getFontMetrics(fuente);
        g.setColor(Color.yellow);

        float thickness;
        int separacion = 0;
        
        for(Boton btn : lista)
        {            
            btn.anchoTexto = fMet.stringWidth(btn.texto);
            int anchoContenedor = btn.anchoTexto + espaciadoContenedor;
            int altoContenedor = fuente.getSize() + espaciadoContenedor;
            int X = (PacMan.ancho/2)-(anchoContenedor/2);
            int Y = separacionTope + separacion;
            int Xtexto = X + (anchoContenedor/2) - (btn.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 8;
            
            btn.contenedor = new Rectangle(X, Y, anchoContenedor, altoContenedor);
            
            if(btn.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(btn.contenedor);
                
                thickness = thickActivo;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fill(btn.contenedor);
                
                thickness = thickNormal;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            g.drawString(btn.texto, Xtexto, Ytexto);
            separacion += 70;
        }
    }

}
