package net.cuddlebat.terrawa.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.EntityDamageSource;

public class MagicMeleeDamageSource extends EntityDamageSource
{
	
	public MagicMeleeDamageSource(Entity source)
	{
		super("magic", source);
		this.setBypassesArmor();
		this.setUsesMagic();
	}
}
