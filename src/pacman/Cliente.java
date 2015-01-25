/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import pacmanserver.Usuario;


/**
 *
 * @author Diego
 */
public class Cliente implements Runnable {

    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    public Cliente(Socket sock) {
        socket = sock;
        in = null;
        out = null;
    }

    @Override
    public void run() {
        try
        {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e)
        {
            System.out.println("Error:"+e.getMessage());
        }
    }
    public String leido;
    public void read() throws IOException
    {
        leido = in.readLine();
        
        if(leido == null)
            throw new IOException();
        
        System.out.println("Recibido:" + leido);
    }
    
    public void readUsuario() throws IOException
    {
        ObjectInputStream oIn = new ObjectInputStream(socket.getInputStream());
        Usuario usu = null ;
        
        try {
            usu = (Usuario)oIn.readObject();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(leido == null)
            throw new IOException();
        
        System.out.println("Recibido:" + usu.Nickname);
    }
    public void send(String data) {
        out.println(data);
    }

}