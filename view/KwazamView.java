package view;

import controller.KwazamController;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import model.utils.KwazamConstants;

public class KwazamView extends JFrame {
    KwazamController controller;
    KwazamBoardPanel boardPanel;
    
    public KwazamView() {
        setTitle(KwazamConstants.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Add Chess Board
        boardPanel = new KwazamBoardPanel();
        add(boardPanel, BorderLayout.CENTER);

        // Add Menu Bar

        pack();
		setMinimumSize(new Dimension(KwazamConstants.WINDOW_WIDTH, KwazamConstants.WINDOW_HEIGHT));
		setSize(KwazamConstants.WINDOW_WIDTH, KwazamConstants.WINDOW_HEIGHT);
		setLocationRelativeTo(null);
		setVisible(true);
    }

    public void setController(KwazamController c) {
        this.controller = c;
    }

    public KwazamBoardPanel getBoardPanel() {
        return boardPanel;
    }
}
