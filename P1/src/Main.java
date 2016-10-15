import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Mian class for the project. 
 * @author Gladymar O'Neill
 *@author Cristian F. Torres Collazo
 */
public class Main {
	static JFrame myFrame = new JFrame("Minesweeper Game");
	static MyPanel myPanel = new MyPanel();
	static Minesweeper myMouseAdapter = new Minesweeper();

	public static void main(String[] args) {
		
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(360, 410);
		
		//Information about the game to be shown to the user
		JLabel instructions = new JLabel("NOTE: For new game, click on the smiley face"); 
		instructions.setBounds(2, 306, myFrame.getWidth()-2, myFrame.getHeight()-300);
		instructions.setHorizontalAlignment(SwingConstants.CENTER);
		instructions.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel instructions1 = new JLabel("TO WIN: Uncover all squares withouth mines."); 
		instructions1.setBounds(2, 280, myFrame.getWidth()-2, myFrame.getHeight()-300);
		instructions1.setHorizontalAlignment(SwingConstants.CENTER);
		instructions1.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel instructions2 = new JLabel("YOU LOSE: when you click on a mine. "); 
		instructions2.setBounds(2, 293, myFrame.getWidth()-2, myFrame.getHeight()-300);
		instructions2.setHorizontalAlignment(SwingConstants.CENTER);
		instructions2.setFont(new Font("Arial", Font.BOLD, 12));
	
		myPanel.add(instructions1);
		myPanel.add(instructions2);
		myPanel.add(instructions);
		
		myFrame.getContentPane().add(myPanel);
		myPanel.setLayout(null);
		myFrame.addMouseListener(myMouseAdapter);
		myFrame.setVisible(true);

	}

	/**
	 * Method clears the frame and paints a 
	 * new MyPanel with its variables set to
	 * their initial values. 
	 */
	public static void restart(){
		myFrame.setVisible(false);
		myPanel.resetGame();
		myFrame.setVisible(true);
	}

}