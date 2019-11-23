package net.cuddlebat.terrawa.cardinal;

import java.util.Optional;

import net.cuddlebat.terrawa.api.interfaces.ModFishingRod;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.tag.FluidTags;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.loot.LootTables;

public class FishingComponent implements IFishingComponent
{
	private Optional<ModFishingRod> rod = Optional.empty();

	@Override
	public Optional<ModFishingRod> maybeGetRod()
	{
		return rod;
	}

	@Override
	public Identifier getLootTable()
	{
		return rod.isPresent() ? rod.get().getLootTable() : LootTables.FISHING_GAMEPLAY;
	}

	@Override
	public Tag<Fluid> getFluid()
	{
		return rod.isPresent() ? rod.get().getFluid() : FluidTags.WATER;
	}

	@Override
	public Block getFluidBlock()
	{
		return rod.isPresent() ? rod.get().getFluidBlock() : Blocks.WATER;
	}

	public void setRod(ModFishingRod rod)
	{
		this.rod = Optional.of(rod);
	}

	@Override
	public void fromTag(CompoundTag tag)
	{
		CompoundTag comp = tag.getCompound("terrawa_fishing");
		if(comp.containsKey("rod"))
			rod = Optional.of((ModFishingRod) Registry.ITEM.get(new Identifier(comp.getString("rod"))));
	}

	@Override
	public CompoundTag toTag(CompoundTag tag)
	{
		CompoundTag comp = new CompoundTag();
		if(rod.isPresent())
			comp.putString("rod", Registry.ITEM.getId(rod.get()).toString());
		tag.put("terrawa_fishing", comp);
		return tag;
	}

	@Override
	public DefaultParticleType getBubbleParticle()
	{
		return rod.isPresent() ? rod.get().getBubbleParticle() : ParticleTypes.BUBBLE;
	}

	@Override
	public DefaultParticleType getFishingParticle()
	{
		return rod.isPresent() ? rod.get().getFishingParticle() : ParticleTypes.FISHING;
	}

	@Override
	public DefaultParticleType getSplashParticle()
	{
		return rod.isPresent() ? rod.get().getSplashParticle() : ParticleTypes.SPLASH;
	}

}
