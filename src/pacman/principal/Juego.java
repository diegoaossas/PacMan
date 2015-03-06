package pacman.principal;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Juego extends JPanel
{
    private static final long serialVersionUID = 1L;

    public static String IP = "190.75.131.10";
    public static int PUERTO = 3000;
    public static Cliente cliente = new Cliente();
    
    public static void main(String[] args)
    {
        Juego.IP = JOptionPane.showInputDialog("Introduzca la IP del servidor", Juego.IP);
        Panel panel = new Panel();
        JFrame frame = new JFrame("PacMan");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setSize(Panel.ANCHO, Panel.ALTO);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
