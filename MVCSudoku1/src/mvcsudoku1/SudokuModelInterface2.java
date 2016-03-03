/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

/**
 *
 * @author didrik
 */
public interface SudokuModelInterface2 
{
    boolean solveBoard();
    void clearBoard();
    void setStrBoard(String input);
    boolean isLegal(int row, int col, int val);
    void setBoardVal(int row, int col, int val);
    void printModelBoard();    
}
