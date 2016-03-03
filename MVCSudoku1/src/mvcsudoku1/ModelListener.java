/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mvcsudoku1;

import java.util.EventListener;

/**
 *
 * @author didrik
 */
public interface ModelListener extends EventListener 
{
    void boardChanged(); //clear som passar in här bara
    void squareChanged(int row, int col);    
    void gameChanged();   
}
