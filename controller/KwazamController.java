package controller;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.KwazamGameManager;
import model.pieces.KwazamPiece;
import model.pieces.Ram;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;
import utils.SoundEffect;
import view.KwazamView;
import view.components.KwazamRenderPiece;

public class KwazamController {
    private static KwazamController instance;
    private final KwazamView view;
    private final KwazamGameManager model;
    private KwazamPiece selectedPiece;
    private KwazamPiece draggedPiece;
    private int pressX, pressY; // Mouse press coordinates
    private boolean isDragging = false;

    private KwazamController(KwazamView v, KwazamGameManager m) {
        this.view = v;
        this.model = m;
    }

    public static KwazamController getInstance(KwazamView v, KwazamGameManager m) {
        if (instance == null) {
            instance = new KwazamController(v, m);
        }
        return instance;
    }

    public void startGame() {
        // Check for saved game and load it if found
        if (model.hasSavedGame()) {
            model.loadGame();
            view.initView();
            initController();

            if (model.getCurrentColor() == KwazamPieceColor.RED)
                view.getBoardPanel().flipBoard();

            updateView();
        } else {
            // Initialize a new game if no saved game exists
            model.initGame();
            view.initView();
            initController();
            updateView();

            // Ask for player names via the view
            Optional<String[]> playerNames = view.showStartGameDialog();

            if (playerNames.isPresent()) {
                // Extract player names
                String player1 = playerNames.get()[0];
                String player2 = playerNames.get()[1];

                // Pass names to the model for game initialization
                model.setPlayerNames(player1, player2);
            } else {
                // Exit the game if the dialog is canceled
                System.exit(0);
            }
        }
    }

    public void initController() {
        initGameListeners();
        initMenuListeners();
        initWindowListeners();
    }

    public void initGameListeners() {
        // MouseListener for click and drag actions
        view.getBoardPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int gridX = (e.getX() - view.getBoardPanel().getXOffset()) / view.getBoardPanel().getSquareSize();
                int gridY = (e.getY() - view.getBoardPanel().getYOffset()) / view.getBoardPanel().getSquareSize();

                // Flip the coordinates if the board is flipped
                if (view.getBoardPanel().isBoardFlipped()) {
                    gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
                    gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
                }

                // Record the press position to detect a click later
                pressX = e.getX();
                pressY = e.getY();

                draggedPiece = model.getGameBoard().getPiece(gridX, gridY);

                if (draggedPiece != null) {
                    // Prevent interaction if the piece is not the current player's
                    if (draggedPiece.getColor() != model.getCurrentColor()) {
                        draggedPiece = null; // Reset dragged piece if it's an opponent's piece
                        return; // Don't allow further interaction
                    }

                    // Fetch and display valid moves for the dragged piece
                    List<int[]> validMoves = draggedPiece.getValidMoves(model.getGameBoard());
                    view.showValidMoves(validMoves);

                    view.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); // Change to grabbing cursor
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                boolean moved = false;

                int releaseX = e.getX();
                int releaseY = e.getY();

                int tileSize = view.getBoardPanel().getSquareSize();
                int gridX = (releaseX - view.getBoardPanel().getXOffset()) / tileSize;
                int gridY = (releaseY - view.getBoardPanel().getYOffset()) / tileSize;

                // Flip the coordinates if the board is flipped
                if (view.getBoardPanel().isBoardFlipped()) {
                    gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
                    gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
                }

                if (isDragging && draggedPiece != null) {
                    // Handle drag-and-drop release
                    if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                            && gridY < KwazamConstants.BOARD_ROWS) {
                        KwazamPiece targetPiece = model.getGameBoard().getPiece(gridX, gridY);

                        if (model.movePiece(draggedPiece, gridX, gridY)) {
                            if (targetPiece != null) {
                                // Play capture sound if a piece exists on the target square
                                SoundEffect.playCaptureSound();
                            } else {
                                // Play move sound if the square is empty
                                SoundEffect.playMoveSound();
                            }
                            model.printGameState();
                            moved = true;
                        }
                        view.hideValidMoves();
                    }

                    // Deselect piece after dragging is done
                    selectedPiece = null;
                    isDragging = false;
                    draggedPiece = null;
                    view.setCursor(Cursor.getDefaultCursor()); // Restore cursor after drag
                } else if (!isDragging) {
                    // Handle click-to-move
                    if (selectedPiece == null) {
                        // Select piece
                        selectedPiece = model.getGameBoard().getPiece(gridX, gridY);

                        if (selectedPiece != null) {
                            // Prevent selecting opponent's piece
                            if (selectedPiece.getColor() != model.getCurrentColor()) {
                                selectedPiece = null; // Reset if opponent's piece is clicked
                                return;
                            }

                            // Fetch and display valid moves for the selected piece
                            List<int[]> validMoves = selectedPiece.getValidMoves(model.getGameBoard());
                            view.showValidMoves(validMoves);
                        }
                    } else {
                        KwazamPiece targetPiece = model.getGameBoard().getPiece(gridX, gridY);

                        if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                                && gridY < KwazamConstants.BOARD_ROWS) {
                            if (model.movePiece(selectedPiece, gridX, gridY)) {
                                if (targetPiece != null) {
                                    SoundEffect.playCaptureSound();
                                } else {
                                    SoundEffect.playMoveSound();
                                }
                                model.printGameState();
                                moved = true;
                            }
                            view.hideValidMoves();
                        }
                        selectedPiece = null; // Deselect after moving
                    }

