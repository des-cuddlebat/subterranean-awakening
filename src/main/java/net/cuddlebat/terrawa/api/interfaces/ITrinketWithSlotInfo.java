package net.cuddlebat.terrawa.api.interfaces;

import dev.emi.trinkets.api.ITrinket;

public interface ITrinketWithSlotInfo extends ITrinket
{
	public String getSlotNameForTooltip();
}
