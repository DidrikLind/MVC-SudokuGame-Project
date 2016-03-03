/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author didrik
 */
public class SudokuController 
{
    private SudokuModelInterface2 modelInterface2;
    private SudokuView view; 
    
    public SudokuController(SudokuModelInterface2 modelInterface2, SudokuView view)
    {
        this.modelInterface2 = modelInterface2;
        this.view = view;
        
        view.addBoardKeyListener(new BoardKeyListener());
        view.addSolveButtonListener(new SolveButtonListener());
        view.addClearButtonListener(new ClearButtonListener());
        view.addNewButtonListener(new NewButtonListener());
    }
        
    private class BoardKeyListener extends KeyAdapter
    {
        @Override
        public void keyReleased(KeyEvent keyevt) 
        {         
            System.out.println("You released a key!!");

            SudokuSquare sudoSquare = (SudokuSquare) keyevt.getSource();
            int userVal=0;
            try
            {
                userVal = Integer.parseInt(sudoSquare.getText()); 
            }
            catch(NumberFormatException e) 
            {
                sudoSquare.setVal(0);
                Toolkit.getDefaultToolkit().beep(); // else gör ljud!
            }
            
            int row = sudoSquare.getRow();
            int col = sudoSquare.getCol();
            
            if(modelInterface2.isLegal(row, col, userVal)) //this atm is always true :O
            { 
                modelInterface2.setBoardVal(row, col, userVal);
                sudoSquare.setVal(userVal);
                //modelInterface2.setBoardVal(row, col, val);
            }
            else 
            {
                sudoSquare.setVal(0);
                Toolkit.getDefaultToolkit().beep(); // else gör ljud!
            }             
            modelInterface2.printModelBoard();
        }  
    }
    
    private class SolveButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            modelInterface2.solveBoard();
        }   
    }
    
    private class ClearButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae)
        {
            modelInterface2.clearBoard();    
        }
    }
    
    private class NewButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent ae) 
        {
            openSudFile();
        }   
    }
    
    public void openSudFile() 
    {
        
        String line;
        BufferedReader inFil;
        String lineTemp = "";

		// making the dialogbox for open files.
        String aktuellMapp = System.getProperty("user.dir"); // active mapp
        JFileChooser fc = new JFileChooser(aktuellMapp); 
	     int resultat = fc.showOpenDialog(null); 
     	
	     if(resultat == JFileChooser.APPROVE_OPTION) 
        { // if not cancel	
            String namn = fc.getSelectedFile().getName();
            String path = fc.getSelectedFile().getAbsolutePath();
            try 
            {
                inFil = new BufferedReader(new FileReader(path)); // file opening
                line = inFil.readLine();
                while (line != null) 
                {
                    lineTemp = lineTemp + line + '\n';
                    line = inFil.readLine();
                }
                inFil.close();
                // known method from MySudokuModel
                modelInterface2.setStrBoard(lineTemp); 
            }   
                // might just need "IllegalArgumentException e",
            catch(Exception e) 
            { // general catcher, anyways :).
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "God says you cannot open: " + namn);
            }
        }
    }
}