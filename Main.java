import controller.KwazamController;
import model.KwazamGameManager;
import view.KwazamView;

public class Main {
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
