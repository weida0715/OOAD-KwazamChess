package view.dialogs;

import javax.swing.*;

public class QuitGameDialog {
    public boolean showDialog(JFrame parent) {
        int response = JOptionPane.showConfirmDialog(
                parent,
                "Are you sure you want to quit?",
                "Quit Game",
                JOptionPane.YES_NO_OPTION);

        return response == JOptionPane.YES_OPTION;
    }
}
