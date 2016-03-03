/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author didrik
 */
public class MVCSudoku1 extends JFrame
{
    
    public MVCSudoku1()
    {
        SudokuModelInterface modelInterface = new SudokuModel();
        SudokuModelInterface2 modelInterface2 = (SudokuModelInterface2) modelInterface;
        SudokuView view = new SudokuView(modelInterface);
        modelInterface.setListener(view);
        SudokuController controller = new SudokuController(modelInterface2, view);
        
        add(view);
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); 
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        new MVCSudoku1();
    }    
}

