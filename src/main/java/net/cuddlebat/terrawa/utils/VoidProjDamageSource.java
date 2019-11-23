package net.cuddlebat.terrawa.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.ProjectileDamageSource;

public class VoidProjDamageSource extends ProjectileDamageSource
{
	
	public VoidProjDamageSource(Entity projectile, Entity owner)
	{
		super("indirectVoid", projectile, owner);
		this.setBypassesArmor();
		this.setDamageToCreative();
	}
	
	public VoidProjDamageSource(Entity source)
	{
		super("indirectVoid", source, null);
		this.setBypassesArmor();
		this.setDamageToCreative();
	}
}
