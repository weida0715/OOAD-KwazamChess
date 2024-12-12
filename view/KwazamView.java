package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import model.utils.KwazamConstants;

public class KwazamView extends JFrame {
    private final KwazamBoardPanel boardPanel;
    private final KwazamMenuBar menuBar;

    public KwazamView() {
        boardPanel = new KwazamBoardPanel();
        menuBar = new KwazamMenuBar();
    }

    public KwazamBoardPanel getBoardPanel() {
        return boardPanel;
    }

    public KwazamMenuBar getKwazamMenuBar() {
        return menuBar;
    }

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

    public void addChessBoard() {
        add(boardPanel, BorderLayout.CENTER);
    }

    public void addMenuBar() {
        setJMenuBar(menuBar);
    }
}
