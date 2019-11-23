package net.cuddlebat.terrawa.entity.mob;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.cardinal.IProjectileComponent;
import net.cuddlebat.terrawa.cardinal.ProjectileComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.SkeletonEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class IvySkeleton extends SkeletonEntity
{

	public IvySkeleton(EntityType<? extends IvySkeleton> entityType_1, World world_1)
	{
		super(entityType_1, world_1);
	}
	
	@Override
	protected void initAttributes()
	{
		super.initAttributes();
		this.getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(80.0);
		this.getAttributeInstance(EntityAttributes.ATTACK_DAMAGE).setBaseValue(8.0);
		this.getAttributeInstance(EntityAttributes.ARMOR).setBaseValue(10.0);
		this.getAttributeInstance(EntityAttributes.ARMOR_TOUGHNESS).setBaseValue(5.0);
	}
	
	@Override
	protected ProjectileEntity createArrowProjectile(ItemStack stack, float f)
	{
		ProjectileEntity projectile = super.createArrowProjectile(stack, f);
		projectile.setDamage(projectile.getDamage() + 6.0);
		
		IProjectileComponent comp = Const.Component.PROJECTILE_COMPONENT.maybeGet(projectile).orElse(null);
		
		if(comp != null && comp instanceof ProjectileComponent)
		{
			((ProjectileComponent) comp).addMagicDamage(6);
		}
		else
		{
			System.out.println("Could not find projectile component");
		}
		
		return projectile;
	}
}
