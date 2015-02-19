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
package pacman.Musica;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.spi.*;
/**
 *
 * @author Diego
 */
public class Reproductor
{
    private BasicPlayer player;
    private static ArrayList<String> canciones;
    private static int CancionActual;
    
    public Reproductor()
    {
        player = new BasicPlayer();
        canciones = new ArrayList<String>();
        CancionActual = canciones.size() - 1;
    }
    
    public void inicializar()
    {
        canciones.add("Acelera.mp3");
        CancionActual = 0;
    }
    
    public void reproducir() throws IOException, UnsupportedAudioFileException
    {
        try
        {
            int status = player.getStatus();
            
            if(status == BasicPlayer.PLAYING)
            {
                player.pause();
            }
            else if(status == BasicPlayer.PAUSED)
            {
                player.resume();
            }
            else
            {
                InputStream stream = getClass().getResourceAsStream("/Sonidos/"+canciones.get(CancionActual));
                AudioInputStream aIn = AudioSystem.getAudioInputStream(stream);
                AudioInputStream din = null;
                AudioFormat baseFormat = aIn.getFormat();
                AudioFormat decodedFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 
                                                                                  baseFormat.getSampleRate(),
                                                                                  16,
                                                                                  baseFormat.getChannels(),
                                                                                  baseFormat.getChannels() * 2,
                                                                                  baseFormat.getSampleRate(),
                                                                                  false);
                din = AudioSystem.getAudioInputStream(decodedFormat, aIn);
                
                //player.open(din);
                player.play();
            }
        }
        catch (BasicPlayerException ex)
        {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
