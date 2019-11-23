package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import dev.emi.trinkets.api.TrinketsApi;
import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.item.ModInventoryBelt;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.util.DefaultedList;
import net.minecraft.world.World;

@Mixin(PotionItem.class)
public class PotionMixin
{
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"),
		method = "finishUsing")
	private void decrementProxy(ItemStack stack, int one, ItemStack itemStack_1, World world_1, LivingEntity maybePlayer)
	{
		if (maybePlayer instanceof PlayerEntity)
		{
			PlayerEntity player = (PlayerEntity) maybePlayer;
			ItemStack belt = TrinketsApi.getTrinketComponent(player).getStack(Const.TrinketSlot.BELT);
			if (!belt.isEmpty() && belt.getItem() instanceof ModInventoryBelt)
			{
				if(((ModInventoryBelt)belt.getItem()).canGoIn(stack))
				{
					DefaultedList<ItemStack> list = DefaultedList.ofSize(5, ItemStack.EMPTY);
					Inventories.fromTag(belt.getOrCreateTag(), list);
					for (ItemStack potential : list)
					{
						if(ItemStack.areItemsEqual(stack, potential) && ItemStack.areTagsEqual(stack, potential))
						{
							potential.decrement(one);
							Inventories.toTag(belt.getTag(), list);
							return;
						}
					}
				}
			}
		}
		stack.decrement(one);
	}
}
