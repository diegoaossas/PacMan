package pacman.Menus;

import Libreria.Actions;
import Libreria.Sala;
import Libreria.Usuario;
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
import pacman.Musica.Sonidos;
import pacman.PacMan;

public class MenuTorneoSalaEspera extends MenuPane
{
    private ArrayList<Boton> lista = null;
    private Thread listenSala = null;
    private long idSala = 0;
    private Cliente cliente = null;
    
    public MenuTorneoSalaEspera(PacMan paqui, long idSala)
    {
        super(paqui);
        this.idSala = idSala;
        
        ArrayList<Usuario> lobbys1 = new ArrayList<Usuario>();
        lista = new ArrayList<Boton>();
        cliente = PacMan.cliente;
        
        listenSala = new Thread(()->
        {
            try {
                cliente.getOut().writeObject(Actions.GETSALAstream);
                cliente.getOut().writeObject(idSala);
                
                while (true)
                {                        
                        Sala sala = (Sala) cliente.getIn().readObject();
                        System.out.println("Obtenida sala: " + sala.nombreSala );
                        System.out.println("MenuTorneoSalaEspera::listenSala -> Sala recibida" + sala.nombreSala + " con " + sala.jugadoresEnSala + " de " + sala.maxjugadores);   

                        lobbys1.clear();
                        lista.clear();

                        Boton boton;

                        boton = new Boton();
                        boton.texto = "Atras";
                        lista.add(boton);

                        for(Usuario usuario : sala.jugadores)
                        {
                            lobbys1.add(usuario);
                            boton = new Boton();
                            boton.texto = usuario.Nombre;
                            lista.add(boton);
                        }

                        repaint();            
                        
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
                Logger.getLogger(MenuTorneoSalaEspera.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        listenSala.start();
    }
    
    @Override
    public void paint(Graphics2D g)
    {
        super.paint(g);
        
        Font fuente = new Font("PacFont", Font.PLAIN, 24);
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
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        for(Boton btn : lista)
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
                        if(btn.texto.equals("Atras"))
                        {
                                this.listenSala.interrupt();
                                this.listenSala = null;
                                cliente.getOut().writeObject(Actions.GETSALAstreamStop);
                                cliente.getOut().writeObject(Actions.LeaveSALA);
                                cliente.getOut().writeObject(this.idSala);
                                cambiarMenu(new MenuTorneo(paquito));
                        }
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MenuTorneoSalaEspera.class.getName()).log(Level.SEVERE, null, ex);
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

}
