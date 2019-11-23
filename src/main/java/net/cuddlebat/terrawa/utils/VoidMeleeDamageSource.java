package net.cuddlebat.terrawa.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class VoidMeleeDamageSource extends EntityDamageSource
{
	
	public VoidMeleeDamageSource(Entity source)
	{
		super("void", source);
		this.setBypassesArmor();
//		this.setDamageToCreative();
		this.setUnblockable();
	}
}
