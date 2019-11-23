package net.cuddlebat.terrawa.world;

public abstract class SystemLayerBase<T extends CavernsSectorLayer>
{
	public abstract T createLayer(SectorPos pos);
}
