import controller.KwazamController;
import model.KwazamGameManager;
import view.KwazamView;

/**
 * Author(s):
 * 
 * Main class to start the Kwazam game.
 * Initializes the model, view, and controller.
 */
public class Main {
    /**
     * Author(s):
     * 
     * Entry point for the Kwazam game.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        // Model
        KwazamGameManager model = new KwazamGameManager();

        // View
        KwazamView view = new KwazamView();

        // Controller
        KwazamController controller = KwazamController.getInstance(view, model);

        // Start the game
        controller.startGame();
    }
}
