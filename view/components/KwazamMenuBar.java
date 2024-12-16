package view.components;

import javax.swing.*;

public class KwazamMenuBar extends JMenuBar {
    private final JMenu menu;
    private final JMenuItem quitOption;

    public KwazamMenuBar() {
        // Create menu and menu items
        menu = new JMenu("Menu");
        quitOption = new JMenuItem("Quit Game");

        // Add menu item to menu
        menu.add(quitOption);

        // Add menu to the menu bar
        this.add(menu);
    }

    public JMenuItem getQuitOption() {
        return quitOption;
    }
}
