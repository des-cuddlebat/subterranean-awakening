package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.noise.OpenSimplexNoise;
import net.cuddlebat.terrawa.utils.IntRange;
import net.cuddlebat.terrawa.world.feature.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.CactusFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.IceSpikeFeature;

public class FrostSubBiome extends SubBiome
{
	// TODO this needs to use world based seed
	private final OpenSimplexNoise FLOOR_NOISE = new OpenSimplexNoise(89623);
	private final double THRESHOLD = 0.2;
	private final double STRETCH = 0.03;

	protected FrostSubBiome(Settings settings)
	{
		super(settings);
		
		// TODO spawns
	}

	@Override
	public void decorateSubterraneanChunk(IWorld world, Chunk chunk, ChunkGenerator<?> gen, IntRange[][] heightmap, SubBiome[][] biomemap, Random rand)
	{
		ChunkPos cp = chunk.getPos();
//		BlockPos pos = new BlockPos(cp.x * 16 + 8, heightmap[8][8].getMin(), cp.z * 16 + 8);
//		ModFeatures.SUB_ICE_SPIKES.generate(world, gen, rand, pos, FeatureConfig.DEFAULT);
		for(int quadrant = 0; quadrant < 4; quadrant++)
		{
			int i = (quadrant % 2) * 8 + 1 + rand.nextInt(6);
			int j = (quadrant / 2) * 8 + 1 + rand.nextInt(6);
			if(!(biomemap[i][j] instanceof FrostSubBiome))
				continue;
			
			int roll = rand.nextInt(3);
			if(roll == 0)
			{
				BlockPos pos = new BlockPos(cp.x * 16 + i, heightmap[i][j].getMin() - 1, cp.z * 16 + j);
				ModFeatures.SUB_ICE_SPIKES.generate(world, gen, rand, pos, FeatureConfig.DEFAULT);
			}
			if(roll == 1)
			{
				BlockPos pos = new BlockPos(cp.x * 16 + i, heightmap[i][j].getMax(), cp.z * 16 + j);
				ModFeatures.SUB_ICE_SPIKES.generate(world, gen, rand, pos, FeatureConfig.DEFAULT);
			}
		}
	}

	@Override
	public void digColumn(Chunk chunk, int i, int j, IntRange range)
	{
		final int bottom = range.getMin();
		final int top = range.getMax();
		final int xPos = i + chunk.getPos().x * 16;
		final int zPos = j + chunk.getPos().z * 16;
		BlockPos.Mutable pos = new BlockPos.Mutable(i, bottom - 1, j);
		for(int k = bottom - 1; k <= top; k++)
		{
			if(k == bottom - 1)
			{
				// TODO this should probably go in decorate tbh
				if(FLOOR_NOISE.eval(xPos * STRETCH, zPos * STRETCH) > THRESHOLD)
				{
					chunk.setBlockState(pos, Blocks.ICE.getDefaultState(), false);
				}
				else
				{
					chunk.setBlockState(pos, Blocks.SNOW_BLOCK.getDefaultState(), false);
				}
			}
			else if(k == top)
			{
				chunk.setBlockState(pos, Blocks.SNOW_BLOCK.getDefaultState(), false);
			}
			else
			{
				chunk.setBlockState(pos, Blocks.CAVE_AIR.getDefaultState(), false);
			}
			pos.setOffset(Direction.UP);
		}
	}

	@Override
	public void decorateColumn(IWorld world, Chunk chunk, int xRel, int zRel, IntRange range, Random rand)
	{
		if(rand.nextInt(128) == 17)
		{
			IceSpikeFeature feat = new IceSpikeFeature(DefaultFeatureConfig::deserialize);
			feat.generate(world, world.getChunkManager().getChunkGenerator(), rand,
				new BlockPos(xRel, range.getMin(), zRel), FeatureConfig.DEFAULT);
		}
	}

}
