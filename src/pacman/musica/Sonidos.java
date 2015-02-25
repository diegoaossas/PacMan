package pacman.musica;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Sonidos
{

    public static final AudioClip MENUIN = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/menuClickIn.wav"));
    public static final AudioClip MENUOUT = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/menuClickOut.wav"));
    
    public static final AudioClip INICIO = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/pacman_beginning.wav"));
    public static final AudioClip DEATH = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/pacman_death.wav"));
    public static final AudioClip FRUIT = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/pacman_eatfruit.wav"));
    public static final AudioClip EATGHOST = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/pacman_eatghost.wav"));
    public static final AudioClip EXTRAPAC = Applet.newAudioClip(Sonidos.class.getResource("/Sonidos/pacman_extrapac.wav"));

    public static final String EAT = "/Sonidos/pacman_chomp.wav";
    
    private static Clip clip;
    private static AudioInputStream audio;
    private boolean reproduciendo = false;
    private LineListener listener;

    public Sonidos()
    {
        try
        {
            clip = AudioSystem.getClip();
        }
        catch (LineUnavailableException ex)
        {
            System.err.println(ex.getMessage());
        }

        listener = new LineListener()
        {

            @Override
            public void update(LineEvent le)
            {
                if(le.getType() == LineEvent.Type.START)
                {
                    reproduciendo = true;
                }
                
                if (le.getType() == LineEvent.Type.STOP)
                {
                    clip.close();
                    reproduciendo = false;
                }
            }
        };

        clip.addLineListener(listener);
    }


    public void reproduceSonido(String archivo)
    {
        if(reproduciendo)
            return;
        
        URL url = getClass().getResource(archivo);
        try
        {
            AudioInputStream aIn = AudioSystem.getAudioInputStream(url);
            if(!clip.isOpen())
                clip.open(aIn);
            clip.start();
        }
        catch (UnsupportedAudioFileException | IOException | NullPointerException | LineUnavailableException ex)
        {
            System.err.println("Error abriendo audio: " + ex.getMessage());
        }
    }

    public void detenerReproduccion()
    {
        if (reproduciendo)
        {
            reproduciendo = false;
            clip.stop();
            clip.setFramePosition(0);
        }
    }
}
