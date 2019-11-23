package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.terrawa.entity.trait.IElemental;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

@Mixin(BlazeEntity.class)
public class BlazeMixin
{
	@Redirect(at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC,
		target = "Lnet/minecraft/particle/ParticleTypes;LARGE_SMOKE:Lnet/minecraft/particle/DefaultParticleType;"),
		method = "tickMovement()V")
	protected DefaultParticleType particleProxy()
	{
		BlazeEntity blaze = (BlazeEntity) (Object) this;
		if (blaze instanceof IElemental)
			return ((IElemental)blaze).getSmoke();
		
		return ParticleTypes.LARGE_SMOKE;
	}
}
