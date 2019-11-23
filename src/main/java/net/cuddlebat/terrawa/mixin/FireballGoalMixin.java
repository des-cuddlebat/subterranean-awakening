package net.cuddlebat.terrawa.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.cuddlebat.terrawa.entity.mob.PoisonElemental;
import net.cuddlebat.terrawa.entity.projectile.PoisonElementalProjectile;
import net.cuddlebat.terrawa.entity.trait.IElemental;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

@Mixin(targets = "net/minecraft/entity/mob/BlazeEntity$ShootFireballGoal")
public class FireballGoalMixin
{
	@Redirect(at = @At(value = "NEW",
		target = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;DDD)Lnet/minecraft/entity/projectile/SmallFireballEntity;"),
		method = "tick()V")
	protected SmallFireballEntity fireballCtorProxy(World world, LivingEntity blaze, double xVel, double yVel, double zVel)
	{
		System.out.println("Elemental mixin: ");
		if (blaze instanceof IElemental)
			return ((IElemental)blaze).createFireball(world, blaze, new Vec3d(xVel, yVel, zVel));
		
		System.out.println("Failed");
		return new SmallFireballEntity(world, blaze, xVel, yVel, zVel);
	}
}
