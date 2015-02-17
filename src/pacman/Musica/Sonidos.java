package pacman.Musica;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonidos {
    public static final AudioClip MENUIN = Applet.newAudioClip(Sonidos.class.getResource("menuClickIn.wav"));
    public static final AudioClip MENUOUT = Applet.newAudioClip(Sonidos.class.getResource("menuClickOut.wav"));
    
    public static final AudioClip INICIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_beginning.wav"));
    public static final AudioClip COME = Applet.newAudioClip(Sonidos.class.getResource("pacman_chomp.wav"));
    public static final AudioClip DEATH = Applet.newAudioClip(Sonidos.class.getResource("pacman_death.wav"));
    public static final AudioClip FRUIT = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatfruit.wav"));
    public static final AudioClip EATGHOST = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatghost.wav"));
    public static final AudioClip EXTRAPAC = Applet.newAudioClip(Sonidos.class.getResource("pacman_extrapac.wav"));
    
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
            
            if(audio != null)
                clip.open(audio);
            
        }
        catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
        
        clip.addLineListener(new LineListener()
        {
            @Override
            public void update(LineEvent myLineEvent)
            {
                if (myLineEvent.getType() == LineEvent.Type.STOP)
                {
                    if(reproduciendo)
                        Sonidos.reproduceSiguiente();
                }
            }
        });
    }
    
    private static AudioInputStream abrirAudio(String archivo)
    {
        InputStream inputStream = Sonidos.class.getResourceAsStream(archivo);
        InputStream bufferedIn = new BufferedInputStream(inputStream);
        AudioInputStream audioIn = null;
                
        try
        {
            audioIn = AudioSystem.getAudioInputStream(bufferedIn);
        }
        catch (UnsupportedAudioFileException | IOException | NullPointerException ex)
        {
            System.err.println("Error abriendo audio: " + ex.getMessage());
        }
        
        return audioIn;
    }
    
    public static void reproduceMusica()
    {
            reproduciendo = false;
            
            if(audio != null)
            {
                clip.start();
                reproduciendo = true;
            }
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
            
            if(audio != null)
            {
                clip.open(audio);
                clip.start();
                reproduciendo = true;
            }
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
            reproduciendo = true;
            clip.start();
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
            if(audio != null)
            {
                reproduciendo = true;
                clip.start();
            }
            
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
