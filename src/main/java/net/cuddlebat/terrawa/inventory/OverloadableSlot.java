package net.cuddlebat.terrawa.inventory;

import net.minecraft.container.Slot;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class OverloadableSlot extends Slot
{
	public OverloadableSlot(Inventory inventory_1, int int_1, int int_2, int int_3)
	{
		super(inventory_1, int_1, int_2, int_3);
	}
	
	@Override
	public ItemStack takeStack(int amount)
	{
		amount = Math.min(amount, getStack().getMaxCount());
		return super.takeStack(amount);
	}
}
