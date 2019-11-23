package net.cuddlebat.terrawa.entity.mob;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.world.World;

public class VenombiteSpider extends CaveSpiderEntity
{
	public VenombiteSpider(EntityType<? extends CaveSpiderEntity> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
	}
	
	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(60.0);
		this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(12.0);
		this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(6.0);
		this.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0);
	}
	
	@Override
	public boolean tryAttack(Entity entity)
	{
		boolean result = super.tryAttack(entity);
		if(result && entity instanceof LivingEntity)
			((LivingEntity)entity).addPotionEffect(new StatusEffectInstance(
				StatusEffects.POISON, 3 * 20, 2));
		return result;
	}
}
