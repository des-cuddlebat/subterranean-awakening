package net.cuddlebat.terrawa.item.trinket;

import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;

public class PotionBelt extends ModInventoryBelt
{

	public PotionBelt(Settings settings)
	{
		super(settings);
	}

	@Override
	public boolean canGoIn(ItemStack stack)
	{
		return stack.getItem() instanceof PotionItem;
	}

	@Override
	public int getSlotsMaxCount()
	{
		return 16;
	}
}
