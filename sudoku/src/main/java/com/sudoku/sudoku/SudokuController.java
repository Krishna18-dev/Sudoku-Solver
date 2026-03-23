package com.sudoku.sudoku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@CrossOrigin(origins = "*")
public class SudokuController {

    public boolean isSafe(char[][] board, int row, int col, int number) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][col] == (char)(number + '0')) return false;
            if (board[row][i] == (char)(number + '0')) return false;
        }
        int sr = (row / 3) * 3, sc = (col / 3) * 3;
        for (int i = sr; i < sr + 3; i++)
            for (int j = sc; j < sc + 3; j++)
                if (board[i][j] == (char)(number + '0')) return false;
        return true;
    }

    public boolean helper(char[][] board, int row, int col) {
        if (row == board.length) return true;
        int nrow, ncol;
        if (col != board.length - 1) { nrow = row; ncol = col + 1; }
        else                          { nrow = row + 1; ncol = 0; }
        if (board[row][col] != '.') {
            return helper(board, nrow, ncol);
        } else {
            for (int i = 1; i <= 9; i++) {
                if (isSafe(board, row, col, i)) {
                    board[row][col] = (char)(i + '0');
                    if (helper(board, nrow, ncol)) return true;
                    board[row][col] = '.';
                }
            }
        }
        return false;
    }

    @PostMapping("/api/solve")
    public SolveResponse solve(@RequestBody SolveRequest req) {
        char[][] board = new char[9][9];
        for (int i = 0; i < 81; i++)
            board[i / 9][i % 9] = req.board()[i] == 0
                                   ? '.' : (char)('0' + req.board()[i]);
        boolean solved = helper(board, 0, 0);
        if (!solved) return new SolveResponse(false, null);
        int[] result = new int[81];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                result[i * 9 + j] = board[i][j] - '0';
        return new SolveResponse(true, result);
    }

    record SolveRequest(int[] board) {}
    record SolveResponse(boolean success, int[] board) {}

    public static void main(String[] args) {
        SpringApplication.run(SudokuController.class, args);
    }
}