                    view.setCursor(Cursor.getDefaultCursor()); // Restore cursor
                }

                updateView();

                if (moved) {
                    if (model.isWinnerFound()) {
                        view.showEndGameDialog(model.getWinner());
                        model.clearSavedGame();

                        quitGame();
                    }

                    view.getBoardPanel().flipBoard();
                    model.switchColor();
                    updateView();

                    model.saveGame();
                }
            }

        });

        // MouseMotionListener for drag movement
        view.getBoardPanel().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    // Prevent dragging opponent's piece
                    if (draggedPiece.getColor() != model.getCurrentColor()) {
                        draggedPiece = null;
                        return; // Don't allow dragging if it's an opponent's piece
                    }

                    // Check if the movement is significant enough to qualify as a drag
                    if (Math.abs(e.getX() - pressX) > 5 || Math.abs(e.getY() - pressY) > 5) {
                        isDragging = true;
                    }

                    // Create a KwazamRenderPiece for the dragged piece, considering flipping
                    KwazamRenderPiece draggedPieceData = new KwazamRenderPiece(
                            draggedPiece.getColor().name().substring(0, 1) + "_" + draggedPiece.getType(),
                            draggedPiece.getX(),
                            draggedPiece.getY());

                    // Flip the dragged piece if necessary
                    if (draggedPiece.getType() == KwazamPieceType.RAM) {
                        Ram ramPiece = (Ram) draggedPiece;

                        // Only flip if the direction hasn't already been set to the opposite of the
                        // piece's current direction
                        if ((ramPiece.getColor() == KwazamPieceColor.RED && ramPiece.getDirection() == -1) ||
                                (ramPiece.getColor() == KwazamPieceColor.BLUE && ramPiece.getDirection() == 1)) {
                            draggedPieceData.flip();
                        }
                    }

                    // If the piece is not the current player's piece, flip it
                    if (draggedPiece.getColor() != model.getCurrentColor()) {
                        draggedPieceData.flip();
                    }

                    // Update the dragging piece position to follow mouse, with offset
                    view.getBoardPanel().setDraggingPiece(draggedPieceData, e.getX(), e.getY());

                    // Update hovered grid while dragging
                    int tileSize = view.getBoardPanel().getSquareSize();
                    int gridX = (e.getX() - view.getBoardPanel().getXOffset()) / tileSize;
                    int gridY = (e.getY() - view.getBoardPanel().getYOffset()) / tileSize;

                    // Flip the coordinates if the board is flipped
                    if (view.getBoardPanel().isBoardFlipped()) {
                        gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
                        gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
                    }

                    // Ensure hover is only within the chessboard bounds (0-7 for both X and Y)
                    if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                            && gridY < KwazamConstants.BOARD_ROWS) {
                        view.getBoardPanel().setHoveredGrid(gridX, gridY);
                    }
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // Get the tile size based on the chessboard size
                int tileSize = view.getBoardPanel().getSquareSize();
                int gridX = (e.getX() - view.getBoardPanel().getXOffset()) / tileSize;
                int gridY = (e.getY() - view.getBoardPanel().getYOffset()) / tileSize;

                // Flip the coordinates if the board is flipped
                if (view.getBoardPanel().isBoardFlipped()) {
                    gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
                    gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
                }

                // Ensure hover effect only occurs within the bounds of the grid
                if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                        && gridY < KwazamConstants.BOARD_ROWS) {
                    KwazamPiece hoveredPiece = model.getGameBoard().getPiece(gridX, gridY);

                    if (hoveredPiece != null) {
                        // Only change cursor to "grab" if the hovered piece belongs to the current
                        // player
                        if (hoveredPiece.getColor() == model.getCurrentColor()) {
                            view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        } else {
                            // Change to default cursor if it's an opponent's piece
                            view.setCursor(Cursor.getDefaultCursor());
                        }
                    } else {
                        // Change to default cursor when hovering over an empty square
                        view.setCursor(Cursor.getDefaultCursor());
                    }

                    // Update hovered grid when mouse moves
                    view.getBoardPanel().setHoveredGrid(gridX, gridY);
                } else {
                    // Reset cursor when outside chessboard bounds
                    view.setCursor(Cursor.getDefaultCursor());
                    view.getBoardPanel().setHoveredGrid(-1, -1); // Clear hover effect
                }
            }

        });
    }

    public void initMenuListeners() {
        view.getKwazamMenuBar().getQuitOption().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitGame();
            }
        });
    }

    public void initWindowListeners() {
        view.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                quitGame();
            }
        });
    }

    private void updateView() {
        if (isDragging)
            return;

        // Convert Pieces to PieceData for the view
        List<KwazamRenderPiece> pieceDataList = new ArrayList<>();
        for (KwazamPiece piece : model.getGameBoard().getPieces()) {
            KwazamRenderPiece newRenderPiece = new KwazamRenderPiece(
                    piece.getColor().name().substring(0, 1) + "_" + piece.getType(),
                    piece.getX(), piece.getY());

            if (piece.getType() == KwazamPieceType.RAM) {
                Ram ramPiece = (Ram) piece;
                if ((ramPiece.getColor() == KwazamPieceColor.RED && ramPiece.getDirection() == -1) ||
                        (ramPiece.getColor() == KwazamPieceColor.BLUE && ramPiece.getDirection() == 1)) {
                    newRenderPiece.flip();
                }
            }

            if (piece.getColor() != model.getCurrentColor())
                newRenderPiece.flip();

            pieceDataList.add(newRenderPiece);
        }

        view.getBoardPanel().setRenderPieces(pieceDataList);

        // Deselect the piece after moving or dragging is completed
        KwazamRenderPiece selectedPieceData = selectedPiece != null
                ? new KwazamRenderPiece(selectedPiece.getColor().name().substring(0, 1) + "_" + selectedPiece.getType(),
                        selectedPiece.getX(), selectedPiece.getY())
                : null;
        view.getBoardPanel().setSelectedPiece(selectedPieceData);

        view.getBoardPanel().clearDraggingPiece(); // Reset dragging visuals
    }

    private void quitGame() {
        boolean confirmQuit = view.showQuitDialog();
        if (confirmQuit) {
            // Close the game (exit the application)
            System.exit(0);
        }
    }

}
