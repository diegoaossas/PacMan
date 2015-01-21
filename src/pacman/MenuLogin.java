package pacman;

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
import java.util.ArrayList;

public class MenuLogin extends MenuPane{
    
    public class Campo
    {
        public String texto;
        public String textoContenedor;
        public Rectangle contenedor;
        public int anchoTexto;
        public boolean mouse;
        public boolean seleccionado;
    }
    
    public class Boton
    {
        public Rectangle contenedor;
        public String texto;
        public int anchoTexto;
        public boolean mouse;
    }
           
    private String usuario = "";
    private String clave = "";
    
    public ArrayList<Campo> listaCampos = new ArrayList<>(2);
    public ArrayList<Boton> lista = new ArrayList<>(2);
    
    public MenuLogin(PacMan paqui) {
        super(paqui);
        
        Campo campo = new Campo();
        campo.texto = "Nombre de usuario:";
        campo.textoContenedor = usuario;
        listaCampos.add(campo);
        Campo campo2 = new Campo();
        campo2.texto = "Clave:";
        campo2.textoContenedor = clave;
        listaCampos.add(campo2);
        
        Boton boton = new Boton();
        boton.texto = "Atras";
        lista.add(boton);
        Boton boton2 = new Boton();
        boton2.texto = "Entrar";
        lista.add(boton2);
    }
     
    @Override
    public void paint(Graphics2D g)
    {
        super.paint(g);
        
        Font fuente = new Font("PacFont", Font.PLAIN, 18);
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
            int X = (paquito.ancho/2)-(anchoContenedor/2);
            int Y = 250;
            int bordeOvalado = 15;
            int Xtexto = X + (anchoContenedor/2) - (cmp.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 6;
            
            g.drawString(cmp.texto, (paquito.ancho/2)-(cmp.anchoTexto/2), Y+separacion);
            separacion += 10;
            cmp.contenedor = new Rectangle(X, Y+separacion, anchoContenedor, altoContenedor);
            
            if(cmp.mouse == true)
            {
                g.setColor(Color.RED);
                g.fill(cmp.contenedor);
                
                thickness = 6;
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
                
                thickness=3;
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
            int espaciadoContenedor = 15;
            int anchoContenedor = btn.anchoTexto + espaciadoContenedor;
            int altoContenedor = fuente.getSize() + espaciadoContenedor;
            int X = (paquito.ancho/2)-(anchoContenedor/2);
            int Y = 250+separacion;
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
                
                if(cmp.texto.equals("Nombre de usuario:"))
                    usuario = cmp.textoContenedor;
                else
                    clave = cmp.textoContenedor;
            }
        }
        
        paquito.repaint();
    }
    
    @Override
    public void mouseMovido(MouseEvent me)
    {
        super.mouseMovido(me);
        
        Point punto = new Point(me.getX(), me.getY());
        
        for(Campo cmp:listaCampos)
        {
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
            if( btn.contenedor.contains(punto))
            {
                btn.mouse = true;
                
                if(me.getClickCount() == 1)
                {                    
                    if(btn.texto.equals("Atras"))
                    {
                        paquito.menu = new MenuInicial(paquito);
                    }                    
                    if(btn.texto.equals("Entrar"))
                    {
                        //System.out.println("Logear a '"+usuario+"' con clave:" + clave);
                        ProcesoLogin login = new ProcesoLogin(usuario, clave);
                        MenuMensaje mMensaje;
                        
                        if(login.procesaDatos() == false)
                        {
                            mMensaje = new MenuMensaje(paquito, "Login", "Login incorrecto, intente de nuevo...", this);
                        }
                        else
                        {
                            mMensaje = new MenuMensaje(paquito, "Login", "Login correcto.", this);
                        }
                        
                        paquito.menu = mMensaje;
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
