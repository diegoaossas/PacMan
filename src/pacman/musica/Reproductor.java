package pacman.musica;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.tritonus.share.sampled.file.TAudioFileFormat;

public class Reproductor
{
    private String titulo = "";
    private String autor = "";
    public static boolean reproduciendo = false;

    private static Clip clip;
    private static AudioInputStream audio;
    private static final ArrayList<String> MUSICAS = new ArrayList<>();
    private static int CancionActual = 0;
    
    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public Reproductor()
    {
        String cancion;

        cancion = "bounce.mp3";
        MUSICAS.add(cancion);
        cancion = "withoutme.mp3";
        MUSICAS.add(cancion);

        try
        {
            clip = AudioSystem.getClip();
        }
        catch (LineUnavailableException ex)
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
                    if (reproduciendo)
                        reproduceSiguiente();
                }
            }
        });
    }

    private AudioInputStream abrirAudio(String archivo)
    {
        URL url = getClass().getResource("/Musica/" + MUSICAS.get(CancionActual));

        try
        {
            AudioInputStream aIn = AudioSystem.getAudioInputStream(url);
            AudioInputStream din = null;
            AudioFileFormat baseFileFormat = AudioSystem.getAudioFileFormat(aIn);
            AudioFormat baseFormat = aIn.getFormat();
            AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false);
            
            din = AudioSystem.getAudioInputStream(decodedFormat, aIn);

            if (baseFileFormat instanceof TAudioFileFormat)
            {
                Map properties = baseFileFormat.properties();
                String key = "author";
                this.autor = (String) properties.get(key);
                String key2 = "title";
                this.titulo = (String) properties.get(key2);
            }

            return din;
        }
        catch (UnsupportedAudioFileException | IOException | NullPointerException ex)
        {
            System.err.println("Error abriendo audio: " + ex.getMessage());
        }

        return null;
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

    public void alternarReproduccion()
    {
        if (reproduciendo)
        {
            reproduciendo = false;
            clip.stop();
        }
        else
        {
            if (audio != null)
            {
                reproduciendo = true;
                clip.start();
                System.out.println();
            }
            else
            {
                try
                {
                    audio = abrirAudio(MUSICAS.get(CancionActual));
                    clip.open(audio);
                    alternarReproduccion();
                }
                catch (LineUnavailableException | IOException ex)
                {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    public void reproduceAnterior()
    {
        if (CancionActual <= 0)
            CancionActual = MUSICAS.size() - 1;
        else
            CancionActual--;
        
        reproduceNueva();
    }


    public void reproduceSiguiente()
    {
        if (CancionActual + 1 >= MUSICAS.size())
            CancionActual = 0;
        else
            CancionActual++;
        
        reproduceNueva();
    }
    
    private void reproduceNueva()
    {        
        try
        {
            reproduciendo = false;
            clip.close();
            audio = abrirAudio(MUSICAS.get(CancionActual));

            if (audio != null)
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
}
