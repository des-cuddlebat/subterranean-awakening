package net.cuddlebat.terrawa.item;

import net.cuddlebat.terrawa.api.interfaces.ITrinketWithSlotInfo;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public abstract class ModInventoryBelt extends Item implements ITrinketWithSlotInfo
{
	public ModInventoryBelt(Settings settings)
	{
		super(settings);
	}
	
	@Override
	public boolean canWearInSlot(String group, String slot)
	{
		return "belt".equals(slot);
	}
	
	@Override
	public String getSlotNameForTooltip()
	{
		return "Belt";
	}

	public abstract boolean canGoIn(ItemStack stack);

	public abstract int getSlotsMaxCount();
}
