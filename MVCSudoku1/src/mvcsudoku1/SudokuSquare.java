/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import javax.swing.JTextField;
import static javax.swing.SwingConstants.CENTER;

/**
 *
 * @author didrik
 */
public class SudokuSquare extends JTextField {
    public int row, col;
    private int val; 

    public SudokuSquare(int row, int col) {
        this.row = row;
        this.col = col;
        setHorizontalAlignment(CENTER);
    }

    public  SudokuSquare(int row, int col, int val)
    {                    
        setVal(val);
        this.row = row;
        this.col = col;
        this.val = val;
        setHorizontalAlignment(CENTER);

    }

    public void setVal(int inVal) 
    {
        this.val = inVal;
        String str = Integer.toString(this.val);
        if( inVal < 1 || inVal > 9) 
        {
            str=""; // empty
        }
        setText(str);
    }

    public int getRow() 
    {
        return row;
    }

    public int getCol() 
    {
        return col;
    }

    public int getVal()
    {
        return val;
    }        
}
