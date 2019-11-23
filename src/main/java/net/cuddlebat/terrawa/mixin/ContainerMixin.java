package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.terrawa.inventory.OverloadableSlot;
import net.minecraft.container.Container;
import net.minecraft.container.Slot;
import net.minecraft.container.SlotActionType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(Container.class)
public class ContainerMixin
{
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getMaxCount()I"),
		method = "onSlotClick")
	private int getMaxCountProxy(ItemStack stack, int index, int int_2, SlotActionType slotActionType_1, PlayerEntity player)
	{
		if(index < 0)
			return stack.getMaxCount();
		
		Container self = (Container) (Object) this;
		Slot slot = self.slotList.get(index);
		if(slot instanceof OverloadableSlot)
			return slot.getMaxStackAmount();
		return stack.getMaxCount();
	}
}
