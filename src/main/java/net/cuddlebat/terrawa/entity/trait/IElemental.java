package net.cuddlebat.terrawa.entity.trait;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public interface IElemental
{
	public DefaultParticleType getSmoke();

	public SmallFireballEntity createFireball(World world, LivingEntity entity, Vec3d velocity);
}
