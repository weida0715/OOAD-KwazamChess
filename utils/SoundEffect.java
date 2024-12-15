package utils;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SoundEffect {
    private static final String MOVE_SOUND_PATH = "audio/move.wav";
    private static final String CAPTURE_SOUND_PATH = "audio/capture.wav";

    // General method to play sound from a WAV file
    private static void playSound(String filePath) {
        try {
            // Load the sound file
            File soundFile = new File(filePath);
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundFile);

            // Get the sound clip and open it
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            // Play the clip
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    // Play sound for a move action
    public static void playMoveSound() {
        playSound(MOVE_SOUND_PATH);
    }

    // Play sound for a capture action
    public static void playCaptureSound() {
        playSound(CAPTURE_SOUND_PATH);
    }
}
