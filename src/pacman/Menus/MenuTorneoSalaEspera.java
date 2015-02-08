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
import pacman.PacMan;

public class MenuTorneoSalaEspera extends MenuPane {
    
    private final MenuPane menuAnterior;
    private final ArrayList<Boton> lista;
    private Thread listenSala;
    private long idSala;
    
    public MenuTorneoSalaEspera(PacMan paqui, long idSala, MenuPane menuAnterior) throws IOException {
        super(paqui);
        
        ArrayList<Usuario> lobbys1 = new ArrayList<>();
        this.idSala = idSala;
        this.lista = new ArrayList<>();
        this.menuAnterior = menuAnterior;
        
        listenSala = new Thread(()->
        {
            try {
                paquito.cliente.out.writeObject(Actions.GETSALAstream);
                paquito.cliente.out.writeObject(idSala);
                
                while (true)
                {                        
                        Sala sala = (Sala) paquito.cliente.in.readObject();
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
                            boton.texto = usuario.Usuario;
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
            int X = (paquito.ancho/2)-(anchoContenedor/2);
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
                    try
                    {
                        if(btn.texto.equals("Atras"))
                        {
                                this.listenSala.interrupt();
                                this.listenSala = null;
                                paquito.cliente.out.writeObject(Actions.GETSALAstreamStop);
                                paquito.cliente.out.writeObject(Actions.LeaveSALA);
                                paquito.cliente.out.writeObject(this.idSala);
                                paquito.cambiarMenu(menuAnterior);
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
        
        paquito.repaint();
    }

}
