/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import java.util.Observable;

/**
 *
 * @author didrik
 */
public class SudokuModel extends Observable implements SudokuModelInterface, SudokuModelInterface2
{
    public static int SIZE = 9;
    private int[][] modelBoard;
    private ModelListener listener;
    private boolean ifItUnique = false;
    
    public SudokuModel()
    {
        modelBoard = new int[SIZE][SIZE]; 
    }
    
    @Override
    public void setListener(ModelListener listener) //addListener for plural :)
    {
        this.listener = listener;
    }
    
    public void fireBoardChanged() //clear som passar in här bara
    {
        if (listener != null)
            listener.boardChanged();  
    }
    
    public void fireSquareChanged(int row, int col)
    {
        if (listener != null)
            listener.squareChanged(row, col);  
    }
    
    public void fireGameChanged()
    {
        if (listener != null)
                listener.gameChanged(); 
    }
    
    public void clearBoard() 
    {
        for (int r = 0; r < SIZE; r++) 
        {
            for (int c = 0; c < SIZE; c++) 
            {
                modelBoard[r][c] = 0;
            }
        }
        fireBoardChanged();
    } 
    
    public void setBoardVal(int r, int c, int val)
    {
        Object[] whatHappend = new Object[2];
        int[] boardSpotInfo = new int[3];
        
        if((val>9 || val<0) && !isLegal(r,c,val)) // hmmm???****
        {
            throw new IllegalArgumentException("Not a valid value"); 
        }
        modelBoard[r][c] = val;
        fireSquareChanged(r, c);
    }
    
    @Override
    public void setStrBoard(String input) 
    {
        String oldBoard = getStrBoard(); // old board, used for listener        
        String[] rows = input.split("\n");
        if(rows.length != SIZE)
        {
            throw new IllegalArgumentException("rows wrong");            
        }
        int i = 0;
        while (i < rows.length)
        {
            String numberCol = rows[i];
            if(numberCol.length() != SIZE)
            {
                throw new IllegalArgumentException("wrong cols");                
            }
            i=i+1;
        }
        for(int r = 0; r < SIZE; r=r+1)
        {
            for(int c = 0; c < SIZE; c=c+1)
            {
                String s = rows[r];
                char val = s.charAt(c);
                
                if(!Character.isDigit(val))
                {
                    setBoardVal(r, c, 0);
                }
                else
                {
                    setBoardVal(r, c, Character.getNumericValue(val)); // setBoard(r,c,num) itself checks if valid, above.
                }
            }        
        }        
        fireGameChanged();
    }
    
    public String getStrBoard() 
    { 
        int i = 0;
        String s1 = "";
        for (int r=0; r<SIZE; r=r+1) 
        {
            for (int c=0; c<SIZE; c=c+1)
            {
                s1 = s1 + modelBoard[r][c];
                i = i + 1;
                if (i%SIZE == 0) 
                {
                    s1 = s1 + "\n";
                }
            }
        }
        s1 = s1.replace('0','.');
        return s1; 
    }

    @Override
    public int getBoardVal(int r, int c)
    {
        return modelBoard[r][c];
    }
    
    public int[][] getCopyBoard()
    {
        int[][] copyBoard = new int[SIZE][SIZE];
        for(int r=0; r<SIZE; r=r+1)
        {
            for(int c=0; c<SIZE; c=c+1)
            {
                copyBoard[r][c] = modelBoard[r][c];
            }
        }
        return copyBoard;
    }
    
    public void printModelBoard()
    {
        for(int r=0; r<SIZE; r=r+1)
        {
            for(int c=0; c<SIZE; c=c+1)
            {                
                if(c%9==0)
                {
                    System.out.println("");
                }
                System.out.print(""+modelBoard[r][c]);                        
            }
        }
    }
    
    @Override
    public boolean isLegal (int row, int col, int val) 
    {        
        //check block it is in!
        for (int i=0;i<3;i=i+1) 
        { 
            for (int j=0;j<3;j=j+1) 
            {
                if (val == modelBoard[(row/3)*3 + i][(col/3)*3 + j])                           
                    return false;                                          
            }                                                          
        }
        
        // check rows!
        for (int k=0;k<SIZE;k=k+1) 
        {
            if (val == modelBoard[row][k]) 
            {
                return false;
            }
        } 
        
        // check colons!
        for (int l=0; l<SIZE; l=l+1) 
        { 
            if (val == modelBoard[l][col])
                return false;
        }
        return true; // we are good
    }
    
    @Override
    public boolean solveBoard() 
    {
        //String oldBoard = getBoard(); // nytt!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        
        int[]blank = findBlank(); // use findBlank method.
        int r = blank[0]; // pick values.
        int c = blank[1];
            
        if ( r==-1 || c==-1) 
        { // we are done, no blanks left.
            return true;
        }
        
        for (int val=1;val<=SIZE;val=val+1) 
        { // check value from 1-9.
            if ( isLegal(r,c,val) ) 
            { 
                setBoardVal(r,c,val); 
                if (solveBoard()) 
                { // recursion
                    return true;
                }
                setBoardVal(r,c,0); 
            }
        }
        return false; // if not solveable.
    }   
    
     public int[] findBlank() 
     {
        int[] blank = new int[2]; // 2 element ray.
        for (int r=0;r<SIZE;r=r+1) 
        {
            for (int c=0;c<SIZE;c=c+1) 
            {
                if (modelBoard[r][c]==0) 
                { // if empty 
                    blank[0] = r;
                    blank[1] = c;
                    return blank;
                }
            }
        }
        blank[0]=-1; // no zeros left
        blank[1]=-1;
        return blank;
    }
}