import controller.KwazamController;
import model.KwazamBoard;
import model.KwazamGameManager;
import view.KwazamView;

public class Main {
    public static void main(String[] args) {
        // Model
        KwazamBoard board = new KwazamBoard();
        KwazamGameManager model = new KwazamGameManager(board);

        // View
        KwazamView view = new KwazamView();

        // Controller
        KwazamController controller = KwazamController.getInstance(view, model);

        // Start the game
        controller.startGame();
    }
}
