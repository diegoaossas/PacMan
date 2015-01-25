package pacman;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonidos {
    public static final AudioClip INICIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_beginning.wav"));
    public static final AudioClip COME = Applet.newAudioClip(Sonidos.class.getResource("pacman_chomp.wav"));
    public static final AudioClip DEATH = Applet.newAudioClip(Sonidos.class.getResource("pacman_death.wav"));
    public static final AudioClip FRUIT = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatfruit.wav"));
    public static final AudioClip EATGHOST = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatghost.wav"));
    public static final AudioClip EXTRAPAC = Applet.newAudioClip(Sonidos.class.getResource("pacman_extrapac.wav"));
    public static final AudioClip INTERMEDIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_intermission.wav"));
    
    public static boolean reproduciendo = false;
    
    private static Clip clip;
    private static AudioInputStream audio;
    private static final ArrayList<String> MUSICAS = new ArrayList<>();
    private static int CancionActual = 0;
    
    public static void inicializar()
    {
        String cancion;

        cancion = "Summer.wav";
        MUSICAS.add(cancion);
        cancion = "Stache.wav";
        MUSICAS.add(cancion);
        
        try
        {
            clip = AudioSystem.getClip();
            audio = abrirAudio(MUSICAS.get(CancionActual));
            clip.open(audio);
        }
        catch (LineUnavailableException|IOException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        new Thread(() -> {
            while(true)
            {
                while(reproduciendo)
                {
                    long tPos;
                    long tSize;

                    do
                    {
                        tPos = clip.getMicrosecondPosition();
                        tSize = clip.getMicrosecondLength();
                    }while(tPos != tSize);

                    Sonidos.reproduceSiguiente();
                }
            }
        }).start();
    }
    
    private static AudioInputStream abrirAudio(String archivo)
    {
        InputStream inputStream = Sonidos.class.getResourceAsStream(archivo);
        AudioInputStream audioIn = null;
        
        try
        {
            audioIn = AudioSystem.getAudioInputStream(inputStream);
        }
        catch (UnsupportedAudioFileException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        return audioIn;
    }
    
    public static void reproduceMusica()
    {
            reproduciendo = false;
            clip.start();
            reproduciendo = true;
    }
    
    public static void reproduceSiguiente()
    {        
        if( CancionActual+1 >= MUSICAS.size() )
            CancionActual = 0;
        else
            CancionActual++;
            
        try
        {
            reproduciendo = false;
            clip.close();
            audio = abrirAudio(MUSICAS.get(CancionActual));
            clip.open(audio);
            clip.start();
            reproduciendo = true;
        }
        catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public static void reproduceAnterior()
    {        
        if( CancionActual <= 0 )
            CancionActual = MUSICAS.size()-1;
        else
            CancionActual--;
            
        try
        {
            reproduciendo = false;
            clip.close();
            audio = abrirAudio(MUSICAS.get(CancionActual));
            clip.open(audio);
            clip.start();
            reproduciendo = true;
        }
        catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
    
    public static void pausarReproduccion()
    {  
        if(reproduciendo)
        {
            reproduciendo = false;
            clip.stop();
        }
        else
        {
            reproduciendo = true;
            clip.start();
            
        }
    }    
    
    public static void detenerReproduccion()
    {  
        if(reproduciendo)
        {
            reproduciendo = false;
            clip.stop();
            clip.setFramePosition(0);
        }
    }
}
