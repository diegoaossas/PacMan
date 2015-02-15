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
import java.util.ArrayList;
import pacman.Musica.Sonidos;
import pacman.PacMan;

public class MenuInicial extends MenuPane
{
    private ArrayList<Boton> lista = null;
    
    public MenuInicial(PacMan paqui)
    {
        super(paqui);
        lista = new ArrayList<Boton>();
        
        Boton boton;
        
        boton = new Boton();
        boton.texto = "Login";
        lista.add(boton);
        
        boton = new Boton();
        boton.texto = "Registrar";
        lista.add(boton);
        
        boton = new Boton();
        boton.texto = "Salir";
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
            int X = (PacMan.ancho/2)-(anchoContenedor/2);
            int Y = separacionTope + separacion;
            int Xtexto = X + (anchoContenedor/2) - (btn.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 8;
            
            btn.contenedor = new Rectangle(X, Y, anchoContenedor, altoContenedor);
            Stroke oldStroke = g.getStroke();
            if(btn.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(btn.contenedor);
                thickness = thickActivo;
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fill(btn.contenedor);
                thickness = thickNormal;
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
            }
            g.setStroke(oldStroke);
            g.drawString(btn.texto, Xtexto, Ytexto);
            separacion += 100;
        }
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        for(Boton btn:lista)
        {
            if( (btn == null) || (btn.contenedor == null) )
                continue;
            
            if( btn.contenedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {
                    Sonidos.FRUIT.play();
                    
                    if(btn.texto.equals("Salir"))
                        System.exit(0);
                    
                    if(btn.texto.equals("Login"))
                        cambiarMenu(new MenuLogin(paquito));
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
