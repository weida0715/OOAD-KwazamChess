package controller;

import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import model.pieces.KwazamPiece;
import model.pieces.Ram;
import utils.KwazamConstants;
import utils.KwazamPieceColor;
import utils.KwazamPieceType;
import utils.SoundEffect;
import view.components.KwazamRenderPiece;
import view.dialogs.PostGameDialog;

/**
 * Handles mouse actions for the Kwazam game.
 * Manages mouse events like pressing, dragging, and releasing pieces.
 */
public class KwazamMouseHandler {
    // =================================================================
    // ATTRIBUTES
    // =================================================================
    private final KwazamController controller;

    // =================================================================
    // CONSTRUCTION
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Constructs a KwazamMouseHandler with the given controller.
     * 
     * @param c the controller
     */
    public KwazamMouseHandler(KwazamController c) {
        this.controller = c;
    }

    // =================================================================
    // MOUSE LISTENERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Initializes mouse listeners for the board.
     * Sets up actions for mouse press, release, drag, and move events.
     */
    public void initMouseListeners() {
        controller.getView().getBoardPanel().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                handleMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                handleMouseReleased(e);
            }
        });

        controller.getView().getBoardPanel().addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                handleMouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                handleMouseMoved(e);
            }
        });
    }

    // =================================================================
    // MOUSE EVENT HANDLERS
    // =================================================================
    /**
     * Author(s): Ng Wei Da
     * 
     * Removes all mouse listeners.
     * Clears existing listeners to avoid duplication.
     */
    public void removeMouseListeners() {
        if (controller.getView().getBoardPanel().getMouseListeners().length > 0) {
            controller.getView().getBoardPanel()
                    .removeMouseListener(controller.getView().getBoardPanel().getMouseListeners()[0]);
        }
        if (controller.getView().getBoardPanel().getMouseMotionListeners().length > 0) {
            controller.getView().getBoardPanel()
                    .removeMouseMotionListener(controller.getView().getBoardPanel().getMouseMotionListeners()[0]);
        }
    }

    /**
     * Author(s): Ng Wei Da
     * 
     * Handles mouse press events.
     * Selects and prepares a piece for dragging if it belongs to the current
     * player.
     */
    public void handleMousePressed(MouseEvent e) {
        int gridX = (e.getX() - controller.getView().getBoardPanel().getXOffset())
                / controller.getView().getBoardPanel().getSquareSize();
        int gridY = (e.getY() - controller.getView().getBoardPanel().getYOffset())
                / controller.getView().getBoardPanel().getSquareSize();

        // Flip the coordinates if the board is flipped
        if (controller.getView().getBoardPanel().isBoardFlipped()) {
            gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
            gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
        }

        // Record the press position to detect a click later
        controller.setPressedX(e.getX());
        controller.setPressedY(e.getY());

        controller.setDraggedPiece(controller.getModel().getGameBoard().getPiece(gridX, gridY));

        if (controller.getDraggedPiece() != null) {
            // Prevent interaction if the piece is not the current player's
            if (controller.getDraggedPiece().getColor() != controller.getModel().getCurrentColor()) {
                controller.setDraggedPiece(null);
                return;
            }

            // Fetch and display valid moves for the dragged piece
            List<int[]> validMoves = controller.getDraggedPiece().getValidMoves(controller.getModel().getGameBoard());
            controller.getView().showValidMoves(validMoves);

            controller.getView().setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
        }
    }

    /**
     * Author(s): Ng Wei Da, Lim Kar Joon, Lam Rong Yi
     * 
     * Handles mouse release events.
     * Moves the piece to the target location if valid, or deselects the piece.
     */
    public void handleMouseReleased(MouseEvent e) {
        boolean moved = false;

        int releaseX = e.getX();
        int releaseY = e.getY();

        int tileSize = controller.getView().getBoardPanel().getSquareSize();
        int gridX = (releaseX - controller.getView().getBoardPanel().getXOffset()) / tileSize;
        int gridY = (releaseY - controller.getView().getBoardPanel().getYOffset()) / tileSize;

        // Flip the coordinates if the board is flipped
        if (controller.getView().getBoardPanel().isBoardFlipped()) {
            gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
            gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
        }

        if (controller.isDragging() && controller.getDraggedPiece() != null) {
            // Handle drag-and-drop release
            if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                    && gridY < KwazamConstants.BOARD_ROWS) {
                KwazamPiece targetPiece = controller.getModel().getGameBoard().getPiece(gridX, gridY);

                if (controller.getModel().movePiece(controller.getDraggedPiece(), gridX, gridY)) {
                    // Check if the piece is a Ram and update its direction immediately
                    if (controller.getDraggedPiece().getType() == KwazamPieceType.RAM) {
                        Ram ramPiece = (Ram) controller.getDraggedPiece();

                        if (ramPiece.getY() == 0 && ramPiece.getDirection() == -1) {
                            ramPiece.setDirection(1); // Change direction to down
                        } else if (ramPiece.getY() == KwazamConstants.BOARD_ROWS - 1
                                && ramPiece.getDirection() == 1) {
                            ramPiece.setDirection(-1); // Change direction to up
                        }
                    }

                    if (targetPiece != null) {
                        SoundEffect.playCaptureSound();
                    } else {
                        SoundEffect.playMoveSound();
                    }
                    moved = true;
                }
                controller.getView().hideValidMoves();
            }

            // Deselect piece after dragging is done
            controller.setSelectedPiece(null);
            controller.setDraggedPiece(null);
            controller.setIsDragging(false);
            controller.getView().setCursor(Cursor.getDefaultCursor());
        } else if (!controller.isDragging()) {
            // Handle click-to-move
            if (controller.getSelectedPiece() == null) {
                // Select piece
                controller.setSelectedPiece(controller.getModel().getGameBoard().getPiece(gridX, gridY));

                if (controller.getSelectedPiece() != null) {
                    // Prevent selecting opponent's piece
                    if (controller.getSelectedPiece().getColor() != controller.getModel().getCurrentColor()) {
                        controller.setSelectedPiece(null);
                        return;
                    }

                    // Fetch and display valid moves for the selected piece
                    List<int[]> validMoves = controller.getSelectedPiece()
                            .getValidMoves(controller.getModel().getGameBoard());
                    controller.getView().showValidMoves(validMoves);
                }
            } else {
                KwazamPiece targetPiece = controller.getModel().getGameBoard().getPiece(gridX, gridY);

                if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                        && gridY < KwazamConstants.BOARD_ROWS) {
                    if (controller.getModel().movePiece(controller.getSelectedPiece(), gridX, gridY)) {
                        // Check if the piece is a Ram and update its direction immediately
                        if (controller.getSelectedPiece().getType() == KwazamPieceType.RAM) {
                            Ram ramPiece = (Ram) controller.getSelectedPiece();

                            // Change direction immediately when it reaches the edge
                            if (ramPiece.getY() == 0 && ramPiece.getDirection() == -1) {
                                ramPiece.setDirection(1); // Change direction to down
                            } else if (ramPiece.getY() == KwazamConstants.BOARD_ROWS - 1
                                    && ramPiece.getDirection() == 1) {
                                ramPiece.setDirection(-1); // Change direction to up
                            }
                        }

                        if (targetPiece != null) {
                            SoundEffect.playCaptureSound();
                        } else {
                            SoundEffect.playMoveSound();
                        }
                        moved = true;
                    }
                    controller.getView().hideValidMoves();
                }
                controller.setSelectedPiece(null);
            }

            controller.getView().setCursor(Cursor.getDefaultCursor());
        }

        controller.updateView();

        if (moved) {
            if (controller.getModel().isWinnerFound()) {
                SoundEffect.playWinningSound();

                controller.getView().showEndGameDialog(controller.getModel().getWinner());
                controller.getModel().clearSavedGame();

                // Show the PostGameDialog to ask the user what to do next
                int choice = controller.getView().showPostGameDialog();

                // Handle the user's choice
                switch (choice) {
                    case PostGameDialog.RESTART_GAME:
                        controller.getMenuHandler().restartGame();
                        break;
                    case PostGameDialog.NEW_GAME:
                        controller.getMenuHandler().newGame();
                        break;
                    case PostGameDialog.QUIT:
                        System.exit(0);
                        break;
                    default:
                        break;
                }
            } else {
                controller.getView().getBoardPanel().flipBoard();
                controller.getModel().switchColor();
                controller.updateView();

                controller.getModel().saveGame(null);
            }
        }
    }

    /**
     * Author(s): Ng Wei Da, Willie Teoh Chin Wei
     * 
     * Handles mouse drag events.
     * Updates the dragged piece's position and hovered grid during dragging.
     */
    public void handleMouseDragged(MouseEvent e) {
        if (controller.getDraggedPiece() != null) {
            // Prevent dragging opponent's piece
            if (controller.getDraggedPiece().getColor() != controller.getModel().getCurrentColor()) {
                controller.setDraggedPiece(null);
                return; // Don't allow dragging if it's an opponent's piece
            }

            // Check if the movement is significant enough to qualify as a drag
            if (Math.abs(e.getX() - controller.getPressedX()) > 5
                    || Math.abs(e.getY() - controller.getPressedY()) > 5) {
                controller.setIsDragging(true);
            }

            // Create a KwazamRenderPiece for the dragged piece, considering flipping
            KwazamRenderPiece draggedPieceData = new KwazamRenderPiece(
                    controller.getDraggedPiece().getColor().name().substring(0, 1) + "_"
                            + controller.getDraggedPiece().getType(),
                    controller.getDraggedPiece().getX(),
                    controller.getDraggedPiece().getY());

            // Flip the dragged piece if necessary
            if (controller.getDraggedPiece().getType() == KwazamPieceType.RAM) {
                Ram ramPiece = (Ram) controller.getDraggedPiece();

                // Only flip if the direction hasn't already been set to the opposite of the
                // piece's current direction
                if ((ramPiece.getColor() == KwazamPieceColor.RED && ramPiece.getDirection() == -1) ||
                        (ramPiece.getColor() == KwazamPieceColor.BLUE && ramPiece.getDirection() == 1)) {
                    draggedPieceData.flip();
                }
            }

            // If the piece is not the current player's piece, flip it
            if (controller.getDraggedPiece().getColor() != controller.getModel().getCurrentColor()) {
                draggedPieceData.flip();
            }

            // Update the dragging piece position to follow mouse, with offset
            controller.getView().getBoardPanel().setDraggingPiece(draggedPieceData, e.getX(), e.getY());

            // Update hovered grid while dragging
            int tileSize = controller.getView().getBoardPanel().getSquareSize();
            int gridX = (e.getX() - controller.getView().getBoardPanel().getXOffset()) / tileSize;
            int gridY = (e.getY() - controller.getView().getBoardPanel().getYOffset()) / tileSize;

            // Flip the coordinates if the board is flipped
            if (controller.getView().getBoardPanel().isBoardFlipped()) {
                gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
                gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
            }

            // Ensure hover is only within the chessboard bounds (0-7 for both X and Y)
            if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                    && gridY < KwazamConstants.BOARD_ROWS) {
                controller.getView().getBoardPanel().setHoveredGrid(gridX, gridY);
            }
        }
    }

    /**
     * Author(s): Ng Wei Da, Willie Teoh Chin Wei
     * 
     * Handles mouse move events.
     * Updates the cursor and hovered grid based on the mouse position.
     */
    public void handleMouseMoved(MouseEvent e) {
        // Get the tile size based on the chessboard size
        int tileSize = controller.getView().getBoardPanel().getSquareSize();
        int gridX = (e.getX() - controller.getView().getBoardPanel().getXOffset()) / tileSize;
        int gridY = (e.getY() - controller.getView().getBoardPanel().getYOffset()) / tileSize;

        // Flip the coordinates if the board is flipped
        if (controller.getView().getBoardPanel().isBoardFlipped()) {
            gridX = KwazamConstants.BOARD_COLS - 1 - gridX;
            gridY = KwazamConstants.BOARD_ROWS - 1 - gridY;
        }

        // Ensure hover effect only occurs within the bounds of the grid
        if (gridX >= 0 && gridX < KwazamConstants.BOARD_COLS && gridY >= 0
                && gridY < KwazamConstants.BOARD_ROWS) {
            KwazamPiece hoveredPiece = controller.getModel().getGameBoard().getPiece(gridX, gridY);

            if (hoveredPiece != null) {
                // Only change cursor to "grab" if the hovered piece belongs to the current
                // player
                if (hoveredPiece.getColor() == controller.getModel().getCurrentColor()) {
                    controller.getView().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    // Change to default cursor if it's an opponent's piece
                    controller.getView().setCursor(Cursor.getDefaultCursor());
                }
            } else {
                // Change to default cursor when hovering over an empty square
                controller.getView().setCursor(Cursor.getDefaultCursor());
            }

            // Update hovered grid when mouse moves
            controller.getView().getBoardPanel().setHoveredGrid(gridX, gridY);
        } else {
            // Reset cursor when outside chessboard bounds
            controller.getView().setCursor(Cursor.getDefaultCursor());
            controller.getView().getBoardPanel().setHoveredGrid(-1, -1);
        }
    }
}