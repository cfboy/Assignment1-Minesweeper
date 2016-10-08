import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper extends MouseAdapter {
	//private Random generator = new Random();
	public int minesAtTime = 10;    //variable para contar las bombas
	
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
			//Do nothing
			myPanel.mouseDownGridX = myPanel.getGridX(x, y);
			myPanel.mouseDownGridY = myPanel.getGridY(x, y);
			myPanel.repaint();
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}
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
		//Color para los cuadros que no tienen minas
		Color notMinesColor = Color.WHITE;
		//Color para las Flags.
		Color flagsColor = Color.RED;
		//TODO cuando se seleccione un cuadro con el valor
		//de una mina, etonces que se pinte de negro
		Color minesColor = Color.BLACK;
		Color quitFlags = Color.lightGray;
		int mines = myPanel.mines;
		

		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);

		switch (e.getButton()) {
		case 1:		//Left mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) {


					} else {
						//if para que los flags no se eliminen con un left click
						if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flagsColor){
							// Do nothing
						}else{
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = notMinesColor;
							myPanel.repaint();
						}
						if (myPanel.minesArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== 1){
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = minesColor;
							JOptionPane.showMessageDialog(null, "You clicked on a bomb!", "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
							
						}
					}
				}
			}
			myPanel.repaint();
			break;
		case 3:		//Right mouse button
			// FLAGS
			//TODO
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			} else {
				if ((gridX == -1) || (gridY == -1)) {
					//Is releasing outside
					//Do nothing
				} else {
					//Cuando haces click en un grid
					if((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)){
						//Si ese grid es color blanco no hagas nada
						if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == notMinesColor){
							//Do nothing
						}
						else {  
							//Si ese grid es color gris entonces poner el flag
							if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == quitFlags){
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = flagsColor;
								this.minesAtTime--;
								myPanel.minesAtTime = this.minesAtTime + "";
								myPanel.repaint();	
								
											  
							}
							else{
								//si ese grid tiene un flag entonces puedes quitarla
								if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flagsColor){
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = quitFlags;
								this.minesAtTime++;
								myPanel.minesAtTime = this.minesAtTime + ""; 
								myPanel.repaint();
								
								}
							}
								
						}
					}
				}
			}

		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}
	}

}