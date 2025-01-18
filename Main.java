
// =================================================================
// DESIGN PATTERNS USED IN THE PROJECT
// =================================================================
/**
 * 1. **Model-View-Controller (MVC) Pattern**:
 *    - The project is structured using the MVC pattern to separate concerns:
 *      - **Model**: Handles game logic, state, and data (e.g., `KwazamModel`, `KwazamBoard`, `KwazamPiece`).
 *      - **View**: Manages the user interface and rendering (e.g., `KwazamView`, `KwazamBoardPanel`, `KwazamRenderPiece`).
 *      - **Controller**: Acts as the mediator between the model and view, handling user input and updating the model and view (e.g., `KwazamController`, `KwazamMouseHandler`, `KwazamMenuHandler`).
 *
 * 2. **Singleton Pattern**:
 *    - The `KwazamController` class uses the Singleton pattern to ensure only one instance of the controller exists throughout the application.
 *    - Applied in: `KwazamController.getInstance()`.
 *
 * 3. **Factory Pattern**:
 *    - The `KwazamPieceFactory` class uses the Factory pattern to create instances of different piece types (e.g., `Ram`, `Biz`, `Sau`, `Tor`, `Xor`).
 *    - Applied in: `KwazamPieceFactory.getPiece()`.
 *
 * 4. **Strategy Pattern**:
 *    - The `MovementStrategy` interface and its implementations (e.g., `RamMovement`, `BizMovement`, `SauMovement`, `TorMovement`, `XorMovement`) use the Strategy pattern to encapsulate different movement behaviors for pieces.
 *    - Applied in: `MovementStrategy` and its concrete implementations.
 *
 * 5. **Mediator Pattern**:
 *    - The `KwazamController` acts as a mediator between the `Model` and `View`. It coordinates communication between the two, ensuring that the model and view remain decoupled.
 *    - Additionally, the `KwazamController` serves as a central connecting point for the handlers (`KwazamMouseHandler`, `KwazamMenuHandler`, and `KwazamWindowHandler`). It manages their interactions and ensures they work together seamlessly.
 *    - Applied in: `KwazamController`, which handles interactions between `KwazamModel`, `KwazamView`, and the handlers.
 */

import controller.KwazamController;
import model.KwazamModel;
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
        KwazamModel model = new KwazamModel();

        // View
        KwazamView view = new KwazamView();

        // Controller
        KwazamController controller = KwazamController.getInstance(view, model);

        // Start the game
        controller.startGame();
    }
}
