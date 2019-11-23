package net.cuddlebat.terrawa.enchant.all;

import net.cuddlebat.terrawa.enchant.ModEnch;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EnchSwiftPull extends ModEnch
{
	
	public EnchSwiftPull()
	{
		super(Weight.RARE, EnchantmentTarget.BOW,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}

	@Override
	public int getMaximumLevel()
	{
		return 4;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { level * 10 };
	}
	
	@Override
	public int getCost(int level)
	{
		return 15*level + 5 * level * (level - 1);
	}
}
