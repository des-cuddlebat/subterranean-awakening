package net.cuddlebat.terrawa.tests;

public class ParentedTwoInts
{
	private TwoInts parent;
	private TwoInts self;
	
	public ParentedTwoInts(TwoInts parent, TwoInts self)
	{
		super();
		this.parent = parent;
		this.self = self;
	}

	public TwoInts getParent()
	{
		return parent;
	}

	public TwoInts getSelf()
	{
		return self;
	}
}
