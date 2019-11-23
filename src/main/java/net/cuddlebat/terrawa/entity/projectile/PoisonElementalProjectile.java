package net.cuddlebat.terrawa.entity.projectile;

import io.netty.buffer.Unpooled;
import net.cuddlebat.terrawa.entity.ModEntities;
import net.cuddlebat.terrawa.entity.trait.IOnClientConstruct;
import net.cuddlebat.terrawa.network.ModPackets;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.client.network.packet.EntitySpawnS2CPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.PacketByteBuf;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class PoisonElementalProjectile extends SmallFireballEntity implements IOnClientConstruct
{
	private static final float DAMAGE = 8.0f;
	
	public PoisonElementalProjectile(EntityType<PoisonElementalProjectile> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
	}

	public PoisonElementalProjectile(World world, LivingEntity owner, double xVel, double yVel,double zVel)
	{
		super(ModEntities.POISON_PROJECTILE, world);

		this.owner = owner;
		this.setPositionAndAngles(owner.x, owner.y, owner.z, owner.yaw, owner.pitch);
		this.setPosition(this.x, this.y, this.z);
		this.setVelocity(Vec3d.ZERO);
		xVel += this.random.nextGaussian() * 0.4D;
		yVel += this.random.nextGaussian() * 0.4D;
		zVel += this.random.nextGaussian() * 0.4D;
		double vel = (double) MathHelper.sqrt(xVel * xVel + yVel * yVel + zVel * zVel);
		this.posX = xVel / vel * 0.1D;
		this.posY = yVel / vel * 0.1D;
		this.posZ = zVel / vel * 0.1D;
	}

	public PoisonElementalProjectile(World world, double x, double y, double z, double xVel, double yVel, double zVel)
	{
		super(ModEntities.POISON_PROJECTILE, world);
		
		applyConstructionParams(x, y, z, xVel, yVel, zVel);
	}
	
	@Override
	protected void onCollision(HitResult hit)
	{
		if(hit.getType() == Type.ENTITY)
		{
			Entity e = ((EntityHitResult)hit).getEntity();
			e.damage(DamageSource.magic(this, this.owner), DAMAGE);
			if(e instanceof LivingEntity)
				((LivingEntity)e).addPotionEffect(new StatusEffectInstance(StatusEffects.POISON, 20 * 4, 2));
		}
		
		this.remove();
	}
	
	@Override
	public boolean isOnFire()
	{
		return false;
	}
	
	@Override
	public ItemStack getStack()
	{
		return Items.SLIME_BALL.getStackForRender();
	}
	
	@Override
	protected ParticleEffect getParticleType()
	{
		return ParticleTypes.HAPPY_VILLAGER;
	}

	@Override
	public void applyConstructionParams(double x, double y, double z, double xVel, double yVel, double zVel)
	{
		this.setPositionAndAngles(x, y, z, this.yaw, this.pitch);
		this.setPosition(x, y, z);
		double double_7 = (double) MathHelper.sqrt(xVel * xVel + yVel * yVel + zVel * zVel);
		this.posX = xVel / double_7 * 0.1D;
		this.posY = yVel / double_7 * 0.1D;
		this.posZ = zVel / double_7 * 0.1D;
	}
	
	@Override
	public Packet<?> createSpawnPacket()
	{
		int entityData = this.owner == null ? 0 : this.owner.getEntityId();
		PacketByteBuf buffer = new PacketByteBuf(Unpooled.buffer());
		buffer.writeVarInt(this.getEntityId());
		buffer.writeUuid(this.uuid);
		buffer.writeVarInt(Registry.ENTITY_TYPE.getRawId(this.getType()));
		buffer.writeDouble(this.x);
		buffer.writeDouble(this.y);
		buffer.writeDouble(this.z);
		buffer.writeByte(MathHelper.floor(pitch * 256.0F / 360.0F));
		buffer.writeByte(MathHelper.floor(yaw * 256.0F / 360.0F));
		buffer.writeInt(entityData);
		buffer.writeShort((int) (this.posX * 8000.0D));
		buffer.writeShort((int) (this.posY * 8000.0D));
		buffer.writeShort((int) (this.posZ * 8000.0D));
		return ServerSidePacketRegistry.INSTANCE.toPacket(ModPackets.SPAWN_NONLIVING, buffer);
	}
}

