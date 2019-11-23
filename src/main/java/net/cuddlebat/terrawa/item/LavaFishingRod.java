package net.cuddlebat.terrawa.item;

import net.cuddlebat.terrawa.Const;
import net.cuddlebat.terrawa.api.interfaces.ModFishingRod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public class LavaFishingRod extends ModFishingRod
{
	public LavaFishingRod(Settings settings)
	{
		super(settings);
	}

	@Override
	public Identifier getLootTable()
	{
		return new Identifier(Const.MODID, "gameplay/fishing_lava");
	}

	@Override
	public Tag<Fluid> getFluid()
	{
		return FluidTags.LAVA;
	}

	@Override
	public Block getFluidBlock()
	{
		return Blocks.LAVA;
	}

	@Override
	public DefaultParticleType getBubbleParticle()
	{
		return ParticleTypes.FLAME;
	}

	@Override
	public DefaultParticleType getFishingParticle()
	{
		return ParticleTypes.FLAME;
	}

	@Override
	public DefaultParticleType getSplashParticle()
	{
		return ParticleTypes.FLAME;
	}

	@Override
	public Identifier getBobberTexture()
	{
		return new Identifier(Const.MODID, "textures/entity/lava_fishing_hook.png");
	}

}
