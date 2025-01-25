package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import javax.swing.JFrame;
import utils.KwazamConstants;
import view.components.KwazamMenuBar;
import view.dialogs.EndGameDialog;
import view.dialogs.NewGameDialog;
import view.dialogs.PostGameDialog;
import view.dialogs.QuitGameDialog;
import view.dialogs.RestartGameDialog;
import view.dialogs.RulesDialog;
import view.dialogs.SaveGameDialog;
import view.dialogs.StartGameDialog;
import view.panels.KwazamBoardPanel;

/**
 * Represents the main view for the Kwazam game.
 * Manages the game window, board, and dialogs.
 */
public class KwazamView extends JFrame {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final KwazamBoardPanel boardPanel;
    private final KwazamMenuBar menuBar;
    private final QuitGameDialog quitGameDialog;
    private final StartGameDialog startGameDialog;
    private final EndGameDialog endGameDialog;
    private final RestartGameDialog restartGameDialog;
    private final NewGameDialog newGameDialog;
    private final RulesDialog rulesDialog;
    private final SaveGameDialog saveGameDialog;
    private final PostGameDialog postGameDialog;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s): Ng Wei Da, Willie Teoh Chin Wei, Lam Rong Yi
     * 
     * Constructs a KwazamView.
     * Initializes the board, menu bar, and dialogs.
     */
    public KwazamView() {
        boardPanel = new KwazamBoardPanel();
        menuBar = new KwazamMenuBar();
        quitGameDialog = new QuitGameDialog();
        startGameDialog = new StartGameDialog();
        endGameDialog = new EndGameDialog();
        restartGameDialog = new RestartGameDialog();
        newGameDialog = new NewGameDialog();
        saveGameDialog = new SaveGameDialog();
        rulesDialog = new RulesDialog();
        postGameDialog = new PostGameDialog();
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the board panel.
     * 
     * @return the board panel
     */
    public KwazamBoardPanel getBoardPanel() {
        return boardPanel;
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Gets the menu bar.
     * 
     * @return the menu bar
     */
    public KwazamMenuBar getKwazamMenuBar() {
        return menuBar;
    }

    // =================================================================
    // PUBLIC METHODS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Initializes the game view.
     * Sets up the window, board, and menu bar.
     */
    public void initView() {
        setTitle(KwazamConstants.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addChessBoard();
        addMenuBar();

        pack();
        setMinimumSize(new Dimension(KwazamConstants.WINDOW_WIDTH, KwazamConstants.WINDOW_HEIGHT));
        setSize(KwazamConstants.WINDOW_WIDTH, KwazamConstants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // =================================================================
    // BOARD
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Adds the chess board to the view.
     */
    public void addChessBoard() {
        add(boardPanel, BorderLayout.CENTER);
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Adds the menu bar to the view.
     */
    public void showValidMoves(List<int[]> validMoves) {
        boardPanel.setAvailableMoves(validMoves);
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Hides the valid moves on the board.
     */
    public void hideValidMoves() {
        boardPanel.clearAvailableMoves();
    }

    // =================================================================
    // MENU
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Adds the menu bar to the view.
     */
    public void addMenuBar() {
        setJMenuBar(menuBar);
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Refreshes the "Load Game" submenu with saved game files.
     */
    public void refreshLoadGameMenu() {
        menuBar.populateLoadGameMenu();
    }

    // =================================================================
    // DIALOGS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Shows the start game dialog to input player names.
     * 
     * @return an Optional containing player names, or empty if canceled
     */
    public Optional<String[]> showStartGameDialog() {
        return startGameDialog.showDialog(this);
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Shows the end game dialog with the winner's name.
     * 
     * @param winner the name of the winner
     */
    public void showEndGameDialog(String winner) {
        endGameDialog.showDialog(this, winner);
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Shows the new game confirmation dialog.
     * 
     * @return true if the user confirms, false otherwise
     */
    public boolean showNewGameDialog() {
        return newGameDialog.showDialog(this);
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Shows the restart game confirmation dialog.
     * 
     * @return true if the user confirms, false otherwise
     */
    public boolean showRestartDialog() {
        return restartGameDialog.showDialog(this);
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Gets the save game dialog.
     * 
     * @return the save game dialog
     */
    public SaveGameDialog getSaveGameDialog() {
        return saveGameDialog;
    }

    /**
     * Author(s): Lam Rong Yi
     * 
     * Shows the quit game confirmation dialog.
     * 
     * @return true if the user confirms, false otherwise
     */
    public boolean showQuitDialog() {
        return quitGameDialog.showDialog(this);
    }

    /**
     * Author(s): Willie Teoh Chin Wei
     * 
     * Shows the game rules dialog.
     */
    public void showRulesDialog() {
        rulesDialog.showDialog(this);
    }

    /**
     * Author(s): Lam Rong Yi
    * 
    * Shows the post game dialog.
    * 
    * @return the user's choice as integer.
    */
    public int showPostGameDialog() {
        return postGameDialog.showDialog(this);
    }
}
