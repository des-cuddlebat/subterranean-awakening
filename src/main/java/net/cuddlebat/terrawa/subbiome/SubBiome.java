package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class SubBiome extends Biome
{

	protected SubBiome(Settings settings)
	{
		super(settings);
		// TODO Auto-generated constructor stub
	}

	public abstract void decorateSubterraneanChunk(IWorld world, Chunk chunk, ChunkGenerator<?> gen, IntRange[][] heightmap,
		SubBiome[][] biomemap, Random rand);

	public abstract void digColumn(Chunk chunk, int xRel, int zRel, IntRange range);

	public abstract void decorateColumn(IWorld world, Chunk chunk, int xRel, int zRel, IntRange range, Random rand);
	
}
