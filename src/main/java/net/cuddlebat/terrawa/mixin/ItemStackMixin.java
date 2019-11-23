package net.cuddlebat.terrawa.mixin;

import java.util.Map.Entry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.common.collect.Multimap;

import net.cuddlebat.terrawa.enchant.ModEnch;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;

@Mixin(ItemStack.class)
public class ItemStackMixin
{

	@Inject(at = @At("RETURN"), cancellable = true, method = "getAttributeModifiers")
	private void getAttributeModifiersMixin(EquipmentSlot slot, CallbackInfoReturnable<Multimap<String, EntityAttributeModifier>> cir)
	{
		ItemStack self = (ItemStack) (Object) this;
		Multimap<String, EntityAttributeModifier> multimap = cir.getReturnValue();
		for(Entry<Enchantment, Integer> ench : EnchantmentHelper.getEnchantments(self).entrySet())
		{
			if (ench.getKey() instanceof ModEnch)
				((ModEnch)ench.getKey()).appendModifiers(ench.getValue(), self, slot, multimap);
		}
	}

}
