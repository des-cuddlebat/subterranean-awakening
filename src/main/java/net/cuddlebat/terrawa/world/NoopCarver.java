package net.cuddlebat.terrawa.world;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.CaveCarver;

public class NoopCarver extends CaveCarver
{
	public NoopCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> function_1, int int_1)
	{
		super(function_1, int_1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean carve(Chunk var1, Random var2, int var3, int var4, int var5, int var6, int var7, BitSet var8,
		ProbabilityConfig var9)
	{
		return false;
	}

	@Override
	public boolean shouldCarve(Random var1, int var2, int var3, ProbabilityConfig var4)
	{
		return false;
	}
}
