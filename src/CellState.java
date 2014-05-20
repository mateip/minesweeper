
public class CellState {
	
	private boolean isBomb;
	private int numOfBombsAround;
	boolean visited;
	boolean flagged;
		
	public void setAsBomb() 
	{
		isBomb = true;
	}
	
	public boolean isBomb()
	{
		return isBomb;
	}

	public String toString() {
		if (isBomb && flagged == false) {
				return "x(" + numOfBombsAround + ")";
		} 
		else if (visited == true && !flagged){
			if (numOfBombsAround == 0) {
				return "v   ";
			} else {
				return "v(" + numOfBombsAround + ")";
			}
		}
		else if ( flagged == true ) {
			return "f   ";
		}
		else {
			return "_(" + numOfBombsAround + ")";
		}
	}
	
	public void setNumberOfBombsAround(int num) {
		numOfBombsAround = num;
	}
	
	public int getNumberOfBombsAround() {
		return numOfBombsAround;
	}
}
