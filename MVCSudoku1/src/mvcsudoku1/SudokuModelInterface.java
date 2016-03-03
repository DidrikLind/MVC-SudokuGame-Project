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
public interface SudokuModelInterface 
{
    int getBoardVal(int r, int c);
    void setListener(ModelListener listener);    
}

