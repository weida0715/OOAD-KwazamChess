package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import javax.swing.JFrame;
import utils.KwazamConstants;

public class KwazamView extends JFrame {
    private final KwazamBoardPanel boardPanel;
    private final KwazamMenuBar menuBar;
    private final QuitGameDialog quitGameDialog;
    private final StartGameDialog startGameDialog;

    public KwazamView() {
        boardPanel = new KwazamBoardPanel();
        menuBar = new KwazamMenuBar();
        quitGameDialog = new QuitGameDialog();
        startGameDialog = new StartGameDialog();
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

    public void showValidMoves(List<int[]> validMoves) {
        boardPanel.setAvailableMoves(validMoves);
    }

    public void hideValidMoves() {
        boardPanel.clearAvailableMoves();
    }

    public boolean showQuitDialog() {
        return quitGameDialog.showDialog(this);
    }

    public Optional<String[]> showStartGameDialog() {
        return startGameDialog.show(this);
    }
}
