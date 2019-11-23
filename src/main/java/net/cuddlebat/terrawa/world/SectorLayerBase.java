package net.cuddlebat.terrawa.world;

import java.util.Optional;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public abstract class SectorLayerBase
{
	protected int x, z;
	
	public SectorLayerBase(int x, int z)
	{
		this.x = x;
		this.z = z;
	}

	public abstract Optional<Biome> maybeGetBiomeOverride(BlockPos pos);

	public abstract void digCaverns(World world, Chunk chunk);

	public abstract void decorateBiomes(IWorld world, Chunk chunk, ChunkGenerator<?> gen);

	public abstract void generate();
}
