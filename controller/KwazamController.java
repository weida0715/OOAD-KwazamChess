package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import model.KwazamGameManager;
import utils.KwazamConstants;
import view.KwazamPiecePanel;
import view.KwazamView;

public class KwazamController implements Runnable {
    private static KwazamController instance;
    private final KwazamView view;
    private final KwazamGameManager model;
    private Thread gameThread;
    private KwazamMouse mouse;
    private KwazamPiecePanel holdingPiece;

    private int gameTurn = 1; // Start with turn 1
    private boolean piecesRendered = false; // Track if pieces have been rendered for this turn

    private KwazamController(KwazamView v, KwazamGameManager m) {
        this.view = v;
        this.model = m;
    }

    public static KwazamController getInstance(KwazamView v, KwazamGameManager m) {
        if (instance == null)
            instance = new KwazamController(v, m);

        return instance;
    }

    public void startGame() {
        // Initialize the view
        view.initView();

        // Initialize game
        model.initGame();

        // Initialize controller
        initController();

        // Start game loop
        startGameLoop();
    }

    private void initController() {
        // Add mouse listeners here, in the controller
        mouse = new KwazamMouse();
        view.getBoardPanel().addMouseListener(mouse);
        view.getBoardPanel().addMouseMotionListener(mouse);
    }

    public void updateView() {
        // Render the board only once per turn
        if (!piecesRendered) {
            view.getBoardPanel().renderBoard(model.getGameState());
            piecesRendered = true; // Mark pieces as rendered for this turn
        }

        // Simulate move if mouse is pressed and holding a piece
        if (mouse.pressed && holdingPiece != null) {
            simulateMove();
        }

        view.repaint();
    }

    public void simulateMove() {
        // Get the current mouse position
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();

        // Update the piece's screen position (make it follow the mouse)
        holdingPiece.setX(mouseX - mouse.getDragOffsetX());
        holdingPiece.setY(mouseY - mouse.getDragOffsetY());

        // Repaint to reflect the new position
        view.repaint();
    }

    public void incrementTurn() {
        gameTurn++; // Increment turn
        piecesRendered = false; // Reset the render flag to allow rendering again

        System.out.println("Turn " + gameTurn + " started.");
    }

    // Method to end the current turn (optional, for other game logic)
    public void endTurn() {
        // If any additional logic is needed for ending a turn
    }

    public void quitGame() {
        model.stopGame();
        System.exit(0);
    }

    @Override
    public void run() {
        // Main game loop
        while (model.isRunning()) {
            updateView();
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    // Mouse event handler class (for dragging and snapping)
    public class KwazamMouse extends MouseAdapter {
        public int x, y;
        public boolean pressed;
        private int dragOffsetX, dragOffsetY;  // Offset to follow the mouse

        @Override
        public void mousePressed(MouseEvent e) {
            pressed = true;

            // Try to find a piece to hold
            for (int row = 0; row < KwazamConstants.BOARD_ROWS; row++) {
                for (int col = 0; col < KwazamConstants.BOARD_COLS; col++) {
                    KwazamPiecePanel currPiece = view.getBoardPanel().getPiecePanels()[row][col];
                    if (currPiece != null && currPiece.getPieceColor() == model.getCurrentColor()
                            && isMouseOnPiece(e, currPiece)) {
                        holdingPiece = currPiece;

                        // Calculate drag offset to keep piece aligned with mouse
                        dragOffsetX = e.getX() - currPiece.getX();
                        dragOffsetY = e.getY() - currPiece.getY();

                        break;  // Exit loop once we find the piece
                    }
                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;

            // Snap the piece to the nearest grid position
            if (holdingPiece != null) {
                holdingPiece.snapToGrid(view.getBoardPanel().getSquareSize());
                // Update the piece's row and column based on the snapped position
                int targetCol = (e.getX() - view.getBoardPanel().getXOffset()) / view.getBoardPanel().getSquareSize();
                int targetRow = (e.getY() - view.getBoardPanel().getYOffset()) / view.getBoardPanel().getSquareSize();

                holdingPiece.setCol(targetCol);
                holdingPiece.setRow(targetRow);
                view.repaint();
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (holdingPiece != null) {
                // Update piece position while dragging
                holdingPiece.setX(e.getX() - dragOffsetX);
                holdingPiece.setY(e.getY() - dragOffsetY);
                view.repaint();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            x = e.getX();
            y = e.getY();
        }

        // Utility method to check if mouse is over a piece
        private boolean isMouseOnPiece(MouseEvent e, KwazamPiecePanel piece) {
            int squareSize = view.getBoardPanel().getSquareSize();
            int offsetX = view.getBoardPanel().getXOffset();
            int offsetY = view.getBoardPanel().getYOffset();

            int pieceX = piece.getCol() * squareSize + offsetX;
            int pieceY = piece.getRow() * squareSize + offsetY;

            return (e.getX() >= pieceX && e.getX() <= pieceX + squareSize) &&
                   (e.getY() >= pieceY && e.getY() <= pieceY + squareSize);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public boolean isPressed() {
            return pressed;
        }

        public int getDragOffsetX() {
            return dragOffsetX;
        }

        public int getDragOffsetY() {
            return dragOffsetY;
        }
    }
}
