package net.cuddlebat.terrawa.enchant;

import java.util.Optional;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

public class VanillaEnchTransform
{
	private boolean doDesc;
	private AttackPreCallback attackPre;
	private EnchantmentCostCallback enchCost;
	private DescParamsGetter descParams;
	
	public VanillaEnchTransform(boolean doDesc)
	{
		super();
		this.doDesc = doDesc;
	}
	
	public Optional<Object[]> getDescParams(int level)
	{
		if(descParams != null)
			return Optional.of(descParams.getDescParams(level));
		return Optional.empty();
	}
	
	public void onAttackPre(LivingEntity attacker, Entity target, int level)
	{
		if(attackPre != null)
			attackPre.onAttackPre(attacker, target, level);
	}
	
	public Optional<Integer> getEnchCost(int level)
	{
		if(enchCost != null)
			return Optional.of(enchCost.getEnchantmentCost(level));
		return Optional.empty();
	}

	public boolean doDesc()
	{
		return doDesc;
	}
	
	public VanillaEnchTransform withOnAttackPre(AttackPreCallback onAttackPre)
	{
		attackPre = onAttackPre;
		return this;
	}
	
	public VanillaEnchTransform withEnchCostGetter(EnchantmentCostCallback enchCostGetter)
	{
		enchCost = enchCostGetter;
		return this;
	}
	
	public VanillaEnchTransform withDescParamsGetter(DescParamsGetter descParamsGetter)
	{
		descParams = descParamsGetter;
		return this;
	}

	public static interface AttackPreCallback
	{
		public void onAttackPre(LivingEntity attacker, Entity target, int level);
	}

	public static interface EnchantmentCostCallback
	{
		public int getEnchantmentCost(int level);
	}

	public static interface DescParamsGetter
	{
		public Object[] getDescParams(int level);
	}
}
