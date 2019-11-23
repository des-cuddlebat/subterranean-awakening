package net.cuddlebat.terrawa.entity.mob;

import net.cuddlebat.terrawa.entity.ModEntities;
import net.cuddlebat.terrawa.entity.projectile.PoisonElementalProjectile;
import net.cuddlebat.terrawa.entity.trait.IElemental;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PoisonElemental extends BlazeEntity implements IElemental
{
	public PoisonElemental(EntityType<? extends BlazeEntity> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isOnFire()
	{
		return false;
	}

	@Override
	public DefaultParticleType getSmoke()
	{
		return ParticleTypes.HAPPY_VILLAGER;
	}

	@Override
	public SmallFireballEntity createFireball(World world, LivingEntity entity, Vec3d velocity)
	{
		return new PoisonElementalProjectile(world, entity, velocity.getX(), velocity.getY(), velocity.getZ());
	}
}
