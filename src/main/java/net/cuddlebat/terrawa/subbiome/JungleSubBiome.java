package net.cuddlebat.terrawa.subbiome;

import java.util.Random;

import net.cuddlebat.terrawa.entity.ModEntities;
import net.cuddlebat.terrawa.utils.IntRange;
import net.cuddlebat.terrawa.world.feature.ModFeatures;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.EntityCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.JungleTreeFeature;

public class JungleSubBiome extends SubBiome
{

	protected JungleSubBiome(Settings settings)
	{
		super(settings);
		
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(
			ModEntities.IVY_SKELETON, 100, 2, 4));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(
			ModEntities.VENOMBITE_SPIDER, 100, 1, 2));
		this.addSpawn(EntityCategory.MONSTER, new Biome.SpawnEntry(
			ModEntities.POISON_ELEMENTAL, 50, 1, 2));
	}

	@Override
	public void decorateSubterraneanChunk(IWorld world, Chunk chunk, ChunkGenerator<?> gen, IntRange[][] heightmap, SubBiome[][] biomemap, Random rand)
	{
		int i = 1 + rand.nextInt(13); // 1-13
		int j = 1 + rand.nextInt(13); // 1-13
		BlockPos.Mutable mPos = new BlockPos.Mutable();
		BlockState log = Blocks.JUNGLE_LOG.getDefaultState();
		if(biomemap[i][j] == SubterraneanBiomes.JUNGLE)
		{
			fillColumn(heightmap[i    ][j    ], mPos.set(i    , 0, j    ), chunk, log);
			fillColumn(heightmap[i    ][j + 1], mPos.set(i    , 0, j + 1), chunk, log);
			fillColumn(heightmap[i + 1][j    ], mPos.set(i + 1, 0, j    ), chunk, log);
			fillColumn(heightmap[i + 1][j + 1], mPos.set(i + 1, 0, j + 1), chunk, log);
		}
		
		for(i = 0; i < 15; i++)
		{
			for(j = 0; j < 15; j++)
			{
				if(biomemap[i][j] != SubterraneanBiomes.JUNGLE)
					continue;
				IntRange range = heightmap[i][j];
				int leafHeight = range.difference() < 8 ? 1 : 2; // TODO better this
				mPos.set(i, range.getMax() - leafHeight - 1, j);
				
				// im too scared of reading outside the chunk
				if(chunk.getBlockState(mPos).isAir() && i != 0 && i != 15 && j != 0 && j != 15)
				{
					BlockState eState = chunk.getBlockState(mPos.offset(Direction.EAST));
					BlockState wState = chunk.getBlockState(mPos.offset(Direction.WEST));
					BlockState sState = chunk.getBlockState(mPos.offset(Direction.SOUTH));
					BlockState nState = chunk.getBlockState(mPos.offset(Direction.NORTH));
					boolean east = !eState.isAir() && eState.getBlock() != Blocks.VINE;
					boolean west = !wState.isAir() && wState.getBlock() != Blocks.VINE;
					boolean south = !sState.isAir() && sState.getBlock() != Blocks.VINE;
					boolean north = !nState.isAir() && nState.getBlock() != Blocks.VINE;
					if(east || west || south || north)
					{
						BlockState vine = Blocks.VINE.getDefaultState()
							.with(VineBlock.EAST, east)
							.with(VineBlock.WEST, west)
							.with(VineBlock.SOUTH, south)
							.with(VineBlock.NORTH, north);
						chunk.setBlockState(mPos, vine, false);
					}
				}
				
				int foliageRoll = rand.nextInt(16);
				
				if(foliageRoll == 13)
				{
					chunk.setBlockState(mPos.set(i, range.getMax() - 1, j),
						Blocks.JUNGLE_WOOD.getDefaultState(), false);
				}
				
				mPos.set(i, range.getMin(), j);
				
				if(foliageRoll == 2 && chunk.getBlockState(mPos).isAir())
				{
					chunk.setBlockState(mPos.set(i, range.getMin(), j),
						Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER), false);
					chunk.setBlockState(mPos.set(i, range.getMin() + 1, j),
						Blocks.TALL_GRASS.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), false);
				}
				
				if(foliageRoll == 7 && chunk.getBlockState(mPos).isAir())
				{
					chunk.setBlockState(mPos.set(i, range.getMin(), j),
						Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.LOWER), false);
					chunk.setBlockState(mPos.set(i, range.getMin() + 1, j),
						Blocks.LARGE_FERN.getDefaultState().with(TallPlantBlock.HALF, DoubleBlockHalf.UPPER), false);
				}
			}
		}
		
		ChunkPos cp = chunk.getPos();
		for(int k = 0; k < 8; k++)
		{
			i = 1 + rand.nextInt(14); // 1-14
			j = 1 + rand.nextInt(14); // 1-14
			
			if(biomemap[i][j] instanceof JungleSubBiome)
			{
				mPos.set(cp.x * 16 + i, heightmap[i][j].getMin() - 1, cp.z * 16 + j);
				ModFeatures.SUB_JUNGLE_BUSH.generate(world, gen, rand, mPos, FeatureConfig.DEFAULT);				
			}
		}
	}
	
	private static void fillColumn(IntRange range, BlockPos.Mutable mutablePos, Chunk chunk, BlockState state)
	{
		for (int k = range.getMin(); k < range.getMax(); k++)
		{
			mutablePos.set(mutablePos.getX(), k, mutablePos.getZ());
			chunk.setBlockState(mutablePos, state, false);
		}
	}
	
	private static void fillUpwards(int count, BlockPos.Mutable mutablePos, Chunk chunk, BlockState state)
	{
		for (int k = 0; k < count; k++)
		{
			chunk.setBlockState(mutablePos, state, false);
			mutablePos.setOffset(Direction.UP);
		}
	}

	@Override
	public void digColumn(Chunk chunk, int i, int j, IntRange range)
	{
		final int bottom = range.getMin();
		final int top = range.getMax();
		BlockPos.Mutable pos = new BlockPos.Mutable(i, bottom - 1, j);
		int leafHeight = range.difference() < 8 ? 1 : 2; // TODO better this
		for (int k = bottom - 1; k < top; k++)
		{
			if (k == bottom - 1)
			{
				chunk.setBlockState(pos, Blocks.GRASS_BLOCK.getDefaultState(), false);
			}
			else if (k >= top - leafHeight)
			{
				chunk.setBlockState(pos, Blocks.JUNGLE_LEAVES.getDefaultState()
					.with(Properties.DISTANCE_1_7, 1), false);
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
//		if(rand.nextInt(128) == 17)
//		{
//			JungleTreeFeature feat = new JungleTreeFeature(DefaultFeatureConfig::deserialize, true,4 + rand.nextInt(4),
//				Blocks.JUNGLE_LOG.getDefaultState(), Blocks.JUNGLE_LEAVES.getDefaultState(), rand.nextInt(3) == 1);
//			feat.generate(world, world.getChunkManager().getChunkGenerator(), rand,
//				new BlockPos(xRel, range.getMin(), zRel), FeatureConfig.DEFAULT);
//		}
	}

}
