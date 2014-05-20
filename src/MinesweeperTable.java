import javax.swing.JOptionPane;


public class MinesweeperTable extends JOptionPane {

	private CellState[][] table;
	private int numRows, numCols;
	private int numBombs;
	private boolean gameOver;
	private boolean gameWon;


	public MinesweeperTable(int numRows, int numCols, int numBombs)
	{
		this.numRows = numRows;
		this.numCols = numCols;
		this.numBombs = numBombs;

		table = new CellState[numRows][numCols];

		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				table[i][j] = new CellState();
			}
		}

		
	}

	public boolean getGameOver() {
		return gameOver;
	}
	
	public boolean getGameWon() {
		return gameWon;
	}
	public int getNumOfBombsInGame() {
		return numBombs;
	}
	//prints game to console
	public void print(){
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}

	//randomizes the locations of the bombs
	public void setBombs() {
		for (int i = 0; i < numBombs; ) {
			int row = (int)(Math.random() * numRows);
			int col = (int)(Math.random() * numCols);
			if (!table[row][col].isBomb()){
				table [row][col].setAsBomb();
				i++;
			}
		}
	}

	//computes the number of bombs around cells. Sets that number to the cell.
	public void computeNumberOfBombs() {
		for (int row = 0; row < numRows; row++) {
			for (int col = 0; col < numCols; col++) {
				int n = 0;
				n += isBombAt(row - 1, col - 1);
				n += isBombAt(row - 1, col);
				n += isBombAt(row - 1, col + 1);
				n += isBombAt(row, col - 1);
				n += isBombAt(row, col + 1);
				n += isBombAt(row + 1, col - 1);
				n += isBombAt(row + 1, col);
				n += isBombAt(row + 1, col + 1);
				table[row][col].setNumberOfBombsAround(n);
			}
		}
	}

	//checks to see if there is a bomb at a particular cell. Returns 1 if bomb, 0 if not.
	public int isBombAt(int row, int col) {
		if (row >= 0 && row < numRows &&
				col >= 0 && col < numCols &&
				table[row][col].isBomb()) {
			return 1;
		} else {
			return 0;
		}
	}

	//returns the number of bombs around a particular cell. MinesweeperTable version of getNumberOfBombsAround() from CellState 
	public int getNumberOfBombsAround(int row, int col){
		return table[row][col].getNumberOfBombsAround();
	}

	
	public boolean getVisited(int row, int col) {
		return table[row][col].visited;
	}
	

	public void setVisited(int row, int col, boolean b) {
		table[row][col].visited = b;
	}

	public void click(int row, int col) {
		table[row][col].visited = true;
		if (table[row][col].isBomb()){
			gameOver = true;
			gameWon = false;
		} else {
			if (table[row][col].getNumberOfBombsAround() == 0) {
				expand(row, col);
			}
			checkIfGameIsOver();
		}
		
	}
	
	public void checkIfGameIsOver() {
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j<numCols; j++) {
				if (!table[i][j].visited && !table[i][j].isBomb()) {
					return;
				}
			}
		}
		gameOver = true;
		gameWon = true;
	}
		
	public void flag(int row, int col) {
		if (!table[row][col].visited) {
			table[row][col].flagged = true;
		}
	}

	public void unFlag(int row, int col) {
		table[row][col].flagged = false;
	}
	
	public boolean isFlagged(int row, int col) {
		return table[row][col].flagged;
	}

	public void expand(int row, int col) {
		table[row][col].visited = true;
		if (table[row][col].getNumberOfBombsAround() != 0) {
			return;
		}
		if (row-1 >= 0 && !table[row-1][col].isBomb() && !table[row -1][col].visited){

			expand(row - 1, col);
		}
		if (row + 1 < numRows && !table[row +1][col].isBomb() && !table[row + 1][col].visited){
			expand(row + 1, col);
		}
		if (col - 1 >= 0 && !table[row][col - 1].isBomb() && !table[row][col - 1].visited){
			expand(row, col - 1);
		}
		if (col + 1 < numCols && !table[row][col + 1].isBomb() && !table[row][col + 1].visited){
			expand(row, col + 1);
		}
	}
}
