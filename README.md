# OOAD-KwazamChess
--- Rules ---
board: 5x8
Pieces:
1. Ram - Moves 1 step forward. If reaches end of the board, turns around and head back.
2. Biz - Moves in 3x2 L shape. Can skip over other piece
3. Tor - Move orthogonally at any distance. after 2 turns, becomes Xor.
4. Xor - Move diagonally at any distance. after 2 turns, becomes Tor.
5. Sau - Moves only one step in any direction. Games ends if Sau is captured by other side.
Requirements:
1. After 2 turns (counting one blue move and one red move as one turn), all Tor pieces will turn int Xor pieces and vice versa.
2. Must use good oo design concepts such as subclassing, delegation, composition and aggregation appropriately.
3. Must use MVC pattern with Java Swing GUI. All game logic must be in model classes only.
4. May use Singleton and/or Template method, but if you do, you need to use at least one other design pattern as well.
5. You should make your program user friendly, with suitable menus, save game, resizable windows, flipping the screen when it's the other player's turn, etc.
6. For save and load game, the game should be saved into a text file so that it's human-readable.
7. Let's say if you are now playing Blue player, Red player's pieces should be 90 degree oriented facing you

--- Board Layout ---
    | RT | RB | RS | RB | RX |
    | RR | RR | RR | RR | RR |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | BR | BR | BR | BR | BR |
    | BT | BB | BS | BB | BX |

--- How i want it to look like ---
1. Each class one file. package into model view controller or any other
2. Resizable freely, chess board remains center and only zoom by scale, if the width or height exceeds chessboard, darken the sides of the chessboard
3. have a menu bar at the top (Menu and Help)
4. Menu have dropdowns such as New Game, Load Game, Pause, Restart, Quit.
5. Help have dropdowns such as About and Rules.
7. When application starts, show the chessboard, but blur out, and have buttons such as New Game, Load Game, Quit, and Rules.
6. If click New Game, pop a new window and ask for player 1(blue) and player 2(red) name then start button. If clicked from menu bar, if game is already going on but pressed new game, ask for confirmation whether to save game or not.
7. If click Load Game, pop a new window and show a list of saved games and choose from the list. If clicked from menu bar, if game is already going on but pressed load game, ask for confirmation whether to save game or not.
8. If click Restart, ask for confirmation whether to save game or not then restarts.
9. If click Pause, blurs the screen then show the word pause. show a resume button. change the Pause from menu bar dropdown to Resume.
10. Quit, quits the game. If clicked from menu bar, if game is already going on but pressed quit, ask for confirmation whether to save game or not.
11. In each move, i can click on a piece to hightlight the available moves, i can also choose to drag the piece or just tap on other piece to capture if possible.
12. Save game will look someting simmilar to, it is based on the 2d array of the following enum type PieceType(contains R_RAM, B_SAU and so on, if no pieces then is type -1), which then converted to this :
    | RT | RB | RS | RB | RX |
    | RR | RR | RR | RR | RR |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | BR | BR | BR | BR | BR |
    | BT | BB | BS | BB | BX |

13. PieceFactory will accept the elements of the above strings to check the type and to generate the pieces accourdingly. Thus there will be two same arrays, one for the model, then controller will update a copy to view

--- Design Patterns ---
1. MVC (Mandatory)
Ensures a separation of concerns:
    Model: Contains game logic, board state, and piece behaviors.
    View: Displays the game board and UI components.
    Controller: Handles user input and communicates changes between the View and Model.

2. Strategy Pattern
Use the Strategy Pattern for piece movement logic. Each piece (e.g., Ram, Biz, Tor, etc.) can have its own movement strategy encapsulated in a separate class.

3. Factory Pattern
Use the Factory Pattern to create game pieces dynamically. This is especially useful when initializing the board or loading a saved game.

4. Template Method Pattern
Use the Template Method for the game's turn logic, which is common for both players but may have slight variations for player-specific logic (e.g., flipping the board).

5. Singleton Pattern
Use the Singleton Pattern for the GameManager to ensure there is only one active game instance managing the state.

6. Composite Pattern
Use the Composite Pattern for the board, where the board can aggregate multiple squares, and each square can aggregate pieces.

--- Folder Structure ---
.
├── App.java                          # Entry point of the application
├── controller/
│   ├── KwazamController.java          # Mediates communication between Model and View
├── model/
│   ├── KwazamBoard.java              # Composite: Represents the board as a collection of BoardSquares
│   ├── KwazamBoardSquare.java        # Represents individual squares on the board
│   ├── KwazamGameManager.java        # Manages game state, player turns, and logic
│   ├── TurnLogic.java                # Template Method for defining the turn sequence
│   ├── PlayerTurn.java               # Concrete class for player-specific turn logic
│   ├── pieces/
│   │   ├── KwazamPiece.java          # Abstract base class for all pieces
│   │   ├── Ram.java                  # Specific piece subclass (moves forward, reverses at edge)
│   │   ├── Biz.java                  # Specific piece subclass (L-shaped movement)
│   │   ├── Tor.java                  # Switches to Xor after 2 turns (orthogonal movement)
│   │   ├── Xor.java                  # Switches to Tor after 2 turns (diagonal movement)
│   │   ├── Sau.java                  # Game-ending piece (moves one step in any direction)
│   ├── movements/
│   │   ├── MovementStrategy.java     # Strategy Pattern interface for movement
│   │   ├── OrthogonalMovement.java   # Movement in orthogonal directions
│   │   ├── DiagonalMovement.java     # Movement in diagonal directions
│   │   ├── LShapedMovement.java      # L-shaped (knight-like) movement
│   │   ├── StepMovement.java         # Single-step movement
│   ├── utils/
│   │   ├── KwazamConstants.java      # Static constants like board size, piece types
│   │   ├── KwazamPieceFactory.java   # Factory Pattern for creating pieces
│   │   ├── GameFileHandler.java      # Handles saving/loading games
│   ├── exceptions/
│   │   ├── InvalidMoveException.java # Exception for invalid moves
│   │   ├── GameStateException.java   # Exception for illegal game states
├── view/
│   ├── KwazamView.java               # Main game window (JFrame)
│   ├── BoardPanel.java               # Displays the board and pieces
│   ├── MenuBar.java                  # Contains dropdown menus (e.g., New Game, Quit)
│   ├── StartGameDialog.java          # Dialog to input player names for a new game
│   ├── PausePanel.java               # Blurs the screen and shows "Pause" overlay
│   ├── SavedGamesDialog.java         # Lists saved games to load from
│   ├── KwazamHelper.java             # Utility to visually highlight valid moves on the board
├── README.md                         # Instructions and game documentation
