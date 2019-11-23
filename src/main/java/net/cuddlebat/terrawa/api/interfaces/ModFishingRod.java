package net.cuddlebat.terrawa.api.interfaces;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.FishingRodItem;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.world.loot.LootTables;

public abstract class ModFishingRod extends FishingRodItem
{
	public static Identifier DEFAULT_LOOT_TABLE = LootTables.FISHING_GAMEPLAY;
	public static Tag<Fluid> DEFAULT_FLUID = FluidTags.WATER;
	public static Block DEFAULT_BLOCK = Blocks.WATER;
	
	public static DefaultParticleType DEFAULT_BUBBLE = ParticleTypes.BUBBLE;
	public static DefaultParticleType DEFAULT_FISHING = ParticleTypes.FISHING;
	public static DefaultParticleType DEFAULT_SPLASH = ParticleTypes.SPLASH;
	
	public static Identifier DEFAULT_BOBBER_TEXTURE = new Identifier("textures/entity/fishing_hook.png");
	
	public ModFishingRod(Settings settings)
	{
		super(settings);
	}
	
	public abstract Identifier getLootTable();
	public abstract Tag<Fluid> getFluid();
	public abstract Block getFluidBlock();
	
	public abstract DefaultParticleType getBubbleParticle();
	public abstract DefaultParticleType getFishingParticle();
	public abstract DefaultParticleType getSplashParticle();

	public abstract Identifier getBobberTexture();
}
