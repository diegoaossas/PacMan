package pacman.principal;

import Libreria.Actions;
import Libreria.Credenciales;
import Libreria.Respuesta;
import Libreria.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ProcesaRegistro
{
    private String usuario = "";
    private String clave = "";
    
    public ProcesaRegistro(String usuario, String clave)
    {
        this.usuario = usuario;
        this.clave = clave;
    }
    
    public Respuesta procesaDatos() throws ClassNotFoundException, IOException
    {        
    	Cliente cliente = Juego.cliente;
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
            System.err.println(ex.getMessage());
            return Respuesta.ERRORCONECTANDO;
        }
        
        Respuesta respuesta;
        
        out.writeObject(Actions.REGISTRO);
        Credenciales cred = new Credenciales(usuario, clave);
        out.writeObject(cred);
        
        respuesta = (Respuesta)in.readObject();
        
        if(respuesta == Respuesta.REGISTRADO)
        {
            Usuario usu = (Usuario)in.readObject();
            cliente.setUsuario(usu);
        }
        else
        {
            socket.close();
        }
        
        return respuesta;
    }
}
