package vista;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class audio {
    private static audio instancia;
    private Clip clip;
    private audio() {}

    public static audio getInstancia() {
        if (instancia == null) {
            instancia = new audio();
        }
        return instancia;
    }

    public void iniciarMusicaTaberna() {
        if (clip != null && clip.isRunning()) {
            return;
        }

        try {
            java.net.URL urlMusica = getClass().getResource("/audio/Tavern.wav");
            if (urlMusica != null) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(urlMusica);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                clip.start();
            }
        } catch (Exception e) {
            System.out.println("Error al reproducir audio: " + e.getMessage());
        }
    }
    
    public void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}