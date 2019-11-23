package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.utils.IntRange;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CrystalSubBiome extends SubBiome
{
//	private static final BlockState CACTUS = Blocks.CACTUS.getDefaultState();
//	// TODO this needs to use world based seed
//	private final OpenSimplexNoise FLOOR_NOISE = new OpenSimplexNoise(89623);
//	private final double THRESHOLD = 0.2;
//	private final double STRETCH = 0.03;

	protected CrystalSubBiome(Settings settings)
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
//		final int xPos = i + chunk.getPos().x * 16;
//		final int zPos = j + chunk.getPos().z * 16;
		BlockPos.Mutable pos = new BlockPos.Mutable(i, bottom + 1, j); // ??
		for(int k = bottom; k < top; k++)
		{
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
			chunk.setBlockState(pos, Blocks.CAVE_AIR.getDefaultState(), false);
			pos.setOffset(Direction.UP);
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
//			CactusFeature feat = new CactusFeature(DefaultFeatureConfig::deserialize);
//			feat.generate(world, world.getChunkManager().getChunkGenerator(), rand,
//				new BlockPos(xRel, range.getMin(), zRel), FeatureConfig.DEFAULT);
//		}
	}

}
