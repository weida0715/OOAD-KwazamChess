# Welcome to OOAD-KwazamChess!

I will include the structure that I used in this project in this README.

Videos:

00. Assignment Briefing: https://drive.google.com/file/d/1bHg6_eUcZYY488Vtvya50ixECpDLL0iU/view?usp=sharing
01. Project Startup: https://drive.google.com/file/d/12Anw2vmEwZqN8ncCNeV83x2ur9csdRuj/view?usp=sharing


# Rules

Board: 5x8

Pieces:
1. Ram - Moves 1 step forward. If reaches end of the board, turns around and head back.
2. Biz - Moves in 3x2 L shape. Can skip over other piece
3. Tor - Move orthogonally at any distance. after 2 turns, becomes Xor.
4. Xor - Move diagonally at any distance. after 2 turns, becomes Tor.
5. Sau - Moves only one step in any direction. Games ends if Sau is captured by other side.
	

## Requirements

1. After 2 turns (counting one blue move and one red move as one turn), all Tor pieces will turn int Xor pieces and vice versa.
2. Must use good oo design concepts such as subclassing, delegation, composition and aggregation appropriately.
3. Must use MVC pattern with Java Swing GUI. All game logic must be in model classes only.
4. May use Singleton and/or Template method, but if you do, you need to use at least one other design pattern as well.
5. You should make your program user friendly, with suitable menus, save game, resizable windows, flipping the screen when it's the other player's turn, etc.
6. For save and load game, the game should be saved into a text file so that it's human-readable.
7. Let's say if you are now playing Blue player, Red player's pieces should be 90 degree oriented facing you

## Board Layout

Plain Text Layout:

    | RT | RB | RS | RB | RX |
    | RR | RR | RR | RR | RR |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | .. | .. | .. | .. | .. |
    | BR | BR | BR | BR | BR |
    | BT | BB | BS | BB | BX |

## How i want it to look like

1. Each class one file. package into model view controller or any other
2. Resizable freely, chess board remains center and only zoom by scale, if the width or height exceeds chessboard, darken the sides of the chessboard
3. Have a menu bar at the top (Menu and Help)
4. Menu have dropdowns such as New Game, Load Game, Pause, Restart, Quit.
5. Help have dropdowns such as About and Rules.
6. When application starts, show the chessboard, but blur out, and have buttons such as New Game, Load Game, Quit, and Rules.
7. If click New Game, pop a new window and ask for player 1(blue) and player 2(red) name then start button. If clicked from menu bar, if game is already going on but pressed new game, ask for confirmation whether to save game or not.
8. If click Load Game, pop a new window and show a list of saved games and choose from the list. If clicked from menu bar, if game is already going on but pressed load game, ask for confirmation whether to save game or not.
9. If click Restart, ask for confirmation whether to save game or not then restarts.
10. If click Pause, blurs the screen then show the word pause. show a resume button. change the Pause from menu bar dropdown to Resume.
11. Quit, quits the game. If clicked from menu bar, if game is already going on but pressed quit, ask for confirmation whether to save game or not.
12. In each move, i can click on a piece to hightlight the available moves, i can also choose to drag the piece or just tap on other piece to capture if possible.
13. Save game will look someting simmilar to, it is based on the 2d array of the following enum type PieceType(contains R_RAM, B_SAU and so on, if no pieces then is type -1), which then converted to this :

        | RT | RB | RS | RB | RX |
        | RR | RR | RR | RR | RR |
        | .. | .. | .. | .. | .. |
        | .. | .. | .. | .. | .. |
        | .. | .. | .. | .. | .. |
        | .. | .. | .. | .. | .. |
        | BR | BR | BR | BR | BR |
        | BT | BB | BS | BB | BX |

14. PieceFactory will accept the elements of the above strings to check the type and to generate the pieces accourdingly. Thus there will be two same arrays, one for the model, then controller will update a copy to view


## Design Patterns

### 1. MVC (Mandatory)
Ensures a separation of concerns:
- **Model**: Contains game logic, board state, and piece behaviors.
- **View**: Displays the game board and UI components.
- **Controller**: Handles user input and communicates changes between the View and Model.

### 2. Strategy Pattern
The **Strategy Pattern** is used to define a family of algorithms, allowing a piece to choose its movement strategy at runtime. In your project, classes like `BizMovement` and `RamMovement` encapsulate different movement behaviors, letting each piece use its specific movement logic without hardcoding it in the piece itself.

### 3. Factory Pattern
The **Factory Pattern** is used to create objects without exposing the creation logic to the client. In your project, the `KwazamPieceFactory` class is responsible for creating different types of game pieces (like `Biz`, `Ram`, `Tor`), hiding the complexity of object creation from the controller or model.

### 4. Singleton Pattern
The **Singleton Pattern** ensures that a class has only one instance, providing a global point of access. In your project, the `KwazamController` follows this pattern to ensure there is only one instance of the controller, which manages the interaction between the model and the view.

### 5. Composite Pattern
The **Composite Pattern** allows individual objects and compositions of objects to be treated uniformly. In your project, the `KwazamBoard` can contain individual pieces and manage them together, letting the board and pieces be treated in the same way when updating or rendering the game state.

## Folder Structure

    .
    ├── audio
    │   ├── capture.wav
    │   └── move.wav
    ├── controller
    │   └── KwazamController.java
    ├── data
    │   └── savegame.txt
    ├── images
    │   ├── b_biz.png
    │   ├── b_ram.png
    │   ├── b_sau.png
    │   ├── b_tor.png
    │   ├── b_xor.png
    │   ├── r_biz.png
    │   ├── r_ram.png
    │   ├── r_sau.png
    │   ├── r_tor.png
    │   └── r_xor.png
    ├── images.zip
    ├── Main.java
    ├── model
    │   ├── board
    │   │   └── KwazamBoard.java
    │   ├── KwazamGameManager.java
    │   ├── movements
    │   │   ├── BizMovement.java
    │   │   ├── MovementStrategy.java
    │   │   ├── RamMovement.java
    │   │   ├── SauMovement.java
    │   │   ├── TorMovement.java
    │   │   └── XorMovement.java
    │   └── pieces
    │       ├── Biz.java
    │       ├── KwazamPieceFactory.java
    │       ├── KwazamPiece.java
    │       ├── Ram.java
    │       ├── Sau.java
    │       ├── Tor.java
    │       └── Xor.java
    ├── README.md
    ├── utils
    │   ├── GameFileHandler.java
    │   ├── KwazamConstants.java
    │   ├── KwazamPieceColor.java
    │   ├── KwazamPieceType.java
    │   └── SoundEffect.java
    └── view
        ├── components
        │   ├── KwazamMenuBar.java
        │   └── KwazamRenderPiece.java
        ├── dialogs
        │   ├── EndGameDialog.java
        │   ├── QuitGameDialog.java
        │   ├── SavedGamesDialog.java
        │   └── StartGameDialog.java
        ├── KwazamView.java
        └── panels
            ├── KwazamBoardPanel.java
            └── PausePanel.java
