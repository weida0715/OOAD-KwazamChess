
/**
 * CCP2201 Project
 * Trimester 2310
 * by Group F
 * 
 * Team Leader: 
 *  - Ng Wei Da, 0165297743, 1211107034@student.mmu.edu.my
 * Team members:
 *  - Lim Kar Joon, 01136118955, 1211108893@student.mmu.edu.my
 *  - Willie Teoh Chin Wei, 0102020873, 1211106712@student.mmu.edu.my
 *  - Lam Rong Yi, 0176952983, 1211107112@student.mmu.edu.my
 * 
 * Task Distributions:
 *  - Ng Wei Da: Project structure initializations, design patterns decision, 
 *              involvement in implementing KwazamController, KwazamMouseHandler, KwazamMenuHandler,
 *                                          KwazamBoard, KwazamPiece, KwazamModel, 
 *                                          KwazamMenuBar, KwazamRenderPiece, StartGameDialog, QuitGameDialog, KwazamBoardPanel, KwazamView, 
 *                                          KwazamConstants, SoundEffect
 * 
 *  - Lim Kar Joon: Code refactoring
 *              involvement in implementing KwazamController, KwazamMouseHandler, 
 *                                          BizMovement, RamMovement, SauMovement, TorMovement, XorMovement, KwazamPiece, Biz, Ram, Sau, Tor, Xor
 *                                          KwazamBoardPanel
 *                                          KwazamConstants, KwazamPieceColor, KwazamPieceType
 * 
 *  - Willie Teoh Chin Wei: Extra Features
 *              involvement in implementing KwazamMenuHandler, KwazamMouseHandler, 
 *                                          KwazamBoard, KwazamPiece
 *                                          KwazamMenuBar, RulesDialog, KwazamBoardPanel, KwazamView
 *                                          KwazamConstants, SoundEffect
 * 
 *  - Lam Rong Yi: Core Features Extensions
 *              involvement in implementing KwazamController, KwazamMouseHandler, KwazamMenuHandler, KwazamWindowHandler,
 *                                          KwazamModel, 
 *                                          KwazamMenuBar, EndGameDialog, NewGameDialog, PostGameDialog, RestartGameDialog, SaveGameDialog, KwazamRenderPiece, KwazamView
 */

import controller.KwazamController;
import model.KwazamModel;
import view.KwazamView;

/**
 * Main class to start the Kwazam game.
 * Initializes the model, view, and controller.
 */
public class Main {
    /**
     * Author(s): Ng Wei Da, Lim Kar Joon, Willie Teoh Chin Wei, Lam Rong Yi
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
