package main;

import pieces.Piece;

public class CheckScanner {

    Board board;

    public CheckScanner(Board board){
        this.board = board;
    }

    public boolean isKingChecked(Move move){
        Piece king = board.findKing(move.piece.isWhite);
        assert king != null;

        int kingCol = king.col;
        int kingRow = king.row;

        if (board.selectedPiece != null && board.selectedPiece.name.equals("King")){
            kingCol = move.newCol;
            kingRow = move.newRow;
        }

        return hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, 1) || //up
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 1, 0) || //right
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, 0, -1) || //down
                hitByRook(move.newCol, move.newRow, king, kingCol, kingRow, -1, 0) || //left
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, -1) || //up left
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, -1) || // up right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, 1, 1) || //down right
                hitByBishop(move.newCol, move.newRow, king, kingCol, kingRow, -1, 1) || //down left
                hitByKnight(move.newCol, move.newRow, king, kingCol, kingRow) ||
                hitByPawn(move.newCol, move.newRow, king, kingCol, kingRow) ||
                hitByKing(king, kingCol, kingRow);

    }

    public boolean isCheckmate(Move move) {
        if (isKingChecked(move)) {
            // Check if the king can move out of check
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i == 0 && j == 0) continue; // Skip the king's current position
                    int newCol = move.piece.col + i;
                    int newRow = move.piece.row + j;
                    if (board.isValidMove(new Move(board, move.piece, newCol, newRow))) {
                        return false; // King can escape check by moving
                    }
                }
            }

            // Check if any piece can block the check
            for (Piece piece : board.pieceList) {
                if (!piece.isWhite == move.piece.isWhite && board.isValidMove(new Move(board, piece, move.newCol, move.newRow))) {
                    return false; // Piece can block the check
                }
            }

            // No moves can escape or block the check, it's checkmate
            return true;
        }
        return false; // King is not checked, so not checkmate
    }

    private boolean hitByRook(int col ,int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < 8; i++) {
            if (kingCol + (i*colVal) == col && kingRow + (i*rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol + (i*colVal),kingRow + (i*rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, king) && (piece.name.equals("Rook") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByBishop(int col ,int row, Piece king, int kingCol, int kingRow, int colVal, int rowVal){
        for (int i = 1; i < 8; i++) {
            if (kingCol - (i*colVal) == col && kingRow - (i*rowVal) == row){
                break;
            }

            Piece piece = board.getPiece(kingCol - (i*colVal),kingRow - (i*rowVal));
            if (piece != null && piece != board.selectedPiece){
                if (!board.sameTeam(piece, king) && (piece.name.equals("Bishop") || piece.name.equals("Queen"))){
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private boolean hitByKnight(int col ,int row, Piece king, int kingCol, int kingRow){
        return checkKnight(board.getPiece(kingCol - 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow - 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow - 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol + 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 1, kingRow + 2), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow + 1), king, col, row) ||
                checkKnight(board.getPiece(kingCol - 2, kingRow - 1), king, col, row);
    }

    private boolean checkKnight(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p,k) && p.name.equals("Knight") && !(p.col == col && p.row == row);
    }

    private boolean hitByKing(Piece king, int kingCol, int kingRow){
        return checkKing(board.getPiece(kingCol - 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow - 1), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow), king) ||
                checkKing(board.getPiece(kingCol - 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol + 1, kingRow + 1), king) ||
                checkKing(board.getPiece(kingCol, kingRow + 1), king);
    }

    private boolean checkKing(Piece p, Piece k){
        return p != null && !board.sameTeam(p, k) && p.name.equals("King");
    }

    private boolean hitByPawn(int col, int row, Piece king, int kingCol, int kingRow){
        int colorVal = king.isWhite ? -1 : 1;

        return checkPawn(board.getPiece(kingCol + 1, kingRow + colorVal), king, col, row) ||
                checkPawn(board.getPiece(kingCol -1, kingRow + colorVal), king, col, row);
    }

    private boolean checkPawn(Piece p, Piece k, int col, int row){
        return p != null && !board.sameTeam(p, k) && p.name.equals("Pawn") && !(p.col == col && p.row == row);
    }
}
