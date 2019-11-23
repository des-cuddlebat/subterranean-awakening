package net.cuddlebat.terrawa.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class SpikeFeature extends Feature<DefaultFeatureConfig>
{
	private final BlockState BLOCK;
	
	public SpikeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> cfg, BlockState state)
	{
		super(cfg);
		BLOCK = state;
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGen, Random rand,
		BlockPos pos, DefaultFeatureConfig cfg)
	{
		BlockPos start = pos;
		BlockPos end = pos.add(
			rand.nextInt(3) - 1 + 5 * (rand.nextInt(3) - 1),
			rand.nextInt(5) + 5,
			rand.nextInt(3) - 1 + 5 * (rand.nextInt(3) - 1));
		boolean downwards = world.isAir(start.down()) && !world.isAir(start.up());
		BlockPos.Mutable mutablePos = new BlockPos.Mutable();
		for(int y = start.getY(); y < end.getY(); y++)
		{
			float progress = ((float)y - start.getY()) / (end.getY() - start.getY());
			float xForY = end.getX() * progress + start.getX() * (1 - progress);
			float zForY = end.getZ() * progress + start.getZ() * (1 - progress);
			int xForYi = (int) xForY;
			int zForYi = (int) zForY;
			double xDelta;
			double zDelta;
			for(int x = -4; x <= 4; x++)
			{
				for(int z = -4; z <= 4; z++)
				{
					mutablePos.set(xForYi + x, downwards ? 2 * start.getY() - y : y, zForYi + z);
					if(!world.isAir(mutablePos))
						continue;
					xDelta = mutablePos.getX() - xForY;
					zDelta = mutablePos.getZ() - zForY;
					if(xDelta * xDelta + zDelta * zDelta < 8.0f * (1 - progress) * (1 - progress))
					{
						this.setBlockState(world, mutablePos, BLOCK);
					}
				}
			}
		}
		return true;
	}
}
