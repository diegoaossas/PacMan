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

import Libreria.Actions;
import java.io.IOException;
import java.net.Socket;
import Libreria.Credenciales;
import Libreria.Respuesta;
import Libreria.Usuario;
import java.net.InetSocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diego
 */
public class ProcesoLogin {

    private final String usuario;
    private final String clave;
    private final PacMan paquito;
    
    public ProcesoLogin(PacMan paqui, String usuario, String clave) {
        this.paquito = paqui;
        this.usuario = usuario;
        this.clave = clave;
    }
    
    public Respuesta procesaDatos() throws ClassNotFoundException, IOException
    {        
        
        InetSocketAddress address = new InetSocketAddress(paquito.IP, paquito.PUERTO);
        Socket socket = new Socket();
        Cliente cliente;
        
        try
        {
            System.out.println("Conectadon al servidor...");
            socket.connect(address, 500);
            System.out.println("Conectado!");
            cliente = new Cliente(socket);
        }
        catch (IOException ex)
        {
            System.err.println("Error conectando al servidor...");
            System.err.println("-Mensaje del error: " + ex.getMessage());
            
            return Respuesta.ERRORCONECTANDO;
        }
        
        Respuesta respuesta;
        cliente.run();
        cliente.out.writeObject(Actions.LOGIN);
        System.out.println("Solicitud de login enviada.");
        
        Credenciales cred = new Credenciales();
        cred.usuario = usuario;
        cred.clave = clave;
        cliente.out.writeObject(cred);
        System.out.println("Credenciales enviadas.");
        
        respuesta = (Respuesta)cliente.in.readObject();
        System.out.println("Resultado de login recibido -> " + respuesta);
        
        if(respuesta == Respuesta.LOGGED)
        {
            Usuario usu = (Usuario)cliente.in.readObject();
            System.out.println("Usuario recibido -> " + usu.Usuario);
            
            paquito.sock = socket;
            paquito.cliente = cliente;
        }
        else
        {
            socket.close();
        }
        
        return respuesta;
    }
}
