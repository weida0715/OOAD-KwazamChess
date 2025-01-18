package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import javax.swing.JOptionPane;

import utils.KwazamPieceColor;
import utils.SoundEffect;
import view.components.KwazamMenuBar;
import view.dialogs.SaveGameDialog;

/**
 * Author(s):
 * 
 * Handles menu actions for the Kwazam game.
 * Manages listeners for menu options like New Game, Restart, Quit, etc.
 */
public class KwazamMenuHandler {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final KwazamController controller;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a KwazamMenuHandler with the given controller.
     * 
     * @param c the controller
     */
    public KwazamMenuHandler(KwazamController c) {
        this.controller = c;
    }

    // =================================================================
    // MENU LISTENERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Initializes listeners for menu options.
     * Sets up actions for New Game, Restart, Quit, Rules, Save Game, and Toggle
     * Sound.
     */
    public void initMenuListeners() {
        KwazamMenuBar menuBar = controller.getView().getKwazamMenuBar();

        // New Game
        menuBar.getNewGameOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });

        // Restart
        menuBar.getRestartOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restartGame();
            }
        });

        // Quit
        menuBar.getQuitOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });

        // Rules
        menuBar.getRulesOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.getView().showRulesDialog();
            }
        });

        // Save Game
        menuBar.getSaveGameOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGame();
            }
        });

        // Toggle Sound
        menuBar.getToggleSoundOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.toggleSound();
            }
        });

        // Toggle Background Music
        menuBar.getToggleBackgroundMusicOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.toggleBackgroundMusic();
            }
        });
    }

    /**
     * Author(s):
     * 
     * Removes all menu listeners.
     * Clears existing listeners to avoid duplication.
     */
    public void removeMenuListeners() {
        KwazamMenuBar menuBar = controller.getView().getKwazamMenuBar();
        for (ActionListener listener : menuBar.getNewGameOption().getActionListeners()) {
            menuBar.getNewGameOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getRestartOption().getActionListeners()) {
            menuBar.getRestartOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getQuitOption().getActionListeners()) {
            menuBar.getQuitOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getRulesOption().getActionListeners()) {
            menuBar.getRulesOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getSaveGameOption().getActionListeners()) {
            menuBar.getSaveGameOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getToggleSoundOption().getActionListeners()) {
            menuBar.getToggleSoundOption().removeActionListener(listener);
        }
        for (ActionListener listener : menuBar.getToggleBackgroundMusicOption().getActionListeners()) {
            menuBar.getToggleBackgroundMusicOption().removeActionListener(listener);
        }
    }

    // =================================================================
    // GAME ACTIONS
    // =================================================================
    /**
     * Author(s):
     * 
     * Starts a new game.
     * Resets the game state, initializes a new game, and prompts for player names.
     */
    private void newGame() {
        // Prompt the user to confirm starting a new game
        boolean confirm = controller.getView().showNewGameDialog();
        if (confirm) {
            // Check if the game has been saved
            if (!controller.getModel().hasSavedGame()) {
                // Prompt the user to save the game before starting a new game
                boolean saveGame = controller.getView().getSaveGameDialog().promptSaveBeforeAction(controller.getView(),
                        "starting a new game");

                if (saveGame) {
                    saveGame(); // Save the game if the user chooses "Yes"
                }
                // If the user chooses "No," proceed without saving
            }

            // Clear the current game state (without deleting the file)
            controller.getModel().resetGame();

            controller.getModel().setCurrentFilename(null);

            // Initialize a new game
            controller.getModel().initGame();
            controller.getView().getBoardPanel().flipBoardToDefault();
            controller.getView().initView();
            controller.initController();
            controller.updateView();

            // Ask for player names via the view
            Optional<String[]> playerNames = controller.getView().showStartGameDialog();

            if (playerNames.isPresent()) {
                // Extract player names
                String player1 = playerNames.get()[0];
                String player2 = playerNames.get()[1];

                // Pass names to the model for game initialization
                controller.getModel().setPlayerNames(player1, player2);
            } else {
                // Exit the game if the dialog is canceled
                System.exit(0);
            }
        }
    }

    /**
     * Author(s):
     * 
     * Restarts the current game.
     * Resets the game state and reinitializes the game with default settings.
     */
    private void restartGame() {
        // Prompt the user to confirm restarting the game
        boolean confirm = controller.getView().showRestartDialog();
        if (confirm) {
            // Clear the current game state (without deleting the file)
            controller.getModel().resetGame();

            // Save the reset game state to the file (if a filename exists)
            if (controller.getModel().getCurrentFilename() != null) {
                controller.getModel().saveGame(null); // Save the reset game state to the current file
            }

            // Reinitialize the game with the default chess setup
            controller.getModel().initGame();

            // Flip the board back to the default orientation (blue at the bottom)
            controller.getView().getBoardPanel().flipBoardToDefault();

            // Update the view to reflect the new game state
            controller.getView().initView();
            controller.initController();
            controller.updateView();
        }
    }

    /**
     * Author(s):
     * 
     * Saves the current game.
     * Prompts for a filename if not already saved, otherwise saves to the existing
     * file.
     */
    public void saveGame() {
        SaveGameDialog saveGameDialog = new SaveGameDialog();

        // If the game doesn't have a filename yet, prompt the user to enter one
        if (controller.getModel().getCurrentFilename() == null) {
            Optional<String> filenameOpt = saveGameDialog.showDialog(controller.getView());

            if (filenameOpt.isPresent()) {
                String filename = filenameOpt.get();
                if (filename != null && !filename.isEmpty()) {
                    controller.getModel().saveGame(filename); // Save the game to the new filename
                    controller.getView().refreshLoadGameMenu();
                } else {
                    JOptionPane.showMessageDialog(controller.getView(), "Filename cannot be empty!", "Invalid Filename",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        } else {
            // If the game already has a filename, save to that file without prompting
            controller.getModel().saveGame(null); // Pass null to use the current filename
            controller.getView().refreshLoadGameMenu(); // Refresh the Load Game submenu

        }
    }

    /**
     * Author(s):
     * 
     * Loads a saved game.
     * 
     * @param filename the name of the file to load
     */
    public void loadGame(String filename) {
        // Check if the game is already running and prompt to save before loading
        if (controller.getModel().isRunning()) {
            if (!controller.getModel().hasSavedGame()) {
                // Prompt the user to save the game before restarting
                boolean saveGame = controller.getView().getSaveGameDialog().promptSaveBeforeAction(controller.getView(),
                        "loading a game");

                if (saveGame) {
                    saveGame(); // Save the game if the user chooses "Yes"
                }
                // If the user chooses "No," proceed without saving
            }
        }

        // Load the game from the specified file
        controller.getModel().loadGame(filename);
        controller.getView().getBoardPanel().flipBoardToDefault();
        controller.getView().initView();
        controller.initController();

        // Update the view to reflect the loaded game state
        controller.updateView();

        // Flip the board if necessary
        if (controller.getModel().getCurrentColor() == KwazamPieceColor.RED) {
            controller.getView().getBoardPanel().flipBoard();
        }
    }

    /**
     * Author(s):
     * 
     * Quits the game.
     * Prompts for confirmation before exiting the application.
     */
    private void quitGame() {
        boolean confirmQuit = controller.getView().showQuitDialog();
        if (confirmQuit) {
            // Check if the game has been saved
            if (!controller.getModel().hasSavedGame()) {
                // Prompt the user to save the game before restarting
                boolean saveGame = controller.getView().getSaveGameDialog().promptSaveBeforeAction(controller.getView(),
                        "quit");

                if (saveGame) {
                    saveGame(); // Save the game if the user chooses "Yes"
                }
                // If the user chooses "No," proceed without saving
            }

            System.exit(0);
        }
    }
}