package net.cuddlebat.terrawa.tests;

import java.util.ArrayList;

public class Banana
{
	private void banana()
	{
		ArrayList<Integer> list = new ArrayList<>();
		list.sort((a, b) -> { return Integer.compare(b, a); });
	}
}
