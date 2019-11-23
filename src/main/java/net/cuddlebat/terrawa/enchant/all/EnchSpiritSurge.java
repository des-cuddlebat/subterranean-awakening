package net.cuddlebat.terrawa.enchant.all;

import net.cuddlebat.terrawa.enchant.ModEnch;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class EnchSpiritSurge extends ModEnch
{
	protected EnchSpiritSurge()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 4;
	}
	
	@Override
	public boolean isBlessing()
	{
		return true;
	}
	
	@Override
	public boolean isSupportOf(Enchantment ench)
	{
		return ench instanceof EnchMagicSting;
	}
	
	@Override
	public int getCost(int level)
	{
		return 75 * level + 5 * level * (level + 1) / 2;
	}
}
