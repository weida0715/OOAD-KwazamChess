package utils;

import java.awt.Color;

public class KwazamConstants {
    // =================================================================
    // GAME CONFIGURATION
    // =================================================================
    public static final String TITLE = "Kwazam Chess";
    public static final int WINDOW_WIDTH = 600;
    public static final int WINDOW_HEIGHT = 800;
    public static final int BOARD_ROWS = 8;
    public static final int BOARD_COLS = 5;
    public static final int BORDER_WIDTH = 4;

    // =================================================================
    // FILE PATHS
    // =================================================================
    public static final String MOVE_SOUND_PATH = "/audio/move.wav";
    public static final String CAPTURE_SOUND_PATH = "/audio/capture.wav";
    public static final String WINNING_SOUND_PATH = "/audio/winning.wav";
    public static final String BACKGROUND_MUSIC_PATH = "/audio/background.wav";

    // =================================================================
    // COLORS
    // =================================================================
    public static final Color SAU_CHECKED_COLOR = new Color(255, 182, 193, 180); // Red pink
    public static final Color SQUARE_HIGHLIGHT_COLOR = new Color(200, 200, 255); // Blue purple
    public static final Color SQUARE_COLOR_1 = new Color(235, 215, 194); // Soft warm peach
    public static final Color SQUARE_COLOR_2 = new Color(201, 222, 195); // Light, muted green
    public static final Color BORDER_COLOR = new Color(82, 125, 107); // Deep, calm forest green
    public static final Color BACKGROUND_COLOR = new Color(233, 224, 218); // Soft pastel beige
    public static final Color VALID_MOVE_COLOR = new Color(128, 128, 128, 180); // Semi-transparent grey
}
