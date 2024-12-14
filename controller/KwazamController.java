package controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import model.KwazamGameManager;
import model.piece.KwazamPiece;
import model.utils.KwazamConstants;
import view.KwazamRenderPiece;
import view.KwazamView;

public class KwazamController {
    private static KwazamController instance;
    private final KwazamView view;
    private final KwazamGameManager model;
    private KwazamPiece selectedPiece;
    private KwazamPiece draggedPiece;
    private int originalX, originalY; // Original position of dragged piece
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
        view.initView();
        model.initGame();
        initController();
    }

    public void initController() {
        // MouseListener for click and drag actions
        view.getBoardPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int gridX = (e.getX() - view.getBoardPanel().getXOffset()) / view.getBoardPanel().getSquareSize();
                int gridY = (e.getY() - view.getBoardPanel().getYOffset()) / view.getBoardPanel().getSquareSize();

                // Record the press position to detect a click later
                pressX = e.getX();
                pressY = e.getY();

                draggedPiece = model.getGameBoard().getPiece(gridX, gridY);
                if (draggedPiece != null) {
                    // Save original position for drag-and-drop
                    originalX = draggedPiece.getX();
                    originalY = draggedPiece.getY();
                    view.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR)); // Change to grabbing cursor
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                int releaseX = e.getX();
                int releaseY = e.getY();
                
                if (isDragging && draggedPiece != null) {
                    // Handle drag-and-drop release
                    int tileSize = view.getBoardPanel().getSquareSize();
                    int gridX = (releaseX - view.getBoardPanel().getXOffset()) / tileSize;
                    int gridY = (releaseY - view.getBoardPanel().getYOffset()) / tileSize;

                    if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0 && gridY < KwazamConstants.BOARD_ROWS) {
                        // Move piece to new grid
                        model.getGameBoard().movePiece(draggedPiece, gridX, gridY);
                    } else {
                        // Return to original position if invalid
                        model.getGameBoard().movePiece(draggedPiece, originalX, originalY);
                    }

                    // Deselect piece after dragging is done
                    selectedPiece = null;

                    isDragging = false;
                    draggedPiece = null;
                    view.setCursor(Cursor.getDefaultCursor()); // Restore cursor after drag
                } else if (!isDragging) {
                    // Handle click-to-move
                    int tileSize = view.getBoardPanel().getSquareSize();
                    int gridX = (releaseX - view.getBoardPanel().getXOffset()) / tileSize;
                    int gridY = (releaseY - view.getBoardPanel().getYOffset()) / tileSize;

                    if (selectedPiece == null) {
                        // Select piece
                        selectedPiece = model.getGameBoard().getPiece(gridX, gridY);
                    } else {
                        // Move selected piece
                        if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0 && gridY < KwazamConstants.BOARD_ROWS) {
                            model.getGameBoard().movePiece(selectedPiece, gridX, gridY);
                        }
                        selectedPiece = null; // Deselect after moving
                    }

                    view.setCursor(Cursor.getDefaultCursor()); // Restore cursor
                }

                updateView();
            }
        });

        // MouseMotionListener for drag movement
        view.getBoardPanel().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (draggedPiece != null) {
                    // Check if the movement is significant enough to qualify as a drag
                    if (Math.abs(e.getX() - pressX) > 5 || Math.abs(e.getY() - pressY) > 5) {
                        isDragging = true;
                    }

                    // Update dragging piece position to follow mouse, with offset
                    KwazamRenderPiece draggedPieceData = new KwazamRenderPiece(
                            draggedPiece.getColor().name().substring(0, 1) + "_" + draggedPiece.getType(),
                            draggedPiece.getX(),
                            draggedPiece.getY()
                    );
                    view.getBoardPanel().setDraggingPiece(draggedPieceData, e.getX(), e.getY());

                    // Update hovered grid while dragging
                    int tileSize = view.getBoardPanel().getSquareSize();
                    int gridX = (e.getX() - view.getBoardPanel().getXOffset()) / tileSize;
                    int gridY = (e.getY() - view.getBoardPanel().getYOffset()) / tileSize;
                    // Ensure that hover is only within the chessboard bounds (0-7 for both X and Y)
                    if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0 && gridY < KwazamConstants.BOARD_ROWS) {
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

                // Ensure hover effect only occurs within the bounds of the grid
                if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0 && gridY < KwazamConstants.BOARD_ROWS) {
                    KwazamPiece hoveredPiece = model.getGameBoard().getPiece(gridX, gridY);
                    if (hoveredPiece != null) {
                        // Change cursor to "grab" when hovering over a piece
                        view.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                    } else {
                        // Change to default cursor when not hovering over a piece
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

        updateView();
    }

    private void updateView() {
        // Convert Pieces to PieceData for the view
        List<KwazamRenderPiece> pieceDataList = new ArrayList<>();
        for (KwazamPiece piece : model.getGameBoard().getPieces()) {
            pieceDataList.add(new KwazamRenderPiece(piece.getColor().name().substring(0, 1) + "_" + piece.getType(), piece.getX(), piece.getY()));
        }

        view.getBoardPanel().setRenderPieces(pieceDataList);

        // Deselect the piece after moving or dragging is completed
        KwazamRenderPiece selectedPieceData = selectedPiece != null
                ? new KwazamRenderPiece(selectedPiece.getColor().name().substring(0, 1) + "_" + selectedPiece.getType(), selectedPiece.getX(), selectedPiece.getY())
                : null;
        view.getBoardPanel().setSelectedPiece(selectedPieceData);

        view.getBoardPanel().clearDraggingPiece(); // Reset dragging visuals
    }
}
