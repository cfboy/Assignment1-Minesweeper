import java.awt.Color;
import java.awt.Component;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Minesweeper extends MouseAdapter
{
	private Random generator = new Random(); //Random object		
		
	
	
	//cuando se presiona el mouse con cualquiera de los botones
	public void mousePressed(MouseEvent e) 
	{
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) 
		{
			c = c.getParent();
			if (c == null) 
			{
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
		
		switch (e.getButton()) 
		{  //event busca que boton fue presionado
			case 1:		//Left mouse button //Busca en que posicion el mouse button fue presionado
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y); //posicion en la que se presiono el boton
				myPanel.repaint();
				break;
			case 3:		//Right mouse button
				//Do nothing
				myPanel.mouseDownGridX = myPanel.getGridX(x, y);
				myPanel.mouseDownGridY = myPanel.getGridY(x, y); //posicion en la que se presiono el boton
				myPanel.repaint();
				break;
			default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
				break;
		}//switch casos con que botn se aprieta
	}//metodo mousePressed
	
	public void mouseReleased(MouseEvent e) 
	{
		Component c = e.getComponent();
		while (!(c instanceof JFrame)) 
		{
			c = c.getParent();
			if (c == null) 
			{
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
		int gridX = myPanel.getGridX(x, y);
		int gridY = myPanel.getGridY(x, y); //posicion en la que se solto el mouse

		
				
		switch (e.getButton()) 
		{
		case 1:		//Left mouse button
			
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1))
			{ 
				//Had pressed outside the grid, Do nothing
			} 
			else 
			{//casos de donde se esta soltando el mouse si se presiono dentro del grid
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside, Do nothing
				} 
				else 
				{//casos si se suelta el mouse dentro del grid
					if ((myPanel.mouseDownGridX != gridX) || (myPanel.mouseDownGridY != gridY)) 
					{
						//Released the mouse button on a different cell where it was pressed...do nothing
		
					} 
					else 
					{
						//myPanel.colorArray[gridX][gridY]= Color.lightGray;
						for(int i=0; i<10; i++){
							for(int j=0; j<10; j++){
								if(myPanel.colorArray[i][j] == Color.BLACK){
									JOptionPane.showMessageDialog(null, "You clicked on a bomb!", "YOU LOSE", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
								}
							if(myPanel.colorArray[i][j]==Color.BLACK){
								myPanel.repaint();
							}
							
							}
						}
					}	
				}
			}
			myPanel.repaint();
			break;
			
			
			
			
		case 3:		//Right mouse button
			if ((myPanel.mouseDownGridX == -1) || (myPanel.mouseDownGridY == -1)) 
			{ 
				//Had pressed outside the grid, Do nothing
			} 
			else 
			{//casos de donde se esta soltando el mouse si se presiono dentro del grid
				if ((gridX == -1) || (gridY == -1)) 
				{
					//Is releasing outside, Do nothing
				} 
				else 
				{//casos si se suelta el mouse dentro del grid
					if ((myPanel.mouseDownGridX == gridX) && (myPanel.mouseDownGridY == gridY)) 
					{
					myPanel.colorArray[gridX][gridY] = Color.RED;
					myPanel.repaint();
					}
				}
			}
			break;
		default:    //Some other button (2 = Middle mouse button, etc.)
			//Do nothing
			break;
		}//switch casos de que boton del moouse se apriea
	}//metodo mouseReleased
}//clase