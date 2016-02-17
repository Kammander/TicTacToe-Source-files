
public class MoveValue 
{
	private Coordinates move;
	private int value;
	
	public MoveValue(Coordinates m, int v)
	{
		move = m;
		value = v;
	}
	
	public Coordinates Move()
	{
		return move;
	}
	
	public int Value()
	{
		return value;
	}
}
