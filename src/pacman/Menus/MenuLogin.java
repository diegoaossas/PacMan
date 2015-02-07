package pacman.Menus;

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
import pacman.PacMan;
import pacman.ProcesoLogin;

public class MenuLogin extends MenuPane{
    
    private final MenuPane menuAnterior;
    private final ArrayList<Campo> listaCampos;
    private final ArrayList<Boton> lista;
           
    private String usuario;
    private String clave;
    
    public MenuLogin(PacMan paqui, MenuPane anterior) throws IOException {
        super(paqui);
        
        usuario = "";
        clave = "";
        listaCampos = new ArrayList<>();
        lista = new ArrayList<>();
        menuAnterior = anterior;
        
        Campo campo;
        Boton boton;
        
        campo = new Campo();
        campo.texto = "Nombre de usuario:";
        campo.textoContenedor = usuario;
        listaCampos.add(campo);
        
        campo = new Campo();
        campo.texto = "Clave:";
        campo.textoContenedor = clave;
        listaCampos.add(campo);
        
        boton = new Boton();
        boton.texto = "Atras";
        lista.add(boton);
        
        boton = new Boton();
        boton.texto = "Entrar";
        lista.add(boton);
    }
     
    @Override
    public void paint(Graphics2D g)
    {
        super.paint(g);
        
        Font fuente;
        FontMetrics fMet;
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
            int Y = separacionTope;
            
            g.drawString(cmp.texto, (paquito.ancho/2)-(cmp.anchoTexto/2), Y+separacion);
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
            int X = (paquito.ancho/2)-(anchoContenedor/2);
            int Y = separacionTope + separacion;
            int Xtexto = X + (anchoContenedor/2) - (btn.anchoTexto/2);
            int Ytexto = Y + (altoContenedor/2) + 6;
            
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
                
                thickness=3;
                g.setColor(Color.YELLOW);
                Stroke oldStroke = g.getStroke();
                g.setStroke(new BasicStroke(thickness));
                g.drawRoundRect(X, Y, anchoContenedor, altoContenedor, bordeOvalado, bordeOvalado);
                g.setStroke(oldStroke);
            }
            g.drawString(btn.texto, Xtexto, Ytexto);
            separacion += altoContenedor + 26;
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
                    try
                    {
                        if(btn.texto.equals("Atras"))
                        {
                            paquito.cambiarMenu(menuAnterior);
                        }                    
                        if(btn.texto.equals("Entrar"))
                        {
                            //System.out.println("Logear a '"+usuario+"' con clave:" + clave);
                            System.err.println("PREINIT");
                            ProcesoLogin login = new ProcesoLogin(paquito, usuario, clave);
                            System.err.println("POSTINIT");
                            
                            MenuPane mMensaje;
                            boolean logged;
                            
                            System.err.println("PREPROC");
                            logged = login.procesaDatos();
                            System.err.println("POSTPROC");
                            
                            if(!logged)
                            {
                                System.err.println("PREFAIL");
                                mMensaje = new MenuMensaje(paquito, "Login", "Login incorrecto, intente de nuevo...", this);
                                System.err.println("POSTFAIL");
                            }
                            else
                            {
                                System.err.println("PREOK");
                                mMensaje = new MenuPrincipal(paquito);
                                System.err.println("POSTOK");
                            }

                            paquito.cambiarMenu(mMensaje);
                        }
                    }
                    catch(IOException | ClassNotFoundException ex)
                    {
                        System.err.println("No se pudo cargar correctamente el menu: " + btn.texto);
                        System.err.println("Error: " + ex.getMessage());
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
