package controller;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.WindowConstants;

/**
 * Author(s):
 * 
 * Handles window actions for the Kwazam game.
 * Manages window events like closing the application.
 */
public class KwazamWindowHandler {
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
     * Constructs a KwazamWindowHandler with the given controller.
     * 
     * @param c the controller
     */
    public KwazamWindowHandler(KwazamController c) {
        this.controller = c;
    }

    // =================================================================
    // WINDOW LISTENERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Initializes window listeners.
     * Sets up actions for window closing events.
     */
    public void initWindowListeners() {
        controller.getView().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (controller.getView().showQuitDialog()) {
                    // Check if the game has been saved
                    if (!controller.getModel().hasSavedGame()) {
                        // Prompt the user to save the game before restarting
                        boolean saveGame = controller.getView().getSaveGameDialog()
                                .promptSaveBeforeAction(controller.getView(), "quit");

                        if (saveGame) {
                            controller.getMenuHandler().saveGame(); // Save the game if the user chooses "Yes"
                        }
                        // If the user chooses "No," proceed without saving
                    }

                    // If the user confirms quitting, exit the application
                    System.exit(0);
                } else {
                    // If the user refuses to quit, cancel the window closing operation
                    controller.getView().setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                }
            }
        });
    }

    /**
     * Author(s):
     * 
     * Removes all window listeners.
     * Clears existing listeners to avoid duplication.
     */
    public void removeWindowListeners() {
        for (WindowListener listener : controller.getView().getWindowListeners()) {
            controller.getView().removeWindowListener(listener);
        }
    }
}