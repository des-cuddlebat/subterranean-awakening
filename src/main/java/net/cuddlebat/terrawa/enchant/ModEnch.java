package net.cuddlebat.terrawa.enchant;

import com.google.common.collect.Multimap;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;

public abstract class ModEnch extends Enchantment
{
	protected ModEnch(Weight weight, EnchantmentTarget target, EquipmentSlot[] slots)
	{
		super(weight, target, slots);
	}

	public void applyToArrow(ProjectileEntity arrow, int level)
	{
	}

	/**
	 * Returns whether this enchantment virtually increases the level of provided
	 * <b>other</b> enchantment.
	 */
	public boolean isSupportOf(Enchantment other)
	{
		return false;
	}

	public boolean isBlessing()
	{
		return false;
	}

	public Object[] getDescParams(int level)
	{
		return new Object[] { level * 1.0f };
	}

	public abstract int getCost(int level);

	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
	}

	public void appendModifiers(int level, ItemStack stack, EquipmentSlot slot, Multimap<String, EntityAttributeModifier> multimap)
	{
		
	}
}
