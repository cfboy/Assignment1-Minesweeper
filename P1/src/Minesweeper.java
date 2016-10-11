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
		Color notMinesColor = Color.LIGHT_GRAY;
		Color flagsColor = Color.RED;
		Color minesColor = Color.BLACK;
		Color quitFlagsColor = Color.WHITE;
		int mines = myPanel.mines;
		
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y);

		switch (e.getButton()) {
		case 1:		//Left mouse button

			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) {
				//Had pressed outside
				//Do nothing
			}else {
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
							//Cuando se haga click en un grid que tenga una mina
						if (myPanel.minesArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== 1){
							// Si el grid tiene un flag, no hace nada.
							if (myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY]== flagsColor){
							//do Nothing
							}
							//si el grid no tiene un flag, pierde el juego.
							else{
							myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = minesColor;
							JOptionPane.showMessageDialog(null, "You clicked on a bomb!", "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
							System.exit(0);
							}

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
						//Si ese grid no ya lo clickeaste/no hay bomba (es gris) no se pone un flag
						if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == notMinesColor){
							//Do nothing
						}
						else {  
							//Si ese grid no lo has clickeado (blanco) entonces puedes poner un flag
							if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == quitFlagsColor){
								myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = flagsColor;
								this.minesAtTime--;
								myPanel.minesAtTime = this.minesAtTime + "";
								myPanel.repaint();	
							}
							else{
								//si ese grid tiene un flag entonces puedes quitarla
								if(myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] == flagsColor){
									myPanel.colorArray[myPanel.mouseDownGridX][myPanel.mouseDownGridY] = quitFlagsColor;
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