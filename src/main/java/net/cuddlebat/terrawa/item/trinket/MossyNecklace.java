package net.cuddlebat.terrawa.item.trinket;

import net.cuddlebat.terrawa.api.interfaces.IEffectResistanceTrinket;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;

public class MossyNecklace extends Item implements IEffectResistanceTrinket
{

	public MossyNecklace(Settings settings)
	{
		super(settings);
	}

	@Override
	public boolean doesPreventEffect(StatusEffect effect)
	{
		return StatusEffects.POISON.equals(effect);
	}

	@Override
	public boolean canWearInSlot(String group, String slot)
	{
		return "necklace".equals(slot);
	}
	
	@Override
	public String getSlotNameForTooltip()
	{
		return "Necklace";
	}

}
