package utils;

import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Author(s):
 * 
 * Handles sound effects for the Kwazam game.
 * Provides methods to play sounds for moves, captures, and winning.
 */
public class SoundEffect {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private static boolean soundEnabled = true;
    private static Clip backgroundMusicClip; // Clip for background music
    private static boolean backgroundMusicEnabled = true; // Toggle for background music
    private static long musicPosition = 0; // Tracks the current position of the music

    // =================================================================
    // SOUND PLAYBACK
    // =================================================================
    /**
     * Author(s):
     * 
     * Plays a sound from the specified file path.
     * 
     * @param filePath the path to the sound file
     */
    private static void playSound(String filePath) {
        if (!soundEnabled) {
            return; // Do not play sound if sound is disabled
        }

        try {
            // Load the sound file from the classpath
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                    SoundEffect.class.getResourceAsStream(filePath));

            // Get the sound clip and open it
            Clip clip = AudioSystem.getClip();
            clip.open(audioInput);

            // Play the clip
            clip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing sound: " + e.getMessage());
        }
    }

    // =================================================================
    // SOUND TOGGLES
    // =================================================================
    /**
     * Author(s):
     * 
     * Plays a sound from the specified file path.
     * 
     * @param filePath the path to the sound file
     */
    public static void toggleSound() {
        soundEnabled = !soundEnabled; // Toggle the sound flag
    }

    /**
     * Author(s):
     * 
     * Toggles background music on or off.
     */
    public static void toggleBackgroundMusic() {
        backgroundMusicEnabled = !backgroundMusicEnabled;
        if (backgroundMusicEnabled) {
            playBackgroundMusic(); // Resume music from the stored position
        } else {
            stopBackgroundMusic(); // Pause music and store the current position
        }
    }

    // =================================================================
    // BACKGROUND MUSIC
    // =================================================================
    /**
     * Author(s):
     * 
     * Plays background music.
     */
    public static void playBackgroundMusic() {
        if (!backgroundMusicEnabled || backgroundMusicClip != null) {
            return; // Do not play if music is disabled or already playing
        }

        try {
            // Load the background music from the classpath
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                    SoundEffect.class.getResourceAsStream(KwazamConstants.BACKGROUND_MUSIC_PATH));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInput);

            // Set the position to the stored position (resume from where it was paused)
            backgroundMusicClip.setMicrosecondPosition(musicPosition);

            // Loop the background music
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);

            // Start playback
            backgroundMusicClip.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {
            System.err.println("Error playing background music: " + e.getMessage());
        }
    }

    /**
     * Author(s):
     * 
     * Stops background music.
     */
    public static void stopBackgroundMusic() {
        if (backgroundMusicClip != null) {
            musicPosition = backgroundMusicClip.getMicrosecondPosition(); // Store the current position
            backgroundMusicClip.stop();
            backgroundMusicClip.close();
            backgroundMusicClip = null;
        }
    }

    // =================================================================
    // GAME SOUND EFFECTS
    // =================================================================
    /**
     * Author(s):
     * 
     * Plays the sound for a move action.
     */
    public static void playMoveSound() {
        playSound(KwazamConstants.MOVE_SOUND_PATH);
    }

    /**
     * Author(s):
     * 
     * Plays the sound for a capture action.
     */
    public static void playCaptureSound() {
        playSound(KwazamConstants.CAPTURE_SOUND_PATH);
    }

    /**
     * Author(s):
     * 
     * Plays the sound for a winning action.
     */
    public static void playWinningSound() {
        playSound(KwazamConstants.WINNING_SOUND_PATH);
    }
}
