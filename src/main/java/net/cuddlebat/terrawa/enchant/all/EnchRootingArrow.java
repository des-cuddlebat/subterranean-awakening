package net.cuddlebat.terrawa.enchant.all;

import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;

public class EnchRootingArrow extends ModEnch
{
	
	public EnchRootingArrow()
	{
		super(Weight.RARE, EnchantmentTarget.BOW,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}

	@Override
	public void applyToArrow(ProjectileEntity arrow, int level)
	{
		if(arrow instanceof ArrowEntity)
		{
			((ArrowEntity)arrow).addEffect(new StatusEffectInstance(
				ModStatusEffects.ROOTED, getDuration(level), 1));
		}
	}

	private int getDuration(int level)
	{
		return 40 + 40 * level;
	}

	@Override
	public int getMaximumLevel()
	{
		return 3;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { 2 + 2 * level };
	}
	
	@Override
	public int getCost(int level)
	{
		return 30 + 40 * level;
	}
}
