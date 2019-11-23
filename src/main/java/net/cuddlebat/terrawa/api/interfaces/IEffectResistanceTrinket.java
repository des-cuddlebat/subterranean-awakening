package net.cuddlebat.terrawa.api.interfaces;

import net.minecraft.entity.effect.StatusEffect;

public interface IEffectResistanceTrinket extends ITrinketWithSlotInfo
{
	public boolean doesPreventEffect(StatusEffect effect);
}
