/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import Libreria.Usuario;


/**
 *
 * @author Diego
 */
public class Cliente
{
    private Socket socket = null;
    private ObjectInputStream in = null;
    private ObjectOutputStream out = null;
    private Usuario usuario = null;

    public void conectar() throws IOException
    {
        InetSocketAddress address = new InetSocketAddress(PacMan.IP, PacMan.PUERTO);
        socket = new Socket();
        
        System.out.println("Conectando al servidor...");
        socket.connect(address, 1000);
        System.out.println("Conectado!");
    	in = new ObjectInputStream(socket.getInputStream());
    	out = new ObjectOutputStream(socket.getOutputStream());
    }
    
    public ObjectInputStream getIn()
    {
        return in;
    }
    
    public ObjectOutputStream getOut()
    {
        return out;
    }
    
    public Socket getSocket()
    {
        return socket;
    }
    
    public Usuario getUsuario()
    {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario)
    {
        this.usuario = usuario;
    }
}