package net.cuddlebat.terrawa.world;

import java.util.Optional;

import net.cuddlebat.terrawa.cardinal.CavernsComponent;
import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class Sector
{
	protected SectorLayerBase[] layers;
	
	public Sector(SectorPos pos, CavernsComponent comp)
	{
		SystemLayerBase<?>[] systemLayers = comp.getLayers();
		layers = new SectorLayerBase[systemLayers.length];
		for (int i = 0; i < systemLayers.length; i++)
		{
			layers[i] = systemLayers[i].createLayer(pos);
		}
	}

	public Optional<Biome> maybeGetBiomeOverride(BlockPos pos)
	{
		for (SectorLayerBase lay : layers)
		{
			Optional<Biome> potential = lay.maybeGetBiomeOverride(pos);
			if(potential.isPresent())
				return potential;
		}
		return Optional.empty();
	}

	public void digCaverns(World world, Chunk chunk)
	{
		for(SectorLayerBase layer : layers)
			layer.digCaverns(world, chunk);
	}

	public void decorateBiomes(IWorld world, Chunk chunk, ChunkGenerator<?> gen)
	{
		for(SectorLayerBase layer : layers)
			layer.decorateBiomes(world, chunk, gen);
	}

	public void generate()
	{
		for(SectorLayerBase layer : layers)
			layer.generate();
	}

	public IntRange getRangeForPacket(int x, int z)
	{
		// TODO this will break if I do pretty much anything but I need to move on
		CavernsSectorLayer lay = (CavernsSectorLayer) layers[0];
		return lay.getCaveBoundaries(
			Math.floorMod(x, SectorPos.SECTOR_SIZE),
			Math.floorMod(z, SectorPos.SECTOR_SIZE))
			.orElse(new IntRange(-Integer.MAX_VALUE, -Integer.MAX_VALUE));
	}
}
