package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import model.KwazamModel;
import model.pieces.KwazamPiece;
import model.pieces.Ram;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;
import utils.SoundEffect;
import view.KwazamView;
import view.components.KwazamRenderPiece;

/**
 * Controller class for the Kwazam game.
 * Acts as a mediator between handlers, model, and view.
 */
public class KwazamController {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    // MVC variables
    private static KwazamController instance;
    private final KwazamView view;
    private final KwazamModel model;

    // Game Handlers
    private final KwazamMouseHandler mouseHandler;
    private final KwazamMenuHandler menuHandler;
    private final KwazamWindowHandler windowHandler;

    private KwazamPiece selectedPiece;
    private KwazamPiece draggedPiece;
    private int pressX, pressY;
    private boolean dragging = false;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Construct a Singleton KwazamController class
     * 
     * @param v the view
     * @param m the model
     */
    private KwazamController(KwazamView v, KwazamModel m) {
        this.view = v;
        this.model = m;
        this.mouseHandler = new KwazamMouseHandler(this);
        this.menuHandler = new KwazamMenuHandler(this);
        this.windowHandler = new KwazamWindowHandler(this);
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for view variable
     * 
     * @return view variable
     */
    public KwazamView getView() {
        return view;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for model variable
     * 
     * @return model variable
     */
    public KwazamModel getModel() {
        return model;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the mouse handler.
     * 
     * @return the mouse handler
     */
    public KwazamMouseHandler getMouseHandler() {
        return mouseHandler;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the menu handler.
     * 
     * @return the menu handler
     */
    public KwazamMenuHandler getMenuHandler() {
        return menuHandler;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the window handler.
     * 
     * @return the window handler
     */
    public KwazamWindowHandler getWindowHandler() {
        return windowHandler;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for selectedPiece variable
     * 
     * @return selectedPiece variable
     */
    public KwazamPiece getSelectedPiece() {
        return selectedPiece;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for draggedPiece variable
     * 
     * @return draggedPiece variable
     */
    public KwazamPiece getDraggedPiece() {
        return draggedPiece;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for pressX variable
     * 
     * @return pressX variable
     */
    public int getPressedX() {
        return pressX;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for pressY variable
     * 
     * @return pressY variable
     */
    public int getPressedY() {
        return pressY;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Getter for dragging variable
     * 
     * @return dragging variable
     */
    public boolean isDragging() {
        return dragging;
    }

    // =================================================================
    // SETTERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Setter for selectedPiece variable
     */
    public void setSelectedPiece(KwazamPiece piece) {
        this.selectedPiece = piece;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Setter for draggedPiece variable
     */
    public void setDraggedPiece(KwazamPiece piece) {
        this.draggedPiece = piece;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Setter for pressX variable
     */
    public void setPressedX(int x) {
        pressX = x;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Setter for pressY variable
     */
    public void setPressedY(int y) {
        pressY = y;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Setter for dragging variable
     */
    public void setIsDragging(boolean dragging) {
        this.dragging = dragging;
    }

    // =================================================================
    // SINGLETON INSTANCE
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Static method to get instance of KwazamController
     * If instance is null, create a new instance of KwazamController
     * If instance is not null, return existing instance of KwazamController
     * 
     * @param v the view
     * @param m the model
     * @return KwazamController instance
     */
    public static KwazamController getInstance(KwazamView v, KwazamModel m) {
        if (instance == null) {
            instance = new KwazamController(v, m);
        }
        return instance;
    }

    // =================================================================
    // CONTROLLER INITIALIZATION
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Initializes all listeners (handlers)
     * First removes all existing listeners, to avoid duplication, then initialized
     * one-by-one
     */
    public void initController() {
        // Remove existing listeners
        mouseHandler.removeMouseListeners();
        menuHandler.removeMenuListeners();
        windowHandler.removeWindowListeners();

        // Initialize new listeners
        mouseHandler.initMouseListeners();
        menuHandler.initMenuListeners();
        windowHandler.initWindowListeners();
    }

    // =================================================================
    // GAME INITIALIZATION
    // =================================================================
    /**
     * Author(s): Ng Wei Da, Lam Rong Yi
     * 
     * Method used to start the Kwazam Chess game
     * Initializes model, view and controller
     */
    public void startGame() {
        // Check for saved game and load it if found
        if (model.hasSavedGame()) {
            model.loadGame(model.getCurrentFilename());
            view.initView();
            initController();

            if (model.getCurrentColor() == KwazamPieceColor.RED)
                view.getBoardPanel().flipBoard();

            updateView();
        } else {
            // Initialize a new game if no saved game exists
            model.initGame();
            view.initView();
            initController();
            updateView();

            // Ask for player names via the view
            Optional<String[]> playerNames = view.showStartGameDialog();

            if (playerNames.isPresent()) {
                // Extract player names
                String player1 = playerNames.get()[0];
                String player2 = playerNames.get()[1];

                // Pass names to the model for game initialization
                model.setPlayerNames(player1, player2);
            } else {
                // Exit the game if the dialog is canceled
                System.exit(0);
            }
        }

        SoundEffect.playBackgroundMusic();
        view.refreshLoadGameMenu();
    }

    // =================================================================
    // GAME LOADING
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Loads a saved game.
     * 
     * @param filename the name of the file to load
     */
    public void loadGame(String filename) {
        menuHandler.loadGame(filename);
    }

    // =================================================================
    // VIEW UPDATES
    // =================================================================
    /**
     * Author(s): Ng Wei Da, Lim Kar Joon
     * 
     * Update the view
     */
    public void updateView() {
        if (isDragging())
            return;

        // Convert Pieces to PieceData for the view
        List<KwazamRenderPiece> pieceDataList = new ArrayList<>();
        for (KwazamPiece piece : model.getGameBoard().getPieces()) {
            KwazamRenderPiece newRenderPiece = new KwazamRenderPiece(
                    piece.getColor().name().substring(0, 1) + "_" + piece.getType(),
                    piece.getX(), piece.getY());

            if (piece.getType() == KwazamPieceType.RAM) {
                Ram ramPiece = (Ram) piece;
                if ((ramPiece.getColor() == KwazamPieceColor.RED && ramPiece.getDirection() == -1) ||
                        (ramPiece.getColor() == KwazamPieceColor.BLUE && ramPiece.getDirection() == 1)) {
                    newRenderPiece.flip();
                }
            }

            if (piece.getColor() != model.getCurrentColor())
                newRenderPiece.flip();

            pieceDataList.add(newRenderPiece);
        }

        view.getBoardPanel().setRenderPieces(pieceDataList);

        // Deselect the piece after moving or dragging is completed
        KwazamRenderPiece selectedPieceData = selectedPiece != null
                ? new KwazamRenderPiece(selectedPiece.getColor().name().substring(0, 1) + "_" + selectedPiece.getType(),
                        selectedPiece.getX(), selectedPiece.getY())
                : null;
        view.getBoardPanel().setSelectedPiece(selectedPieceData);

        view.getBoardPanel().clearDraggingPiece(); // Reset dragging visuals

        // Check if the current player's Sau is in check
        if (model.isSauInCheck(model.getCurrentColor())) {
            KwazamPiece sau = model.getSau(model.getCurrentColor());
            if (sau != null) {
                view.getBoardPanel().setSauInCheck(sau.getX(), sau.getY()); // Highlight the Sau's position
            }
        } else {
            view.getBoardPanel().clearSauInCheck(); // Clear the highlight if the Sau is not in check
        }
    }
}
