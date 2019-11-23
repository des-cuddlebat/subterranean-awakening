package net.cuddlebat.terrawa.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;

public class ModStatusEffect extends StatusEffect
{
	private IAmplifierOperation amplifierOp = super::method_5563;
	private IOnTick onTick = (inst, ent) -> {};

	public ModStatusEffect(StatusEffectType type, int color)
	{
		super(type, color);
	}

	@Override
	public double method_5563(int amplifier, EntityAttributeModifier modifier)
	{
		return amplifierOp.get(amplifier, modifier);
	}
	
	public ModStatusEffect withAmplifierOp(IAmplifierOperation op)
	{
		amplifierOp = op;
		return this;
	}

	public void tick(StatusEffectInstance inst, LivingEntity entity)
	{
		onTick.tick(inst, entity);
	}
	
	public ModStatusEffect withOnTick(IOnTick op)
	{
		onTick = op;
		return this;
	}
	
	@FunctionalInterface
	public interface IAmplifierOperation
	{
		public double get(int amplifier, EntityAttributeModifier modifier);
	}
	
	@FunctionalInterface
	public interface IOnTick
	{
		public void tick(StatusEffectInstance inst, LivingEntity entity);
	}
}
