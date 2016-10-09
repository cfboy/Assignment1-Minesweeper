import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	static JLabel viewTime;


	public static void main(String[] args) {
		JFrame myFrame = new JFrame("Minesweeper Game");
		myFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(400, 400);

		MyPanel myPanel = new MyPanel();
		myFrame.getContentPane().add(myPanel);
		myPanel.setLayout(null);

		TimerCounter tasknew = new TimerCounter();
		Timer timer = new Timer();		
		viewTime = new JLabel();
		timer.schedule(new TimerTask(){
			
			public void run() {
				tasknew.doStuff();
				viewTime.setText("Time: "  + TimerCounter.getTime() +" seconds");
			}
		},100, 1000); 

		viewTime.setBounds(28, 300, 174, 15);
		
		viewTime.setHorizontalAlignment(SwingConstants.LEFT);
		viewTime.setFont(new Font("Arial", Font.BOLD, 12));
		myPanel.add(viewTime);

		Minesweeper myMouseAdapter = new Minesweeper();
		myFrame.addMouseListener(myMouseAdapter);

		myFrame.setVisible(true);

	}


}