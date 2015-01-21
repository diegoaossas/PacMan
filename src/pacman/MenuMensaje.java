/*
 * Copyright (C) 2015 Diego
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pacman;

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

/**
 *
 * @author Diego
 */
public class MenuMensaje extends MenuPane{
    
    private MenuPane menuAnterior;
    private String titulo;
    private String mensaje;
    
    public class Boton
    {
        public Rectangle contenedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
    
    public ArrayList<Boton> lista = new ArrayList<>(2);
    
    public MenuMensaje(PacMan paqui, String titulo, String mensaje, MenuPane anterior) {
        super(paqui);        
        this.menuAnterior = anterior;
        this.titulo = titulo;
        this.mensaje = mensaje;
        
        Boton boton = new Boton();
        boton.texto = "Atras";
        lista.add(boton);
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
        
        int ancho = fMet.stringWidth(titulo);
        g.drawString(titulo, (paquito.ancho/2)-(ancho/2), 250);
        fuente = new Font("Verdana", Font.BOLD, 18);
        g.setFont(fuente);
        fMet = g.getFontMetrics(fuente);
        ancho = fMet.stringWidth(mensaje);
        g.drawString(mensaje, (paquito.ancho/2)-(ancho/2), 300);
        
        for(Boton btn : lista)
        {            
            fuente = new Font("PacFont", Font.PLAIN, 16);
            g.setFont(fuente);
            fMet = g.getFontMetrics(fuente);
            btn.anchoTexto = fMet.stringWidth(btn.texto);
            int espaciadoContenedor = 15;
            int anchoContenedor = btn.anchoTexto + espaciadoContenedor;
            int altoContenedor = fuente.getSize() + espaciadoContenedor;
            int X = (paquito.ancho/2)-(anchoContenedor/2);
            int Y = 340+separacion;
            int bordeOvalado = 15;
            int Xtexto = X + (anchoContenedor/2) - (btn.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 6;
            
            btn.contenedor = new Rectangle(X, Y, anchoContenedor, altoContenedor);
            if(btn.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(btn.contenedor);
                
                thickness = 6;
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
                
                thickness=3;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            g.drawString(btn.texto, Xtexto, Ytexto);
            separacion += altoContenedor + 6 + 20;
        }
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        
        for(Boton btn : lista)
        {
            if( btn.contenedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {                    
                    if(btn.texto.equals("Atras"))
                    {
                        paquito.menu = this.menuAnterior;
                    }                    
                }
            }
            else
            {
                btn.mouse = false;
            }
        };
        
        paquito.repaint();
    }
}
