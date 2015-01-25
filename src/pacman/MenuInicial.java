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
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuInicial extends MenuPane {

    public class Boton
    {
        public Rectangle contanedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
            
    public ArrayList<Boton> lista = new ArrayList<>(3);
    
    public MenuInicial(PacMan paqui) throws IOException {
        super(paqui);
        
        Boton boton = new Boton();
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
            int espaciadoContenedor = 15;
            int anchoContenedor = btn.anchoTexto + espaciadoContenedor;
            int altoContenedor = fuente.getSize() + espaciadoContenedor;
            int X = (paquito.ancho/2)-(anchoContenedor/2);
            int Y = 250+separacion;
            int bordeOvalado = 15;
            int Xtexto = X + (anchoContenedor/2) - (btn.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 8;
            
            btn.contanedor = new Rectangle(X, Y, anchoContenedor, altoContenedor);
            Stroke oldStroke = g.getStroke();
            if(btn.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(btn.contanedor);
                thickness = 6;
                g.setColor(Color.YELLOW);
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fill(btn.contanedor);
                thickness = 3;
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
            if( btn.contanedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {
                    if(btn.texto.equals("Login"))
                        try {
                            paquito.menu = new MenuLogin(paquito);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuInicial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(btn.texto.equals("Registrar"))
                        try {
                            paquito.menu = new MenuPrincipal(paquito);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuInicial.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if(btn.texto.equals("Salir"))
                        System.exit(0);
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
