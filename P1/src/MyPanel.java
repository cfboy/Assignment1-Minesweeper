import java.awt.Color;
import java.awt.Graphics;
import java.awt.Insets;
import java.util.Random;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	//	private Random generator = new Random();
	//Decalracion de variables estaticas
	private static final long serialVersionUID = 3426940946811133635L;
	private static final int GRID_X = 25;
	private static final int GRID_Y = 25;
	private static final int INNER_CELL_SIZE = 29;
	private static final int TOTAL_COLUMNS = 9;
	private static final int TOTAL_ROWS = 9;   //Last row has only one cell
	private static final int TOTAL_MINES =10; 

	public int x = -1;
	public int y = -1;
	public int mouseDownGridX = 0;
	public int mouseDownGridY = 0;
	//declaradas para probar los numeros
	public int amountOfNearMines = 0;
	public Color[][] cells = new Color[TOTAL_COLUMNS][TOTAL_ROWS];

	public Color[][] colorArray = new Color[TOTAL_COLUMNS][TOTAL_ROWS]; //array de dos dimensiones

	public int[][] minesArray = new int[TOTAL_COLUMNS][TOTAL_ROWS];
	public String minesAtTime; 
	public int mines = 0;
	//public Color colorMines = Color.BLACK;
	public int colorMines = 1;

	Minesweeper m = new Minesweeper(); //variable tipo minesweeper parapode rutilizar el valor dela variable minesAtTime
	//que se manipulo en la clase  Minesweeper
	public int adjacentMines = 0;
	int i, j; //variables para los loops de center grid

	public MyPanel() {   //This is the constructor... this code runs first to initialize

		//exceptions si los parametros de tama-o no se cumplen
		//exceptions si los parametros de tama-o no se cumplen
		if (INNER_CELL_SIZE + (new Random()).nextInt(1) < 1) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("INNER_CELL_SIZE must be positive!");
		}
		if (TOTAL_COLUMNS + (new Random()).nextInt(1) < 2) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_COLUMNS must be at least 2!");
		}
		if (TOTAL_ROWS + (new Random()).nextInt(1) < 3) {	//Use of "random" to prevent unwanted Eclipse warning
			throw new RuntimeException("TOTAL_ROWS must be at least 3!");
		}


		//doble loop para pintar todos los cuadros color gris
		for (int x = 0; x < TOTAL_COLUMNS; x++) {   //The rest of the grid
			for (int y = 0; y < TOTAL_ROWS; y++) {
				colorArray[x][y] = Color.WHITE;
			}
		}

		minesGenerator();
	}


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
		g.setColor(new Color(0xF5F5DC));
		g.fillRect(x1, y1, width + 1, height + 1);


		//Draw the grid minus the bottom row (which has only one cell)
		//By default, the grid will be 10x10 (see above: TOTAL_COLUMNS and TOTAL_ROWS) 
		//g.setColor(colorMines);
		//dibujar las lineas que dividen cada recuadro del grid 9x9
		for (int y = 0; y <= TOTAL_ROWS ; y++) {
			g.drawLine(x1 + GRID_X, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)), x1 + GRID_X + ((INNER_CELL_SIZE + 1) * TOTAL_COLUMNS), y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)));
		}//lineas horizontales
		for (int x = 0; x <= TOTAL_COLUMNS; x++) {
			g.drawLine(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y , x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)), y1 + GRID_Y + ((INNER_CELL_SIZE + 1) * (TOTAL_ROWS )));
		}//lineas verticales


		//Paint cell colors
		for (int x = 0; x < TOTAL_COLUMNS; x++) {
			for (int y = 0; y < TOTAL_ROWS; y++) {
				if ((x == 0) || (y != TOTAL_ROWS )) {
					Color c = colorArray[x][y];
					g.setColor(c);
					g.fillRect(x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 1, y1 + GRID_Y + (y * (INNER_CELL_SIZE + 1)) + 1, INNER_CELL_SIZE, INNER_CELL_SIZE);

				}
			}
		}


		minesAtTime = m.minesAtTime + "";
		g.drawString("Remaining mines:"+ minesAtTime, x1+200, y1+20);

		//draws the number of adjacent mines to the square specified
		g.setColor(Color.BLUE);
		//Todos los casos para imprimir la cantidad de minas adyacentes
		for(int x=0;x<=8; x++){
			for(int y=0; y<=8 ;y++){
				//right top corner
				if(x==0 && y==0){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y+1]==colorMines){adjacentMines++;}
					}
				}
				//left top corner
				if(x==8 && y==0){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y+1]==colorMines){adjacentMines++;}
					}
				}
				// right bottom corner
				if(x==0 && y==8){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
					}
				}
				// left bottom corner
				if(x==8 && y==8){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y-1]==colorMines){adjacentMines++;}
					}
				}
				//center comparation
				if((x>=1 && x<=7) && (y>=1 && y<=7)){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x-1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y+1]==colorMines){adjacentMines++;}
					}
				}
				//left column
				if (x == 0 && (y>=1 && y<=7)){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
					}
				}
				//right column
				if (x == 8 && (y>=1 && y<=7)){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
					}
				}
				//				//top row
				if (y == 0 && (x>=1 && x<=7)){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y+1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
					}
				}
				//bottom row
				if (y == 8 && (x>=1 && x<=7)){
					if(minesArray[x][y]!=colorMines){
						if(minesArray[x-1][y]==colorMines){adjacentMines++;}
						if(minesArray[x-1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y-1]==colorMines){adjacentMines++;}
						if(minesArray[x+1][y]==colorMines){adjacentMines++;}
					}
				}
				//Asignando los diferentes colores a la cantidad de minas adyacentes 
				if(adjacentMines == 0) {
					g.drawString("", x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);
				}
				if(adjacentMines == 1){g.setColor(Color.BLUE);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 2){g.setColor(Color.GREEN);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 3){g.setColor(Color.RED);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 4){g.setColor(Color.BLUE);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 5){g.setColor(Color.RED);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 6){g.setColor(Color.GREEN);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}

				if(adjacentMines == 7){g.setColor(Color.BLUE);
				g.drawString(""+adjacentMines, x1 + GRID_X + (x * (INNER_CELL_SIZE + 1)) + 12, y1 + +5 +GRID_Y + (y * (INNER_CELL_SIZE + 1)) +12);}


				adjacentMines = 0;	
			}
		}

	}

	/*verifies that there are ten mines and the method 
	 * randomMines() did not repeat the same square in 
	 * the grid*/
	public void minesGenerator(){
		tenMines();
		do{
			for (int x=0; x<TOTAL_COLUMNS; x++){
				for (int y=0; y<TOTAL_ROWS; y++){
					if(minesArray[x][y] == 1){
						//					if(colorArray[x][y] == colorMines){
						mines++;

					}
				}
			}
			if(mines!= TOTAL_MINES){
				randomMines();
			}
		}while(mines!=TOTAL_MINES);
	}

	//colors a random square in the grid black, equivalent to a mine
	public void randomMines(){
		Random r = new Random();
		int x = r.nextInt(TOTAL_COLUMNS);
		int y = r.nextInt(TOTAL_ROWS);

		//		colorArray[x][y] = colorMines;
		minesArray[x][y] = 1;
		//repaint();
	}

	//places 10 random mines 
	public void tenMines(){
		for(int i = 0; i<10; i++){
			randomMines();
		}
	}


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
		if (x < 0 || x > TOTAL_COLUMNS  || y < 0 || y > TOTAL_ROWS ) {   //Outside the rest of the grid
			return -1;
		}
		return x;
	}

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
		if (x < 0 || x > TOTAL_COLUMNS  || y < 0 || y > TOTAL_ROWS ) {   //Outside the rest of the grid
			return -1;
		}
		return y;
	}

}