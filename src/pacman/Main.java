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

import java.awt.FontFormatException;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Diego
 */
public class Main
{
    public static void main(String[] args)
    {
        try
        {
            JFrame frame = new JFrame("PacMan");
            PacMan juego = new PacMan();
            frame.add(juego);
            frame.setSize(juego.ancho, juego.alto);
            frame.setVisible(true);
            
            //Cambiar a DISPOSE_ON_CLOSE y manejar el cierre del programa
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            Sonidos.inicializar();
            //Sonidos.reproduceMusica();
        }
        catch (FontFormatException | IOException | IllegalArgumentException | NullPointerException ex)
        {
            System.err.println("Error encontrado: [" + ex.getClass() + "]: " + ex.getLocalizedMessage());
            
            System.err.println("Detalles:");
            for(StackTraceElement stack : ex.getStackTrace())
            {
                System.err.println(stack.toString());
            }
            System.err.println();
            
            JOptionPane.showMessageDialog(null, "El programa ha encontrado un error y no puede continuar.", "Error encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }
}
