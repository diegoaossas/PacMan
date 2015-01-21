package pacman;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sonidos {
    private static final AudioClip MUSICA = Applet.newAudioClip(Sonidos.class.getResource("Stache.wav"));
    public static final AudioClip INICIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_beginning.wav"));
    public static final AudioClip COME = Applet.newAudioClip(Sonidos.class.getResource("pacman_chomp.wav"));
    public static boolean reproduciendo = false;
    
    public static void reproduceFondo()
    {
        reproduciendo = true;
        MUSICA.loop();
    }
    
    public static void detenFondo()
    {
        reproduciendo = false;
        MUSICA.stop();
    }
    
}
