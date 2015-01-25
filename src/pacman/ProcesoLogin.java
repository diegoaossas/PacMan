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

import java.io.IOException;
import java.net.Socket;
import pacmanserver.Servidor;

/**
 *
 * @author Diego
 */
public class ProcesoLogin {

    private String usuario;
    private String clave;
    private PacMan paquito;
    
    public ProcesoLogin(PacMan paqui, String usuario, String clave) {
        this.paquito = paqui;
        this.usuario = usuario;
        this.clave = clave;
    }
    
    public boolean procesaDatos() 
    {
        String logged = "";
        
        Cliente cliente = paquito.cliente;
        Socket socket = paquito.sock;
        
        try
        {
            if(socket == null)
            {
                paquito.sock = new Socket(paquito.IP, paquito.PUERTO);
                paquito.cliente = new Cliente(paquito.sock);
                System.out.println("Socket nulo.");
                System.out.println("Conectado al servidor.");
            }
            else
            {
                if(socket.isClosed())
                {
                    paquito.sock = new Socket(paquito.IP, paquito.PUERTO);
                    paquito.cliente = new Cliente(paquito.sock);
                    System.out.println("Socket cerrado.");
                    System.out.println("Conectado al servidor.");
                }
            }
        }
        catch(IOException ex)
        {
            System.err.println("Error conectando al servidor...");
            System.err.println("-Mensaje del error: " + ex.getMessage());
            
            return false;
        }
        finally
        {
            socket = paquito.sock;
            cliente = paquito.cliente;
        }
        
        try
        {
            cliente.run();
            cliente.send("login");
            cliente.send(usuario);
            cliente.send(clave);
        
            cliente.read();
            logged = cliente.leido;        
            
            if(logged.equals("true"))
            {
                cliente.readUsuario();
                return true;
            }
            else
            {
                return false;
            }
        }
        catch (IOException ex)
        {
            System.err.println("Error Leyendo del Servidor...");
            System.err.println("-Mensaje del error: " + ex.getMessage());
            
            return false;
        }
    }
}
