package net.cuddlebat.terrawa.tests;

public class TwoInts
{
	int x;
	int y;
	
	public TwoInts(int x, int y)
	{
		super();
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TwoInts other = (TwoInts) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	public int getX()
	{
		return x;
	}

	public int getY()
	{
		return y;
	}
}
