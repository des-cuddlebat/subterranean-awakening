package net.cuddlebat.terrawa.api.interfaces;

import net.minecraft.enchantment.Enchantment;

public interface IEnchSupportTrinket extends ITrinketWithSlotInfo
{
	public int getSupportFor(Enchantment ench);
}
