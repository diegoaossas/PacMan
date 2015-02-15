package pacman.Menus;

import Libreria.Actions;
import Libreria.Sala;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacman.Cliente;
import pacman.Musica.Sonidos;
import pacman.PacMan;

public class MenuTorneoNuevo extends MenuPane
{
    private final ArrayList<Campo> listaCampos;
    private final ArrayList<Boton> lista;
           
    private String nombreSala;
    private int maxJugadores;
    
    public MenuTorneoNuevo(PacMan paqui)
    {
        super(paqui);
        listaCampos = new ArrayList<>();
        lista = new ArrayList<>();
        
        nombreSala = "Sala";
        maxJugadores = 4;
                
        Campo campo;
        Boton boton;
                
        campo = new Campo();
        campo.texto = "Nombre de sala:";
        campo.textoContenedor = nombreSala;
        listaCampos.add(campo);
        
        campo = new Campo();
        campo.texto = "Maximo Jugadores:";
        campo.textoContenedor = Integer.toString(maxJugadores);
        listaCampos.add(campo);
        
        boton = new Boton();
        boton.texto = "Crear";
        lista.add(boton);

        boton = new Boton();
        boton.texto = "Atras";
        lista.add(boton);
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
        
        for(Campo cmp : listaCampos)
        {            
            fuente = new Font("PacFont", Font.PLAIN, 16);
            g.setFont(fuente);
            fMet = g.getFontMetrics(fuente);
            cmp.anchoTexto = fMet.stringWidth(cmp.texto);
            int anchoContenedor = 250;
            int altoContenedor = 30;
            int X = (PacMan.ancho/2)-(anchoContenedor/2);
            int Y = separacionTope;
            
            g.drawString(cmp.texto, (PacMan.ancho/2)-(cmp.anchoTexto/2), Y+separacion);
            separacion += 10;
            cmp.contenedor = new Rectangle(X, Y+separacion, anchoContenedor, altoContenedor);
            
            if(cmp.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(cmp.contenedor);
                
                thickness = thickActivo;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y+separacion, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fill(cmp.contenedor);
                
                thickness = thickNormal;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y+separacion, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            
            fuente = new Font("Arial", Font.PLAIN, 15);
            g.setFont(fuente);
            g.drawString(cmp.textoContenedor, X+12, Y+separacion+20);
            separacion += 80;
        }
        
        for(Boton btn : lista)
        {            
            fuente = new Font("PacFont", Font.PLAIN, 16);
            g.setFont(fuente);
            fMet = g.getFontMetrics(fuente);
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
    public void keyPressed(KeyEvent e)
    {
        for(Campo cmp : listaCampos)
        {
            if(cmp.seleccionado)
            {
                if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
                {
                    if(cmp.textoContenedor.length() <= 0)
                        return;
                    
                    cmp.textoContenedor = cmp.textoContenedor.substring(0, cmp.textoContenedor.length()-1);
                }
                else
                {
                    cmp.textoContenedor += e.getKeyChar();
                }
                
                if(cmp.texto.equals("Nombre de sala:"))
                    nombreSala = cmp.textoContenedor;
                else
                    maxJugadores = Integer.parseInt(cmp.textoContenedor);
            }
        }
        
        repaint();
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
            
        for(Campo cmp : listaCampos)
        {
            if( (cmp == null) || (cmp.contenedor == null) )
                continue;
            
            if(me.getClickCount() >= 1)
            {
                if(cmp.contenedor.contains(punto))
                {
                    cmp.seleccionado = true;
                    cmp.mouse = true;
                }
                else
                {
                    cmp.seleccionado = false;
                    cmp.mouse = false;
                }
            }
        }
        
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
                    
                    if(btn.texto.equals("Crear"))
                    {
                        try {
                            Cliente cliente = PacMan.cliente;
                            Sala sala = new Sala();
                            sala.nombreSala = nombreSala;
                            sala.maxjugadores = maxJugadores;
                            
                            cliente.getOut().writeObject(Actions.NEWLOBBY);
                            cliente.getOut().writeObject(sala);
                            long creado = (long) cliente.getIn().readObject();
                            System.out.println("ID SALA DEVUELTO: "+creado);
                            
                            if(creado > 0)
                            {
                                MenuTorneoSalaEspera espero = new MenuTorneoSalaEspera(paquito, creado);
                                cambiarMenu(espero);
                            }
                            
                        } catch (IOException | ClassNotFoundException ex) {
                            Logger.getLogger(MenuTorneoNuevo.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    
                    if(btn.texto.equals("Atras"))
                    {
                        cambiarMenu(new MenuTorneo(paquito));
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
