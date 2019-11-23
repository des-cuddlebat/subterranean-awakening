package net.cuddlebat.terrawa.enchant.all;

import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.cuddlebat.terrawa.enchant.ModEnch;
import net.cuddlebat.terrawa.enchant.ModEnchHelper;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;

public class EnchArmorbreak extends ModEnch
{
	public EnchArmorbreak()
	{
		super(Weight.RARE, EnchantmentTarget.WEAPON,
			new EquipmentSlot[] { EquipmentSlot.MAINHAND });
	}
	
	@Override
	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
		if(target instanceof LivingEntity)
		{
			LivingEntity t = (LivingEntity)target;
			t.addPotionEffect(new StatusEffectInstance(
				ModStatusEffects.ARMORBREAK, getDuration(level), getAmplifier(level)));
		}
	}

	@Override
	public void applyToArrow(ProjectileEntity arrow, int level)
	{
		if(arrow instanceof ArrowEntity)
		{
			((ArrowEntity)arrow).addEffect(new StatusEffectInstance(
				ModStatusEffects.ARMORBREAK, getDuration(level), getAmplifier(level)));
		}
	}
	
	private int getAmplifier(int level)
	{
		return level;
	}
	
	private int getDuration(int level)
	{
		return 40 + 20 * level;
	}
	
	@Override
	public int getMaximumLevel()
	{
		return 4;
	}
	
	@Override
	public Object[] getDescParams(int level)
	{
		return new Object[] { ModEnchHelper.getRomanNumeral(level - 1), 2.0 + level, 20.0 * level };
	}
	
	@Override
	public int getCost(int level)
	{
		// 1 4 9 16
		// 0 2 4 6
		// ---------
		// 1 2 5 10
		
		return 10 * (level * level + 2 - 2 * level);
	}

	@Override
	public boolean isAcceptableItem(ItemStack stack)
	{
		return EnchantmentTarget.BOW.isAcceptableItem(stack.getItem())
			|| EnchantmentTarget.WEAPON.isAcceptableItem(stack.getItem());
	}
}
