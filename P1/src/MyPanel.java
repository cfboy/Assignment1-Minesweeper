import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;
/** Class to create all components of a JPanel
 * 
 * @author Gladymar O'Neill Rivas
 * @author Cristian F. Torres Collazo 
 * 
 */

public class MyPanel extends JPanel {
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 40;
	private static final int GRID_Y = 45;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	public static final int TOTAL_ROWS = 9;  
	public static final int TOTAL_MINES =10; 

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS]; 
	public int[][] valuesArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public int isMine = 1; //assigns 1 to a cell that is mine 
	public int remainingMines=10; //Initial counter of flags that can be used in the game.
	public int resetButtonX = 140; // x coordinate of where the smiley face is being drawn
	public int resetButtonY= 8; // y coordinate of where the smiley face is being drawn
	/**
	 * Constructor: As the object is created, it paints all cells if the grid white
	 * and generates the total number of random mines.
	 */
	public MyPanel() {  

		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}

		//Paints every cell of the grid color White
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		mines();
	}

	/**
	 * Method to add graphics and colors to the JPanel
	 * 
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Compute interior coordinates
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		int x2 = getWidth() - myInsets.right - 1;
		int y2 = getHeight() - myInsets.bottom - 1;
		int width = x2 - x1;
		int height = y2 - y1;

		//Paint the background
		g.setColor(new Color(0x7CB9E8));
		g.fillRect(x1, y1, width + 1, height + 1);

		//Paint rectangle on top of the grid
		g.setColor(Color.BLACK);
		g.drawRect(GRID_X,8, 30*9,GRID_Y);
		g.setColor(new Color(0x848482)); // Battleship Grey https://en.wikipedia.org/wiki/List_of_colors:_A–F
		g.fillRect(GRID_X,8, 30*9,GRID_Y);
		
		//Rectangle for Smiley Face
		g.setColor(Color.BLACK);
		g.drawRect(GRID_X+113 ,8,46 ,GRID_Y);
		g.setColor(Color.lightGray);
		g.fillRect(GRID_X+113 ,8,46 ,GRID_Y);

		//Draw a Smiley Face
		g.setColor(Color.BLACK);
		g.drawOval(GRID_X+121, 10, 30, 30);
		g.setColor(Color.YELLOW);
		g.fillOval(GRID_X+121, 10, 30, 30);
		g.setColor(Color.BLACK);
		g.fillOval(GRID_X+125, 16, 9, 9);
		g.fillOval(GRID_X+137, 16, 9,9);
		g.drawArc(GRID_X+126, 24, 20, 10, 180, 180); 

		// Rectangle to separate the string "Mines:"
		g.drawRect(GRID_X + 180 ,8, 90 ,GRID_Y-8);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(GRID_X + 180 ,8, 90 ,GRID_Y-8);

		//Print the string corresponding to the remaining mines that have not been flagged. 
		g.setColor(new Color(0xC40233)); //RED NCS https://en.wikipedia.org/wiki/Shades_of_red#Red_.28NCS.29_.28psychological_primary_red.29
		g.setFont(new Font("Arial", Font.BOLD, 18));
		g.drawString("Mines:"+ remainingMines, x1+225, y1+31);

		//Draw the lines that make the grid of the specified size (TOTAL_COLUMNSxTOTAL_ROWS)
		g.setColor(Color.BLACK);
		for (int y = 0; y <= TOTAL_ROWS ; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y , x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
		}

		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS )) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);

					//Add a string equivalent to the number of adjacent mines if is greater than 0
					if (colorArray[x][y].equals(Color.LIGHT_GRAY)) {
						int adjacentMines = adjacentMinesCalculator(x, y);

						if(adjacentMines == 1){g.setColor(Color.BLUE);}
						if(adjacentMines == 2){g.setColor(new Color(0x00A550));} //Source:https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M
						if(adjacentMines == 3){g.setColor(Color.RED);}
						if(adjacentMines == 4){g.setColor(new Color(0x663854));} //Source:https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M
						if(adjacentMines == 5){g.setColor(new Color(0x960018));} //Source:https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M
						if(adjacentMines == 6){g.setColor(new Color(0x30BFBF));} //Source:https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M
						if(adjacentMines == 7){g.setColor(new Color(0x343434));} //Source:https://en.wikipedia.org/wiki/List_of_colors:_G%E2%80%93M
						if(adjacentMines>0){
							g.drawString("" + adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 21);
						}
					}
				}
			}
		}
	}

	/**Assigns the int value 1(equivalent to a mine)
	 *  to a random cell of the array.
	 */
	public void randomMines(){
		Random r = new Random();
		int x = r.nextInt(TOTAL_COLUMNS);
		int y = r.nextInt(TOTAL_ROWS);
		valuesArray[x][y] = 1;
	}

	/**Repeat the method randomMines() until there 
	 * are total amount of mines wanted and no random 
	 * number was repeated.
	 */
	public void mines(){
		int mines=0;
		for(int i=0; i<TOTAL_MINES; i++){
			randomMines();
		}
		for(int m=0; m<TOTAL_COLUMNS; m++){
			for(int n=0; n<TOTAL_ROWS; n++){
				if(valuesArray[m][n]==isMine){mines++;}
			}
		}
		while(mines!=TOTAL_MINES){
			randomMines();
			mines++;
		}
	}

	/** Method calculates how many mines are adjacent
	 * to the cell[x][y] in the array. (Its surrounding
	 * 8 squares)
	 * 
	 * @param x, x coordinate
	 * @param y, ycoordinate
	 * @return amount of adjacent mines to that cell
	 */
	public  int adjacentMinesCalculator(int x, int y) {		
		int adjacentMines = 0;
		for(int i = x-1; i <= x+1; i++) {
			for(int j = y-1; j <= y+1; j++) {
				if(i >=0 && i<TOTAL_COLUMNS && j>=0 && j <TOTAL_ROWS && valuesArray[i][j]==isMine) {
					adjacentMines++;
				}
			}
		}
		return adjacentMines;
	}

	/**
	 * Method verifies that the cell[x][y] of the array
	 * has adjacent mines greater than 0. 
	 * 
	 * @param x, x coordinate
	 * @param y, y coordinate
	 * @return true or false
	 */
	public boolean hasNumber(int x, int y) {
		return adjacentMinesCalculator(x,y)>0;
	}

	/**
	 * Recursive method to check and clear every adjacent cell to 
	 * the cell[x][y] that have adjacent mines = 0, until reaching 
	 * a cell that has  a number of adjacent mines>0. 
	 * @param x, x coordinate
	 * @param y, y coordinate
	 */
	public void noAdjacentMines(int x, int y){
		if(!hasNumber(x,y)){
			for(int i=x-1; i<=x+1; i++){
				for (int j=y-1; j<=y+1; j++){
					if( !(i<0 || i>=TOTAL_COLUMNS || j<0 || j>=TOTAL_ROWS) &&
							colorArray[i][j]==Color.WHITE){
						colorArray[i][j]=Color.LIGHT_GRAY;
						noAdjacentMines(i,j);
					}
				}
			}
		}
	}

	public static int getColumns(){
		return TOTAL_COLUMNS;
	}

	public static int getRows(){
		return TOTAL_ROWS;
	}

	public static int getNumberOfMines(){
		return TOTAL_MINES;
	}

	/**
	 * Method to get what column in the grid is being manipulated.
	 * @param x
	 * @param y 
	 * @return the value of the column as an int
	 */
	public int getGridX(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > (TOTAL_COLUMNS-1)  || y < 0 || y > (TOTAL_ROWS-1) ) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}

	/**
	 * Method to get what row of the grid is being manipulated.
	 * @param x
	 * @param y
	 * @return the value of the row as an int
	 */
	public int getGridY(int x, int y) {
		Insets myInsets = getInsets();
		int x1 = myInsets.left;
		int y1 = myInsets.top;
		x = x - x1 - GRID_X;
		y = y - y1 - GRID_Y;
		if (x < 0) {   //To the left of the grid
			return -1;
		}
		if (y < 0) {   //Above the grid
			return -1;
		}
		if ((x % (INNER_CELL_SIZE + 1) == 0) || (y % (INNER_CELL_SIZE + 1) == 0)) {   //Coordinate is at an edge; not inside a cell
			return -1;
		}
		x = x / (INNER_CELL_SIZE + 1);
		y = y / (INNER_CELL_SIZE + 1);
		if (x < 0 || x > (TOTAL_COLUMNS-1)  || y < 0 || y > (TOTAL_ROWS -1)) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

	/**
	 * Method resets all the variables or methods in use to 
	 * their initial value.
	 */
	public void resetGame(){
		x = -1;
		y = -1;
		mouseDownGridX = 0;
		mouseDownGridY = 0;
		remainingMines=10;
		colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS]; 
		valuesArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}
		mines();
	}

	/**
	 * Method calculates the amount of cells that have been cleared.
	 * if it is equal to the total cells in the grid minus the mines,
	 * the user wins the game. 
	 * 
	 * @return true or false
	 */
	public boolean winGame(){
		int okCells=0; 
		for (int i=0; i<TOTAL_COLUMNS; i++){
			for(int j=0; j<TOTAL_ROWS; j++){
				if(colorArray[i][j]==Color.LIGHT_GRAY){okCells++;} 
			}
		}
		return okCells==(TOTAL_COLUMNS*TOTAL_ROWS -TOTAL_MINES);

	}
}