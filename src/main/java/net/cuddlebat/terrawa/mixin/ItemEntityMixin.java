package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.ItemEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.tag.Tag;

@Mixin(ItemEntity.class)
public class ItemEntityMixin
{
	@Redirect(at = @At(value="INVOKE",
		target = "Lnet/minecraft/fluid/FluidState;matches(Lnet/minecraft/tag/Tag;)Z"),
		method="tick()V")
	public boolean getLavaCollisionProxy(FluidState state, Tag<Fluid> tag)
	{
		ItemEntity self = (ItemEntity) (Object) this;
		if(self.isInvulnerable())
			return false;
		return state.matches(tag);
	}
}
