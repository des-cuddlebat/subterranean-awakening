package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.enchantment.Enchantments;

@Mixin(Enchantments.class)
public class OnVanillaEnchRegisterMixin
{
	@Inject(at = @At("RETURN"), method = "<clinit>")
	private static void initEnchHelper(CallbackInfo yourMom)
	{
		ModEnchHelper.doInit();
	}
}
