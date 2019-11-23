package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.noise.OpenSimplexNoise;
import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.CactusFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;

public class DroughtSubBiome extends SubBiome
{
//	private static final BlockState CACTUS = Blocks.CACTUS.getDefaultState();
	// TODO this needs to use world based seed
	private final OpenSimplexNoise FLOOR_NOISE = new OpenSimplexNoise(89623);
//	private final double THRESHOLD = 0.2;
	private final double STRETCH = 0.02;

	protected DroughtSubBiome(Settings settings)
	{
		super(settings);
		
		// TODO spawns
	}

	@Override
	public void decorateSubterraneanChunk(IWorld world, Chunk chunk, ChunkGenerator<?> gen, IntRange[][] heightmap, SubBiome[][] biomemap, Random rand)
	{
		
	}

	@Override
	public void digColumn(Chunk chunk, int i, int j, IntRange range)
	{
		final int bottom = range.getMin();
		final int top = range.getMax();
		final int xPos = i + chunk.getPos().x * 16;
		final int zPos = j + chunk.getPos().z * 16;
		double duneNoise = (FLOOR_NOISE.eval(xPos * STRETCH, zPos * STRETCH) + 1) % 0.25;
		duneNoise = Math.min(duneNoise, 1.0 - 4 * duneNoise);
		final int duneHeight = 1 + (int)(duneNoise * 15);
		BlockPos.Mutable mPos = new BlockPos.Mutable(i, bottom - 1, j);
		for(int k = bottom - 1; k < top; k++)
		{
			if(k < bottom + duneHeight)
			{
				chunk.setBlockState(mPos, Blocks.SAND.getDefaultState(), false);
			}
			else
			{
				chunk.setBlockState(mPos, Blocks.CAVE_AIR.getDefaultState(), false);
			}
			mPos.setOffset(Direction.UP);
//			if(k == bottom - 1)
//			{
//				// TODO this should probably go in decorate tbh
//				if(FLOOR_NOISE.eval(xPos * STRETCH, zPos * STRETCH) > THRESHOLD)
//				{
//					chunk.setBlockState(pos, Blocks.RED_SAND.getDefaultState(), false);
//				}
//				else
//				{
//					chunk.setBlockState(pos, Blocks.SAND.getDefaultState(), false);
//				}
//			}
//			else
//			{
//				chunk.setBlockState(pos, Blocks.CAVE_AIR.getDefaultState(), false);
//			}
//			pos.setOffset(Direction.UP);
		}
	}

	@Override
	public void decorateColumn(IWorld world, Chunk chunk, int xRel, int zRel, IntRange range, Random rand)
	{
//		if(rand.nextInt(64) == 17)
//		{
//			BlockPos.Mutable pos = new BlockPos.Mutable(xRel, range.getMin(), zRel);
//			for (int i = 0; i < 1 + rand.nextInt(3); ++i)
//			{
//				pos.setOffset(Direction.UP); // TODO this should be below idk wtf is happening
//				chunk.setBlockState(pos, Blocks.CACTUS.getDefaultState(), false);
//			}
////			CactusFeature feat = new CactusFeature(DefaultFeatureConfig::deserialize);
////			feat.generate(world, world.getChunkManager().getChunkGenerator(), rand,
////				new BlockPos(xRel, range.getMin(), zRel), FeatureConfig.DEFAULT);
//		}
	}

}
