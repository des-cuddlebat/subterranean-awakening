package net.cuddlebat.terrawa.cardinal;

import java.util.Optional;

import nerdhub.cardinal.components.api.component.Component;
import net.cuddlebat.terrawa.api.interfaces.ModFishingRod;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;

public interface IFishingComponent extends Component
{
	public Optional<ModFishingRod> maybeGetRod();
	public Identifier getLootTable();
	public Tag<Fluid> getFluid();
	Block getFluidBlock();
	
	public DefaultParticleType getBubbleParticle();
	public DefaultParticleType getFishingParticle();
	public DefaultParticleType getSplashParticle();
}
