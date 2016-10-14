import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Class that receives mouse events and uses characteristics 
 * of class MyPanel.
 * @author Gladymar O'Neill Rivas
 * @author Cristian F. Torres Collazo
 *
 */

public class Minesweeper extends MouseAdapter {

	/**
	 * Actions to do when the mouse is pressed
	 */
	public void mousePressed(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;

		switch (e.getButton()) {
		case 1:		//Left mouse button
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button, do nothing 
			break;
		}
	}

	/**
	 * Actions to do when the mouse is released
	 */
	public void mouseReleased(MouseEvent e) {
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame)c;
		MyPanel myPanel = (MyPanel) myFrame.getContentPane().getComponent(0);  //Can also loop among components to find MyPanel
		Insets myInsets = myFrame.getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		e.translatePoint(-x1, -y1);
		int x = e.getX();
		int y = e.getY();
		myPanel.x = x;
		myPanel.y = y;
		Color clickedCellColor = Color.LIGHT_GRAY;
		Color flagsColor = Color.RED;
		Color minesColor = Color.BLACK;
		Color originalCellColor = Color.WHITE;
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);

		switch (e.getButton()) {
		case 1:		//Left mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {//Had pressed outside the grid
				if(e.getX()>=myPanel.resetButtonX && e.getX()<=myPanel.resetButtonX+46 
					&& e.getY()>=myPanel.resetButtonY && e.getY()<=myPanel.resetButtonY+40){
					//If has clicked on the smiley or it surrounding rectangle, restart the game
						Main.restart();
				}
				else{
					//do nothing
				}
			}
			else { 
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside the grid, do nothing
				} 
				else { 
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {
						//Is releasing in a different cell that the one where mouse was pressed, nothing
					} 
					else { //releasing in the same cell
						//If the clicked cell has a value of 1, equivalent to a mine
						if (myPanel.valuesArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== myPanel.isMine){
							if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== flagsColor){
								//If the grid has a flag, do nothing., mine has been checked and cannot make the user lose.
							}
							else{
								//Lose the game and shows where all the mines where located.
								for(int i = 0; i<MyPanel.getColumns(); i++){
									for(int j = 0; j<MyPanel.getRows(); j++){
										if (myPanel.valuesArray[i][j]== myPanel.isMine)
											myPanel.colorArray[i][j] = minesColor;
										if(myPanel.colorArray[i][j] == flagsColor)
											myPanel.colorArray[i][j] = minesColor;
									}
								}
								myPanel.repaint();
								int lose = JOptionPane.showConfirmDialog(null, "YOU LOSE THE GAME \n NEW GAME?", "", JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
								if( lose == JOptionPane.YES_NO_OPTION){
									Main.restart();
								}
								if (lose == JOptionPane.NO_OPTION){
									System.exit(0);
								}
							}
						}
						else{//Clicked cell does not have a value of 1 (is not a mine)
							if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== clickedCellColor){
								//If cell has already been clicked, do nothing
							}
							else if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== flagsColor){
								//If the cell has a flag, do nothing
							}
				
							else{ //the cell is white
								if(myPanel.hasNumber(myPanel.mouseDownGridX, myPanel.mouseDownGridY)){
										myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = clickedCellColor;
										myPanel.repaint();
								}
								else{ //Cell is not a mine, has 0 adjacent mines, is not a flag and is white
									myPanel.noAdjacentMines(myPanel.mouseDownGridX, myPanel.mouseDownGridY);
								}
							}

							if(myPanel.winGame()){
								//Checks if the user have win the came 
								int win = JOptionPane.showConfirmDialog(null, "YOU WIN! \n NEW GAME?", "Nice job", JOptionPane.YES_NO_OPTION,
										JOptionPane.INFORMATION_MESSAGE);
								if( win == JOptionPane.YES_NO_OPTION){
									Main.restart();
								}
								if (win == JOptionPane.NO_OPTION){
									System.exit(0);
								}
							}
						}
					}
				}
			}
					
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside, do nothing
			} 
			else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside, do nothing
				} 
				else {
					if((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)){
						if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == clickedCellColor){
							//If cell has already been clicked with left mouse button, do nothing
						}
						else {  
							//To put a flag with a right click
							if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == originalCellColor){
								//If cell is still white, user choose to put a flag. 
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = flagsColor;
								myPanel.remainingMines--;
								myPanel.repaint();	
							}
							else{
								if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flagsColor){
									//If cell has a flag, user can undo it.
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = originalCellColor;
									myPanel.remainingMines++;
									myPanel.repaint();
								}
							}
						}
					}
				}
			}

		default: //Some other button, do nothing
			break;
		}
	}
}