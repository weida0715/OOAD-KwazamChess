package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import java.util.Optional;
import javax.swing.JFrame;
import utils.KwazamConstants;
import view.components.KwazamMenuBar;
import view.dialogs.EndGameDialog;
import view.dialogs.QuitGameDialog;
import view.dialogs.StartGameDialog;
import view.panels.KwazamBoardPanel;

public class KwazamView extends JFrame {
    private final KwazamBoardPanel boardPanel;
    private final KwazamMenuBar menuBar;
    private final QuitGameDialog quitGameDialog;
    private final StartGameDialog startGameDialog;
    private final EndGameDialog endGameDialog;

    public KwazamView() {
        boardPanel = new KwazamBoardPanel();
        menuBar = new KwazamMenuBar();
        quitGameDialog = new QuitGameDialog();
        startGameDialog = new StartGameDialog();
        endGameDialog = new EndGameDialog();
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
        return startGameDialog.showDialog(this);
    }

    public void showEndGameDialog(String winner) {
        endGameDialog.showDialog(this, winner);
    }
}
