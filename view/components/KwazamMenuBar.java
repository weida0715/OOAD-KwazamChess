package view.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

import javax.swing.*;

import controller.KwazamController;
import utils.SoundEffect;

/**
 * Author(s):
 * 
 * Represents the menu bar for the Kwazam game.
 * Contains options for new game, restart, save, quit, rules, and sound
 * settings.
 */
public class KwazamMenuBar extends JMenuBar {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final JMenu menu;
    private final JMenu help;
    private final JMenu settings;
    private final JMenuItem newGameOption;
    private final JMenuItem restartOption;
    private final JMenuItem saveGameOption;
    private final JMenu loadGameMenu;
    private final JMenuItem quitOption;
    private final JMenuItem toggleSoundOption;
    private final JMenuItem toggleBackgroundMusicOption;
    private final JMenuItem rulesOption;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s):
     * 
     * Constructs a KwazamMenuBar.
     * Initializes menu items and adds them to the menu bar.
     */
    public KwazamMenuBar() {
        // Create menu and menu items
        menu = new JMenu("Menu");
        newGameOption = new JMenuItem("New Game");
        restartOption = new JMenuItem("Restart");
        saveGameOption = new JMenuItem("Save Game");
        loadGameMenu = new JMenu("Load Game");
        quitOption = new JMenuItem("Quit Game");

        help = new JMenu("Help");
        rulesOption = new JMenuItem("Game Rules");

        settings = new JMenu("Settings");
        toggleSoundOption = new JCheckBoxMenuItem("Toggle Sound");
        toggleBackgroundMusicOption = new JCheckBoxMenuItem("Toggle Background Music");

        toggleSoundOption.setSelected(true);
        toggleBackgroundMusicOption.setSelected(true);

        toggleBackgroundMusicOption.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundEffect.toggleBackgroundMusic(); // Toggle background music on/off
            }
        });

        // Add menu items to menu in the desired order
        menu.add(newGameOption);
        menu.addSeparator();
        menu.add(restartOption);
        menu.addSeparator();
        menu.add(saveGameOption);
        menu.addSeparator();
        menu.add(loadGameMenu);
        menu.addSeparator();
        menu.add(quitOption); // Add "Quit Game" first

        settings.add(toggleSoundOption);
        settings.add(toggleBackgroundMusicOption);

        help.add(rulesOption); // Add "Game Rules" below "Quit Game"

        // Add menu to the menu bar
        this.add(menu);
        this.add(settings);
        this.add(help);
    }

    // =================================================================
    // GETTERS
    // =================================================================
    /**
     * Author(s):
     * 
     * Gets the "New Game" menu item.
     * 
     * @return the "New Game" menu item
     */
    public JMenuItem getNewGameOption() {
        return newGameOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Restart" menu item.
     * 
     * @return the "Restart" menu item
     */
    public JMenuItem getRestartOption() {
        return restartOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Save Game" menu item.
     * 
     * @return the "Save Game" menu item
     */
    public JMenuItem getSaveGameOption() {
        return saveGameOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Quit Game" menu item.
     * 
     * @return the "Quit Game" menu item
     */
    public JMenuItem getQuitOption() {
        return quitOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Game Rules" menu item.
     * 
     * @return the "Game Rules" menu item
     */
    public JMenuItem getRulesOption() {
        return rulesOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Toggle Sound" menu item.
     * 
     * @return the "Toggle Sound" menu item
     */
    public JMenuItem getToggleSoundOption() {
        return toggleSoundOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Toggle Background Music" menu item.
     * 
     * @return the "Toggle Background Music" menu item
     */
    public JMenuItem getToggleBackgroundMusicOption() {
        return toggleBackgroundMusicOption;
    }

    /**
     * Author(s):
     * 
     * Gets the "Load Game" submenu.
     * 
     * @return the "Load Game" submenu
     */
    public JMenu getLoadGameMenu() {
        return loadGameMenu;
    }

    // =================================================================
    // MENU POPULATION
    // =================================================================
    /**
     * Author(s):
     * 
     * Populates the "Load Game" submenu with saved game files.
     */
    public void populateLoadGameMenu() {
        File dataFolder = new File("data");
        if (!dataFolder.exists()) {
            dataFolder.mkdir(); // Create the /data folder if it doesn't exist
        }

        // Get all files in the /data folder
        File[] savedGames = dataFolder.listFiles();
        if (savedGames != null) {
            Arrays.sort(savedGames); // Sort files by name

            // Clear existing items in the load game submenu
            loadGameMenu.removeAll();

            // Add each saved game as a menu item
            for (File file : savedGames) {
                if (file.isFile()) {
                    JMenuItem gameItem = new JMenuItem(file.getName());
                    gameItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            // Handle loading the selected game
                            String filename = file.getName();
                            // Call the controller to load the game
                            KwazamController.getInstance(null, null).loadGame(filename);
                        }
                    });
                    loadGameMenu.add(gameItem);
                }
            }
        }
    }
}