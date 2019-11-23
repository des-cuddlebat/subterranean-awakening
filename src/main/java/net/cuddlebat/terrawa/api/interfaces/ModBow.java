package net.cuddlebat.terrawa.api.interfaces;

import net.minecraft.item.BowItem;

public abstract class ModBow extends BowItem
{

	public ModBow(Settings settings)
	{
		super(settings);
	}
	
	public abstract int getPullTicks();

}
