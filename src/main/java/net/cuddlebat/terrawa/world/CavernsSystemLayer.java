package net.cuddlebat.terrawa.world;

import net.cuddlebat.terrawa.noise.OpenSimplexNoise;

public class CavernsSystemLayer extends SystemLayerBase<CavernsSectorLayer>
{
	public int altBottom = 8;
	public int altTop    = 32;
	public double altStretch = 0.01;
	
	public double caveHeight = 6.0;
	public double biomeHeight = 12.0;
	
	public int biomePartitionBias = 8;
	public int biomeAttempts = 192;
	
	public double mainStretch = 0.04;
	
	public double thresholdBase = 0.23;
	public double thresholdEffect = 0.07;
	public double thresholdStretch = 0.02;
	
	public long randSeed;
	
	public OpenSimplexNoise mainNoise;
	public OpenSimplexNoise altNoise;
	public OpenSimplexNoise thrNoise;

	public CavernsSystemLayer(long mainSeed, long altSeed, long thrSeed, long randSeed)
	{
		mainNoise = new OpenSimplexNoise(mainSeed);
		altNoise = new OpenSimplexNoise(altSeed);
		thrNoise = new OpenSimplexNoise(thrSeed);
		this.randSeed = randSeed;
	}
	
	@Override
	public CavernsSectorLayer createLayer(SectorPos pos)
	{
		return new CavernsSectorLayer(pos.getX(), pos.getZ(), this);
	}

}
