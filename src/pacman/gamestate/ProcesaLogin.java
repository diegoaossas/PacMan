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
package pacman.gamestate;

import Libreria.Actions;
import Libreria.Credenciales;
import Libreria.Respuesta;
import Libreria.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Diego
 */
public class ProcesaLogin
{
    private String usuario = "";
    private String clave = "";
    
    public ProcesaLogin(String usuario, String clave) {
        this.usuario = usuario;
        this.clave = clave;
    }
    
    public Respuesta procesaDatos() throws ClassNotFoundException, IOException
    {        
    	Cliente cliente = GameStateManager.cliente;
        Socket socket;
        ObjectOutputStream out;
        ObjectInputStream in;
         
        try
        {
        	cliente.conectar();
            socket = cliente.getSocket();
            out = cliente.getOut();
            in = cliente.getIn();            
        }
        catch (IOException ex)
        {
            System.err.println("Error conectando al servidor...");
            System.err.println("-Mensaje del error: " + ex.getMessage());
            
            return Respuesta.ERRORCONECTANDO;
        }
        
        Respuesta respuesta;
        
        out.writeObject(Actions.LOGIN);
        System.out.println("Solicitud de login enviada.");
        
        Credenciales cred = new Credenciales();
        cred.usuario = usuario;
        cred.clave = clave;
        out.writeObject(cred);
        System.out.println("Credenciales enviadas.");
        
        respuesta = (Respuesta)in.readObject();
        System.out.println("Resultado de login recibido -> " + respuesta);
        
        if(respuesta == Respuesta.LOGGED)
        {
            Usuario usu = (Usuario)in.readObject();
            System.out.println("Usuario recibido -> " + usu.Cuenta);
            
            cliente.setUsuario(usu);
        }
        else
        {
            socket.close();
        }
        
        return respuesta;
    }
}
