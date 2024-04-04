# Chess2D
This is a basic 2 player chess game that has all the basic features and rules of the game of Chess.

In this Java-based 2D chess game implementation, object-oriented programming principles are utilized to structure the codebase, incorporating graphical user interface elements for rendering the chessboard and pieces. The game consists of two primary classes:

CheckScanner Class: This class is responsible for detecting if the king is in a checked state or if the game has reached a checkmate condition. It employs methods to scan the board for threats to the king from opponent pieces. Additionally, it determines if any legal moves are available to the king to escape check or if any other piece can block the check.

Board Class: Extending JPanel, the Board class serves as the graphical interface for the chess game. It renders the chessboard, pieces, and highlights valid moves. This class also manages the game state, including the placement of pieces, player turns, and detecting checkmate conditions. It handles user input and executes moves based on the rules of chess.

Challenges were encountered during the implementation of the pawn and king movement logic. For pawns, considerations such as en passant captures and pawn promotion had to be addressed. Similarly, ensuring accurate detection of king check involved considering various factors such as threats from opponent pieces, movement restrictions, and special moves like castling. However, through iterative testing and debugging, these challenges were overcome, resulting in a functional and enjoyable chess gaming experience.

Also there are other classes:
Piece Class: Serving as a base class, Piece represents individual chess pieces on the board. It encapsulates common properties like position, ownership, and image representation. Subclasses such as Pawn, King, Queen, Knight, etc., extend Piece to inherit common attributes and behaviors while implementing specific movement rules tailored to each piece type.

Move Class: This class represents a move made by a player. It encapsulates information about the old and new positions of a piece, as well as details about any captured piece during the move.
