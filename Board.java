
import java.util.ArrayList;


public class Board
{
	public static final int NUM_ROWS = 3;
	public static final int NUM_COLS = 3;

	// row, column
	private Letter[][] board;
	private Letter next;
	private Letter victor;
	
	public Board() 
	{
		board = new Letter[NUM_ROWS][NUM_COLS];
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				board[i][j] = Letter._;
			}
		}
		next = Letter.x;
		victor = Letter._;
	}
	
	public Letter GetNext() 
	{
		return next;
	}
	
	public Letter GetVictor()
	{
		return victor;
	}
	
	public Board GetCopy()
	{
		Board copy = new Board();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				copy.Put(new Coordinates(i, j), this.board[i][j]);
			}
		}
		copy.SetNext(this.next);
		return copy;
	}
	
	private void SetNext(Letter letter) 
	{
		next = letter;
	}
	
	public void Print()
	{
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				switch(board[i][j]) {
				case x:
					System.out.printf("%c", 'x');
					break;
				case o:
					System.out.printf("%c", 'o');
					break;
				case _:
					System.out.printf("%c", ' ');
				}
				if (j < NUM_COLS - 1) 
					System.out.printf("%c", '|');
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public boolean Put(Coordinates coordinates)
	{
		if (SquareOnBoard(coordinates.Row(), coordinates.Col()) &&
				SquareEmpty(coordinates)) {
			board[coordinates.Row()][coordinates.Col()] = next;
			next = Next();
			return true;
		}
		return false;
	}
	
	public boolean Put(Coordinates coordinates, Letter letter)
	{
		if (SquareOnBoard(coordinates.Row(), coordinates.Col()) &&
				SquareEmpty(coordinates)) {
			board[coordinates.Row()][coordinates.Col()] = letter;
			return true;
		}
		return false;
	}
	
	private boolean SquareEmpty(Coordinates coordinates) 
	{
		return board[coordinates.Row()][coordinates.Col()] == Letter._;
	}
	
	private Letter Next()
	{
		if (next == Letter.x) {
			return Letter.o;
		}
		return Letter.x;
	}
	
	private boolean SquareOnBoard(int r, int c) 
	{
		return RowInsideBoard(r) && ColInsideBoard(c);
	}
	
	private boolean RowInsideBoard(int row) 
	{
		return row < NUM_ROWS && row >= 0;
	}
	
	private boolean ColInsideBoard(int col) 
	{
		return col < NUM_COLS && col >= 0;
	}
	
	public boolean Full()
	{
		return EmptySquares().size() == 0;
	}
	
	public ArrayList<Coordinates> EmptySquares()
	{
		ArrayList<Coordinates> squares = new ArrayList<Coordinates>();
		for (int i = 0; i < NUM_ROWS; i++) {
			for (int j = 0; j < NUM_COLS; j++) {
				if (board[i][j] == Letter._) {
					squares.add(new Coordinates(i, j));
				}
			}
		}
		return squares;
	}
	
	public Letter Victor(Coordinates coordinates)
	{
		for (int i = coordinates.Row() - 1; i <= coordinates.Row() + 1; i++) {
			for (int j = coordinates.Col() - 1; j <= coordinates.Col() + 1; j++) {
				if (SquareOnBoard(i, j) && !(coordinates.Row() == i && coordinates.Col() == j)) {
					if (board[i][j].equals(board[coordinates.Row()][coordinates.Col()])) {
						int num = 2 + Direction(new Coordinates(i,j), i - coordinates.Row(), j - coordinates.Col(), 0)
									+ Direction(coordinates, -(i - coordinates.Row()), -(j - coordinates.Col()), 0);
						if (num >= 3) {
							victor = board[coordinates.Row()][coordinates.Col()];
							return board[coordinates.Row()][coordinates.Col()];
						}
					}
				}
			}
		}
		return Letter._;
	}	
	
	public int Direction(Coordinates last, int rowDirection, int colDirection, int num)
	{
		Coordinates next = new Coordinates(last.Row() + rowDirection, last.Col() + colDirection);
		if (!SquareOnBoard(next.Row(), next.Col())) {
			return num;
		}
		if (board[last.Row()][last.Col()] == board[next.Row()][next.Col()]) {
			return Direction(next, rowDirection, colDirection, num + 1);
		}
		else {
			return num;
		}		
	}
}
