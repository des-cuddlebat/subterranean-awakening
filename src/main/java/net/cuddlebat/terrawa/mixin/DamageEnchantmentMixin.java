package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import net.minecraft.enchantment.DamageEnchantment;

@Mixin(DamageEnchantment.class)
public class DamageEnchantmentMixin
{

	@ModifyConstant(
		constant = @Constant(floatValue = 0.5F),
		method = "getAttackDamage")
	private float modifyMultiplier(float zeroPointFive)
	{
		return 1.0f;
	}
}
