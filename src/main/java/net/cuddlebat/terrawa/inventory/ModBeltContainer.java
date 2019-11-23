package net.cuddlebat.terrawa.inventory;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.DefaultedList;

public class ModBeltContainer extends Container
{
	private final Inventory inventory;
	ItemStack belt;

	public ModBeltContainer(int sync, PlayerInventory pinv)
	{
		super(null, sync);
		belt = TrinketsApi.getTrinketComponent(pinv.player).getStack(Const.TrinketSlot.BELT);
		if (!(belt.getItem() instanceof ModInventoryBelt))
		{
			throw new RuntimeException("belt ItemStack not instancof ModInventoryBelt!!");
		}
		
		ModInventoryBelt beltItem = (ModInventoryBelt) belt.getItem();

		DefaultedList<ItemStack> list = DefaultedList.ofSize(5, ItemStack.EMPTY);
		if (!belt.getOrCreateTag().containsKey("Items"))
			belt.getTag().put("Items", new ListTag());
		Inventories.fromTag(belt.getTag(), list);
		ItemStack[] stacks = new ItemStack[5];

		for (int i = 0; i < 5; ++i)
		{
			stacks[i] = list.get(i);
		}

		inventory = new BasicInventory(stacks)
			{
				public void markDirty()
				{
					super.markDirty();
					ModBeltContainer.this.onContentChanged(this);
				}
			};
		inventory.onInvOpen(pinv.player);

		for (int col = 0; col < 5; ++col)
		{
			this.addSlot(new OverloadableSlot(inventory, col, 44 + col * 18, 20)
				{
					public int getMaxStackAmount()
					{
						return beltItem.getSlotsMaxCount();
					}

					public boolean canInsert(ItemStack stack)
					{
						return ((ModInventoryBelt) belt.getItem()).canGoIn(stack);
					}
				});
		}

		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				this.addSlot(new Slot(pinv, col + row * 9 + 9, 8 + col * 18, row * 18 + 51));
			}
		}

		for (int col = 0; col < 9; ++col)
		{
			this.addSlot(new Slot(pinv, col, 8 + col * 18, 109));
		}

	}

	@Override
	public void close(PlayerEntity player)
	{
		DefaultedList<ItemStack> stacks = DefaultedList.ofSize(5, ItemStack.EMPTY);

		for (int i = 0; i < 5; ++i)
		{
			stacks.set(i, inventory.getInvStack(i));
		}

		belt.setTag(Inventories.toTag(belt.getOrCreateTag(), stacks));

		inventory.onInvClose(player);
		super.close(player);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

}
