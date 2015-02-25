/*
 * Copyright (C) 2015 Diego
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pacman.musica;

/**
 *
 * @author Diego
 */
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

    public String getTitulo()
    {
        return titulo;
    }

    public String getAutor()
    {
        return autor;
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
                Map properties = ((TAudioFileFormat) baseFileFormat).properties();
                String key = "author";
                this.autor = (String) properties.get(key);
                String key2 = "title";
                this.titulo = (String) properties.get(key2);
            }

            return din;
        } catch (UnsupportedAudioFileException | IOException | NullPointerException ex)
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

    public void inicializar()
    {
        String cancion;

        cancion = "bounce.mp3";
        MUSICAS.add(cancion);
        cancion = "withoutme.mp3";
        MUSICAS.add(cancion);

        try
        {
            clip = AudioSystem.getClip();
            audio = abrirAudio(MUSICAS.get(CancionActual));

            if (audio != null)
            {
                clip.open(audio);
            }

        } catch (LineUnavailableException | IOException ex)
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
                    {
                        reproduceSiguiente();
                    }
                }
            }
        });
    }

    public void pausarReproduccion()
    {
        if (reproduciendo)
        {
            reproduciendo = false;
            clip.stop();
        } else
        {
            if (audio != null)
            {
                reproduciendo = true;
                clip.start();
                System.out.println();
            }

        }
    }

    public void reproduceAnterior()
    {
        if (CancionActual <= 0)
        {
            CancionActual = MUSICAS.size() - 1;
        } else
        {
            CancionActual--;
        }

        try
        {
            reproduciendo = false;
            clip.close();
            audio = abrirAudio(MUSICAS.get(CancionActual));
            clip.open(audio);
            reproduciendo = true;
            clip.start();
        } catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }

    public void reproduceMusica()
    {
        reproduciendo = false;

        if (audio != null)
        {
            clip.start();
            reproduciendo = true;
        }
    }

    public void reproduceSiguiente()
    {
        if (CancionActual + 1 >= MUSICAS.size())
        {
            CancionActual = 0;
        } else
        {
            CancionActual++;
        }

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
        } catch (LineUnavailableException | IOException ex)
        {
            System.err.println(ex.getMessage());
        }
    }
}
