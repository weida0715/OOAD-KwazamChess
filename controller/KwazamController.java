package controller;

import model.KwazamBoard;
import model.KwazamGameManager;
import view.KwazamView;

public class KwazamController {
    private static KwazamController instance;
    private final KwazamView view;
    private final KwazamGameManager model;
    
    private KwazamController(KwazamView v, KwazamGameManager m) {
        this.view = v;
        this.model = m;

        view.setController(this);
    }

    public static KwazamController getInstance(KwazamView v, KwazamGameManager m) {
        if (instance == null)
            instance = new KwazamController(v, m);

        return instance;
    }

    public void startGame() {
        // Initialize game
        model.initGame();

        // Update view
        updateView(model.getGameBoard());
    }

    public void updateView(KwazamBoard board) {
        view.getBoardPanel().updateRenderBoard(board.getGameGrid());
    }
}
