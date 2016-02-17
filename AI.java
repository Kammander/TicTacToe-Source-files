
import java.util.ArrayList;


public class AI 
{
	private Letter aiLetter;
	
	public AI(Letter letter)
	{
		aiLetter = letter;
	}
	
	public Coordinates GetMove(Board board)
	{
		return GetHighestValue(board.EmptySquares(), board).Move();
	}
	
	public MoveValue GetHighestValue(ArrayList<Coordinates> moves, Board board)
	{
		if (moves.size() == 0) {
			return new MoveValue(new Coordinates(-2, -2), -2);
		}
		int highest = GetValue(moves.get(0), board);
		Coordinates best = moves.get(0);
		for (int i = 1; i < moves.size(); ++i) {
			int value = GetValue(moves.get(i), board);
			if (board.GetNext() == aiLetter) {
				if (value > highest) {
					highest = value;
					best = moves.get(i);
				}
			}
			else { // == playerLetter
				if (value < highest) {
					highest = value;
					best = moves.get(i);
				}
			}
		}
		return new MoveValue(best, highest);
	}
	
	public int GetValue(Coordinates move, Board board) 
	{
		Board next = board.GetCopy();
		next.Put(move);
		if (next.Victor(move) != Letter._) {
			if (next.GetNext() != aiLetter) {
				return 1;
			}
			return -1;
		}
		if (next.Full()) {
			return 0;
		}
		return GetHighestValue(next.EmptySquares(), next).Value();
	}
}
