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

public class MenuPrincipal extends MenuPane {

    public class Boton
    {
        public Rectangle contanedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
            
    public ArrayList<Boton> lista = new ArrayList<>(3);
    
    public MenuPrincipal(PacMan paqui) {
        super(paqui);
        
        Boton boton = new Boton();
        boton.texto = "Torneo";
        lista.add(boton);
        boton = new Boton();
        boton.texto = "Estadisticas";
        lista.add(boton);
        boton = new Boton();
        boton.texto = "Ayuda";
        lista.add(boton);
        boton = new Boton();
        boton.texto = "Acerca De";
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
        
        Rectangle boton;
        int ancho;
        float thickness;
        int separacion = 0;
        
        for(Boton btn : lista)
        {
            btn.contanedor = new Rectangle((int)((paquito.ancho)/2)-125, 200+separacion, 250, 48);
            btn.anchoTexto = fMet.stringWidth(btn.texto);
            if(btn.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(btn.contanedor);
                
                thickness = 6;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(((paquito.ancho)/2)-125, 200+separacion, 250, 48, 15, 15);
                g.setStroke(oldStroke);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fill(btn.contanedor);
                
                thickness=6;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(((paquito.ancho)/2)-125, 200+separacion, 250, 48, 15, 15);
                g.setStroke(oldStroke);
            }
            g.drawString(btn.texto, btn.contanedor.x+(btn.contanedor.width/2)-(btn.anchoTexto/2), btn.contanedor.y+(btn.contanedor.height/2)+6);
            separacion += 70;
        }
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        lista.stream().forEach((btn) -> {
            if( btn.contanedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {
                    if(btn.texto.equals("Salir"))
                    {
                        System.exit(0);
                    }
                }
            }
            else
            {
                btn.mouse = false;
            }
        });
        
        paquito.repaint();
    }

}
