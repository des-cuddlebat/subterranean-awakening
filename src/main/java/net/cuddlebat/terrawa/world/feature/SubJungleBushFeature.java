package net.cuddlebat.terrawa.world.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SubJungleBushFeature extends AbstractTreeFeature<DefaultFeatureConfig>
{
	private final BlockState leaves;
	private final BlockState log;

	public SubJungleBushFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function_1,
		BlockState blockState_1, BlockState blockState_2)
	{
		super(function_1, false);
		this.log = blockState_1;
		this.leaves = blockState_2;
	}

	public boolean generate(Set<BlockPos> blockSet, ModifiableTestableWorld world, Random rand,
		BlockPos pos, MutableIntBoundingBox box)
	{
		if (isNaturalDirtOrGrass(world, pos))
		{
			pos = pos.up();
			BlockPos.Mutable mutablePos = new BlockPos.Mutable();
			this.setBlockState(blockSet, world, pos, this.log, box);

			for (int y = pos.getY(); y <= pos.getY() + 2; ++y)
			{
				int yRel = y - pos.getY();
				int thickness = 2 - yRel;

				for (int x = pos.getX() - thickness; x <= pos.getX() + thickness; ++x)
				{
					int xRel = x - pos.getX();

					for (int z = pos.getZ() - thickness; z <= pos.getZ() + thickness; ++z)
					{
						int zRel = z - pos.getZ();
						if (Math.abs(xRel) != thickness || Math.abs(zRel) != thickness || rand.nextInt(2) != 0)
						{
							mutablePos.set(x, y, z);
							if (isAirOrLeaves(world, mutablePos))
							{
								this.setBlockState(blockSet, world, mutablePos, this.leaves, box);
							}
						}
					}
				}
			}
		}

		return true;
	}
}
