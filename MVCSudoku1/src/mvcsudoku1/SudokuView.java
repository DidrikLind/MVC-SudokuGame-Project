/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author didrik
 */
public class SudokuView extends JPanel implements ModelListener 
{   //***Denna "SIZE" vill komma från modellen senare, som i sin tur kan komma från användaren!
    private final int SIZE = 9; 
    private JPanel[][] theBlocks = new JPanel[SIZE/3][SIZE/3];
    private SudokuSquare[][] viewBoard = new SudokuSquare[SIZE][SIZE];
    private SudokuModelInterface modelInterface;
    
    private JPanel cPanel, sPanel;
    private JButton solveB, clearB, newB;
    
    public SudokuView(SudokuModelInterface modelInterface)
    {
        //using either for pull-model use or in future to att view as listener for model.
        //and then we let the view be a "properychangelistener", i.e implement the interface "PropertChangeListener"
        this.modelInterface = modelInterface; 
        initGUI();                      
    }
   
    public void initGUI()
    {
        setLayout(new BorderLayout());
        makeCenterPanel();
        makeSouthPanel();
    }
    
    private void makeCenterPanel()
    {
        cPanel = new JPanel();
        cPanel.setLayout(new GridLayout(SIZE/3, SIZE/3));
        makeBlocksAndSquares();
        
        add(cPanel, BorderLayout.CENTER);        
    }
    
    private void makeBlocksAndSquares()
    {
        //blocks.
        for (int r=0; r < SIZE/3; r=r+1)
        {
            for (int c=0; c < SIZE/3; c=c+1) 
            { 
                theBlocks[r][c] = new JPanel(new GridLayout(3, 3));
                theBlocks[r][c].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
                cPanel.add(theBlocks[r][c]);
            }
        }
        
        //squares.
        for (int r=0;r<SIZE;r=r+1) 
        {
            for(int c=0;c<SIZE;c=c+1) 
            {
                //viewBoard[r][c] = new SudokuSquare(r, c, 0); 
                viewBoard[r][c] = new SudokuSquare(r, c, modelInterface.getBoardVal(r,c)); //"PULL-MODEL"
                theBlocks[r/3][c/3].add(viewBoard[r][c]); // so we add in the right blocks.
            }
        }        
    }
    
    private void makeSouthPanel()
    {
        sPanel = new JPanel();
        
        clearB = new JButton("Clear");
        sPanel.add(clearB);
        
        solveB = new JButton("Solve");
        sPanel.add(solveB);
        
        newB = new JButton("new game");
        sPanel.add(newB);
        
        add(sPanel, BorderLayout.SOUTH);
        
    }
    
    public void clearViewBoard() 
    {
        for(int r = 0; r < SIZE; r++) 
        {
            for(int c = 0; c < SIZE; c++) 
            {
                viewBoard[r][c].setVal(0);
                viewBoard[r][c].setEditable(true);
            }
        }
    }
    
    public void addBoardKeyListener(KeyListener keyListen)
    {
        for (int r=0;r<SIZE;r=r+1) 
        {
            for(int c=0;c<SIZE;c=c+1) 
            {
                // Add KeyListener to the TextField, each "square", I WILL ADD THIS IN CONTROLLER"!!!!
                viewBoard[r][c].addKeyListener(keyListen);
            }
        }   
    }
    
    public void addSolveButtonListener(ActionListener actionListen)
    {
        solveB.addActionListener(actionListen);
    }
    
    public void addClearButtonListener(ActionListener actionListen)
    {
       clearB.addActionListener(actionListen); 
    }
    
    public void addNewButtonListener(ActionListener acionListen)
    {
        newB.addActionListener(acionListen);
    }

    @Override
    public void boardChanged()
    {
        clearViewBoard();
    }
       
    @Override
    public void squareChanged(int row, int col) 
    {
        viewBoard[row][col].setVal(modelInterface.getBoardVal(row, col)); // "PULL-MODEL"
    }

    @Override
    public void gameChanged() 
    {
        for(int r = 0; r < SIZE; r++) 
        {
            for(int c = 0; c < SIZE; c++) 
            {
                SudokuSquare txtField = new SudokuSquare(r, c, modelInterface.getBoardVal(r,c)); //pulling data
                viewBoard[r][c].setVal(modelInterface.getBoardVal(r,c));
                int currentVal = txtField.getVal();
                if (currentVal != 0 ) 
                { // locks the standard squaaares!
                    viewBoard[r][c].setEditable(false);
                }
            }
        }
    }
}
        

  

    