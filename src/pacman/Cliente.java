/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 *
 * @author Diego
 */
public class Cliente implements Runnable {
    private final Socket socket;
    public ObjectInputStream in;
    public ObjectOutputStream out;

    public Cliente(Socket sock)
    {
        socket = sock;
        in = null;
        out = null;
    }

    @Override
    public void run()
    {
        try
        {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }
        catch (IOException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
}