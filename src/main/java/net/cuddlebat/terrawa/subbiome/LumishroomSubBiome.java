package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.utils.IntRange;
import net.cuddlebat.terrawa.world.feature.ModFeatures;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.JungleTreeFeature;

public class LumishroomSubBiome extends SubBiome
{

	protected LumishroomSubBiome(Settings settings)
	{
		super(settings);
		
		// TODO spawns
	}

	@Override
	public void decorateSubterraneanChunk(IWorld world, Chunk chunk, ChunkGenerator<?> gen, IntRange[][] heightmap, SubBiome[][] biomemap, Random rand)
	{
		ChunkPos cp = chunk.getPos();
		BlockPos.Mutable mPos = new BlockPos.Mutable();

		for(int quadrant = 0; quadrant < 4; quadrant++)
		{
			int i = (quadrant % 2) * 8 + 1 + rand.nextInt(6);
			int j = (quadrant / 2) * 8 + 1 + rand.nextInt(6);
			
			if(biomemap[i][j] instanceof LumishroomSubBiome)
			{
				mPos.set(cp.x * 16 + i, heightmap[i][j].getMin() - 1, cp.z * 16 + j);
				switch(rand.nextInt(3))
				{
				case 0:
					ModFeatures.SUB_FLAT_LUMISHROOM.generate(world, gen, rand, mPos, FeatureConfig.DEFAULT);
					break;
				case 1:
					ModFeatures.SUB_BOWL_LUMISHROOM.generate(world, gen, rand, mPos, FeatureConfig.DEFAULT);
					break;
				case 2:
					ModFeatures.SUB_CUP_LUMISHROOM.generate(world, gen, rand, mPos, FeatureConfig.DEFAULT);
					break;
				}			
			}
		}
	}

	@Override
	public void digColumn(Chunk chunk, int i, int j, IntRange range)
	{
		final int bottom = range.getMin();
		final int top = range.getMax();
		BlockPos.Mutable pos = new BlockPos.Mutable(i, bottom - 1, j);
		for(int k = bottom - 1; k < top; k++)
		{
			if(k == bottom - 1)
			{
				chunk.setBlockState(pos, Blocks.MYCELIUM.getDefaultState(), false);
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
		
	}

}
