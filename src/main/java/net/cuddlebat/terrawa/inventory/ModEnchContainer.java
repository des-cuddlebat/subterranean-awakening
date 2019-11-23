package net.cuddlebat.terrawa.inventory;

import java.util.Optional;

import net.minecraft.container.BlockContext;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

public class ModEnchContainer extends Container
{
	private final Inventory inventory;
	private final BlockContext context;

	private IEnchAction action;
	private boolean isValid;

	protected ModEnchContainer(int sync, PlayerInventory pinv)
	{
		this(sync, pinv, BlockContext.EMPTY);
	}

	public ModEnchContainer(int sync, PlayerInventory pinv, BlockContext context)
	{
		super(null, sync);

		this.context = context;
		this.inventory = new BasicInventory(3)
			{
				public void markDirty()
				{
					super.markDirty();
					ModEnchContainer.this.onContentChanged(this);
				}
			};

		this.addSlot(new Slot(this.inventory, 0, 39, 20));
		this.addSlot(new Slot(this.inventory, 1, 80, 20)
			{
				public int getMaxStackAmount()
				{
					return 1;
				}

				public boolean canInsert(ItemStack stack)
				{
					return stack.isEnchantable() || stack.hasEnchantments();
				}
			});
		this.addSlot(new Slot(this.inventory, 2, 121, 20)
			{
				public int getMaxStackAmount()
				{
					return 1;
				}

				public boolean canInsert(ItemStack stack)
				{
					return stack.getItem() == Items.BOOK || stack.getItem() == Items.ENCHANTED_BOOK;
				}
			});

		for (int row = 0; row < 3; ++row)
		{
			for (int col = 0; col < 9; ++col)
			{
				this.addSlot(new Slot(pinv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}

		for (int col = 0; col < 9; ++col)
		{
			this.addSlot(new Slot(pinv, col, 8 + col * 18, 142));
		}
	}

	public Optional<IEnchAction> maybeGetAction()
	{
		if (hasValidAction())
			return Optional.of(action);
		return Optional.empty();
	}

	public Optional<IEnchAction> maybeGetHint()
	{
		if (hasHint())
			return Optional.of(action);
		return Optional.empty();
	}

	public boolean hasValidAction()
	{
		return hasHint() && isValid;
	}

	public boolean hasHint()
	{
		return action != null;
	}

	public Inventory getInventory()
	{
		return inventory;
	}

	@Override
	public void onContentChanged(Inventory inv)
	{
		if (inventory == inv)
		{
			Optional<IEnchAction> match = EnchActions.maybeMatch(inv.getInvStack(0), inv.getInvStack(1),
				inv.getInvStack(2));
			Optional<IEnchAction> almostMatch = EnchActions.maybeAlmostMatch(inv.getInvStack(0), inv.getInvStack(1),
				inv.getInvStack(2));
			isValid = true;
			action = match.orElse(null);
			if (action == null)
			{
				isValid = false;
				action = almostMatch.orElse(null);
			}
		}
	}

	@Override
	public boolean onButtonClick(PlayerEntity player, int index)
	{
		if (hasValidAction())
		{
			context.run((world, pos) ->
			{
				action.doApply(inventory, inventory.getInvStack(0), inventory.getInvStack(1), inventory.getInvStack(2));
				world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F,
					world.random.nextFloat() * 0.1F + 0.9F);
			});

			this.inventory.markDirty();
			this.onContentChanged(this.inventory);

			return true;
		}

		return super.onButtonClick(player, index);
	}

	@Override
	public boolean canUse(PlayerEntity player)
	{
		return true;
	}

	@Override
	public void close(PlayerEntity player)
	{
		super.close(player);
		this.context.run((world, pos) ->
		{
			this.dropInventory(player, player.world, this.inventory);
		});
	}
}
