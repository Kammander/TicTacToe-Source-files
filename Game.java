import java.util.Scanner;


public class Game 
{
	public Scanner playerInput;
	private Board board;
	private AI ai;
	
	public Game()
	{
		playerInput = new Scanner(System.in);
		board = new Board();
		ai = new AI(Letter.o);
	}
	
	public void Play() 
	{
		int i = 0;
		while (board.GetVictor() == Letter._ && i < (Board.NUM_ROWS * Board.NUM_COLS)) {
			if (i % 2 == 0) {
				Coordinates move = GetPlayerMove(board);
				board.Put(move);
				board.Victor(move);		
			}
			else {
				Coordinates move = ai.GetMove(board);
				board.Put(move);
				board.Victor(move);
			}
			i++;
		}
		board.Print();
		System.out.println(board.GetVictor() + " wins!");
	}
	
	public Coordinates GetPlayerMove(Board board)
	{
		int row;
		int col;
		board.Print();
		System.out.println("Enter a row");
		row = playerInput.nextInt();
		System.out.println("Enter a column");
		col = playerInput.nextInt();
		return new Coordinates(row, col);
	}
}
