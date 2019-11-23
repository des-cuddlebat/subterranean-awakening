package net.cuddlebat.terrawa.utils;

public class IntRange
{
	private int min;
	private int max;

	public IntRange(int min, int max)
	{
		super();
		this.min = min;
		this.max = max;
	}

	public int getMin()
	{
		return min;
	}

	public int getMax()
	{
		return max;
	}

	public int difference()
	{
		return max - min;
	}
}
