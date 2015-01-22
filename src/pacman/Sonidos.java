package pacman;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sonidos {
    private static final AudioClip MUSICA = Applet.newAudioClip(Sonidos.class.getResource("Stache.wav"));
    private static final AudioClip MUSICA2 = Applet.newAudioClip(Sonidos.class.getResource("Summer.wav"));
    public static final AudioClip INICIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_beginning.wav"));
    public static final AudioClip COME = Applet.newAudioClip(Sonidos.class.getResource("pacman_chomp.wav"));
    public static final AudioClip DEATH = Applet.newAudioClip(Sonidos.class.getResource("pacman_death.wav"));
    public static final AudioClip FRUIT = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatfruit.wav"));
    public static final AudioClip EATGHOST = Applet.newAudioClip(Sonidos.class.getResource("pacman_eatghost.wav"));
    public static final AudioClip EXTRAPAC = Applet.newAudioClip(Sonidos.class.getResource("pacman_extrapac.wav"));
    public static final AudioClip INTERMEDIO = Applet.newAudioClip(Sonidos.class.getResource("pacman_intermission.wav"));
    public static boolean reproduciendo = false;
    
    public static void reproduceFondo()
    {
        reproduciendo = true;
        MUSICA2.loop();
    }
    
    public static void detenFondo()
    {
        reproduciendo = false;
        MUSICA2.stop();
    }
    
}
