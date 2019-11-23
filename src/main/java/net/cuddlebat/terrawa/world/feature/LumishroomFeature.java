package net.cuddlebat.terrawa.world.feature;

import com.mojang.datafixers.Dynamic;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MutableIntBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

public class LumishroomFeature extends Feature<DefaultFeatureConfig>
{
	public static enum Shape
	{
		FLAT,
		BOWL,
		CUP
	}
	
	private final BlockState cap;
	private final BlockState stem;
	private final Shape shape;

	public LumishroomFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configFunc, BlockState stem, BlockState cap, Shape shape)
	{
		super(configFunc, false);
		
		this.shape = shape;
		this.stem = stem;
		this.cap = cap;
	}

	@Override
	public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> gen, Random rand,
		BlockPos pos, DefaultFeatureConfig config)
	{
		if(world.getBlockState(pos).getBlock() != Blocks.MYCELIUM)
			return false;
		
		pos = pos.up();
		int height = rand.nextInt(3) + 4;
		BlockPos.Mutable mPos = new BlockPos.Mutable();
		
		for(int yRel = 0; yRel < height; yRel++)
		{
			mPos.set(pos.getX(), pos.getY() + yRel, pos.getZ());
			this.setBlockState(world, mPos, stem);
		}
		
		switch(shape)
		{
		case CUP:
			generateCupCap(world, pos, mPos, height, rand);
			break;
		case BOWL:
			generateBowlCap(world, pos, mPos, height, rand);
			break;
		case FLAT:
			generateFlatCap(world, pos, mPos, height, rand);
			break;
		}
		
		return true;
	}

	private void generateCupCap(IWorld world, BlockPos pos, BlockPos.Mutable mPos, int height, Random rand)
	{
		for(int i = 0; i < 75; i++)
		{
			int xRel = -2 + (i % 5);
			int zRel = -2 + (i / 5) % 5;
			int yRel = i / 25;
			
			if ((xRel*xRel == 4 && yRel*yRel != 4 && zRel*zRel != 4) ||
				(xRel*xRel != 4 && yRel*yRel == 4 && zRel*zRel != 4) ||
				(xRel*xRel != 4 && yRel*yRel != 4 && zRel*zRel == 4))
			{
				mPos.set(pos.getX() + xRel, pos.getY() + yRel + height - 2, pos.getZ() + zRel);
				this.setBlockState(world, mPos, cap);
			}
		}
	}

	private void generateFlatCap(IWorld world, BlockPos pos, Mutable mPos, int height, Random rand)
	{
		for(int i = 0; i < 49; i++)
		{
			int xRel = -3 + i % 7;
			int zRel = -3 + i / 7;
			
			if(xRel*xRel != 9 || zRel*zRel != 9)
			{
				mPos.set(pos.getX() + xRel, pos.getY() + height, pos.getZ() + zRel);
				this.setBlockState(world, mPos, cap);
			}
		}
	}

	private void generateBowlCap(IWorld world, BlockPos pos, Mutable mPos, int height, Random rand)
	{
		for(int i = 0; i < 49; i++)
		{
			int xRel = -3 + i % 7;
			int zRel = -3 + i / 7;
			int yRel = (xRel*xRel + zRel*zRel > 7) ? -1 : 0;
			
			if(xRel*xRel != 9 || zRel*zRel != 9)
			{
				mPos.set(pos.getX() + xRel, pos.getY() + yRel + height, pos.getZ() + zRel);
				this.setBlockState(world, mPos, cap);
			}
		}
	}
}
