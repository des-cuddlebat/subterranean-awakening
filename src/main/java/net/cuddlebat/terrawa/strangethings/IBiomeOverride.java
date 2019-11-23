package net.cuddlebat.terrawa.strangethings;

import java.util.Map;
import java.util.Optional;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public interface IBiomeOverride
{
	/**
	 * 
	 * @param rangeBottoms i = x*16 + z 
	 * @param rangeTops i = x*16 + z
	 * @param biomes i = x*16 + z
	 * @param map byte => idof(biome)
	 */
	public void setData(int[] rangeBottoms, int[] rangeTops, byte[] biomes, Map<Byte, String> map);
	
	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos);
	
	public boolean isInitialized();
}
