package net.cuddlebat.terrawa.item.trinket;

import net.cuddlebat.terrawa.api.interfaces.IEffectResistanceTrinket;
import net.cuddlebat.terrawa.effect.ModStatusEffects;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;

public class RubyWardRing extends Item implements IEffectResistanceTrinket
{

	public RubyWardRing(Settings settings)
	{
		super(settings);
	}

	@Override
	public boolean doesPreventEffect(StatusEffect effect)
	{
		return ModStatusEffects.BLEEDING.equals(effect)
			|| ModStatusEffects.VULNERABILITY.equals(effect);
	}

	@Override
	public boolean canWearInSlot(String group, String slot)
	{
		return "ring".equals(slot);
	}
	
	@Override
	public String getSlotNameForTooltip()
	{
		return "Ring";
	}

}
