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
import pacman.PacMan;

public class MenuTorneo extends MenuPane {
    
    private final MenuPane menuAnterior;
    private final ArrayList<Boton> lista;
    
    public MenuTorneo(PacMan paqui, MenuPane anterior) throws IOException {
        super(paqui);
        menuAnterior = anterior;
        
        lista = new ArrayList<>();
        
        Boton boton;
        
        boton = new Boton();
        boton.texto = "Crear Torneo";
        lista.add(boton);
        
        boton = new Boton();
        boton.texto = "Jugar Torneo Existente";
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
                        if(btn.texto.equals("Crear Torneo"))
                        {
                                MenuTorneoNuevo admin = new MenuTorneoNuevo(paquito, this);
                                paquito.cambiarMenu(admin);
                        }

                        if(btn.texto.equals("Jugar Torneo Existente"))
                        {
                                MenuTorneoExistente admin = new MenuTorneoExistente(paquito, this);
                                paquito.cambiarMenu(admin);
                        }

                        if(btn.texto.equals("Atras"))
                        {
                            paquito.cambiarMenu(menuAnterior);
                        }
                    }
                    catch (IOException ex)
                    {
                        Logger.getLogger(MenuTorneo.class.getName()).log(Level.SEVERE, null, ex);
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
