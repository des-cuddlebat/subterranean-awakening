package net.cuddlebat.terrawa.mixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.cuddlebat.terrawa.strangethings.IBiomeOverride;
import net.cuddlebat.terrawa.strangethings.IWorldGetter;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.WorldChunk;

@Mixin(WorldChunk.class)
public class WorldChunkClientInterfaceMixin implements IBiomeOverride
{
	int[] bottom;
	int[] top;
	byte[] biomes;
	Map<Byte, Biome> map;
	boolean isInit = false;

	@Override
	public void setData(int[] rangeBottoms, int[] rangeTops, byte[] biomes, Map<Byte, String> map)
	{
		bottom = rangeBottoms;
		top = rangeTops;
		this.biomes = biomes;
		this.map = new HashMap<Byte, Biome>();
		for(Map.Entry<Byte, String> entry : map.entrySet())
		{
			this.map.put(entry.getKey(), Registry.BIOME.get(new Identifier(entry.getValue())));
		}
		isInit = true;
	}

	@Override
	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos)
	{
		if(!isInit)
			return Optional.empty();
		final int xRel = pos.getX() & 15;
		final int zRel = pos.getZ() & 15;
		final byte biome = biomes[xRel * 16 + zRel];
		if(biome != 0)
		{
			final int grace = 3;
			final int low  = bottom[xRel * 16 + zRel] - grace;
			final int high =    top[xRel * 16 + zRel] + grace;
			if(pos.getY() >= low && pos.getY() <= high)
				return Optional.of(map.get(biome));
		}
		return Optional.empty();
	}

	@Override
	public boolean isInitialized()
	{
		return isInit;
	}
	
}
